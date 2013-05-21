(ns clj-aviator.core-test
  (:require [clojure.test :refer :all]
            [clj-aviator.core :refer :all])
  (:import [com.googlecode.aviator AviatorEvaluator]))

(deftest test-execute
  (testing "execute()"
    (is (= 2 (execute "1 + 1")))
    (is (= 2 (execute "1 + x" {"x" 1})))))

(deftest test-execute*
  (testing "execute*()"
    (is (= 2 (execute* "1 + x" {:x 1})))))

(deftest test-exec
  (testing "exec"
    (is (= 2 (exec "1 + x" 1)))))

(deftest test-add-func
  (testing "add-func"
    (add-func "test" #(%))
    (is (not (nil? (AviatorEvaluator/getFunction "test"))))))
