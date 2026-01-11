(ns url-shortener.repository.short-url-repository
  (:require [url-shortener.config.database :as db]
            [url-shortener.schema.url-schema :as schema])
  (:import (org.bson Document)))

(def collection-name "short_urls")

(defn stringify-keys [m]
  (into {}
        (map (fn [[k v]]
               [(name k) v])
             m)))

(defn ->doc [m]
  (Document. (stringify-keys m)))

(defn insert [document]
  (let [validation (schema/validate-short-url document)]
    (if (:valid? validation)
      (let [coll (db/get-collection collection-name)]
        (.insertOne coll (->doc document))
        document)
      (throw (ex-info "Invalid document schema" validation)))))

(defn find-by-short-url [short-url]
  (let [coll (db/get-collection collection-name)
        doc (.find coll (->doc {:short-url short-url}))]
    (some-> doc .first (into {}))))

(defn find-by-original-url [original-url]
  (let [coll (db/get-collection collection-name)
        doc (.find coll (->doc {:original-url original-url}))]
    (some-> doc .first (into {}))))
