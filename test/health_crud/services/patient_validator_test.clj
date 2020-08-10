(ns health-crud.services.patient-validator-test
  (:require [health-crud.services.patient-validator :as validator]
            [health-crud.fixture.patient :as fixture]
            [clojure.test :refer :all]))

(deftest validate-patient-params-test
  (testing "Params are valid"
    (is (true? (empty? (validator/validate-patient-params fixture/valid-patient-data)))))
  (testing "Params are invalid"
    (is (false? (empty? (validator/validate-patient-params fixture/invalid-params))))))

(deftest validate-patient-data-test
  (testing "Patient data is valid"
    (is (true? (empty? (validator/validate-patient fixture/valid-patient-data)))))
  (testing "Patient data is invalid"
    (is (false? (empty? (validator/validate-patient fixture/invalid-patient-data))))))
