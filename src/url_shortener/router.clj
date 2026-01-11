(ns url-shortener.router
  (:require [reitit.ring :as ring]
            [reitit.coercion.spec]
            [reitit.ring.coercion :as rrc]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [muuntaja.core :as m]
            [url-shortener.controller.short-url-controller :as short-url-controller]))

(defn home-handler
  "Handler for the home page"
  [request]
  {:status 200
   :body {:message "URL Shortener API - Powered by Luis Guimaraes"}})

(def router
  (ring/router
   [
    ["/"
     {:get {:handler home-handler
            :summary "Home page"}
     }
    ]
    [
      "/api"
      [
        "/shorten"
        {:post {:handler short-url-controller/create-short-url-handler
                :summary "Create a short URL from a long URL"
                :parameters {:body {:url string?}}}}
      ]
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

(def app
  (ring/ring-handler router))




