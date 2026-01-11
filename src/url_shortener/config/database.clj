(ns url-shortener.config.database
  (:import
    (com.mongodb.client MongoClients MongoDatabase)
    (org.bson Document)))

(defonce client (atom nil))
(defonce db (atom nil))

(defn get-mongo-uri []
  (or (System/getenv "MONGO_URI")
      (throw (ex-info "MONGO_URI is not set" {}))))

(defn connect! []
  (when-not @client
    (let [uri (get-mongo-uri)
          mongo-client (MongoClients/create uri)
          database-name (or (System/getenv "MONGO_DB") "url_shortener")
          database (.getDatabase mongo-client database-name)]
      (reset! client mongo-client)
      (reset! db database))))

(defn get-db []
  (when-not @db
    (connect!))
  @db)

(defn get-collection [name]
  (.getCollection (get-db) name))
