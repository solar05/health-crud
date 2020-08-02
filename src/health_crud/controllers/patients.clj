(ns health-crud.controllers.patients
  (:require [compojure.core :refer [defroutes GET POST DELETE PATCH]]
            [clojure.string :as str]
            [ring.util.response :as ring]
            [health-crud.views.patients :as view]
            [health-crud.models.patient :as model]
            [health-crud.services.patient-validator :as validator]
            [health-crud.services.patient-params-extractor :as extractor]))

(defn index [params]
  (if (contains? params :page)
    (let [page (Math/abs (Integer/parseInt (:page params)))]
      (view/index (model/paginate page 5) [] page))
    (view/index (model/paginate 0 5) [] 0)))

(defn new-patient []
  (view/new-patient []))

(defn create [request]
  (let [post-errors (validator/validate-patient-params (:params request))]
    (if (empty? post-errors)
      (let [patient (extractor/extract-patient (:params request))
            errors (validator/validate-patient patient)]
        (if (empty? errors)
          (do
            (model/create [] patient)
            (view/index (model/paginate 0 5) ["Patient successfully created!"] 0))
          (view/new-patient errors)))
      (view/new-patient post-errors))))

(defn show-patient [id]
  (when-not (str/blank? id)
    (view/show-patient (model/get-patient [] (Integer/parseInt id)) [])))

(defn update-patient [id, request]
  (let [patient (extractor/extract-patient request)
        errors (validator/validate-patient patient)
        patient_id (Integer/parseInt id)]
    (if (empty? errors)
      (do
        (model/update-patient [] patient_id patient)
        (view/index (model/paginate 0 5) ["Patient successfully updated!"] 0))
      (view/show-patient patient errors))))

(defn delete-patient [id]
  (when-not (str/blank? id)
    (model/delete [] (Integer/parseInt id))
    (ring/redirect "/patients")))

(defroutes routes
  (GET "/" [] (ring/redirect "/patients"))
  (GET  "/patients" [& params] (index params))
  (GET "/patients/new" [] (new-patient))
  (GET "/patients/:id/edit" [id] (show-patient id))
  (PATCH "/patients/:id" [& params] (update-patient (:id params) params))
  (POST "/patients" patient (create patient))
  (DELETE "/patients/:id" [id] (delete-patient id)))
