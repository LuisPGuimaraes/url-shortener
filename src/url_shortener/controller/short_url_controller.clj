(ns url-shortener.controller.short-url-controller
  (:require [url-shortener.service.short-url-service :as short-url-service]))

(defn- success-response
  [status body]
  {:status status
   :body body})

(defn- error-response
  [status message]
  {:status status
   :body {:error message}})

(defn- redirect-response
  [location]
  {:status 302
   :headers {"Location" location}
   :body nil})

(defn create-short-url-handler
  [request]
  (if-let [long-url (get-in request [:body-params :url])]
    (let [result (short-url-service/create-short-url long-url)]
      (if (:error result)
        (error-response 409 (:message result))
        (success-response 201 result)))
    (error-response 400 "URL is required")))

(defn redirect-handler
  [request]
  (if-let [short-code (get-in request [:path-params :short-code])]
    (let [result (short-url-service/redirect-to-original-url short-code)]
      (if (:error result)
        (error-response 404 (:message result))
        (redirect-response (:original-url result))))
    (error-response 400 "Short code is required")))