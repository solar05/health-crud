(ns health-crud.models.patient
  (:require [clojure.java.jdbc :as db]
            [honeysql.core :as sql]
            [honeysql.helpers :as h]))

(def spec (or (System/getenv "DATABASE_URL") "postgresql://localhost:5432/patients"))

(defn now []
  (java.time.LocalDateTime/now))

(defn all [db-conn]
  (let [conn (if (empty? db-conn) spec db-conn)]
    (vec (db/query conn
                   (->
                    (h/select :*)
                    (h/from :patients)
                    (h/where [:= :deleted_at nil])
                    (h/order-by [:id :desc])
                    sql/format)))))

(defn paginate [db-conn patients-page num-per-patient]
  (let [conn (if (empty? db-conn) spec db-conn)]
    (vec (db/query conn
                   (->
                    (h/select :*)
                    (h/from [(-> (h/select :*)
                                 (h/from :patients)
                                 (h/where [:= :deleted_at nil])) :patients])
                    (h/order-by [:id :desc])
                    (h/limit num-per-patient)
                    (h/offset (Math/abs (* num-per-patient patients-page)))
                    sql/format)))))

(defn prepare-date [date]
  (java.sql.Timestamp/valueOf (str date " 00:00:00")))

(defn create [db-conn patient]
  (let [conn (if (empty? db-conn) spec db-conn)]
    (db/insert! conn :patients [:first_name :second_name :birth_date :adress :gender :chi_number]
                [(:first_name patient)
                 (:second_name patient)
                 (prepare-date (:birth_date patient))
                 (:adress patient)
                 (:gender patient)
                 (:chi_number patient)])))

(defn delete [db-conn patient]
  (let [current-time (now)
        conn (if (empty? db-conn) spec db-conn)]
    (db/update! conn :patients
                {:updated_at current-time :deleted_at current-time}
                ["id = ?" patient])))

(defn get-patient [db-conn id]
  (let [conn (if (empty? db-conn) spec db-conn)]
    (db/get-by-id conn :patients id)))

(defn update-patient [db-conn id patient]
  (let [conn (if (empty? db-conn) spec db-conn)]
    (db/update! conn :patients
                {:updated_at (now)
                 :first_name (:first_name patient)
                 :second_name (:second_name patient)
                 :birth_date (prepare-date (:birth_date patient))
                 :adress (:adress patient)
                 :gender (:gender patient)
                 :chi_number (:chi_number patient)}
                ["id = ?" id])))

