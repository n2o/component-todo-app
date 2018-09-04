(ns clojuredus.components.web.views
  (:require [ring.util.response :as ring-resp]
            [hiccup.page :as hp]
            [clojuredus.components.web.todos :as todos]))

(defn- toggle-todo-view [todo]
  [:form#toggle-todo {:action "/todo/toggle" :method :POST}
   [:input {:name "id" :value (:id todo) :hidden true}]
   [:input {:class "btn btn-sm btn-primary"
            :type :submit
            :value "Toggle"}]])

(defn- todos->rows []
  (for [todo (sort-by :title (todos/query-all))]
    [:tr
     [:td (:title todo)]
     [:td (if (:done todo) "✅" "❌")]
     [:td (toggle-todo-view todo)]]))

;; -----------------------------------------------------------------------------

(defn base-template [& body]
  (ring-resp/response
   (hp/html5
    [:head
     [:title "Todo-Application"]
     [:meta {:charset "utf-8"}]
     [:meta {:http-equiv "X-UA-Compatible"
             :content "IE=edge"}]
     [:meta {:name "viewport"
             :content "width=device-width, initial-scale=1"}]
     (hp/include-css "css/bootstrap.min.css")]
    [:body
     [:div.container {:style "padding-top: 3rem"}
      body]])))

(defn home-page [request]
  (base-template
   [:h1 "Todo-Application"]
   [:form#add-todo {:action "/todo/add" :method :POST}
    [:div.form-group
     [:label#title "New Todo"]
     [:input.form-control {:name "title" :required true}]]
    [:input {:class "btn btn-primary"
             :type :submit
             :value "Add"}]]

   [:hr {:style "margin: 3rem 0"}]

   [:h4 "Todos"]
   [:table.table.table-striped
    [:thead
     [:tr
      [:th {:width "90%"} "Title"]
      [:th "Done?"]
      [:th]]]
    (vec (conj (todos->rows) :tbody))]))

(defn add-todo [{:keys [form-params]}]
  (todos/add! (:title form-params))
  (ring-resp/redirect "/"))

(defn toggle-todo [{:keys [form-params]}]
  (todos/toggle! (:id form-params))
  (ring-resp/redirect "/"))

(defn respond-hello [request]
  {:status 200 :body "Hello, world!"})
