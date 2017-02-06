(set-env!
  :dependencies '[[org.clojure/clojure "1.9.0-alpha14"]
                  [org.clojure/clojurescript "1.9.293"]
                  [garden "1.3.2"]
                  [adzerk/boot-cljs "1.7.228-2" :scope "test"]]
  :resource-paths #{"src"})

(task-options!
 pom {:project 'stylish
      :version "0.0.1-SNAPSHOT"
      :description "A clojurescript way to write css without selectors"
      :url "https://github.com/guilherme-teodoro/stylish"
      :scm {:url "https://github.com/guilherme-teodoro/stylish"}
      :license {"Eclipse Public License"
                "http://www.eclipse.org/legal/epl-v10.html"}}
 push {:repo "deploy-clojars"})

(deftask local-install []
  (comp
    (pom)
    (jar)
    (install)))

(deftask release []
  (merge-env!
    :repositories [["deploy-clojars" {:url "https://clojars.org/repo"
                                      :username (System/getenv "CLOJARS_USER")
                                      :password (System/getenv "CLOJARS_PASS")}]])
  (comp
    (local-install)
    (sift :include [#".*\.jar"])
    (push)))
