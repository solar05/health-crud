(ns health-crud.models.patient
  (:require [clojure.java.jdbc :as sql]))

(def spec (or (System/getenv "DATABASE_URL") "postgresql://localhost:5432/patients"))

(defn now []
  (java.time.LocalDateTime/now))

(defn all [db]
  (let [conn (if (empty? db) spec db)]
    (vec (sql/query conn ["select * from patients where deleted_at IS NULL order by id desc"]))))

(defn paginate [db patients-page num-per-patient]
  (let [conn (if (empty? db) spec db)]
    (vec (sql/query conn
                    [(str "select * from (select * from patients where deleted_at IS NULL) AS patients order by id desc "
                          "limit " num-per-patient "offset " (Math/abs (* num-per-patient patients-page)))]))))

(defn prepare-date [date]
  (java.sql.Timestamp/valueOf (str date " 00:00:00")))

(defn create [db patient]
  (let [conn (if (empty? db) spec db)]
    (sql/insert! conn :patients [:first_name :second_name :birth_date :adress :gender :chi_number]
                 [(:first_name patient)
                  (:second_name patient)
                  (prepare-date (:birth_date patient))
                  (:adress patient)
                  (:gender patient)
                  (:chi_number patient)])))

(defn delete [db patient]
  (let [current-time (now)
        conn (if (empty? db) spec db)]
    (sql/update! conn :patients
                 {:updated_at current-time :deleted_at current-time}
                 ["id = ?" patient])))

(defn get-patient [db id]
  (let [conn (if (empty? db) spec db)]
    (sql/get-by-id conn :patients id)))

(defn update-patient [db id patient]
  (let [conn (if (empty? db) spec db)]
    (sql/update! conn :patients
                 {:updated_at (now)
                  :first_name (:first_name patient)
                  :second_name (:second_name patient)
                  :birth_date (prepare-date (:birth_date patient))
                  :adress (:adress patient)
                  :gender (:gender patient)
                  :chi_number (:chi_number patient)}
                 ["id = ?" id])))
