package com.rutuja.shopResistrationn.service;

import com.rutuja.shopResistrationn.model.ShopResistrationModel;
import com.rutuja.shopResistrationn.repo.ShopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ShopService {

    Logger log= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private R2dbcEntityTemplate r2dbcEntityTemplate;

    @Autowired
    private ShopRepository shopRepository;

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
                .onErrorResume(e -> Mono.just("Error in saving shop")));
    }

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
                .switchIfEmpty(Mono.just("Shop not found"))
                .onErrorResume(e->{
                    log.error("Error during shop find: ", e);
                    return  Mono.just("Error in shop find");

                });
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

    public Flux<ShopResistrationModel> getAllShops() {
        return shopRepository.findAll()
                .flatMap(existingShop -> Mono.just(existingShop))
                .switchIfEmpty(Mono.error(new Exception("shop not found")))
                .onErrorResume(e->{
                    log.error("Error during shop find: ", e);
                    return  Mono.error(new Exception("Error in shop find"));
                });
    }


}
