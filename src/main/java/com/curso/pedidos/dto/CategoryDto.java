package com.curso.pedidos.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryDto {

    private Long id;

    @NotBlank(message = "El nombre de la categoría es obligatorio")
    private String name;

    public CategoryDto() {
    }

    public CategoryDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
