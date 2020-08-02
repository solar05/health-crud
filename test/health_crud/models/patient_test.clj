(ns health-crud.models.patient-test
  (:require [health-crud.fixture.patient :as fixture]
            [clojure.java.jdbc :as sql]
            [health-crud.models.migrations :as schema]
            [health-crud.models.patient :as model]
            [clojure.test :refer :all]))

(def test-spec
  {:subprotocol "sqlite"
   :subname ":memory:"})

(defn remove-db-fields [patient]
  (dissoc patient :updated_at :created_at :deleted_at :id :birth_date))

(deftest db-create-test
  (testing "Creating database"
    (sql/with-db-transaction [db test-spec]
      (schema/create-patients-db db)
      (is (true?
           (empty? (model/all db)))))))

(deftest patient-create-test
  (testing "Adding and listing patient"
    (sql/with-db-transaction [db test-spec]
      (schema/create-patients-db db)
      (model/create db fixture/valid-patient-data)
      (is (false?
           (empty? (model/all db)))))))

(deftest multiple-user-list-test
  (testing "Adding few patients and listing them"
    (sql/with-db-transaction [db test-spec]
      (schema/create-patients-db db)
      (model/create db fixture/valid-patient-data)
      (model/create db fixture/valid-patient-data)
      (is (true?
           (= 2 (count (model/all db))))))))

(deftest patient-fetch-test
  (testing "Fetching patient after adding"
    (sql/with-db-transaction [db test-spec]
      (schema/create-patients-db db)
      (model/create db fixture/valid-patient-data)
      (is (true?
           (=
            (dissoc fixture/valid-patient-data :birth_date)
            (remove-db-fields (model/get-patient db 1))))))))

(deftest patient-update-test
  (testing "Patient updating"
    (sql/with-db-transaction [db test-spec]
      (schema/create-patients-db db)
      (model/create db fixture/valid-patient-data)
      (model/update-patient db 1 fixture/valid-update-patient-data)
      (is (true?
           (=
            (dissoc fixture/valid-update-patient-data :birth_date)
            (remove-db-fields (model/get-patient db 1))))))))

(deftest patient-delete-test
  (testing "Adding few patients and listing them"
    (sql/with-db-transaction [db test-spec]
      (schema/create-patients-db db)
      (model/create db fixture/valid-patient-data)
      (model/delete db 1)
      (is (true?
           (empty? (model/all db)))))))