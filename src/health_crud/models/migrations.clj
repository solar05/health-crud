(ns health-crud.models.migrations
  (:require [clojure.java.jdbc :as sql]
            [health-crud.models.patient :as patient]))

(defn migrated? []
  (-> (sql/query patient/spec
                 [(str "select count(*) from information_schema.tables "
                       "where table_name='patients'")])
      first :count pos?))

(defn migrate []
  (when (not (migrated?))
    (println "Migrating database...") (flush)
    (sql/db-do-commands patient/spec
                        (sql/create-table-ddl
                         :patients
                         [[:id :serial "PRIMARY KEY"]
                          [:first_name :varchar "NOT NULL"]
                          [:second_name :varchar "NOT NULL"]
                          [:gender :varchar "NOT NULL"]
                          [:birth_date :timestamp "NOT NULL"]
                          [:adress :varchar "NOT NULL"]
                          [:chi_number :varchar "NOT NULL"]
                          [:created_at :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"]
                          [:updated_at :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"]
                          [:deleted_at :timestamp]]))
    (println "Done!")))
