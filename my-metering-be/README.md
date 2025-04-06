# Customer CRUD REST API - Spring Boot

## 1. Basic CRUD Endpoints

| Method  | Endpoint                | Description                  |
|---------|-------------------------|------------------------------|
| **POST**   | `/api/customers`         | Create a new customer       |
| **GET**    | `/api/customers`         | Get a list of all customers |
| **GET**    | `/api/customers/{id}`    | Get a single customer by ID |
| **PUT**    | `/api/customers/{id}`    | Update a customer by ID     |
| **PATCH**  | `/api/customers/{id}`    | Partially update a customer (e.g., update only email) |
| **DELETE** | `/api/customers/{id}`    | Delete a customer by ID     |

---

## 2. Search & Filtering

| Method  | Endpoint                          | Description                 |
|---------|-----------------------------------|-----------------------------|
| **GET**    | `/api/customers/search?name={name}`  | Find customers by name  |
| **GET**    | `/api/customers/search?email={email}` | Find customers by email |
| **GET**    | `/api/customers/search?city={city}`   | Find customers by city  |

---

## 3. Advanced Operations

| Method  | Endpoint                                      | Description                  |
|---------|---------------------------------------------|------------------------------|
| **GET**    | `/api/customers/paged?page={page}&size={size}` | Get paginated customers  |
| **GET**    | `/api/customers/sorted?sort=createdAt,desc` | Get customers sorted by created date |
| **POST**   | `/api/customers/{id}/activate`            | Activate a customer account |
| **POST**   | `/api/customers/{id}/deactivate`          | Deactivate a customer account |

---

## 4. Related Data (if customers have orders)

| Method  | Endpoint                            | Description                     |
|---------|-------------------------------------|---------------------------------|
| **GET**    | `/api/customers/{id}/orders`       | Get all orders of a customer  |
| **POST**   | `/api/customers/{id}/orders`       | Create a new order for a customer |
| **GET**    | `/api/customers/{id}/orders/{orderId}` | Get a specific order of a customer |

---

## 5. Bulk Operations

| Method  | Endpoint                         | Description                    |
|---------|----------------------------------|--------------------------------|
| **DELETE** | `/api/customers/bulk-delete`    | Delete multiple customers (send IDs in request body) |
| **PUT**    | `/api/customers/bulk-update`    | Bulk update customers |

---

## 6. Security & Authentication (If Needed)

| Method  | Endpoint              | Description                         |
|---------|-----------------------|-------------------------------------|
| **POST**   | `/api/auth/register` | Register a new customer            |
| **POST**   | `/api/auth/login`    | Login and get JWT token            |
| **GET**    | `/api/customers/me`  | Get currently logged-in customer details |

---

## Notes:
- **{id}** represents a customer’s unique ID.
- **Query parameters** (e.g., `?name=John`) allow filtering.
- **Bulk operations** require sending an array of IDs or objects in the request body.

