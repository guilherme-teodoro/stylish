# stylish

A better way to write CSS without selectors using [Garden](https://github.com/noprompt/garden) compile.

[![Clojars Project](https://img.shields.io/clojars/v/stylish.svg)](https://clojars.org/stylish)

## Installation

Add to project.clj: [stylish "0.1.0-SNAPSHOT"]

## How it works

The `render` a given style ruleset, returning the produced an unique CSS class name.

## Syntax 

Garden Syntax [wiki](https://github.com/noprompt/garden/wiki/Syntax)

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
  [:div {:class (stylish/render (style color))} ;; return: stylish_example-style-1
   [:button {:class (stylish/render button-style)}]]) ;; return: stylish_example-button-style-1
```

## Contributors

- [Marcelo Nomoto](https://github.com/mynomoto)
- [Andrew Rosa](https://github.com/andrewhr)

## Thanks

[Garden](https://github.com/noprompt/garden/) for a great Clojure CSS compiler

## License

Copyright © 2017 Guilherme Teodoro

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
