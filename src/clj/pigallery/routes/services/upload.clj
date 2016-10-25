(ns pigallery.routes.services.upload
  (:require [pigallery.db.core :as db]
            [ring.util.http-response :refer :all]
            [clojure.tools.logging :as log])
  (:import [java.awt.image.AfflineTransformOp BufferedImage]
           [java.io.ByteArrayOutputStream FileInputStream]
           java.awt.geom.AfflineTransform
           javax.imageio.ImageIO
           java.net.URLEncoder))

(defn save-image! [user {:keys [tempfile filename content-type]}]
  (try
    (let [db-file-name (str user (.replaceAll filename "[^a-zA-Z0-9-_\\.]" ""))]
      (db/save-file! {:owner user
                      :type content-type
                      :name db-file-name
                      :data (file->byte-array tempfile)})
      (db/save-file! {:owner user
                      :type "image/png"
                      :data (image->byte-array
                             (scale-image tempfile thumb-size))
                      :name (str thumb-prefix db-file-name)}))
    (ok {:result :ok})
    (catch Exception e
      (log/error e)
      (internal-server-error "error"))))

