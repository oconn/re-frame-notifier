(ns re-frame-notifier.helpers
  (:require [re-frame.core :as re-frame]))

(defn keywordize-str
  "Like the str function, but will result in a keyword and can take strings or
  kewords as arguments"
  [& vals]
  (->> vals
       (map #(if (keyword? %) (name %) %))
       clojure.string/join
       keyword))

(defn action-and-close
  "Returns a function that calls a given action & fires a close
  event for a given notification type."
  [action notification-type]
  (fn [_]
    (action)
    (re-frame/dispatch [(keywordize-str "notifier/clear-active-"
                                        notification-type)])))

(defn- random-id
  "Returns a random string id."
  []
  (-> js/window
      .-Math
      .random
      (.toString 36)
      (.substr 2 10)))

(defn- pluralize-keyword
  "Takes a keyword and adds an 's'."
  [k]
  (-> k
      name
      (str "s")
      keyword))
