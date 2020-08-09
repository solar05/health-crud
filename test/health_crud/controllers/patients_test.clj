(ns health-crud.controllers.patients-test
  (:require [health-crud.fixture.patient :as fixture]
            [health-crud.core :refer [app]]
            [clojure.test :refer :all]))

(deftest index-test
  (testing "Index action."
    (let [response (app {:uri "/patients" :request-method :get})]
      (is (= 200 (:status response)))
      (is (true? (seq (:body response)))))))

