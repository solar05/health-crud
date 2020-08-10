(ns health-crud.fixture.patient
  (:require [health-crud.models.patient :as model]))

(def invalid-params {:test "test"})

(def valid-patient-data
  {:first_name "testt"
   :second_name "testt"
   :gender "M"
   :birth_date "2020-07-11"
   :adress "asdas"
   :chi_number "1111111111111111"})

(def valid-update-patient-data
  {:first_name "one"
   :second_name "two"
   :gender "F"
   :birth_date "2010-02-10"
   :adress "rewq"
   :chi_number "222222222222222"})

(def invalid-patient-data
  {:first_name "1321"
   :second_name "343"
   :gender "T"
   :birth_date "202007-11"
   :adress ""
   :chi_number "1111"})
