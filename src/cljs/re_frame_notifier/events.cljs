(ns re-frame-notifier.events
  (:require [re-frame.core :refer [reg-event-db]]
            [re-frame-notifier.helpers :refer [random-id
                                               pluralize-keyword
                                               keywordize-str]]))

(defn- add-notification-of-type
  "Adds a notification to its namespaced collection"
  [notification-type]
  (fn [db [_ notification]]
    (update-in db
               [:notifier notification-type]
               #(conj % (assoc notification :id (random-id))))))

(defn- clear-active-notification-of-type
  "Removes the first notification from its namespaced collection"
  [notification-type]
  (fn [db _]
    (update-in db [:notifier notification-type] #(vec (rest %)))))

(defn- clear-notifications-of-type
  "Resets the namespaced notification collection"
  [notification-type]
  (fn [db _]
    (assoc-in db [:notifier notification-type] [])))

(defn- register-events-for-notification-type
  "Registers all the events for a notifcation namespace (alert, modal, toast)"
  [notification-type {:keys [create
                             clear-active
                             clear-all
                             notifier-interceptors]}]

  (reg-event-db
   (keywordize-str "notifier/create-" notification-type)
   (into notifier-interceptors create)
   (add-notification-of-type (pluralize-keyword notification-type)))

  (reg-event-db
   (keywordize-str "notifier/clear-active-" notification-type)
   (into notifier-interceptors clear-active)
   (clear-active-notification-of-type (pluralize-keyword notification-type)))

  (reg-event-db
   (keywordize-str "notifier/clear-" notification-type "s")
   (into notifier-interceptors clear-all)
   (clear-notifications-of-type (pluralize-keyword notification-type))))

(defn register-events
  [{:keys [clear-active-alert-interceptors
           clear-active-modal-interceptors
           clear-active-toast-interceptors
           clear-alerts-interceptors
           clear-modals-interceptors
           clear-toasts-interceptors
           create-alert-interceptors
           create-modal-interceptors
           create-toast-interceptors
           notifier-interceptors]
    :or {clear-active-alert-interceptors []
         clear-active-modal-interceptors []
         clear-active-toast-interceptors []
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
    :clear-active clear-active-alert-interceptors
    :clear-all clear-alerts-interceptors
    :notifier-interceptors notifier-interceptors})

  (register-events-for-notification-type
   :modal
   {:create create-modal-interceptors
    :clear-active clear-active-modal-interceptors
    :clear-all clear-modals-interceptors
    :notifier-interceptors notifier-interceptors})

  (register-events-for-notification-type
   :toast
   {:create create-toast-interceptors
    :clear-active clear-active-toast-interceptors
    :clear-all clear-toasts-interceptors
    :notifier-interceptors notifier-interceptors}))
