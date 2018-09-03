(ns clojuredus.system
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]
            [clojuredus.components.web.pedestal :as pedestal]
            [clojuredus.components.web.routes :as routes]
            [clojuredus.components.db.postgres :as postgres]))

(defn system [env]
  (component/system-map
   :service-map {:env env
                 ::http/routes routes/routes
                 ::http/type   :jetty
                 ::http/port   8890
                 ::http/join?  false}
   :db-config {:db       "clojure"
               :user     "clojure"
               :password "clojure"}

   :web
   (component/using
    (pedestal/new-pedestal)
    [:service-map :db])

   :db
   (component/using
    (postgres/new-database)
    [:db-config])))

(comment
  (def mysystem (component/start (system {})))
  (component/stop mysystem)
  mysystem
  )
