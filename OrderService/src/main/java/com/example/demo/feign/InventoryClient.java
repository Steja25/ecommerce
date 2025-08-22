//package com.example.demo.feign;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.*;
//
//import com.example.demo.dtos.InventoryDTO;
//
//@FeignClient(name = "inventory-service", path = "/api/inventory") // 👈 important: prefix added
//public interface InventoryClient {
//
//    @GetMapping("/{productId}")
//    InventoryDTO getInventory(@PathVariable("productId") Long productId);
//
//    @PutMapping("/{productId}")
//    void updateInventory(@PathVariable("productId") Long productId, @RequestBody InventoryDTO inventory);
//    
//}


package com.example.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "inventoryservice")
public interface InventoryClient {

    // Check stock
    @GetMapping("/api/inventory/{productId}")
    String checkStock(@PathVariable Long productId, @RequestParam int qty);

    // Reduce stock
    @PutMapping("api//inventory/reduce/{productId}")
    String reduceStock(@PathVariable Long productId, @RequestParam int qty);

    // Add stock (admin feature)
    @PostMapping("/api//inventory/add/{productId}")
    String addStock(@PathVariable Long productId, @RequestParam int qty);
}
