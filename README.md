# API de Pedidos — Arquitectura Tradicional (Clase 1)

Proyecto Spring Boot que sirve como **punto de partida** para el curso.
Implementa el dominio de la API de Pedidos con la arquitectura tradicional
por capas (`controller` → `service` → `repository` → `entity` / `dto`),
tal como se enseña en la Clase 1.

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

## Arquitectura hexagonal: módulo `product`

En la rama `feature/hexagonal-products` el módulo de productos se reorganiza
con Puertos y Adaptadores. El resto (categorías, clientes, pedidos) sigue con la
arquitectura por capas.

```
com.curso.pedidos.product
├── domain
│   ├── model        Product (Java puro, valida sus invariantes), ProductCategory
│   └── exception    ProductNotFoundException, CategoryNotFoundException
├── application
│   ├── port/in      CreateProductUseCase, GetProductUseCase, SearchProductsUseCase, CreateProductCommand
│   ├── port/out     ProductRepositoryPort, CategoryLookupPort
│   └── service      ProductService (@Service, implementa los puertos de entrada)
└── infrastructure
    ├── adapter/in/web           ProductController, ProductRequest/Response, ProductWebMapper
    └── adapter/out/persistence  ProductPersistenceAdapter, CategoryLookupAdapter,
                                 ProductEntity (JPA), ProductJpaRepository, ProductPersistenceMapper
```

Regla de dependencias: `infrastructure → application → domain`. El dominio no
importa nada de Spring ni de JPA; la aplicación solo usa `@Service` para registrar
el servicio como bean.

Notas:

* `ProductEntity` conserva el nombre de entidad JPA `Product` y la tabla `products`,
  así que el esquema y las consultas JPQL existentes no cambian.
* `OrderService` / `OrderItem` todavía usan `ProductEntity` y `ProductJpaRepository`
  directamente: es el código legado que se refactoriza en la Clase 2.
* `DataInitializer` crea los productos de ejemplo a través de `CreateProductUseCase`.
* Tests unitarios del dominio y del servicio (con puertos en memoria) en
  `src/test/java/com/curso/pedidos/product`.

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

