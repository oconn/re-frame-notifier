(ns re-frame-notifier.subscriptions
  (:require [re-frame.core :refer [reg-sub]]))

(defn register-subscriptions
  "Register re-frame-notifier subscriptions"
  []

  (reg-sub
   :notifier/core
   (fn [{:keys [notifier]} _] notifier)))
