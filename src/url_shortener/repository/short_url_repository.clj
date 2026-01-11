(ns url-shortener.repository.short-url-repository
  (:require [url-shortener.config.database :as db]
            [url-shortener.schema.url-schema :as schema]
            [monger.collection :as mc]))

(def collection-name "short_urls")

(defn insert
  "Inserts a short URL document into MongoDB after validation"
  [document]
  (let [validation (schema/validate-short-url document)]
    (if (:valid? validation)
      (mc/insert (db/get-db) collection-name document)
      (throw (ex-info "Invalid document schema" validation)))))

(defn find-by-short-url
  "Finds a short URL by its short URL"
  [short-url]
  (mc/find-one-as-map (db/get-db) collection-name {:short-url short-url}))

(defn find-by-original-url
  "Finds a short URL by its original URL"
  [original-url]
  (mc/find-one-as-map (db/get-db) collection-name {:original-url original-url}))
