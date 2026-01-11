(ns url-shortener.router
  (:require [reitit.ring :as ring]
            [reitit.coercion.spec]
            [reitit.ring.coercion :as rrc]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [muuntaja.core :as m]))

(def app
  (ring/ring-handler router))

(def router
  (ring/router
   [
    ["/"
     {:get {:handler home-handler
            :summary "Home page"}
     }
    ]
   ]
   {:data {:muuntaja m/instance
           :coercion reitit.coercion.spec/coercion
           :middleware [muuntaja/format-negotiate-middleware
                       muuntaja/format-response-middleware
                       muuntaja/format-request-middleware
                       rrc/coerce-exceptions-middleware
                       rrc/coerce-request-middleware
                       rrc/coerce-response-middleware]}}))

(defn home-handler
  "Handler for the home page"
  [request]
  {:status 200
   :body {:message "URL Shortener API - Powered by Luis Guimaraes"}})




