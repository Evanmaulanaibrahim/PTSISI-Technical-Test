# Vendor API Spec

## Create Vendor
Endpoint : POST /api/vendor-management/vendors

Request Header :
- X-API-TOKEN : Token (Mandatory)

Request Body :
```json
{
  "vendorname" : "Vendor A",
  "email" : "info@vendora.com",
  "address" : "Jln Pasar Mawar",
  "phone" : "08001"
}
```

Response Body (Success) :
```json
{
  "message" : "Vendor PT Baca Buku berhasil ditambahkan!",
  "statusCode" : 200,
  "status" : "OK"
}
```

Response Body (Error Duplicate/Conflict) :
```json
{
  "statusCode" : "500",
  "message" : "Kesalahan terjadi saat memproses permintaan!",
  "details": "..."
}
```

Response Body (Error Unauthorized)
```json
{
  "statusCode" : 401,
  "message" : "Unauthorized User!",
  "details" : "User belum terautentikasi!"
}
```

Response Body (Error Rate Limit) :
```json
{
  "statusCode" : "500",
  "message" : "Kesalahan terjadi saat memproses permintaan!",
  "details": "Anda telah mencapai batas permintaan. Silakan coba lagi nanti."
}
```

## Update Vendor
Endpoint : PUT /api/vendor-management/vendors/{vendorId}

Request Header :
- X-API-TOKEN : Token (Mandatory)

Request Body :
```json
{
  "vendorname" : "TOKO AA",
  "email" : "info@vendora.com",
  "address" : "Jln Pasar Mawar",
  "phone" : "08001"
}
```

Response Body (Success) :
```json
{
  "message" : "Vendor TOKO AA berhasil diubah!",
  "statusCode" : 200,
  "status" : "OK"
}
```

Response Body (Error Unauthorized)
```json
{
  "statusCode" : 401,
  "message" : "Unauthorized User!",
  "details" : "User belum terautentikasi!"
}
```

Response Body (Error Duplicate/Conflict) :
```json
{
  "statusCode" : "500",
  "message" : "Kesalahan terjadi saat memproses permintaan!",
  "details": "..."
}
```

Response Body (Error Rate Limit) :
```json
{
  "statusCode" : "500",
  "message" : "Kesalahan terjadi saat memproses permintaan!",
  "details": "Anda telah mencapai batas permintaan. Silakan coba lagi nanti."
}
```

## Get All Vendor
Endpoint : /api/vendor-management/vendors

Query Parameter :
- vendorName : String, vendor name, using like query, optional
- page : Integer, start from 0, default 0
- size : Integer, default 8
- sort : vendorName,asc (default)

Request Header :
- X-API-TOKEN : Token (Mandatory)

Response Body (Success) :
```json
{
  "total" : 2,
  "data" : [
    {
      "vendorId": "5ef8ad19-b1b2-4ad4-a971-89a984399869",
      "vendorName": "PT Penghapus",
      "vendorEmail": "info@penghapus.com",
      "vendorAddress": "Jln Pasar Mawar",
      "vendorPhone": "08001"
    },
    {
      "vendorId": "601ef675-6e75-40f8-8f9e-94d3e4f63cc2",
      "vendorName": "PT Penggaris",
      "vendorEmail": "info@penggaris.com",
      "vendorAddress": "Jln Pasar Melati",
      "vendorPhone": "08002"
    }
  ],
  "message": "Berhasil memuat My Vendor!",
  "statusCode": 200,
  "status": "OK"
}
```

Response Body (Error Not Found)
```json
{
  "statusCode": 404,
  "message": "Data tidak ditemukan!",
  "details": "Data tidak ditemukan!"
}
```

Response Body (Error Unauthorized)
```json
{
  "statusCode" : 401,
  "message" : "Unauthorized User!",
  "details" : "User belum terautentikasi!"
}
```

Response Body (Error Rate Limit) :
```json
{
  "statusCode" : "500",
  "message" : "Kesalahan terjadi saat memproses permintaan!",
  "details": "Anda telah mencapai batas permintaan. Silakan coba lagi nanti."
}
```

## GET Vendor By Id
Endpoint : /api/vendor-management/vendors/{vendorId}

Request Header :
- X-API-TOKEN : Token (Mandatory)

Response Body (Success) :
```json
"total": 1,
    "data": {
        "vendorId": "5ef8ad19-b1b2-4ad4-a971-89a984399869",
        "vendorName": "PT Penghapus",
        "vendorEmail": "info@penghapus.com",
        "vendorAddress": "Jln Pasar Mawar",
        "vendorPhone": "08001"
        "userId": "8455dd9b-ff97-4678-84bd-e760ba14c66c"
    },
    "message": "Vendor berhasil ditemukan!",
    "statusCode": 200,
    "status": "OK"
```

Response Body (Error Not Found)
```json
{
  "statusCode": 404,
  "message": "Data tidak ditemukan!",
  "details": "Data tidak ditemukan!"
}
```

Response Body (Error Unauthorized)
```json
{
  "statusCode" : 401,
  "message" : "Unauthorized User!",
  "details" : "User belum terautentikasi!"
}
```

Response Body (Error Rate Limit) :
```json
{
  "statusCode" : "500",
  "message" : "Kesalahan terjadi saat memproses permintaan!",
  "details": "Anda telah mencapai batas permintaan. Silakan coba lagi nanti."
}
```

## Remove Vendor
Endpoint : DELETE /api/vendor-management/vendors/{vendorId}

Request Header :
- X-API-TOKEN : Token (Mandatory)

Response Body (Success) :
```json
{
  "total": 0,
  "data": null,
  "message": "Vendor berhasil dihapus!",
  "statusCode": 200,
  "status": "OK"
}
```

Response Body (Error Not Found)
```json
{
  "statusCode": 404,
  "message": "Data tidak ditemukan!",
  "details": "Data tidak ditemukan!"
}
```

Response Body (Error Unauthorized)
```json
{
  "statusCode" : 401,
  "message" : "Unauthorized User!",
  "details" : "User belum terautentikasi!"
}
```

Response Body (Error Rate Limit) :
```json
{
  "statusCode" : "500",
  "message" : "Kesalahan terjadi saat memproses permintaan!",
  "details": "Anda telah mencapai batas permintaan. Silakan coba lagi nanti."
}
```