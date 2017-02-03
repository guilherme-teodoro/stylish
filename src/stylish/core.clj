(ns stylish.core)

(defn- expand-form
  [form-or-symbol]
  (if (list? form-or-symbol)
    [(first form-or-symbol) (rest form-or-symbol)]
    [form-or-symbol]))

(defmacro render
  "Render a given style ruleset, returning the produced CSS class name."
  [form-or-symbol]
  (let [[f args] (expand-form form-or-symbol)]
    `(let [{ns# :ns,
            name# :name}
           (meta (var ~f))
           class# (mk-classname ns# name# ~@args)
           rules# (build-rules class# ~form-or-symbol)]
       (insert-style! class# rules#)
       class#)))
