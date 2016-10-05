(ns pigallery.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [pigallery.core-test]))

(doo-tests 'pigallery.core-test)

