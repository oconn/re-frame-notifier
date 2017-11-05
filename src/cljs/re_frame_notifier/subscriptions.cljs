(ns re-frame-notifier.subscriptions
  (:require [re-frame.core :refer [reg-sub subscribe]]))

(defn register-subscriptions
  "Register re-frame-notifier subscriptions"
  []

  (reg-sub
   :notifier/core
   (fn [{:keys [notifier]} _] notifier))

  (reg-sub
   :notifier/active-alert
   (fn [_ _] (subscribe [:notifier/core]))
   (fn [{:keys [alerts]} _] (first alerts)))

  (reg-sub
   :notifier/active-modal
   (fn [_ _] (subscribe [:notifier/core]))
   (fn [{:keys [modals]} _] (first modals)))

  (reg-sub
   :notifier/active-toast
   (fn [_ _] (subscribe [:notifier/core]))
   (fn [{:keys [toasts]} _] (first toasts))))
