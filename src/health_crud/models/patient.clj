(ns health-crud.models.patient
  (:require [clojure.java.jdbc :as sql]))

(def spec (or (System/getenv "DATABASE_URL") "postgresql://localhost:5432/health-crud"))

(defn now []
  (java.time.LocalDateTime/now))

(defn all []
  (vec (sql/query spec ["select * from patients where deleted_at IS NULL order by id desc"])))

(defn prepare-date [date]
  (java.sql.Timestamp/valueOf (str date " 00:00:00")))

(defn create [patient]
  (sql/insert! spec :patients [:first_name :second_name :birth_date :adress :gender :chi_number]
               [(:first_name patient)
                (:second_name patient)
                (prepare-date (:birth_date patient))
                (:adress patient)
                (:gender patient)
                (:chi_number patient)]))

(defn delete [patient]
  (let [current-time (now)]
    (sql/update! spec :patients
                 {:updated_at current-time :deleted_at current-time}
                 ["id = ?" patient])))

(defn get-patient [id]
  (sql/get-by-id spec :patients id))

(defn update-patient [id patient]
  (sql/update! spec :patients
               {:updated_at (now)
                :first_name (:first_name patient)
                :second_name (:second_name patient)
                :birth_date (prepare-date (:birth_date patient))
                :adress (:adress patient)
                :gender (:gender patient)
                :chi_number (:chi_number patient)}
               ["id = ?" id]))
