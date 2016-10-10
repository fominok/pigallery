(ns pigallery.routes.services
  (:require [ring.util.http-response :refer :all]
            [pigallery.routes.services.auth :as auth]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]))

(s/defschema UserRegistration
  {:id           s/Str
   :pass         s/Str
   :pass-confirm s/Str})

(s/defschema Result
  {:result                   s/Keyword
   (s/optional-key :message) s/Str})

(defapi service-routes
  {:swagger {:ui "/swagger-ui"
             :spec "/swagger.json"
             :data {:info {:version "1.0.0"
                           :title "Pigallery API"
                           :description "Public Services"}}}}
  (POST "/register" req
    :return Result
    :body [user UserRegistration]
    :summary "register a new user"
    (auth/register! req user)))
