(ns pigallery.components.registration
  (:require [reagent.core :refer [atom]]
            [pigallery.components.common :as c]))

(defn registration-form []
  (let [fields (atom {})]
    (fn []
      [c/modal
       [:div "Pigallery Registration"]
       [:div
        [:div.well.well-sm
         [:strong "* required field"]]
        [c/text-input "name" :id "enter a username" fields]
        [c/password-input "password" :pass "enter a password" fields]
        [c/password-input "password" :pass-confirm "re-enter the password" fields]]
       [:div
        [:button.btn.btn-primary "Register"]
        [:button.btn.btn-danger "Cancel"]]])))
