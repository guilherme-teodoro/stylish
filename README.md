# stylish

A better way to write css without selectors using Garden css compile.

[![Clojars Project](https://img.shields.io/clojars/v/stylish.svg)](https://clojars.org/stylish)

## Example

```clojure
(ns stylish.example
  (:require [stylish.core :as stylish]
            [garden.units :as units]))

(defn style
  [color]
  [{:font-size (units/px 40)
    :background color}])

(def button-style
  [{:color :yellow}
   [:&:hover {:color :blue}]])

(defn show
  []
  [:div {:class (stylish/render (style-1 color))} ;; stylish_example-style-1
   [:button {:class (stylish/render style-2)}]]) ;; stylish_example-button-style-1

```
