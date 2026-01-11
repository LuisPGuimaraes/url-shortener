# URL Shortener

A repository to improve my Clojure skills. The URL shortener is a common example used in coding challenges, interviews, and books.

The application allows users to convert long URLs into short links and access the original URL using the generated code.

## Features

- Create short URLs  
- Redirect to the original URL  

## Planned Features

- Add a web interface  
- Add authentication  
- Improve error handling and validations  

## Tech Stack

- Clojure  
- MongoDB  
- Ring/Reitit (Web framework)
- Monger (MongoDB driver)

## How to Run

1. Install Clojure  
2. Install and start MongoDB  
3. Configure environment variables (see `env.example`):
   - `MONGO_HOST` (default: localhost)
   - `MONGO_PORT` (default: 27017)
   - `MONGO_DB` (default: url_shortener)
   - `MONGO_USER` (optional, for authentication)
   - `MONGO_PASSWORD` (optional, for authentication)
4. Run the application: `lein run`
5. Access the API locally at `http://localhost:3000`

## API Usage

### Create Short URL

**Endpoint:** `POST /api/shorten`

**Request Body:**
```json
{
  "url": "https://www.linkedin.com/in/luisguimaraesp/"
}
```

**Response:**
```json
{
    "short-url": "http://localhost:3000/vcmv7y",
    "original-url": "https://www.linkedin.com/in/luisguimaraesp/",
    "inserted-at": "2026-01-11T22:20:07Z",
    "updated-at": "2026-01-11T22:20:07Z"
}
```

### Access Original URL

**Endpoint:** `GET /{short_code}`

When accessing `http://localhost:3000/abc123`, you will be redirected to the original URL.