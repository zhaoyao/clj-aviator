(ns clj-aviator.core
  (:import [com.googlecode.aviator AviatorEvaluator Expression]
           [com.googlecode.aviator.runtime.function AbstractFunction]
           [com.googlecode.aviator.runtime.type AviatorLong AviatorBoolean AviatorDouble AviatorString AviatorNil AviatorRuntimeJavaType]))

(defn execute
  ([^String expr] (AviatorEvaluator/execute expr))
  ([^String expr env] (AviatorEvaluator/execute expr env)))

(defn- normalize-env
  [sym-map]
  (into {} (for [[k, v] sym-map] [(name k), v])))

(defn execute*
  [expr sym-env]
  (let [env (normalize-env sym-env)]
    (execute expr env)))

(defn exec
  [expr & params]
  (AviatorEvaluator/exec expr (object-array params)))

(defn compile*
  [^String expr]
  (AviatorEvaluator/compile expr))

(defn execute-expr
  ([^Expression expr env] (.execute expr env))
  ([^Expression expr] (.execute expr nil)))

(defn execute-expr*
  ([^Expression expr env] (.execute expr (normalize-env env)))
  ([^Expression expr] (.execute expr nil)))

(defmulti wrap-clj-val type)
(defmethod wrap-clj-val nil [val] AviatorNil/NIL)
(defmethod wrap-clj-val java.lang.Long [val] (new AviatorLong val))
(defmethod wrap-clj-val java.lang.String [val] (new AviatorString val))
(defmethod wrap-clj-val java.lang.Double [val] (new AviatorDouble val))
(defmethod wrap-clj-val java.lang.Boolean [val] (if val AviatorBoolean/TRUE AviatorBoolean/FALSE))
(defmethod wrap-clj-val java.lang.Object [val] (if val AviatorRuntimeJavaType val))

(defn add-func
  [func-name f]
  (let [aviator-func (proxy [AbstractFunction] []
                       (call [env & args]
                         (let [ret (apply f (map #(.getValue % env) args))]
                           (wrap-clj-val ret)))
                       (getName [] func-name))]
    (AviatorEvaluator/addFunction aviator-func)))

(defn enable-trace!
  []
  (AviatorEvaluator/setTrace true))

(defn disable-trace!
  []
  (AviatorEvaluator/setTrace false))

(defn optimize-mode
  [mode]
  (case mode
    :eval (do (AviatorEvaluator/setOptimize AviatorEvaluator/EVAL) true)
    :compile (do (AviatorEvaluator/setOptimize AviatorEvaluator/COMPILE) true)
    false))
