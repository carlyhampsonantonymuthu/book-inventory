Book Inventory Management System
A modular, scalable, and testable backend application built using Spring Boot, designed to manage book inventory operations including categorization, author/publisher management, user handling, shopping cart functionality, and purchase logging.

🔧 Technologies Used

Java 21
Spring Boot 3.5.6
Spring Data JPA & Hibernate
MySQL
Lombok
Swagger (SpringDoc)
JUnit 5 & Mockito
Maven


🧱 Architecture
The application follows a three-layer architecture:

Controller Layer – Handles REST API endpoints.
Service Layer – Contains business logic.
Repository Layer – Interfaces with the database using Spring Data JPA.


🚀 Setup Instructions

Clone the repository:
Shellgit clone https://github.com/your-username/book-inventory.gitcd book-inventoryShow more lines

Configure application.properties with your MySQL credentials.
Run the application using:
Shellmvn spring-boot:runShow more lines

Access Swagger UI at:
http://localhost:8080/swagger-ui/index.html




📑 User Guide – Working with Endpoints
✅ Independent Modules
These modules can be tested directly via their respective controllers:

AuthorController
CategoryController
PublisherController
ReviewerController
PermRoleController
StateController

These endpoints do not require any dependent data and can be used for basic CRUD operations.
⚠️ Dependent Modules
These modules require pre-existing data from independent modules:

BookController – Requires Author, Category, Publisher
BookReviewController – Requires Book, Reviewer
BookConditionController – Requires Book
InventoryController – Requires Book, BookCondition
PurchaseLogController – Requires Inventory, User, ShoppingCart


⚠️ Important: Ensure all required entities are created before testing dependent endpoints to avoid ResourceNotFoundException.


🧪 Testing Strategy

Unit tests written using JUnit 5 and Mockito.
All service classes tested independently.
Repository mocking ensures database-free logic validation.
Green status confirmed in Eclipse JUnit Runner.


📈 Future Enhancements

Reintroduce Spring Security with JWT Authentication
Role-based access control (Admin, Customer, Publisher)
OTP/Email verification for registration
Pagination & Sorting
Redis Caching
Dockerization
CI/CD with GitHub Actions
Cloud deployment (AWS EC2 / Heroku)


👤 Author
Carly Hampson A
30108686
