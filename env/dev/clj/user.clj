(ns user
  (:require [mount.core :as mount]
            [pigallery.figwheel :refer [start-fw stop-fw cljs]]
            pigallery.core))

(defn start []
  (mount/start-without #'pigallery.core/repl-server))

(defn stop []
  (mount/stop-except #'pigallery.core/repl-server))

(defn restart []
  (stop)
  (start))


