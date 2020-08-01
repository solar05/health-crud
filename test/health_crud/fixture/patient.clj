(ns health-crud.fixture.patient)

(def invalid-params {:test "test"})

(def valid-patient-data
  {:first_name "test"
   :second_name "test"
   :gender "M"
   :birth_date "2020-07-11"
   :adress "asdas"
   :chi_number "1111111111111111"})

(def invalid-patient-data
  {:first_name "1321"
   :second_name "343"
   :gender "T"
   :birth_date "2020-07-11"
   :adress "asdas"
   :chi_number "1111"})