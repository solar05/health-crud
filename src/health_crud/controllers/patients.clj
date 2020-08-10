(ns health-crud.controllers.patients
  (:require [compojure.core :refer [defroutes GET POST DELETE PATCH]]
            [clojure.string :as str]
            [ring.util.response :as ring]
            [health-crud.views.patients :as view]
            [health-crud.models.patient :as model]
            [health-crud.services.patient-validator :as validator]
            [ring.handler.dump :as handler]
            [health-crud.services.patient-json-service :as json]
            [health-crud.services.patient-params-extractor :as extractor]))

(defn index [params]
  (if (contains? params :page)
    (let [page (Math/abs (Integer/parseInt (:page params)))]
      {:status 200
       :headers {"Content-Type" "text/json"}
       :body (json/write-json (model/paginate [] page 5))})
    {:status 200
     :headers {"Content-Type" "text/json"}
     :body (json/write-json (model/paginate [] 0 5))}))

(defn new-patient []
  (view/new-patient []))

(defn create [request]
  (let [post-errors (validator/validate-patient-params (:body request))]
    (if (empty? post-errors)
      (let [patient (extractor/extract-patient (:body request))
            errors (validator/validate-patient patient)]
        (if (empty? errors)
          (do
            (model/create [] patient)
            {:status 201
             :headers {"Content-Type" "text/json"}
             :body (json/write-json patient)})
          {:status 422
           :headers {"Content-Type" "text/json"}
           :body (json/write-json errors)}))
      {:status 400
       :headers {"Content-Type" "text/json"}
       :body (json/write-json post-errors)})))

(defn show-patient [id]
  (when-not (str/blank? id)
    (let [patient (model/get-patient [] (Integer/parseInt id))]
      (if (nil? patient)
        {:status 404}
        {:status 200
         :headers {"Content-Type" "text/json"}
         :body (json/write-json patient)}))))

(defn update-patient [id request]
  (let [patient (extractor/extract-patient (:body request))
        errors (validator/validate-patient patient)
        patient_id (Integer/parseInt id)]
    (if (empty? errors)
      (do
        (model/update-patient [] patient_id patient)
        {:status 200
         :headers {"Content-Type" "text/json"}
         :body (json/write-json patient)})
      {:status 422
       :headers {"Content-Type" "text/json"}
       :body (json/write-json errors)})))

(defn delete-patient [id]
  (when-not (str/blank? id)
    (model/delete [] (Integer/parseInt id))
    {:status 200
     :headers {"Content-Type" "text/json"}
     :body (json/write-json {:id id})}))

(defn health []
  {:status 200 :headers {"Content-Type" "text/json"}})

(defroutes routes
  (GET "/health" [] (health))
  (GET  "/patients" [& params] (index params))
  (GET "/patients/new" [] (new-patient))
  (GET "/patients/:id/edit" [id] (show-patient id))
  (PATCH "/patients/:id" params (update-patient (:id (:params params)) params))
  (POST "/patients" patient (create patient))
  (DELETE "/patients/:id" [id] (delete-patient id)))
