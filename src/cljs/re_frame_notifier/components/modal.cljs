(ns re-frame-notifier.components.modal
  (:require [re-frame-notifier.helpers :refer [action-and-close]]))

(defn modal-component
  [{:keys [component
           component-props
           hide-dismiss
           dismiss-button-class
           modal-container-class
           modal-component-container-class
           modal-underlay-class]
    :or {component-props nil
         hide-dismiss false
         dismiss-button-class "notifier-dismiss-button"
         modal-container-class "notifier-modal-container"
         modal-component-container-class "notifier-modal-component-container"
         modal-underlay-class "notifier-underlay"}}]
  [:div {:class modal-underlay-class}
   [:div {:class modal-container-class}
    (when-not hide-dismiss
      [:button
       {:class dismiss-button-class
        :on-click (action-and-close identity :modal)}
       "X"])
    [:div {:class modal-component-container-class}
     [component component-props]]]])
