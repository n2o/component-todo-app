(defproject clojuredus "0.0.1-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [com.stuartsierra/component "0.3.2"]
                 [org.danielsz/system "0.4.1"]

                 [org.clojure/java.jdbc "0.7.8"]
                 [org.postgresql/postgresql "42.2.4"]
                 [korma "0.4.3"]

                 [io.pedestal/pedestal.service "0.5.4"]
                 [io.pedestal/pedestal.jetty "0.5.4"]

                 [org.slf4j/log4j-over-slf4j "1.7.25"]]
  :min-lein-version "2.0.0"
  :resource-paths ["config", "resources"]
  ;; :profiles {:dev {:aliases {"run-dev" ["trampoline" "run" "-m" "clojuredus.server/run-dev"]}
  ;;                  :dependencies [[io.pedestal/pedestal.service-tools "0.5.4"]]}
  ;;            :uberjar {:aot [clojuredus.server]}}
  ;; :main ^{:skip-aot true} clojuredus.server
  )
