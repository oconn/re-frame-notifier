(ns re-frame-notifier.events
  (:require [re-frame.core :refer [reg-event-db]]))

(defn- add-notification-of-type
  "Adds a notification to its namespaced collection"
  [notification-type]
  (fn [db [_ notification]]
    (update-in db [:notifier notification-type] #(conj % notification))))

(defn- clear-next-notification-of-type
  "Removes the first notification from its namespaced collection"
  [notification-type]
  (fn [db _]
    (update-in db [:notifier notification-type] #(vec (rest %)))))

(defn- clear-notifications-of-type
  "Resets the namespaced notification collection"
  [notification-type]
  (fn [db _]
    (assoc-in db [:notifier notification-type] [])))

(defn- pluralize-keyword
  "Takes a keyword and adds an 's'."
  [k]
  (-> k
      name
      (str "s")
      keyword))

(defn- register-events-for-notification-type
  "Registers all the events for a notifcation namespace (alert, modal, toast)"
  [notification-type {:keys [create
                             clear-next
                             clear-all
                             notifier-interceptors]}]

  (reg-event-db
   (keyword (str "notifier/create-" (name notification-type)))
   (into notifier-interceptors create)
   (add-notification-of-type (pluralize-keyword notification-type)))

  (reg-event-db
   (keyword (str "notifier/clear-next-" (name notification-type)))
   (into notifier-interceptors clear-next)
   (clear-next-notification-of-type (pluralize-keyword notification-type)))

  (reg-event-db
   (keyword (str "notifier/clear-" (name notification-type) "s"))
   (into notifier-interceptors clear-all)
   (clear-notifications-of-type (pluralize-keyword notification-type))))

(defn register-events
  [{:keys [clear-next-alert-interceptors
           clear-next-modal-interceptors
           clear-next-toast-interceptors
           clear-alerts-interceptors
           clear-modals-interceptors
           clear-toasts-interceptors
           create-alert-interceptors
           create-modal-interceptors
           create-toast-interceptors
           notifier-interceptors]
    :or {clear-next-alert-interceptors []
         clear-next-modal-interceptors []
         clear-next-toast-interceptors []
         clear-alerts-interceptors []
         clear-modals-interceptors []
         clear-toasts-interceptors []
         create-alert-interceptors []
         create-modal-interceptors []
         create-toast-interceptors []
         notifier-interceptors []}}]

  (register-events-for-notification-type
   :alert
   {:create create-alert-interceptors
    :clear-next clear-next-alert-interceptors
    :clear-all clear-alerts-interceptors
    :notifier-interceptors notifier-interceptors})

  (register-events-for-notification-type
   :modal
   {:create create-modal-interceptors
    :clear-next clear-next-modal-interceptors
    :clear-all clear-modals-interceptors
    :notifier-interceptors notifier-interceptors})

  (register-events-for-notification-type
   :toast
   {:create create-toast-interceptors
    :clear-next clear-next-toast-interceptors
    :clear-all clear-toasts-interceptors
    :notifier-interceptors notifier-interceptors}))
