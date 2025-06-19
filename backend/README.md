# Shop Management Application

A Spring Boot application for managing shop details and products.

## Features

- User authentication and authorization (Admin and Shop Owner roles)
- Shop management (CRUD operations)
- Product management (CRUD operations)
- Search and filter functionality
- JWT-based security
- RESTful API design

## Tech Stack

- Java 17
- Spring Boot 3.2.0
- Spring Security
- Spring Data JPA
- MySQL
- JWT for authentication
- Maven

## Prerequisites

- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

## Setup

1. Clone the repository:
```bash
git clone <repository-url>
cd shop-management
```

2. Configure the database:
- Create a MySQL database named `shop_management`
- Update the database credentials in `src/main/resources/application.properties`

3. Build the application:
```bash
mvn clean install
```

4. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Documentation

### Authentication

#### Register
- **POST** `/api/auth/register`
- **Body**:
```json
{
    "username": "string",
    "email": "string",
    "password": "string",
    "role": "SHOP_OWNER"
}
```

#### Login
- **POST** `/api/auth/login`
- **Body**:
```json
{
    "username": "string",
    "password": "string"
}
```

### Shops

#### Create Shop
- **POST** `/api/shops`
- **Headers**: `Authorization: Bearer <token>`
- **Body**:
```json
{
    "name": "string",
    "ownerName": "string",
    "email": "string",
    "phone": "string",
    "address": "string",
    "city": "string",
    "state": "string",
    "zipCode": "string",
    "description": "string",
    "openHours": "string"
}
```

#### Update Shop
- **PUT** `/api/shops/{id}`
- **Headers**: `Authorization: Bearer <token>`
- **Body**: Same as Create Shop

#### Delete Shop
- **DELETE** `/api/shops/{id}`
- **Headers**: `Authorization: Bearer <token>`

#### Get Shop
- **GET** `/api/shops/{id}`

#### List Shops
- **GET** `/api/shops`
- **Query Parameters**:
  - `page`: Page number (default: 0)
  - `size`: Page size (default: 10)
  - `sort`: Sort field (e.g., `name,asc`)

#### Search Shops
- **GET** `/api/shops/search?query={searchTerm}`
- **Query Parameters**:
  - `query`: Search term
  - `page`: Page number
  - `size`: Page size
  - `sort`: Sort field

### Products

#### Create Product
- **POST** `/api/shops/{shopId}/products`
- **Headers**: `Authorization: Bearer <token>`
- **Body**:
```json
{
    "name": "string",
    "category": "string",
    "price": "number",
    "stock": "integer",
    "imageUrl": "string"
}
```

#### Update Product
- **PUT** `/api/shops/{shopId}/products/{id}`
- **Headers**: `Authorization: Bearer <token>`
- **Body**: Same as Create Product

#### Delete Product
- **DELETE** `/api/shops/{shopId}/products/{id}`
- **Headers**: `Authorization: Bearer <token>`

#### Get Product
- **GET** `/api/shops/{shopId}/products/{id}`

#### List Products
- **GET** `/api/shops/{shopId}/products`
- **Query Parameters**:
  - `page`: Page number
  - `size`: Page size
  - `sort`: Sort field

#### Search Products
- **GET** `/api/shops/{shopId}/products/search?query={searchTerm}`
- **Query Parameters**:
  - `query`: Search term
  - `page`: Page number
  - `size`: Page size
  - `sort`: Sort field

## Security

- JWT-based authentication
- Role-based access control
- Password encryption using BCrypt
- CORS enabled for all origins

## Error Handling

The API returns appropriate HTTP status codes and error messages:

- 200: Success
- 201: Created
- 400: Bad Request
- 401: Unauthorized
- 403: Forbidden
- 404: Not Found
- 500: Internal Server Error

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License. 