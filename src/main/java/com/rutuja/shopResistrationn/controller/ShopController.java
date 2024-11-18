package com.rutuja.shopResistrationn.controller;

import com.rutuja.shopResistrationn.model.ShopResistrationModel;
import com.rutuja.shopResistrationn.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ShopController {

    @Autowired
    private ShopService saveShopService;

    @PostMapping("/saveShop")
    public Mono<String>  saveShop( @RequestBody ShopResistrationModel shopResistrationModel) {
        return saveShopService.saveShop(shopResistrationModel);
    }

    @PutMapping("/updateShop")
    public Mono<String>  updateShop( @RequestBody ShopResistrationModel shopResistrationModel) {
        return saveShopService.updateShop(shopResistrationModel);
    }

    @DeleteMapping("/deleteShop/{contact}")
    public Mono<String>  deleteShop(@PathVariable String contact) {
        return saveShopService.deleteShop(contact);
    }

    @GetMapping("/getShop/{contact}")
    public Mono<ShopResistrationModel> getShopById(@PathVariable String contact) {
        return saveShopService.getShopById(contact);
    }

    @GetMapping("/getAllShop")
    public Flux<ShopResistrationModel> getAllShop() {
        return saveShopService.getAllShops();
    }

}
