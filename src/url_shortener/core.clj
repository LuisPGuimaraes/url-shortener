(ns url-shortener.core
  (:require [url-shortener.router :as router]
            [ring.adapter.jetty :as jetty])
  (:gen-class))

(defn -main
  [& args]
  (let [port (Integer/parseInt "3000")]
    (println (str "Starting server on port " port "3000"))
    (jetty/run-jetty router/app {:port port :join? true})))
