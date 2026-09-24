package com.curso.pedidos.product.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductTest {

    private static final ProductCategory TECNOLOGIA = new ProductCategory(1L, "Tecnología");

    @Test
    void createsProductWithoutId() {
        Product product = Product.create("Teclado", new BigDecimal("59.90"), 15, TECNOLOGIA);

        assertNull(product.getId());
        assertEquals("Teclado", product.getName());
        assertEquals(TECNOLOGIA, product.getCategory());
    }

    @Test
    void hasStockOnlyWhenStockIsPositive() {
        assertTrue(Product.create("Teclado", BigDecimal.TEN, 1, TECNOLOGIA).hasStock());
        assertFalse(Product.create("Teclado", BigDecimal.TEN, 0, TECNOLOGIA).hasStock());
    }

    @Test
    void rejectsBlankName() {
        assertThrows(IllegalArgumentException.class,
                () -> Product.create(" ", BigDecimal.TEN, 1, TECNOLOGIA));
    }

    @Test
    void rejectsNonPositivePrice() {
        assertThrows(IllegalArgumentException.class,
                () -> Product.create("Teclado", BigDecimal.ZERO, 1, TECNOLOGIA));
    }

    @Test
    void rejectsNegativeStock() {
        assertThrows(IllegalArgumentException.class,
                () -> Product.create("Teclado", BigDecimal.TEN, -1, TECNOLOGIA));
    }

    @Test
    void rejectsMissingCategory() {
        assertThrows(NullPointerException.class,
                () -> Product.create("Teclado", BigDecimal.TEN, 1, null));
    }
}
