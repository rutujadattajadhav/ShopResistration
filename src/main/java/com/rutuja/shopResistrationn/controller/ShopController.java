package com.rutuja.shopResistrationn.controller;

import com.rutuja.shopResistrationn.model.ShopResistrationModel;
import com.rutuja.shopResistrationn.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class ShopController {

    @Autowired
    private ShopService saveShopService;

    //input- shopResistrationModel
    //output- Mono<String>- success or fail: if shop is saved successfully then return success otherwise fail
    //call saveShop method of saveShopService-input shopResistrationModel and return the Mono<String>
    @PostMapping("/saveShop")
    public Mono<String>  saveShop( @RequestBody ShopResistrationModel shopResistrationModel) {
        return saveShopService.saveShop(shopResistrationModel);
    }


    //input- shopResistrationModel
    //output- Mono<String>- success or fail: if shop is updated successfully then return success otherwise fail
    //call updateShop method of saveShopService-input shopResistrationModel and return Mono<String>
    @PutMapping("/updateShop")
    public Mono<String>  updateShop( @RequestBody ShopResistrationModel shopResistrationModel) {
        return saveShopService.updateShop(shopResistrationModel);
    }


    //input- contactNumber
    //output- Mono<String>- success or fail: if shop is deleted successfully then return success otherwise fail
    //call deleteShop method of saveShopService-input contactNumber and return Mono<String>
    @DeleteMapping("/deleteShop/{contact}")
    public Mono<String>  deleteShop(@PathVariable String contact) {
        return saveShopService.deleteShop(contact);
    }


    //input- contactNumber
    //output- Mono<ShopResistrationModel>- return the shopResistrationModel of the given contactNumber
    //call getShopById method of saveShopService-input contactNumber and return Mono<ShopResistrationModel>
    @GetMapping("/getShop/{contact}")
    public Mono<ShopResistrationModel> getShopById(@PathVariable String contact) {
        return saveShopService.getShopById(contact);
    }

    @GetMapping("/getAllShop")
    public Flux<ShopResistrationModel> getAllShop() {
        return saveShopService.getAllShops();
    }

    @GetMapping("/getAllShopById")
    public Flux<ShopResistrationModel> getAllShopById(@RequestBody List<String> contactIds) {
        return saveShopService.getShopByIds(contactIds);
    }
}
