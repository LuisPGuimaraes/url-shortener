(ns url-shortener.service.short-url-service
  (:require [url-shortener.repository.short-url-repository :as repo]
            [url-shortener.schema.url-schema :as schema]))

(def base-url "http://localhost:3000")

(def chars "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789")

(defn generate-short-code
  []
  (let [code-length 6]
    (->> (repeatedly code-length #(rand-nth chars))
         (apply str))))

(defn generate-unique-short-code
  []
  (loop [code (generate-short-code)]
    (if (repo/find-by-short-url (str base-url "/" code))
      (recur (generate-short-code))
      code)))

(defn create-short-url
  [original-url]
  (if (repo/find-by-original-url original-url)
    {:error true
     :message "URL already exists in the database"}
    (let [short-code (generate-unique-short-code)
          short-url (str base-url "/" short-code)
          document (schema/create-document short-url original-url)]
      (repo/insert document)
      document)))
