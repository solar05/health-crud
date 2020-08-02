(ns health-crud.models.migrations
  (:require [clojure.java.jdbc :as sql]
            [health-crud.models.patient :as patient]))

(defn migrated? []
  (-> (sql/query patient/spec
                 [(str "select count(*) from information_schema.tables "
                       "where table_name='patients'")])
      first :count pos?))

(defn create-patients-db [db]
  (let [conn (if (empty? db) patient/spec db)]
    (sql/db-do-commands conn
                        (sql/create-table-ddl
                         :patients
                         [[:id :integer "PRIMARY KEY AUTOINCREMENT NOT NULL"]
                          [:first_name :varchar "NOT NULL"]
                          [:second_name :varchar "NOT NULL"]
                          [:gender :varchar "NOT NULL"]
                          [:birth_date :timestamp "NOT NULL"]
                          [:adress :varchar "NOT NULL"]
                          [:chi_number :varchar "NOT NULL"]
                          [:created_at :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"]
                          [:updated_at :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"]
                          [:deleted_at :timestamp]]))))

(defn migrate [db]
  (when-not (migrated?)
    (println "Migrating database...") (flush)
    (create-patients-db db)
    (println "Done!")))

