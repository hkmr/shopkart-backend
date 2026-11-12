package io.shopkart.shopkart.inventory.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invetory")
public class InventoryController {

    @GetMapping("/{productId}")
    public void stockDetail() {

    }

    @PutMapping("/{productId}")
    public void updateStock() {

    }
    
    public void stockReport() {

    }

}
