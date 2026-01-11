(ns url-shortener.controller.short-url-controller
  (:require [url-shortener.service.short-url-service :as short-url-service]
            [ring.util.response :as response]))

(defn- success-response
  "Creates a success response"
  [status body]
  {:status status
   :body body})

(defn- error-response
  "Creates an error response"
  [status message]
  {:status status
   :body {:error message}})

(defn- redirect-response
  "Creates a redirect response"
  [location]
  (response/redirect location))

(defn create-short-url-handler
  "Controller handler for creating a short URL"
  [request]
  (if-let [long-url (get-in request [:body-params :url])]
    (let [result (short-url-service/create-short-url long-url)]
      (if (:error result)
        (error-response 409 (:message result))
        (success-response 201 result)))
    (error-response 400 "URL is required")))

(defn redirect-handler
  "Controller handler for redirecting to the original URL"
  [request]
  (if-let [short-code (get-in request [:path-params :short-code])]
    (let [result (short-url-service/redirect-original-url short-code)]
      (if (:error result)
        (error-response 404 (:message result))
        (redirect-response (:original-url result))))
    (error-response 400 "Short code is required")))