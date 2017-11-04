(ns re-frame-notifier.core
  (:require [cljs.spec.alpha :as s]
            [re-frame-notifier.events :as rfn-events]
            [re-frame-notifier.subscriptions :as rfn-subscriptions]))

(def initial-state
  "Initial re-frame-notifier state.

  All requests are associated into a map."
  {:alerts []
   :modals []
   :toasts []})

;; Specs for each type of notification
(s/def :alert/title string?)
(s/def ::alerts (s/coll-of (s/keys :req-un [:alert/title])))

(s/def :modal/title string?)
(s/def ::modals (s/coll-of (s/keys :req-un [:modal/title])))

(s/def :toast/message string?)
(s/def ::toasts (s/coll-of (s/keys :req-un [:toast/message])))

(s/def ::notifier (s/keys :req-un [::alerts
                                   ::modals
                                   ::toasts]))

(defn register-events
  "Registers re-frame-request events and request handler"
  []
  (rfn-events/register-events))

(defn register-subscriptions
  "Registers re-frame-request subscriptions"
  []
  (rfn-subscriptions/register-subscriptions))

(defn register-all
  "Registers both re-frame-request events & subscriptions"
  []
  (register-events)
  (register-subscriptions))
