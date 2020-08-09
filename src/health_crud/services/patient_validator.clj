(ns health-crud.services.patient-validator
  (:require [clojure.spec.alpha :as s]
            [clojure.set :as set]))

(def validations-map
  {:first_name (fn [name] (s/valid? ::name name))
   :second_name (fn [name] (s/valid? ::name name))
   :gender (fn [gender] (s/valid? ::gender gender))
   :birth_date (fn [birth_date] (s/valid? ::birth-date birth_date))
   :adress (fn [adress] (s/valid? ::adress adress))
   :chi_number (fn [chi] (s/valid? ::chi chi))})

(def errors-map
  {:first_name "Invalid first name!"
   :second_name "Invalid second name!"
   :gender "Invalid gender!"
   :birth_date "Invalid birth date!"
   :adress "Invalid adress!"
   :chi_number "Invalid CHI number!"})

(def fields-map
  #{:first_name :second_name :gender :birth_date :adress :chi_number})

(s/def ::ne-string (s/and string? not-empty))

(s/def ::valid-name (partial re-matches #"([a-zA-Z',.-]+( [a-zA-Z',.-]+)*){2,30}"))

(s/def ::name (s/and ::ne-string ::valid-name))

(s/def ::correct-chi-length (fn [chi] (= (count chi) 16)))

(s/def ::numeric (partial re-matches #"(^[0-9]+$)"))

(s/def ::chi (s/and ::ne-string ::correct-chi-length ::numeric))

(s/def ::correct-gender (fn [gender] (and
                                      (= (count gender) 1)
                                      (or
                                       (= gender "M")
                                       (= gender "F")))))

(s/def ::gender (s/and ::ne-string ::correct-gender))

(s/def ::correct-year (partial re-matches #"([12]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01]))"))

(s/def ::birth-date (s/and ::ne-string ::correct-year))

(s/def ::adress ::ne-string)

(s/def ::patient-fields (fn [params]
                          (= (count (set/intersection (set (keys params)) fields-map)) 6)))

(defn validate-patient-params [params]
  (if (s/valid? ::patient-fields params)
    {} (set/difference fields-map (set (keys params)))))

(defn validate-patient [params]
  (reduce-kv (fn [acc k v]
               (if ((k validations-map) v)
                 acc
                 (assoc acc k (k errors-map)))) {} params))
