(ns re-frame-notifier.events
  (:require [re-frame.core :refer [reg-event-db]]))

(defn register-events
  []
  (reg-event-db
   :notifier/create-alert
   (fn [db [_ [_ alert]]]
     (update-in db [:notifier :alerts] #(conj % alert))))

  (reg-event-db
   :notifier/create-modal
   (fn [db [_ [_ modal]]]
     (update-in db [:notifier :modals] #(conj % modal))))

  (reg-event-db
   :notifier/create-toast
   (fn [db [_ [_ toast]]]
     (update-in db [:notifier :toasts] #(conj % toast)))))
