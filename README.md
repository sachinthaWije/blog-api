# Blog API - Spring Boot & MongoDB

This is a RESTful API for a simple blog application that handles users, posts, and comments. The API allows user registration, authentication, CRUD operations on posts and comments, and more. This project is built using Spring Boot and MongoDB.

## Features

- User Registration and Login: Authentication using JWT (JSON Web Tokens).
- CRUD Operations:
    - Posts: Users can create, update, and delete their own posts.
    - Comments: Users can add, edit, and delete their own comments on posts.
- Pagination: Get paginated lists of posts.
- Search & Filter: Search posts by title or filter by status (published/draft).
- Role-Based Access Control (RBAC): Admins can manage all posts and comments.
- Validation: Validates inputs for fields like email, password, and post title.
- Security: Protect API routes, ensuring only authorized users can perform actions.



## Tech Stack


**Spring Boot:** Backend framework.

**MongoDB:** NoSQL database to store users, posts, and comments.

**Spring Security:** For authentication and authorization.

**JWT:** For stateless authentication.

**Maven:** For building and dependency management.


## Prerequisites

**Java:** JDK 11 or later.

**Maven:** Ensure Maven is installed on your machine.

**MongoDB:** Install and run MongoDB locally

**Postman:** (Optional) For testing API endpoints.
## Installation

1.Clone the Repository:

```bash
git clone https://github.com/your-username/blog-api.git
cd blog-api
```
2.Set up Mongo:

In src/main/resources/application.properties, set your MongoDB connection URI:
```bash
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=blog_system
```
3.Build the project:
```bash
mvn clean install
```
4.Run the application:
```bash
mvn spring-boot:run
```
    
## API Endpoints


| Method | Endpoint     | Description                |
| :-------- | :------- | :------------------------- |
| `POST` | `/api/auth/register` | Register a new user |
| `POST` | `/api/auth/login` | Login and obtain JWT token |
| `GET` | `/api/posts` | List all published posts (paginated) |
| `POST` | `/api/auth/register` | Create a new post (authenticated) |
| `PUT` | `/api/posts/{postId}` | Update a post (authenticated) |
| `DELETE` | `/api/posts/{postId}	` | Delete a post (authenticated) |
| `GET` | `/api/posts/{postId}	` | Get details of a post |
| `POST` | `/api/posts/{postId}/comments	` | Add a comment to a post |
| `PUT` | `/api/comments/{commentId}	` | Update a comment (authenticated) |
| `DELETE` | `/api/comments/{commentId}		` | Delete a comment (authentiated)|



## License

This project is licensed under the [MIT](https://choosealicense.com/licenses/mit/) License


