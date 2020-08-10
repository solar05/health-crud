(ns health-crud.core
  (:require [ring.adapter.jetty :as ring]
            [compojure.core :refer [POST defroutes]]
            [compojure.route :as route]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [health-crud.controllers.patients :as patients]
            [health-crud.models.migrations :as schema])
  (:gen-class))

(defroutes routes
  patients/routes
  (route/resources "/"))

(def app (-> routes wrap-json-response (wrap-json-body {:keywords? true})))

(defn start [port]
  (ring/run-jetty app {:port port :join? false}))

(defn -main []
  ;(schema/migrate [])
  (let [port (Integer/parseInt (or (System/getenv "PORT") "4000"))]
    (start port)))
