(ns health-crud.controllers.patients-test
  (:require [health-crud.fixture.patient :as fixture]
            [health-crud.core :refer [app]]
            [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [clojure.data.json :as json]))

(deftest index-patient-test
  (testing "Index action working."
    (let [response (app {:uri "/patients" :request-method :get})]
      (is (= 200 (:status response)))
      (is (string? (not-empty (:body response)))))))

(deftest create-patient-test
  (testing "Create action working."
    (let [response (app   (mock/json-body
                           (mock/request :post "/patients")
                           fixture/valid-patient-data))]
      (is (= 201 (:status response)))
      (is (= fixture/valid-patient-data (json/read-str (:body response) :key-fn keyword)))))
  (testing "Create action errors with invalid data"
    (let [response (app   (mock/json-body
                           (mock/request :post "/patients")
                           fixture/invalid-patient-data))]
      (is (= 422 (:status response)))
      (is (= (keys fixture/invalid-patient-data) (keys (json/read-str (:body response) :key-fn keyword))))))
  (testing "Create action errors with invalid params"
    (let [response (app   (mock/json-body (mock/request :post "/patients") fixture/invalid-params))]
      (is (= 400 (:status response))))))
