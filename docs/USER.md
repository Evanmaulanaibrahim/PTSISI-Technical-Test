# User API Spec

## Register User

Endpoint : POST /api/user-management/users/sign-up

Request Body :
```json
{
  "username" : "Admin001",
  "fullname" : "Evan Maulana Ibrahim",
  "email" : "Admin001@gmail.com",
  "password" : "Admin001",
  "retypePassword" : "Admin001"
}
```

Response Body (Success) :
```json
{
  "message" : "Berhasil menambahkan Admin001",
  "statusCode" : 200,
  "status" : "OK"
}
```

Response Body (Error Bad Request) :
```json
{
  "message" : "Format belum sesuai",
  "statusCode" : 400,
  "status": "Error"
}
```

Response Body (Error Duplicate/Conflict) :
```json
{
  "message" : "Username telah digunakan oleh user yang telah mendaftar sebelumnya",
  "statusCode" : 400,
  "status" : "ERROR"
}
```

Response Body (Error Internal Server) :
```json
{
  "message" : "Kesalahan terjadi saat memproses permintaan!",
  "statusCode" : 500,
  "status" : "ERROR"
}
```

## Login User

Endpoint : POST /api/user-management/users/sign-in

Request Body :
```json
{
  "username" : "Admin001",
  "password" : "Admin001"
}
```

Response Body (Success) :
```json
{
  "data": {
    "id": "8455dd9b-ff97-4678-84bd-e760ba14c66c",
    "token" : "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBZG1pbjAwMSIsImlhdCI6MTczMDA4Nzg5NCwiZXhwIjoxNzMwMTc0Mjk0fQ.f_mwn8ZACk3vuSXRjmzHm1bM5woYPQXYXfRMWOEtzCE",
    "type": "Bearer",
    "username" : "Admin001",
    "role" : "user"
  },
  "message" : "Auth User Success",
  "statusCode" : 200,
  "status" : "OK"
}
```

Response Body (Error Auth) :
```json
{
  "data": null,
  "message": "Username tidak terdaftar",
  "statusCode" : 401,
  "status" : "UNAUTHORIZED"
}
```

```json
{
  "data": null,
  "message": "Kata sandi tidak sesuai",
  "statusCode" : 401,
  "status" : "UNAUTHORIZED"
}
```

Response Body (Error Rate Limit) :
```json
{
  "data": null,
  "message": "Rate limit exceeded. Try again later.",
  "statusCode" : 429,
  "status" : "TOO_MANY_REQUESTS"
}
```

Response Body (Error Internal Server) :
```json
{
  "data": null,
  "message": "Kesalahan terjadi saat memproses permintaan!",
  "statusCode" : 500,
  "status" : "Internal Server Error"
}
```