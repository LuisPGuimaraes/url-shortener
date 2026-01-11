(ns url-shortener.config.database
  (:require [monger.core :as mg]
            [monger.credentials :as mcred]))

(defn get-db-config
  "Gets database configuration from environment variables with defaults"
  []
  {:uri (System/getenv "MONGO_URI")
   :host (or (System/getenv "MONGO_HOST") "localhost")
   :port (Integer/parseInt (or (System/getenv "MONGO_PORT") "27017"))
   :database (or (System/getenv "MONGO_DB") "url_shortener")
   :username (System/getenv "MONGO_USER")
   :password (System/getenv "MONGO_PASSWORD")})

(defn create-connection
  "Creates a MongoDB connection. Supports MongoDB Atlas (mongodb+srv://) or regular connections"
  []
  (let [config (get-db-config)
        {:keys [uri host port database username password]} config]
    (cond
      ;; MongoDB Atlas connection string (mongodb+srv://)
      uri
      (let [conn (mg/connect-via-uri uri)]
        {:conn (:conn conn)
         :db (:db conn)})
      
      ;; Connection with credentials
      (and username password)
      (let [credentials (mcred/create username database (.toCharArray password))
            conn (mg/connect-with-credentials host port credentials)]
        {:conn conn
         :db (mg/get-db conn database)})
      
      ;; Connection without authentication (local development)
      :else
      (let [conn (mg/connect {:host host :port port})]
        {:conn conn
         :db (mg/get-db conn database)}))))

(def db-connection
  "Database connection atom - initialized on first use"
  (atom nil))

(defn get-db
  "Gets the database instance, creating connection if needed"
  []
  (when (nil? @db-connection)
    (reset! db-connection (create-connection)))
  (:db @db-connection))

(defn get-connection
  "Gets the MongoDB connection, creating if needed"
  []
  (when (nil? @db-connection)
    (reset! db-connection (create-connection)))
  (:conn @db-connection))
