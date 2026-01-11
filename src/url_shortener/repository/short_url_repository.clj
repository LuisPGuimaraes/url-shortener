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

(defn doc->map [^Document doc]
  "Converte Document para mapa com keywords"
  (when doc
    (into {}
          (map (fn [[k v]]
                 [(keyword k) v]))
          doc)))

(defn insert [document]
  (let [validation (schema/validate-short-url document)]
    (if (:valid? validation)
      (let [coll (db/get-collection collection-name)]
        (.insertOne coll (->doc document))
        document)
      (throw (ex-info "Invalid document schema" validation)))))

(defn find-by-short-url [short-code]
  (let [coll (db/get-collection collection-name)
        doc (.find coll (->doc {:generated-short-code short-code}))]
    (some-> doc .first doc->map)))

(defn find-by-original-url [original-url]
  (let [coll (db/get-collection collection-name)
        doc (.find coll (->doc {:original-url original-url}))]
    (some-> doc .first doc->map)))