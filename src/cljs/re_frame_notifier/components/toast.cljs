(ns re-frame-notifier.components.toast
  (:require [re-frame.core :as re-frame]))

(defn toast-component
  [{:keys [duration status message]
    :or {duration 3000
         status :info}}]

  (js/setTimeout
   (fn []
     (re-frame/dispatch [:notifier/clear-active-toast]))
   duration)

  [:p
   {:class (str "notifier-toast " (name status) "-toast")}
   message])
