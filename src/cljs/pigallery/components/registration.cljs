(ns pigallery.components.registration
  (:require [reagent.core :refer [atom]]
            [reagent.session :as session]
            [pigallery.components.common :as c]
            [ajax.core :as ajax]
            [pigallery.validation :refer [registration-errors]]))

(defn register! [fields errors]
  (reset! errors (registration-errors @fields))
  (js/console.log (pr-str @errors))
  (js/console.log (pr-str @fields))
  (when-not @errors
    (ajax/POST "/register"
        {:params @fields
         :handler #(do
                     (session/put! :identity (:id @fields))
                     (reset! fields {}))
         :error-handler #(reset! errors {:server-error (get-in % [:response :message])})})))

(defn registration-form []
  (let [fields (atom {})
        error (atom nil)]
    (fn []
      [c/modal
       [:div "Pigallery Registration"]
       [:div
        [:div.well.well-sm
         [:strong "* required field"]]
        [c/text-input "name" :id "enter a username" fields]
        [c/password-input "password" :pass "enter a password" fields]
        [c/password-input "password" :pass-confirm "re-enter the password" fields]
        (when-let [error (:server-error @error)]
          [:div.alert.alert-danger error])]
       [:div
        [:button.btn.btn-primary
         {:on-click #(register! fields error)}
         "Register"]
        [:button.btn.btn-danger "Cancel"]]])))
