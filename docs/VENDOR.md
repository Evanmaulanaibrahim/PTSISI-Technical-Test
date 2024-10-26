# Vendor API Spec

## Create Vendor
Endpoint : POST /api/vendors

Request Header :
- X-API-TOKEN : Token (Mandatory)

Request Body :
```json
{
  "vendorname" : "The Gade Coffee Bogor",
  "email" : "info@thegade.com",
  "address" : "Kota Bogor",
  "phone" : "08001"
}
```

Response Body (Success) :
```json
{
  "status" : "success",
  "message" : "Add new vendor successfully",
  "data" : {
    "vendorId" : "a1b2c3d4-e5f6-7890-1234-56789abcdef0",
    "vendorname" : "The Gade Coffe Bogor",
    "email" : "info@thegade.com",
    "address" : "Kota Bogor",
    "phone" : "08001",
    "createdAt" : "2024-10-25T10:00:00Z"
  }
}
```

Response Body (Error - 400 Bad Request) :
```json
{
  "status" : "error",
  "message" : "Validation error",
  "errors": [
    {
      "field": "vendorname",
      "message": "Vendor name is required"
    },
    {
      "field": "email",
      "message": "Invalid email format"
    }
  ]
}
```

Response Body (Error - 409 Conflict) :
```json
{
  "status" : "error",
  "message" : "Vendor with this email or phone already exists"
}
```

Response Body (Error - 500 Internal Server) :
```json
{
  "status" : "error",
  "message" : "An unexpected error occurred. Please try again later."
}
```

## Update Vendor
Endpoint : PUT /api/vendors/{vendorId}

Request Header :
- X-API-TOKEN : Token (Mandatory)

Request Body :
```json
{
  "vendorname" : "The Gade Coffee Bogor",
  "email" : "info@thegade.com",
  "address" : "Kota Bogor",
  "phone" : "08001"
}
```

Response Body (Success) :
```json
{
  "status" : "success",
  "message" : "Update vendor successfully",
  "data" : {
    "vendorId" : "a1b2c3d4-e5f6-7890-1234-56789abcdef0",
    "vendorname" : "The Gade Coffe Bogor",
    "email" : "info@thegade.com",
    "address" : "Kota Bogor",
    "phone" : "08001",
    "createdAt" : "2024-10-25T10:00:00Z"
  }
}
```

Response Body (Error - 400 Bad Request) :
```json
{
  "status" : "error",
  "message" : "Invalid input. Ensure all fields follow the required format."
}
```

Response Body (Error - 401 Unauthorized)
```json
{
  "status" : "error",
  "message" : "Unauthorized access. Please provide a valid token."
}
```

Response Body (Error - 409 Conflict) :
```json
{
  "status" : "error",
  "message" : "The email or phone provided is already in use. Please use different values."
}
```

Response Body (Error - 500 Internal Server) :
```json
{
  "status" : "error",
  "message" : "An unexpected error occurred. Please try again later."
}
```

## Get Vendor
Endpoint : /api/vendors/{vendorId}

Request Header :
- X-API-TOKEN : Token (Mandatory)

Response Body (Success) :
```json
{
  "status": "success",
  "message": "Vendor details retrieved successfully",
  "data": {
    "vendorId" : "a1b2c3d4-e5f6-7890-1234-56789abcdef0",
    "vendorname" : "The Gade Coffe Bogor",
    "email" : "info@thegade.com",
    "address" : "Kota Bogor",
    "phone" : "08001",
    "createdAt": "2024-10-25T10:00:00Z",
    "updatedAt": "2024-10-26T08:15:00Z"
  }
}
```

Response Body (Error - 401 Unauthorized) :
```json
{
  "status" : "error",
  "message" : "Unauthorized access. Please provide a valid token."
}
```

Response Body (Error - 404 Not Found) :
```json
{
  "status": "error",
  "message": "Vendor not found"
}
```

Response Body (Error - 500 Internal Server) :
```json
{
  "status" : "error",
  "message" : "An unexpected error occurred. Please try again later."
}
```

## Search Vendor
Endpoint : GET /api/vendors

Request Header :
- X-API-TOKEN : Token (Mandatory)

Request Parameter :
- Query : vendorname or part of the name (Optional)

Response Body (Success) :
```json
{
  "status": "success",
  "message": "Vendors retrieved successfully",
  "data": [
    {
      "vendorId": "a1b2c3d4-e5f6-7890-1234-56789abcdef0",
      "vendorname": "The Gade Coffee Bogor",
      "email": "info@thegade.com",
      "address": "Kota Bogor",
      "phone": "08001",
      "createdAt": "2024-10-25T10:00:00Z"
    }
  ]
}
```

Response Body (Error - 401 Unauthorized) :
```json
{
  "status" : "error",
  "message" : "Unauthorized access. Please provide a valid token."
}
```

Response Body (Error - 404 Not Found) :
```json
{
  "status": "error",
  "message": "No vendors found matching the query."
}
```

Response Body (Error - 500 Internal Server) :
```json
{
  "status": "error",
  "message": "An unexpected error occurred. Please try again later."
}
```

## Remove Vendor
Endpoint : DELETE /api/vendors/{vendorId}

Request Header :
- X-API-TOKEN : Token (Mandatory)

Response Body (Success) :
```json
{
  "status": "success",
  "message": "Vendor deleted successfully"
}
```

Response Body (Error - 401 Unauthorized) :
```json
{
  "status" : "error",
  "message" : "Unauthorized access. Please provide a valid token."
}
```

Response Body (Error - 404 Not Found)
```json
{
  "status": "error",
  "message": "Vendor not found"
}
```

Response Body (Error - 500 Internal Server) :
```json
{
  "status": "error",
  "message": "An unexpected error occurred. Please try again later."
}
```