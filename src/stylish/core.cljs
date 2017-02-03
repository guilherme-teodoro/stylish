(ns stylish.core
  (:require [clojure.string :as string]
            [garden.core :as garden])
  (:require-macros [stylish.core]))

(def ^:private style-refs
  (atom {}))

(def ^:private dom-refs
  (atom {}))

(def ^:private argument-id-index
  (atom {:id 0}))

(def ^:private debug?
  js/goog.DEBUG)

(defn- mounted-root!
  []
  (let [style-tag "app-styles"]
    (if-let [root (.getElementById js/document style-tag)]
      root
      (.appendChild (.-head js/document)
                    (doto (.createElement js/document "style") (.setAttribute "id" style-tag))))))

(defn- name-for
  [args]
  (let [result (swap! argument-id-index
                      (fn [db]
                        (if (contains? db args)
                          db
                          (let [id (:id db)]
                            (assoc db args id :id (inc id))))))]
    (get result args)))

(defn mk-classname
  [ns fn-name & args]
  (let [prefix (string/replace (str ns) "." "_")
        id (name-for args)]
    (str prefix "_" fn-name "_" id)))

(defn build-rules
  [class style]
  (vec (cons (keyword (str "." class)) style)))

(defn- update-ref
  [state class rules]
  (assoc state :new? (not (contains? state class)) class rules))

(defn- update-dom
  [state class curr-node]
  (assoc state :prev-node (get state class) class curr-node))

(defn- insert-rules!
  [root rules]
  (let [css (garden/css {:pretty-print? debug?} rules)
        node (.createTextNode js/document css)]
    (.appendChild root node)))

(defn- update-rules!
  [root class rules]
  (let [node (insert-rules! root rules)]
    (let [{:keys [prev-node]} (swap! dom-refs update-dom class node)]
      (when (and prev-node (.contains root prev-node))
        (.removeChild root prev-node)))))

(defn insert-style!
  [class rules]
  (let [{:keys [new?]} (swap! style-refs update-ref class rules)]
    (if debug?
      (update-rules! (mounted-root!) class rules)
      (when new?
        (insert-rules! (mounted-root!) rules)))))
