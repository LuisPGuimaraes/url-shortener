(ns url-shortener.controller.short-url-controller
  (:require [url-shortener.service.short-url-service :as short-url-service]))

(defn create-short-url-handler
  "Controller handler for creating a short URL"
  [request]
  (let [body (:body-params request)
        long-url (:url body)]
    (if long-url
      (let [result (short-url-service/create-short-url long-url)]
        {:status 201
         :body result})
      {:status 400
       :body {:error "URL is required"}})))
