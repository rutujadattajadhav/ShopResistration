package com.rutuja.shopResistrationn.repo;

import com.rutuja.shopResistrationn.model.ShopResistrationModel;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ShopRepository extends ReactiveCrudRepository<ShopResistrationModel,String> {


    @Query("SELECT * FROM shopresistration WHERE shopOwnerContact = :contact")
   public Mono<ShopResistrationModel> findByShopOwnerContact(String contact);


}
