package com.rutuja.shopResistrationn.service;

import com.rutuja.shopResistrationn.model.ShopResistrationModel;
import com.rutuja.shopResistrationn.repo.ShopRepository;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ShopService {

    Logger log= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private R2dbcEntityTemplate r2dbcEntityTemplate;

    @Autowired
    private ShopRepository shopRepository;
 //method=saveShop
//input=ShopResistrationModel
//output=Mono<String>
//call findByShopOwnerContact() method of shopRepository by passing shopOwnerContact of input
// if it returns a value then call flatmap() return "Shop already exists" ,
// if not then call switchEmpty() inside this call insert() method of r2dbcEntityTemplate by passing input and return
// "Shop saved successfully" if it returns a value,
// if not then return "Error in saving shop" and if any error occurs then return "Error in find shop"
    public Mono<String> saveShop(ShopResistrationModel shopResistrationModel) {
        return shopRepository.findByShopOwnerContact(shopResistrationModel.getShopOwnerContact())
                .flatMap(existingShop -> Mono.just("Shop already exists"))
                .switchIfEmpty(
                        r2dbcEntityTemplate.insert(shopResistrationModel)
                                .flatMap(shop -> Mono.just("Shop saved successfully"))
                                .switchIfEmpty(Mono.just("Error in saving shop"))
                                .onErrorResume(e ->{
                                    log.error("Error during user updation: ", e);
                                  return  Mono.just("Error in saving shop");

                                }
                )
                .onErrorResume(e -> Mono.just("Error in find shop")));
    }

  //method=updateShop
//input=ShopResistrationModel
//output=Mono<String>
//call findByShopOwnerContact() method of shopRepository by passing shopOwnerContact of input
// if it returns a value then call flatmap() call update() method of r2dbcEntityTemplate by passing input and return "Shop updated successfully" ,
// if not then call switchEmpty() return "Shop not found" and if any error occurs then return "Error in user find"

    public Mono<String> updateShop(ShopResistrationModel shopResistrationModel) {
        return shopRepository.findByShopOwnerContact(shopResistrationModel.getShopOwnerContact())
                .flatMap(existingShop -> {
                    shopResistrationModel.setShopId(existingShop.getShopId());
                    return r2dbcEntityTemplate.update(shopResistrationModel)
                            .flatMap(shop -> Mono.just("Shop updated successfully"))
                            .switchIfEmpty(Mono.just("Error in updating shop"))
                            .onErrorResume(e ->{
                                log.error("Error during user updation: ", e);
                                return  Mono.just("Error in updating shop");

                            });
                })
                .switchIfEmpty(Mono.just("Shop not found"))
                .onErrorResume(e->{
                    log.error("Error during user find: ", e);
                    return  Mono.just("Error in user find");

                });
    }

    public Mono<String> deleteShop(String contact) {
        return shopRepository.findByShopOwnerContact(contact)
                        .flatMap(existingShop ->shopRepository.delete(existingShop) )
                        .flatMap(shop -> Mono.just("Shop not deleted successfully"))
                        .switchIfEmpty(Mono.just("Shop  deleted"))
                        .onErrorResume(e->{
                            log.error("Error during delete shop: ", e);
                            return  Mono.just("Error in delete shop");
                        })
                .switchIfEmpty(Mono.just("Shop not found"));


    }

    public Mono<ShopResistrationModel> getShopById(String contact) {
        return shopRepository.findByShopOwnerContact(contact)
                .flatMap(existingShop -> Mono.just(existingShop))
                .switchIfEmpty(Mono.error(new Exception("shop not found")))
                .onErrorResume(e->{
                    log.error("Error during shop find: ", e);
                    return  Mono.error(new Exception("Error in shop find"));

                });
    }

    //method=getAllShops
//input=void
//output=Flux<ShopResistrationModel>
//call findAll() method of shopRepository
// if it returns a value then call flatmap() return existingShop,
// if not then call switchEmpty() return "shop not found" and if any error occurs then return "Error in shop find"
    public Flux<ShopResistrationModel> getAllShops() {
        return shopRepository.findAll()
                .flatMap(existingShop -> Mono.just(existingShop))
                .switchIfEmpty(Mono.error(new Exception("shop not found")))
                .onErrorResume(e->{
                    log.error("Error during shop find: ", e);
                    return  Mono.error(new Exception("Error in shop find"));
                });
    }

    //method=getShopByIds
//input=List<String>
//output=Flux<ShopResistrationModel>
//call findAllById() method of shopRepository by passing contactIds
// if it returns a value then call flatmap() return existingShop,
// if not then call switchEmpty() return "shop not found" and if any error occurs then return "Error in shop find"
    public Flux<ShopResistrationModel> getShopByIds(List<String> contactIds){
        return shopRepository.findAllById(contactIds)
                .flatMap(existingShop -> Mono.just(existingShop))
                .switchIfEmpty(Mono.error(new Exception("shop not found")))
                .onErrorResume(e->{
                    log.error("Error during shop find: ", e);
                    return  Mono.error(new Exception("Error in shop find"));
                });
    }

}
