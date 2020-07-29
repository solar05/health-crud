(ns health-crud.views.layout
  (:require [hiccup.page :as h]
            [hiccup.element :as elem]))

(defn common [title & body]
  (h/html5
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
    [:meta {:name "viewport" :content
            "width=device-width, initial-scale=1, maximum-scale=1"}]
    [:title title]
    (h/include-css "/bootstrap.min.css")]
   [:body
    [:nav {:id "header" :class "navbar navbar-light bg-light"}
     [:a {:class "navbar-brand mb-0 h1" :href "/patients"} "Health-crud"]
     [:a {:class "navbar-text btn btn-outline-success" :href "/patients/new"} "Create patient"]]
    [:div {:id "content" :class "container"}
     body]]))

(defn four-oh-four []
  (common "Page Not Found"
          [:div {:id "four-oh-four"}
           "The page you requested could not be found"]))