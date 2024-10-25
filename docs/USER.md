# User API Spec

## Register User

- Endpoint : POST /api/users
- Request Body : 
```json
{
  "username" : "Evanmaulanaibrahim",
  "fullname" : "Evan Maulana Ibrahim",
  "password" : "Admin001",
  "retypePassword" : "Admin001"
}
```
- Response Body (Success) :
```json
{
  "status" : "success",
  "message" : "User registered successfully",
  "data" : {
    "userId" : "a1b2c3d4-e5f6-7890-1234-56789abcdef0",
    "username" : "Evanmaulanaibrahim",
    "fullname" : "Evan Maulana Ibrahim",
    "createdAt" : "2024-10-25T10:00:00Z"
  }
}
```
- Response Body (Error - 404 Bad Request) :
```json
{
  "status" : "error",
  "message" : "validation error",
  "errors": [
    {
      "field": "username",
      "message": "Username must be at least 4 characters"
    },
    {
      "field": "password",
      "message": "Password must contain at least 8 characters"
    },
    {
      "field": "retypePassword",
      "message": "Passwords do not match"
    }
  ]
}
```
- Response Body (Error - 409 Conflict) :
```json
{
  "status" : "error",
  "message" : "Username already exists"
}
```
- Response Body (Error - 500 Internal Server)
```json
{
  "status" : "error",
  "message" : "An unexpected error occurred. Please try again later."
}
```

## Login User

- Endpoint : POST /api/auth/login
- Request Body :
```json
{
  "username" : "Evanmaulanaibrahim",
  "password" : "Admin001"
}
```
- Response Body (Success) :
```json
{
  "status": "success",
  "message": "Login successful",
  "data": {
    "userId": "a1b2c3d4-e5f6-7890-1234-56789abcdef0",
    "username": "Evanmaulanaibrahim",
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhdXRoIiwidXNlcklkIjoiYTFiMmMzZDQtZTVmNi03ODkwLTEyMzQtNTY3ODlhYmNkZWYwIiwiaWF0IjoxNjAxMjM0NTY3LCJleHAiOjE2MDEyNzg5Njd9.W8fAsdflkQj8Fjfjls8wGdGFI0LKtf0Pqsdf-dfgJkE",
    "tokenExpiry": "2024-10-25T16:00:00Z"
  }
}
```
- Response Body (Error - 401 Unauthorized) :
```json
{
  "status": "error",
  "message": "Invalid username or password"
}
```
- Response Body (Error - 400 Bad Request) : 
```json
{
  "status": "error",
  "message": "Validation error",
  "errors": [
    {
      "field": "username",
      "message": "Username is required"
    },
    {
      "field": "password",
      "message": "Password is required"
    }
  ]
}
```
- Response Body (Error - 500 Internal Server Error) :
```json
{
  "status": "error",
  "message": "An unexpected error occurred. Please try again later."
}
```

## Get User
- Endpoint : GET /api/users/current

- Request Header :
- X-API-TOKEN : Token (Mandatory)

- Response Body (Success) :
```json
{
  "status": "success",
  "message": "User details retrieved successfully",
  "data": {
    "userId": "a1b2c3d4-e5f6-7890-1234-56789abcdef0",
    "username": "Evanmaulanaibrahim",
    "fullname": "Evan Maulana Ibrahim",
    "email": "evanmaulanaibrahim@gmail.com",
    "createdAt": "2024-10-25T10:00:00Z",
    "updatedAt": "2024-10-26T08:15:00Z"
  }
}
```
- Response Body (Error - 401 Unauthorized) :
```json
{
  "status": "error",
  "message": "Unauthorized access. Please provide a valid token."
}
```
- Response Body (Error - 404 Not Found) :
```json
{
  "status": "error",
  "message": "User not found."
}
```

## Update User
- Endpoint : PATCH /api/users/current

- Request Header :
- X-API-TOKEN : Token (Mandatory)

- Request Body :
```json
{
  "fullname": "Evan Maulana Ibrahim New",
  "email": "evanmaulanaibrahim@gmail.com",
  "password": "newAdmin001"
}
```

- Response Body (Success) :
```json
{
  "status": "success",
  "message": "User details updated successfully",
  "data": {
    "userId": "a1b2c3d4-e5f6-7890-1234-56789abcdef0",
    "username": "Evanmaulanaibrahim",
    "fullname": "Evan Maulana Ibrahim New",
    "email": "evanmaulanaibrahim@gmail.com",
    "updatedAt": "2024-10-25T15:00:00Z"
  }
}
```

- Response Body (Error 400 Bad Request) :
```json
{
  "status": "error",
  "message": "Invalid input. Ensure fullname, email, and password follow the required format."
}
```

- Response Body (Error 401 Unauthorized) :
```json
{
  "status": "error",
  "message": "Unauthorized access. Please provide a valid token."
}
```

- Response Body (Error 409 Conflict) :
```json
{
  "status": "error",
  "message": "The email provided is already in use. Please use a different email."
}
```

## Logout User
- Endpoint :  POST /api/auth/logout

- Request Header :
- X-API-TOKEN : Token (Mandatory)

- Response Body (Success) :
```json
{
  "status": "success",
  "message": "User logged out successfully"
}
```