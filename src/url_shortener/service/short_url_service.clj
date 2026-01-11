(ns url-shortener.service.short-url-service
  (:require [url-shortener.repository.short-url-repository :as repo]
            [url-shortener.schema.url-schema :as schema]))

(def base-url "http://localhost:3000")
(def chars "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789")

(defn generate-short-code
  "Generates a random 6-character code"
  []
  (let [code-length 6]
    (->> (repeatedly code-length #(rand-nth chars))
         (apply str))))

(defn generate-unique-short-code
  "Generates a unique short code that doesn't exist in the database"
  []
  (loop [code (generate-short-code)]
    (if (repo/find-by-short-url code)
      (recur (generate-short-code))
      code)))

(defn create-short-url
  "Creates a new short URL or returns error if URL already exists"
  [original-url]
  (if (repo/find-by-original-url original-url)
    {:error true
     :message "URL already exists in the database"}
    (let [short-code (generate-unique-short-code)
          short-url (str base-url "/" short-code)
          document (schema/create-document short-url short-code original-url)]
      (repo/insert document)
      {:short-code short-code
       :short-url short-url
       :original-url original-url})))

(defn redirect-original-url
  "Finds the original URL by short code"
  [short-code]
  (if-let [document (repo/find-by-short-url short-code)]
      {:original-url (:original-url document)
       :short-code short-code}
    {:error true
     :message "Short URL not found"}))