(ns re-frame-notifier.core
  (:require [cljs.spec.alpha :as s]
            [re-frame-notifier.events :as rfn-events]
            [re-frame-notifier.subscriptions :as rfn-subscriptions]))

(def initial-state
  "Initial re-frame-notifier state.

  All requests are associated into a map."
  {})

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
