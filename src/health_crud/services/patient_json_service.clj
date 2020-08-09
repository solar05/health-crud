(ns health-crud.services.patient-json-service
  (:require [clojure.data.json :as json]
            [clojure.string :as str]))

(extend-type java.sql.Timestamp
  json/JSONWriter
  (-write [date out]
    (json/-write (first (str/split (str date) #" ")) out)))

(defn write-json [patient]
  (str (json/write-str patient)))
