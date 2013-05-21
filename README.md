# clj-aviator

Clojure wrapper for [Aviator](https://github.com/killme2008/aviator)

## Usage

```clojure

(require '(clj-aviator [core :as aviator]))

(aviator/execute "1 + 2") ; == AviatorEvaluator.execute("1 + 1")
(aviator/execute "1 + x" {"x" 1}) ; == AviatorEvaluator.execute("1 + 1", {"x" 1})
(aviator/execute* "1 + x" {:x 1}) ; == AviatorEvaluator.execute("1 + 1", {"x" 1})
(aviator/compile "1 + x") ; == AviatorEvaluator.compile("1 + 1")
(aviator/execute-expr (aviator/compile "1+x") {"x" 1})
(aviator/execute-expr* (aviator/compile "1+x") {:x 1})

(aviator/enable-trace!)
(aviator/disable-trace!)

(aviator/optimize-mode :eval)
(aviator/optimize-mode :compile)

(aviator/add-func "isOdd" #(odd? %))
(aviator/execute "isOdd(5)") ; => true

```

##TODO

`AviatorEvaluator.setTraceStream`



## License

Copyright © 2013 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
