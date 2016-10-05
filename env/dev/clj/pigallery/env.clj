(ns pigallery.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [pigallery.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[pigallery started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[pigallery has shut down successfully]=-"))
   :middleware wrap-dev})
