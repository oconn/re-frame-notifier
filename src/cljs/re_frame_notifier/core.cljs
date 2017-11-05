(ns re-frame-notifier.core
  (:require [cljs.spec.alpha :as s]
            [re-frame.core :as re-frame]
            [re-frame-notifier.components.alert :refer [alert-component]]
            [re-frame-notifier.components.modal :refer [modal-component]]
            [re-frame-notifier.components.toast :refer [toast-component]]
            [re-frame-notifier.events :as rfn-events]
            [re-frame-notifier.subscriptions :as rfn-subscriptions]))

(def initial-state
  "Initial re-frame-notifier state."
  {:alerts []
   :modals []
   :toasts []})

;; Specs for each type of notification

;; A unique id is generated for each notification object to ensure
;; the component is re-rendered each time.
(s/def ::id string?)

;; Required Alert Properties
(s/def :alert/message string?)
(s/def :alert/title string?)

;; Optional Alert Properties
(s/def :alert/confirm-action ifn?)
(s/def :alert/confirm-only boolean?)
(s/def :alert/confirm-text string?)
(s/def :alert/deny-action ifn?)
(s/def :alert/deny-text string?)
(s/def :alert/hide-dismiss boolean?)

;; Alert Spec
(s/def ::alerts (s/coll-of (s/keys :req-un [:alert/message
                                            :alert/title
                                            ::id]
                                   :opt-un [:alert/confirm-action
                                            :alert/confirm-only
                                            :alert/confirm-text
                                            :alert/deny-action
                                            :alert/deny-text
                                            :alert/hide-dismiss])))

;; Required Modal Properties
(s/def :modal/component fn?)

;; Optional Modal Properties
(s/def :modal/hide-close boolean?)

;; Modal Spec
(s/def ::modals (s/coll-of (s/keys :req-un [:modal/component
                                            ::id]
                                   :opt-un [:modal/hide-close])))

;; Required Toast Properties
(s/def :toast/message string?)

;; Optional Toast Properties
(s/def :toast/duration number?)
(s/def :toast/status #{:info :warning :error})

;; Toast Spec
(s/def ::toasts (s/coll-of (s/keys :req-un [:toast/message
                                            ::id]
                                   :opt-un [:toast/duration
                                            :toast/status])))

;; Notifier Lib Spec
(s/def ::notifier (s/keys :req-un [::alerts
                                   ::modals
                                   ::toasts]))

(defn register-events
  "Registers re-frame-notifier events"
  [opts]
  (rfn-events/register-events opts))

(defn register-subscriptions
  "Registers re-frame-notifier subscriptions"
  []
  (rfn-subscriptions/register-subscriptions))

(defn register-all
  "Registers both re-frame-notifier events & subscriptions"
  [{:keys [event-options]}]
  (register-events event-options)
  (register-subscriptions))

(defn render-notifications
  [{:keys [render-alert
           render-modal
           render-toast]
    :or {render-alert alert-component
         render-modal modal-component
         render-toast toast-component}}]
  (let [active-alert (re-frame/subscribe [:notifier/active-alert])
        active-modal (re-frame/subscribe [:notifier/active-modal])
        active-toast (re-frame/subscribe [:notifier/active-toast])]
    (fn []
      [:div.notification-center
       (when @active-alert
         [render-alert @active-alert])
       (when @active-modal
         [render-modal @active-modal])
       (when @active-toast
         [render-toast @active-toast])])))
