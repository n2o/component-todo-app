(ns clojuredus.components.web.todos
  (:require [korma.core :as kc]))

(kc/defentity todo
  (kc/table :todos)
  (kc/entity-fields :title :done))

(defn create-table! []
  (kc/exec-raw "CREATE TABLE IF NOT EXISTS todos(id SERIAL, title TEXT NOT NULL, done BOOLEAN DEFAULT FALSE NOT NULL)"))

(defn drop-table! []
  (kc/exec-raw "DROP TABLE IF EXISTS todos"))

(defn clear-table! []
  (kc/delete todo))

(defn add! [title]
  (kc/insert todo (kc/values {:title title})))

(defn delete! [id]
  (kc/delete todo (kc/where {:id id})))

(defn toggle! [id done]
  (kc/update todo
             (kc/set-fields {:done (not done)})
             (kc/where {:id id})))

(defn query-all []
  (kc/select todo))

(comment
  (create-table!)
  (add! "deleteme")
  (delete! 10)
  (clear-table!)
  )
