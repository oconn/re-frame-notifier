(ns re-frame-notifier.events
  (:require [re-frame.core :refer [reg-event-db]]))

(defn register-events
  []
  (reg-event-db
   :notifier/something
   (fn [db [_ {:keys [name request-time error status]}]]
     db)))
