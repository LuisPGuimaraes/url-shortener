# URL Shortener

A repository to improve my Clojure skills. The URL shortener is a common example used in coding challenges, interviews, and books.

The application allows users to convert long URLs into short links and access the original URL using the generated code.

## Features

- Create short URLs  
- Redirect to the original URL  
- Validation to prevent duplicate URLs
- Error handling for invalid requests

## Planned Features

- Add a web interface  
- Add authentication  
- Improve error handling and validations  

## Tech Stack

- Clojure  
- MongoDB  
- Ring/Reitit (Web framework)
- MongoDB Java Driver

## How to Run

1. Install Clojure  
2. Install and start MongoDB  
3. Configure environment variables (see `env.example`)
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

**Success Response (201 Created):**
```json
{
  "short-code": "8QLVJw",
  "short-url": "http://localhost:3000/8QLVJw",
  "original-url": "https://www.linkedin.com/in/luisguimaraesp/"
}
```

**Error Responses:**

**400 Bad Request** - Missing URL:
```json
{
  "error": "URL is required"
}
```

**409 Conflict** - URL already exists:
```json
{
  "error": "URL already exists in the database"
}
```

### Access Original URL (Redirect)

**Endpoint:** `GET /{short_code}`

When accessing a valid short URL, you will receive an HTTP **302 Found** redirect response and be automatically redirected to the original URL.

**Success Response (302 Found):**
```
HTTP/1.1 302 Found
Location: https://www.linkedin.com/in/luisguimaraesp/
```

**Error Responses:**

**400 Bad Request** - Missing short code:
```json
{
  "error": "Short code is required"
}
```

**404 Not Found** - Invalid or non-existent short code:
```json
{
  "error": "Short URL not found"
}
```

## Validation Rules

- **Duplicate Prevention**: Each long URL can only be shortened once. Attempting to shorten the same URL again will return a 409 Conflict error.
- **Required Fields**: The `url` field is mandatory in POST requests. Missing this field will return a 400 Bad Request error.
- **Valid Short Codes**: GET requests require a valid short code. Non-existent or malformed codes will return a 404 Not Found error.

## MongoDB document
<img width="635" height="300" alt="image" src="https://github.com/user-attachments/assets/2f69e9f3-c6d0-4f6a-9407-e799ec1823c7" />
