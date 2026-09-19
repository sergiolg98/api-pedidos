# API de Pedidos — Arquitectura Tradicional (Clase 1)

Proyecto Spring Boot que sirve como **punto de partida** para el curso.
Implementa el dominio de la API de Pedidos con la arquitectura tradicional
por capas (`controller` → `service` → `repository` → `entity` / `dto`),
tal como se enseña en la Clase 1.

Esta es a propósito la versión "antes": en la **Clase 2** se refactoriza en
vivo hacia SOLID + Arquitectura Hexagonal (Ports & Adapters). En particular,
fíjate en `OrderService`: mezcla a propósito crear el pedido, "enviar" un
email y "generar" una factura, y depende directamente de `JpaRepository` —
es el ejemplo de SRP/DIP de las diapositivas, hecho código real.

## Stack

* Java 17
* Spring Boot 3.3.4
* Spring Web + Spring Data JPA
* MySQL
* Bean Validation (`spring-boot-starter-validation`)
* springdoc-openapi (Swagger UI)

## Dominio

```
Customer 1 ── * Order 1 ── * OrderItem * ── 1 Product * ── 1 Category
```

## Cómo correrlo

1. Ten MySQL corriendo localmente (usuario/clave por defecto: `root` / `root`).
   La base de datos `pedidos_db` se crea sola gracias a
   `createDatabaseIfNotExist=true` en `application.properties`.
2. Ajusta `src/main/resources/application.properties` si tu usuario/clave
   de MySQL son distintos.
3. Levanta la app:

   ```bash
   mvn spring-boot:run
   ```

4. Al arrancar, `DataInitializer` carga categorías, productos y clientes de
   ejemplo automáticamente (solo si la base está vacía).

La app queda disponible en `http://localhost:8080`.
Swagger UI: `http://localhost:8080/swagger-ui.html`.

## Endpoints principales

### Categorías

* `POST /api/categories` — crear categoría
* `GET /api/categories` — listar
* `GET /api/categories/{id}`

### Clientes

* `POST /api/customers` — crear cliente
* `GET /api/customers` — listar
* `GET /api/customers/{id}`

### Productos

* `POST /api/products` — crear producto
* `GET /api/products` — listar
* `GET /api/products/{id}`
* `GET /api/products/search?name=teclado` — `findByNameContainingIgnoreCase`
* `GET /api/products/search?minPrice=20&minStock=1` — `findByPriceGreaterThanAndStockGreaterThan`
* `GET /api/products/available` — `@Query` JPQL (`WHERE p.stock > 0`)
* `GET /api/products/by-category?categoryName=Tecnología` — `findByCategoryName`

### Pedidos

* `POST /api/orders` — crear un pedido (caso de uso central de la Clase 2)

  ```json
  {
    "customerId": 1,
    "items": [
      { "productId": 1, "quantity": 2 },
      { "productId": 2, "quantity": 1 }
    ]
  }
  ```

* `GET /api/orders` — listar
* `GET /api/orders/by-customer-email?email=ana.torres@mail.com` — `findByCustomerEmail`
* `GET /api/orders/by-category?categoryName=Tecnología` — `@Query` con JOIN (JPQL)

## Qué se refactoriza en la Clase 2

* `OrderService` pasa a implementar un `CreateOrderUseCase` (Input Port).
* Se define `OrderRepositoryPort` (Output Port); `OrderJpaAdapter` lo implementa
  usando `OrderRepository` (Spring Data JPA) por dentro.
* `Order`, `OrderItem`, `Product`, `Customer`, `Category` se separan en un
  `domain/` sin anotaciones de JPA; las entidades `@Entity` quedan en
  `infrastructure/persistence/`.
* `sendConfirmationEmail` y `generateInvoice` se extraen a sus propias
  clases/adapters (SRP).
