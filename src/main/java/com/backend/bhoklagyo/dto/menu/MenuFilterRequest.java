package com.backend.bhoklagyo.dto.menu;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class MenuFilterRequest {

    private String name;
    private Boolean veg;
    private String category;
    private Double minPrice;
    private Double maxPrice;
    private Integer preparationTime;
    

    @Min(0)
    private int page = 0;

    @Min(1)
    private int size = 10;

}
