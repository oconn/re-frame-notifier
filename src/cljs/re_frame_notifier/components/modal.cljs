(ns re-frame-notifier.components.modal
  (:require [re-frame-notifier.helpers :refer [action-and-close]]))

(defn modal-component
  [{:keys [component
           component-props
           hide-dismiss]
    :or {component-props nil
         hide-dismiss false}}]
  [:div.notifier-underlay
   [:div.notifier-modal
    [component component-props]
    (when-not hide-dismiss
      [:button
       {:class "notifier-dismiss-button"
        :on-click (action-and-close identity :modal)}
       "X"])]])
