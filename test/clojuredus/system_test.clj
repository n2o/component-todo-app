(ns clojuredus.system-test
  (:require [io.pedestal.http :as http]
            [io.pedestal.test :refer [response-for]]
            [com.stuartsierra.component :as component]
            [clojure.test :refer [deftest is are testing]]
            [clojuredus.system :as system]
            [clojuredus.components.web.routes :as routes]))

(defmacro with-system
  [[bound-var test-system] & body]
  `(let [~bound-var (component/start ~test-system)]
     (try
       ~@body
       (finally
         (component/stop ~bound-var)))))

(defn- service-fn
  [system]
  (get-in system [:web :service ::http/service-fn]))

;; -----------------------------------------------------------------------------

(deftest greeting-test
  (testing "Greeting route should print typical hello world."
    (with-system [sut (system/system :test)]
      (let [service (service-fn sut)
            {:keys [status body]} (response-for service
                                                :get
                                                (routes/url-for :greet))]
        (is (= 200 status))
        (is (= "Hello, world!" body))))))
