# Api_MongoDB
API REST


## Quickstart: testing authentication with Postman
1. **Start the API** (for example: `mvn spring-boot:run`). Default port is `8080`.
2. **Register a user** (POST `http://localhost:8080/auth/register`) with JSON body:
   ```json
   {
     "username": "testuser",
     "email": "testuser@example.com",
     "password": "password123",
     "regionId": "<existing-region-id>",
     "role": "USER"
   }
   ```
3. **Login** to get a JWT (POST `http://localhost:8080/auth/login`) with JSON body:
   ```json
   {
     "username": "testuser",
     "password": "password123"
   }
   ```
   The response contains a `token` field (JWT string).
4. **Call protected endpoints** by adding an `Authorization` header to your Postman request:
   ```
   Authorization: Bearer <token>
   ```
   For example, to list products: GET `http://localhost:8080/product`, include the header above.

If you prefer cURL, the same flow works with:
```bash
# Login
TOKEN=$(curl -s -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"password123"}' | jq -r '.token')

# Call a protected endpoint
curl -H "Authorization: Bearer $TOKEN" http://localhost:8080/product
```


# Ejemplo de crear un admin

"""
Roles:

ROLE_ADMIN
ROLE_USER


Los usuarios con estos dominios:

@admin.com

@duocuc.cl

→ Registran automáticamente como ROLE_ADMIN

✔ Todos los demás:

→ Automáticamente ROLE_USER
"""
