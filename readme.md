[//]: # (# Getting Started)

[//]: # (### Reference Documentation)

[//]: # ()
[//]: # (For further reference, please consider the following sections:)

[//]: # ()
[//]: # (* [Official Apache Maven documentation]&#40;https://maven.apache.org/guides/index.html&#41;)

[//]: # (* [Spring Boot Maven Plugin Reference Guide]&#40;https://docs.spring.io/spring-boot/4.1.2/maven-plugin&#41;)

[//]: # (* [Create an OCI image]&#40;https://docs.spring.io/spring-boot/4.1.2/maven-plugin/build-image.html&#41;)

[//]: # (* [Spring Data JDBC]&#40;https://docs.spring.io/spring-boot/4.1.2/reference/data/sql.html#data.sql.jdbc&#41;)

[//]: # (* [Flyway Migration]&#40;https://docs.spring.io/spring-boot/4.1.2/how-to/data-initialization.html#howto.data-initialization.migration-tool.flyway&#41;)

[//]: # (* [Spring Security]&#40;https://docs.spring.io/spring-boot/4.1.2/reference/web/spring-security.html&#41;)

[//]: # (* [Spring Web]&#40;https://docs.spring.io/spring-boot/4.1.2/reference/web/servlet.html&#41;)

[//]: # ()
[//]: # (### Guides)

[//]: # ()
[//]: # (The following guides illustrate how to use some features concretely:)

[//]: # ()
[//]: # (* [Using Spring Data JDBC]&#40;https://github.com/spring-projects/spring-data-examples/tree/main/jdbc/basics&#41;)

[//]: # (* [Securing a Web Application]&#40;https://spring.io/guides/gs/securing-web/&#41;)

[//]: # (* [Spring Boot and OAuth2]&#40;https://spring.io/guides/tutorials/spring-boot-oauth2/&#41;)

[//]: # (* [Authenticating a User with LDAP]&#40;https://spring.io/guides/gs/authenticating-ldap/&#41;)

[//]: # (* [Building a RESTful Web Service]&#40;https://spring.io/guides/gs/rest-service/&#41;)

[//]: # (* [Serving Web Content with Spring MVC]&#40;https://spring.io/guides/gs/serving-web-content/&#41;)

[//]: # (* [Building REST services with Spring]&#40;https://spring.io/guides/tutorials/rest/&#41;)


# ShopKart Ecommerce Backend Application (Spring Boot)

## 1. Project Scope

**Actors:** Customer, Seller (optional), Admin.

**Core capabilities:** user registration and login, product catalog, cart, checkout and orders, payments, inventory, reviews, and admin management.

**Phase 1 (MVP):** Auth, Users, Products, Categories, Cart, Orders, Payment (mock), Admin.

**Phase 2:** Reviews, Wishlist, Coupons, Notifications, Search, Caching.

**Phase 3:** Docker, CI/CD, Kafka/RabbitMQ events, microservice split, monitoring.

---

## 2. Functional Requirements

**Customer**

- Register, log in, log out, refresh token, reset password, verify email
- Manage profile and multiple addresses
- Browse, search, filter and sort products; view product details
- Add, update and remove cart items
- Apply coupon, checkout, place order, pay
- View order history, track status, cancel order, request return
- Write reviews and ratings, manage wishlist

**Seller (optional)**

- Create and manage own products and stock
- View orders for own products

**Admin**

- Manage users, categories, products, orders, coupons
- View reports (sales, top products, low stock)

---

## 3. Non-Functional Requirements

| Area | Requirement |
| --- | --- |
| Security | JWT auth, BCrypt password hashing, role-based access, input validation, HTTPS |
| Performance | Pagination everywhere, DB indexes, caching for catalog (Redis) |
| Scalability | Stateless services so you can run multiple instances behind a load balancer |
| Reliability | Transactions for order and payment, idempotent payment callbacks |
| Maintainability | Layered architecture, DTOs, global exception handling, clean packages |
| Observability | Structured logs, health checks, metrics (Actuator, Prometheus) |
| Documentation | OpenAPI/Swagger, README, Postman collection |
| Testing | Unit tests plus integration tests (Testcontainers) |

--- 

## 4. Technology Stack

| Concern | Choice |
| --- | --- |
| Language / Framework | Java 17 or 21, Spring Boot 3.x |
| Web | Spring Web (REST) |
| Security | Spring Security + JWT (jjwt) |
| Persistence | Spring Data JPA (Hibernate) |
| Database | PostgreSQL or MySQL |
| Migrations | Flyway (preferred) or Liquibase |
| Validation | Jakarta Bean Validation |
| Mapping | MapStruct |
| Boilerplate | Lombok |
| Cache | Redis (Spring Cache) |
| Search (later) | Elasticsearch or PostgreSQL full-text |
| Messaging (later) | Kafka or RabbitMQ |
| File storage | Local disk in dev, AWS S3 or Cloudinary in prod |
| Payments | Stripe or Razorpay (test mode) |
| Email | Spring Mail (SMTP) |
| Docs | springdoc-openapi (Swagger UI) |
| Testing | JUnit 5, Mockito, Testcontainers, MockMvc |
| Build | Maven or Gradle |
| DevOps | Docker, Docker Compose, GitHub Actions |

---

## 5. Folder Structure

```
ecommerce-backend/
├── pom.xml                          (or build.gradle)
├── Dockerfile
├── docker-compose.yml               (app, postgres, redis, mailhog)
├── .env.example
├── .gitignore
├── README.md
├── .github/workflows/ci.yml
│
├── src/main/java/com/example/shop/
│   ├── ShopApplication.java
│   │
│   ├── common/
│   │   ├── entity/BaseEntity.java
│   │   ├── dto/ApiResponse.java, PageResponse.java, ErrorResponse.java
│   │   ├── exception/
│   │   │   ├── GlobalExceptionHandler.java
│   │   │   ├── ResourceNotFoundException.java
│   │   │   ├── DuplicateResourceException.java
│   │   │   ├── OutOfStockException.java
│   │   │   ├── InvalidCouponException.java
│   │   │   └── InvalidOrderStateException.java
│   │   ├── util/SlugUtil.java, OrderNumberGenerator.java
│   │   └── constants/AppConstants.java
│   │
│   ├── config/
│   │   ├── SecurityConfig.java
│   │   ├── JpaAuditingConfig.java
│   │   ├── CacheConfig.java
│   │   ├── OpenApiConfig.java
│   │   ├── AsyncConfig.java
│   │   ├── SchedulerConfig.java
│   │   └── CorsConfig.java
│   │
│   ├── auth/
│   │   ├── controller/AuthController.java
│   │   ├── service/AuthService.java, AuthServiceImpl.java
│   │   ├── security/
│   │   │   ├── JwtService.java
│   │   │   ├── JwtAuthenticationFilter.java
│   │   │   ├── CustomUserDetails.java
│   │   │   └── CustomUserDetailsService.java
│   │   ├── entity/RefreshToken.java, VerificationToken.java
│   │   ├── repository/RefreshTokenRepository.java, VerificationTokenRepository.java
│   │   └── dto/RegisterRequest.java, LoginRequest.java, AuthResponse.java
│   │
│   ├── user/
│   │   ├── controller/UserController.java, AdminUserController.java
│   │   ├── service/UserService.java, UserServiceImpl.java
│   │   ├── entity/User.java, Role.java, Address.java
│   │   ├── repository/UserRepository.java, RoleRepository.java, AddressRepository.java
│   │   ├── dto/ ...
│   │   ├── mapper/UserMapper.java
│   │   └── enums/RoleName.java, UserStatus.java, AddressType.java
│   │
│   ├── catalog/
│   │   ├── controller/ProductController.java, CategoryController.java, BrandController.java
│   │   ├── service/ProductService.java, CategoryService.java, BrandService.java
│   │   ├── entity/Product.java, ProductImage.java, Category.java, Brand.java
│   │   ├── repository/ProductRepository.java, CategoryRepository.java, BrandRepository.java
│   │   ├── specification/ProductSpecification.java   (dynamic filters)
│   │   ├── dto/ ...
│   │   └── mapper/ProductMapper.java, CategoryMapper.java
│   │
│   ├── inventory/
│   │   ├── controller/InventoryController.java
│   │   ├── service/InventoryService.java
│   │   ├── entity/Inventory.java, StockReservation.java
│   │   ├── repository/InventoryRepository.java, StockReservationRepository.java
│   │   ├── scheduler/ReservationCleanupJob.java
│   │   └── enums/ReservationStatus.java
│   │
│   ├── cart/
│   │   ├── controller/CartController.java
│   │   ├── service/CartService.java
│   │   ├── entity/Cart.java, CartItem.java
│   │   ├── repository/CartRepository.java
│   │   ├── dto/ ...
│   │   └── mapper/CartMapper.java
│   │
│   ├── order/
│   │   ├── controller/OrderController.java, AdminOrderController.java
│   │   ├── service/OrderService.java, CheckoutService.java
│   │   ├── entity/Order.java, OrderItem.java, OrderStatusHistory.java,
│   │   │          ReturnRequest.java, ShippingAddressSnapshot.java (@Embeddable)
│   │   ├── repository/ ...
│   │   ├── dto/ ...
│   │   ├── mapper/OrderMapper.java
│   │   ├── event/OrderPlacedEvent.java, OrderPaidEvent.java
│   │   └── enums/OrderStatus.java, ReturnStatus.java
│   │
│   ├── payment/
│   │   ├── controller/PaymentController.java, PaymentWebhookController.java
│   │   ├── service/PaymentService.java
│   │   ├── gateway/PaymentGateway.java (interface),
│   │   │           StripeGateway.java, RazorpayGateway.java, MockGateway.java
│   │   ├── entity/Payment.java, Refund.java
│   │   ├── repository/ ...
│   │   └── enums/PaymentStatus.java, PaymentMethod.java, RefundStatus.java
│   │
│   ├── coupon/
│   │   ├── controller/AdminCouponController.java
│   │   ├── service/CouponService.java
│   │   ├── entity/Coupon.java, CouponUsage.java
│   │   └── repository/ ...
│   │
│   ├── review/        (controller, service, entity, repository, dto)
│   ├── wishlist/      (controller, service, entity, repository)
│   │
│   ├── notification/
│   │   ├── service/NotificationService.java, EmailService.java
│   │   ├── listener/OrderEventListener.java
│   │   └── entity/Notification.java
│   │
│   ├── admin/
│   │   ├── controller/ReportController.java
│   │   └── service/ReportService.java
│   │
│   └── storage/
│       ├── FileStorageService.java (interface)
│       ├── LocalFileStorageService.java
│       └── S3FileStorageService.java
│
├── src/main/resources/
│   ├── application.yml
│   ├── application-dev.yml
│   ├── application-test.yml
│   ├── application-prod.yml
│   ├── db/migration/
│   │   ├── V1__init_schema.sql
│   │   ├── V2__seed_roles.sql
│   │   └── V3__seed_sample_data.sql
│   └── templates/email/order-confirmation.html, password-reset.html
│
└── src/test/java/com/example/shop/
├── auth/AuthServiceTest.java, AuthControllerTest.java
├── catalog/ProductServiceTest.java, ProductRepositoryTest.java
├── cart/CartServiceTest.java
├── order/CheckoutServiceTest.java, OrderFlowIntegrationTest.java
├── inventory/InventoryConcurrencyTest.java
├── payment/PaymentWebhookTest.java
└── support/AbstractIntegrationTest.java   (Testcontainers base class)
```

## 6. API Endpoints

Base path: `/api/v1`. All list endpoints support `?page=0&size=20&sort=createdAt,desc`.

###  Auth

| Method | Endpoint | Description | Access |
| --- | --- | --- | --- |
| POST | /auth/register | Create account | Public |
| POST | /auth/login | Returns access and refresh tokens | Public |
| POST | /auth/refresh | Get a new access token | Public |
| POST | /auth/logout | Revoke refresh token | Auth |
| POST | /auth/forgot-password | Send reset email | Public |
| POST | /auth/reset-password | Reset using token | Public |
| GET | /auth/verify-email?token= | Verify email | Public |

### Users and Addresses

| Method | Endpoint | Description | Access |
| --- | --- | --- | --- |
| GET | /users/me | Current profile | Auth |
| PUT | /users/me | Update profile | Auth |
| PUT | /users/me/password | Change password | Auth |
| GET | /users/me/addresses | List addresses | Auth |
| POST | /users/me/addresses | Add address | Auth |
| PUT | /users/me/addresses/{id} | Update address | Auth |
| DELETE | /users/me/addresses/{id} | Delete address | Auth |
| GET | /admin/users | List users | Admin |
| PATCH | /admin/users/{id}/status | Block or unblock user | Admin |

###  Catalog

| Method | Endpoint | Description | Access |
| --- | --- | --- | --- |
| GET | /products | List with filters: `q`, `categoryId`, `brandId`, `minPrice`, `maxPrice`, `rating`, `sort` | Public |
| GET | /products/{id} | Product detail | Public |
| GET | /products/slug/{slug} | Detail by slug | Public |
| POST | /products | Create product | Admin/Seller |
| PUT | /products/{id} | Update product | Admin/Seller (owner) |
| DELETE | /products/{id} | Soft delete | Admin/Seller (owner) |
| POST | /products/{id}/images | Upload images (multipart) | Admin/Seller |
| DELETE | /products/{id}/images/{imageId} | Remove image | Admin/Seller |
| GET | /categories | Category tree | Public |
| GET | /categories/{id}/products | Products in category | Public |
| POST | /categories | Create category | Admin |
| PUT | /categories/{id} | Update category | Admin |
| DELETE | /categories/{id} | Delete category | Admin |
| GET | /brands | List brands | Public |
| POST | /brands | Create brand | Admin |

### Inventory

| Method | Endpoint | Description | Access |
| --- | --- | --- | --- |
| GET | /inventory/{productId} | Stock level | Admin/Seller |
| PUT | /inventory/{productId} | Set or adjust stock | Admin/Seller |
| GET | /admin/inventory/low-stock?threshold=10 | Low stock report | Admin |

###  Cart

| Method | Endpoint | Description | Access |
| --- | --- | --- | --- |
| GET | /cart | View cart with totals | Customer |
| POST | /cart/items | Add item `{productId, quantity}` | Customer |
| PUT | /cart/items/{itemId} | Change quantity | Customer |
| DELETE | /cart/items/{itemId} | Remove item | Customer |
| DELETE | /cart | Clear cart | Customer |
| POST | /cart/coupon | Apply coupon `{code}` | Customer |
| DELETE | /cart/coupon | Remove coupon | Customer |

### Orders

| Method | Endpoint | Description | Access |
| --- | --- | --- | --- |
| POST | /orders/checkout | Create order from cart `{addressId, paymentMethod}` | Customer |
| GET | /orders | My orders | Customer |
| GET | /orders/{id} | Order detail | Customer (owner) |
| POST | /orders/{id}/cancel | Cancel if not shipped | Customer |
| POST | /orders/{id}/return | Request return | Customer |
| GET | /orders/{id}/invoice | Download invoice (PDF) | Customer |
| GET | /admin/orders | All orders, filter by status/date | Admin |
| PATCH | /admin/orders/{id}/status | Update status (ship, deliver) | Admin |

### Payments

| Method | Endpoint | Description | Access |
| --- | --- | --- | --- |
| POST | /payments/initiate | Create payment intent for an order | Customer |
| POST | /payments/webhook | Gateway callback (verify signature) | Public (signed) |
| GET | /payments/{orderId} | Payment status | Customer |
| POST | /admin/payments/{id}/refund | Refund payment | Admin |

### Reviews and Wishlist

| Method | Endpoint | Description | Access |
| --- | --- | --- | --- |
| GET | /products/{id}/reviews | List reviews | Public |
| POST | /products/{id}/reviews | Add review (only if purchased) | Customer |
| PUT | /reviews/{id} | Edit own review | Customer |
| DELETE | /reviews/{id} | Delete review | Owner/Admin |
| GET | /wishlist | My wishlist | Customer |
| POST | /wishlist/{productId} | Add | Customer |
| DELETE | /wishlist/{productId} | Remove | Customer |

### Coupons and Reports (Admin)

| Method | Endpoint | Description | Access |
| --- | --- | --- | --- |
| POST | /admin/coupons | Create coupon | Admin |
| GET | /admin/coupons | List coupons | Admin |
| PUT | /admin/coupons/{id} | Update coupon | Admin |
| DELETE | /admin/coupons/{id} | Deactivate coupon | Admin |
| GET | /admin/reports/sales?from=&to= | Sales summary | Admin |
| GET | /admin/reports/top-products | Best sellers | Admin |

### Operational

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | /actuator/health | Health check |
| GET | /swagger-ui.html | API documentation |


