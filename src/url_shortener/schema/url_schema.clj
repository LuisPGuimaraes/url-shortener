(ns url-shortener.schema.url-schema
  (:require [clojure.spec.alpha :as s]))

(s/def ::short-url string?)
(s/def ::generated-short-code string?)
(s/def ::original-url string?)
(s/def ::inserted-at inst?)
(s/def ::updated-at inst?)

(s/def ::short-url-document
  (s/keys :req-un [::short-url
                   ::generated-short-code
                   ::original-url
                   ::inserted-at
                   ::updated-at]))

(defn valid-short-url?
  "Validates if a document matches the short URL schema"
  [document]
  (s/valid? ::short-url-document document))

(defn validate-short-url
  "Validates a document and returns either the document or explanation"
  [document]
  (if (valid-short-url? document)
    {:valid? true :document document}
    {:valid? false
     :errors (s/explain-data ::short-url-document document)}))

(defn create-document
  "Creates a new short URL document with inserted_at and updated_at timestamps"
  [short-url generated-short-code original-url]
  (let [now (java.util.Date.)]
    {:short-url short-url
     :generated-short-code generated-short-code
     :original-url original-url
     :inserted-at now
     :updated-at now}))
