(ns health-crud.views.patients
  (:require [health-crud.views.layout :as layout]
            [hiccup.core :refer [h]]
            [hiccup.form :as form]
            [hiccup.util :as util]
            [ring.util.anti-forgery :as anti-forgery]
            [clojure.string :as str]))

(defn stringify-curr-val [val] (str "Current value: " val))

(defn stringify-date [date]
  (first (str/split (str date) #" ")))

(defn display-errors [errors]
  (map (fn [error] [:div {:class "alert alert-danger" :role "alert"} error]) errors))

(defn display-info [info]
  (map (fn [message] [:div {:class "alert alert-primary" :role "alert"} message]) info))

(defn patient-new-form [errors]
  [:div {:id "patient-new-form"}
   (display-errors errors)
   (form/form-to [:post "/patients"]
                 (anti-forgery/anti-forgery-field)
                 [:h4 "Create new patient"]
                 [:div {:class "form-group"}
                  (form/text-area {:class "form-control" :placeholder "First name" :required true} "first_name")]
                 [:div {:class "form-group"}
                  (form/text-area {:class "form-control" :placeholder "Second name" :required true} "second_name")]
                 [:div {:class "form-group"}
                  [:select {:name "gender" :class "form-control" :required true}
                   [:option {:value "M"} "Male"]
                   [:option {:value "F"} "Female"]]]
                 [:div {:class "form-group"}
                  [:input {:name "birth_date" :type "date" :required true :class "form-control" :placeholder "Birth date"}]]
                 [:div {:class "form-group"}
                  (form/text-area {:class "form-control" :placeholder "Adress" :required true} "adress")]
                 [:div {:class "form-group"}
                  (form/text-area {:class "form-control" :placeholder "CHI" :required true} "chi_number")]
                 (form/submit-button {:class "btn btn-success btn-lg"} "Create"))])

(defn patient-show-form [patient errors]
  [:div {:id "patient-show-form"}
   (display-errors errors)
   (form/form-to [:patch (util/url "/patients/" (:id patient))]
                 (anti-forgery/anti-forgery-field)
                 [:h4 "Update patient"]
                 [:div {:class "form-group"}
                  [:span {:class "badge badge-primary"} (stringify-curr-val (:first_name patient))]
                  (form/text-area {:class "form-control" :placeholder "First name" :required true} "first_name" (:first_name patient))]
                 [:div {:class "form-group"}
                  [:span {:class "badge badge-primary"} (stringify-curr-val (:second_name patient))]
                  (form/text-area {:class "form-control" :placeholder "Second name" :required true} "second_name" (:second_name patient))]
                 [:div {:class "form-group"}
                  [:span (stringify-curr-val (:gender patient))]
                  [:select {:name "gender" :class "form-control" :required true}
                   [:option {:value "M"} "Male"]
                   [:option {:value "F"} "Female"]]]
                 [:div {:class "form-group"}
                  [:span {:class "badge badge-primary"} (stringify-curr-val (:birth_date patient))]
                  [:input {:name "birth_date" :type "date" :required true :class "form-control" :placeholder "Birth date" :value (stringify-date (:birth_date patient))}]]
                 [:div {:class "form-group"}
                  [:span {:class "badge badge-primary"} (stringify-curr-val (:adress patient))]
                  (form/text-area {:class "form-control" :placeholder "Adress" :required true} "adress" (:adress patient))]
                 [:div {:class "form-group"}
                  [:span {:class "badge badge-primary"} (stringify-curr-val (:chi_number patient))]
                  (form/text-area {:class "form-control" :placeholder "CHI" :required true} "chi_number" (:chi_number patient))]
                 (form/submit-button {:class "btn btn-success btn-lg"} "Update"))])

(defn display-patients [patients info]
  [:table {:class "table"}
   [:h2 "Patients"]
   (display-info info)
   [:thead
    [:th {:scope "col"} "#"]
    [:th {:scope "col"} "First name"]
    [:th {:scope "col"} "Second name"]
    [:th {:scope "col"} "Birth date"]
    [:th {:scope "col"} "Gender"]
    [:th {:scope "col"} "Actions"]]
   [:tbody
    (map
     (fn [patient]
       [:tr
        [:td (h (:id patient))]
        [:td (h (:first_name patient))]
        [:td (h (:second_name patient))]
        [:td (:birth_date patient)]
        [:td (h (:gender patient))]
        [:td [:div {:class "btn-group"}
              [:a {:class "btn btn-primary" :href (util/url "/patients/" (:id patient) "/edit")} "Update"]
              (form/form-to [:delete (util/url "/patients/" (:id patient))]
                            (anti-forgery/anti-forgery-field)
                            (form/submit-button {:class "btn btn-danger"} "Delete"))]]])
     patients)]])

(defn index [patients info]
  (layout/common "Health-crud"
                 (display-patients patients info)))

(defn new-patient [errors]
  (layout/common "Create patient" (patient-new-form errors)))

(defn show-patient [patient errors]
  (layout/common "Update patient" (patient-show-form patient errors)))
