(ns health-crud.core-test
  (:require [clojure.test :refer :all]
            [health-crud.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 1 1))))

(deftest app-test
  (testing "Basic request to app."
    (let [response (app {:uri "/health" :request-method :get})]
      (is (= 200 (:status response))))))
