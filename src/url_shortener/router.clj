(ns url-shortener.router
  (:require [reitit.ring :as ring]
            [reitit.coercion.spec]
            [reitit.ring.coercion :as rrc]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [muuntaja.core :as m]))

(def app
  (ring/ring-handler
   router
   (ring/create-default-handler)
   {:middleware [muuntaja/format-negotiate-middleware
                 muuntaja/format-response-middleware
                 muuntaja/format-request-middleware
                 rrc/coerce-exceptions-middleware
                 rrc/coerce-request-middleware
                 rrc/coerce-response-middleware]}))
