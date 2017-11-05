(ns re-frame-notifier.core
  (:require [cljs.spec.alpha :as s]
            [re-frame-notifier.events :as rfn-events]
            [re-frame-notifier.subscriptions :as rfn-subscriptions]))

(def initial-state
  "Initial re-frame-notifier state."
  {:alerts []
   :modals []
   :toasts []})

;; Specs for each type of notification
(s/def :alert/title string?)
(s/def :alert/message string?)
(s/def :alert/confirm-text string?)
(s/def :alert/deny-text string?)
(s/def :alert/confirm-action ifn?)
(s/def :alert/deny-action ifn?)
(s/def :alert/confirm-only boolean?)
(s/def ::alerts (s/coll-of (s/keys :req-un [:alert/title
                                            :alert/message]
                                   :opt-un [:alert/confirm-text
                                            :alert/deny-text
                                            :alert/confirm-action
                                            :alert/deny-action
                                            :alert/confirm-only])))

(s/def :modal/title string?)
(s/def :modal/component keyword?)
(s/def :modal/component-props map?)
(s/def :modal/hide-close boolean?)
(s/def ::modals (s/coll-of (s/keys :req-un [:modal/title
                                            :modal/component]
                                   :opt-un [:modal/component-props
                                            :modal/hide-close])))

(s/def :toast/message string?)
(s/def :toast/status #{:info :warning :error})
(s/def ::toasts (s/coll-of (s/keys :req-un [:toast/message
                                            :toast/status])))

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
