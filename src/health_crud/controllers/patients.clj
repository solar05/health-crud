(ns health-crud.controllers.patients
  (:require [compojure.core :refer [defroutes GET POST DELETE PATCH]]
            [clojure.string :as str]
            [ring.util.response :as ring]
            [ring.handler.dump :as dump]
            [health-crud.views.patients :as view]
            [health-crud.models.patient :as model]
            [health-crud.services.patient-validator :as validator]
            [health-crud.services.patient-params-extractor :as extractor]))

(defn index [params]
  (dump/handle-dump params)
  (view/index (model/all) []))

(defn new-patient []
  (view/new-patient []))

(defn create [request]
  (dump/handle-dump (:params request))
  (let [patient (extractor/extract-patient (:params request))
        errors (validator/validate patient)]
    (if (empty? errors)
      (do
        (model/create patient)
        (view/index (model/all) ["Patient successfully created!"]))
      (view/new-patient errors))))

(defn show-patient [id]
  (when-not (str/blank? id)
    (view/show-patient (model/get-patient (Integer/parseInt id)) [])))

(defn update-patient [id, request]
  (dump/handle-dump request)
  (let [patient (extractor/extract-patient request)
        patient_id (Integer/parseInt id)
        errors (validator/validate patient)]
    (if (empty? errors)
      (do
        (model/update-patient patient_id patient)
        (view/index (model/all) ["Patient successfully updated!"]))
      (view/show-patient patient errors))))

(defn delete-patient [id]
  (when-not (str/blank? id)
    (println id)
    (model/delete (Integer/parseInt id))
    (ring/redirect "/patients")))

(defroutes routes
  (GET "/" [] (ring/redirect "/patients"))
  (GET  "/patients" [& params] (index params))
  (GET "/patients/new" [] (new-patient))
  (GET "/patients/:id/edit" [id] (show-patient id))
  (PATCH "/patients/:id" [& params] (update-patient (:id params) params))
  (POST "/patients" patient (create patient))
  (DELETE "/patients/:id" [id] (delete-patient id)))
