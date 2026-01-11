(ns url-shortener.service.short-url-service)

(defn create-short-url
  [long-url]
  {:short-code "abc123"
   :original-url long-url
   :short-url (str "http://localhost:3000/abc123")})
