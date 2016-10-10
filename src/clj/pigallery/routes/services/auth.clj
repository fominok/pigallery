(ns pigallery.routes.services.auth
  (:require [pigallery.db.core :as db]
            [pigallery.validation :refer [registration-errors]]
            [ring.util.http-response :as response]
            [buddy.hashers :as hashers]
            [clojure.tools.logging :as log]))

(defn handle-registration-error [e]
  (if (-> e
          (.getMessage)
          (.startsWith "Error: duplicate key value"))
    (response/precondition-failed
     {:result :error
      :message "user already exists"})
    (do
      (log/error e)
      (response/internal-server-error
       {:result :error
        :message "registration error"}))))

(defn register! [{:keys [session]} user]
  (if (registration-errors user)
    (response/precondition-failed {:result :error})
    (try
      (db/create-user!
       (-> user
           (dissoc :pass-confirm)
           (update :pass hashers/encrypt)))
      (-> {:result :ok}
          (response/ok)
          (assoc :session (assoc session :identity (:id user))))
      (catch Exception e
        (handle-registration-error e)))))

(mount.core/start)
(register! {} {:id "foo" :pass "12341234" :pass-confirm "12341234"})
