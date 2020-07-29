(ns health-crud.core
  (:gen-class)
  (:require [ring.adapter.jetty :as ring]
            [compojure.core :refer [POST defroutes]]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.flash :refer [wrap-flash]]
            [health-crud.controllers.patients :as patients]
            [health-crud.views.layout :as layout]
            [health-crud.models.migrations :as schema]))

(defroutes routes
  patients/routes
  (route/resources "/")
  (route/not-found (layout/four-oh-four)))

(def app (wrap-defaults routes site-defaults))

(defn start [port]
  (ring/run-jetty app {:port port :join? false}))

(defn -main []
  (schema/migrate)
  (let [port (Integer/parseInt (or (System/getenv "PORT") "4000"))]
    (start port)))


