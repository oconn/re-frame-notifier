(ns re-frame-notifier.components.alert
  (:require [re-frame-notifier.helpers :refer [action-and-close]]))

(defn alert-component
  [{:keys [message
           title
           confirm-action
           confirm-only
           confirm-text
           deny-action
           deny-text
           hide-dismiss]
    :or {confirm-action identity
         confirm-only false
         confirm-text "Ok"
         deny-action identity
         deny-text "Cancel"
         hide-dismiss true}}]
  [:div.notifier-underlay
   [:div.notifier-alert
    [:h3.notifier-alert-title title]

    [:p.notifier-alert-message message]

    [:button
     {:class "notifier-alert-confirm-button"
      :on-click (action-and-close confirm-action :alert)}
     confirm-text]

    (when-not confirm-only
      [:button
       {:class "notifier-alert-deny-button"
        :on-click (action-and-close deny-action :alert)}
       deny-text])

    (when-not hide-dismiss
      [:button
       {:class "notifier-dismiss-button"
        :on-click (action-and-close identity :alert)}
       "X"])]])
