(ns clojuredus.system
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]
            [clojuredus.components.web.pedestal :as pedestal]
            [clojuredus.components.web.routes :as routes]
            [clojuredus.components.db.postgres :as postgres]))

(defn- build-service-map [env]
  {:env env
   ::http/routes routes/routes
   ::http/type :jetty
   ::http/port 8080
   ::http/resource-path "/public"
   ::http/join? false})

(def db-config
  {:db       "clojure"
   :user     "clojure"
   :password "clojure"})

(defn system [env]
  (component/system-map
   :service-map (build-service-map env)
   :db-config db-config

   :db
   (component/using
    (postgres/new-database)
    [:db-config])

   :web
   (component/using
    (pedestal/new-pedestal)
    [:db :service-map])))

(defn -main [& args]
  (component/start (system {})))

(comment
  (def mysystem (component/start (system {})))
  (component/stop mysystem)
  mysystem
  )


