package com.rutuja.shopResistrationn.service;

import com.rutuja.shopResistrationn.model.ShopResistrationModel;
import com.rutuja.shopResistrationn.repo.ShopRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ShopServiceTest {
    @InjectMocks
    private ShopService shopService;

    @Mock
    private ShopRepository shopRepository;

    @Mock
    private Mono<ShopResistrationModel> shopResistrationModelMono;

    @Mock
    private R2dbcEntityTemplate r2dbcEntityTemplate;

    @Test
    public void saveShopSuccess(){
        ShopResistrationModel shopResistrationModel=new ShopResistrationModel();
        shopResistrationModel.setShopName("BStore");
        shopResistrationModel.setShopAddress("pune");
        shopResistrationModel.setShopOwnerName("Bhakti");
        shopResistrationModel.setShopOwnerContact("9881050653");
        Mockito.when(shopRepository.findByShopOwnerContact(shopResistrationModel.getShopOwnerContact())).thenReturn(Mono.just(shopResistrationModel));
        Mockito.when(shopResistrationModelMono.flatMap(existingShop -> Mono.just("Shop already exists"))).thenReturn(Mono.just("Shop already exists"));
        Mockito.when(r2dbcEntityTemplate.insert(shopResistrationModel)).thenReturn(Mono.just(shopResistrationModel));
        Mono<String> saveShop=shopService.saveShop(shopResistrationModel);
        String result = saveShop.block();
        Assert.assertEquals("Shop already exists",result);
    }

    @Test
    public void saveShopError(){
        ShopResistrationModel shopResistrationModel=new ShopResistrationModel();
        shopResistrationModel.setShopName("BStore");
        shopResistrationModel.setShopAddress("pune");
        shopResistrationModel.setShopOwnerName("Bhakti");
        shopResistrationModel.setShopOwnerContact("9881050653");
        Mockito.when(shopRepository.findByShopOwnerContact(shopResistrationModel.getShopOwnerContact())).thenReturn(Mono.empty());
        Mockito.when(r2dbcEntityTemplate.insert(shopResistrationModel)).thenReturn(Mono.error(new Exception()));
        Mono<String> saveShop=shopService.saveShop(shopResistrationModel);
        String result = saveShop.block();
        Assert.assertEquals("Error in saving shop",result);
    }

    @Test
    public void updateShopSuccess(){
        ShopResistrationModel shopResistrationModel=new ShopResistrationModel();
        shopResistrationModel.setShopName("BStore");
        shopResistrationModel.setShopAddress("pune");
        shopResistrationModel.setShopOwnerName("Bhakti");
        shopResistrationModel.setShopOwnerContact("9881050653");
        Mockito.when(shopRepository.findByShopOwnerContact(shopResistrationModel.getShopOwnerContact())).thenReturn(Mono.just(shopResistrationModel));
        Mockito.when(r2dbcEntityTemplate.update(shopResistrationModel)).thenReturn(Mono.just(shopResistrationModel));
        Mono<String> updateShop=shopService.updateShop(shopResistrationModel);
        String result = updateShop.block();
        Assert.assertEquals("Shop updated successfully",result);
    }

    @Test
    public void updateShoperror(){
        ShopResistrationModel shopResistrationModel=new ShopResistrationModel();
        shopResistrationModel.setShopName("BStore");
        shopResistrationModel.setShopAddress("pune");
        shopResistrationModel.setShopOwnerName("Bhakti");
        shopResistrationModel.setShopOwnerContact("9881050653");
        Mockito.when(shopRepository.findByShopOwnerContact(shopResistrationModel.getShopOwnerContact())).thenReturn(Mono.just (shopResistrationModel));
        Mockito.when(r2dbcEntityTemplate.update(shopResistrationModel)).thenReturn(Mono.error(new Exception()));
        Mono<String> updateShop=shopService.updateShop(shopResistrationModel);
        String result = updateShop.block();
        Assert.assertEquals("Error in updating shop",result);
    }

    @Test
    public void updateShopfindShop(){
        ShopResistrationModel shopResistrationModel=new ShopResistrationModel();
        shopResistrationModel.setShopName("BStore");
        shopResistrationModel.setShopAddress("pune");
        shopResistrationModel.setShopOwnerName("Bhakti");
        shopResistrationModel.setShopOwnerContact("9881050653");
        Mockito.when(shopRepository.findByShopOwnerContact(shopResistrationModel.getShopOwnerContact())).thenReturn(Mono.error (new Exception()));
        Mockito.when(r2dbcEntityTemplate.update(shopResistrationModel)).thenReturn(Mono.error(new Exception()));
        Mono<String> updateShop=shopService.updateShop(shopResistrationModel);
        String result = updateShop.block();
        Assert.assertEquals("Error in user find",result);
    }

    @Test
    public void deleteShopSuccess(){
        Mockito.when(shopRepository.findByShopOwnerContact("9881050653")).thenReturn(Mono.just(new ShopResistrationModel()));
        Mockito.when(shopRepository.delete(new ShopResistrationModel())).thenReturn(Mono.empty());
        Mono<String> deleteShop=shopService.deleteShop("9881050653");
        String result = deleteShop.block();
        Assert.assertEquals("Shop  deleted",result);
    }

    @Test
    public void deleteShopError(){
        Mockito.when(shopRepository.findByShopOwnerContact("9881050653")).thenReturn(Mono.just(new ShopResistrationModel()));
        Mockito.when(shopRepository.delete(new ShopResistrationModel())).thenReturn(Mono.error(new Exception()));
        Mono<String> deleteShop=shopService.deleteShop("9881050653");
        String result = deleteShop.block();
        Assert.assertEquals("Error in delete shop",result);
    }

    @Test
    public void getShopByIdSuccess() {
        Mockito.when(shopRepository.findByShopOwnerContact("9881050653")).thenReturn(Mono.just(new ShopResistrationModel()));
        Mono<ShopResistrationModel> shopResistrationModelMono = shopService.getShopById("9881050653");
        Assert.assertEquals(new ShopResistrationModel().toString(), shopResistrationModelMono.block().toString());
    }

    @Test(expected = Exception.class)
    public void getShopByIdError() {
        Mockito.when(shopRepository.findByShopOwnerContact("9881050653")).thenReturn(Mono.error(new Exception("Error in shop find")));
        Mono<ShopResistrationModel> shopResistrationModelMono = shopService.getShopById("9881050653");
        shopResistrationModelMono.block();
    }

    @Test(expected = Exception.class)
    public void getAllShopSuccess(){
        Mockito.when(shopRepository.findAll()).thenReturn(Flux.error(new Exception()));
        Flux<ShopResistrationModel> allShops =shopService.getAllShops();
        allShops.blockFirst();
    }

    @Test(expected = Exception.class)
    public void getAllShopByIds(){
        List<String> stringList=new ArrayList<>();
        stringList.add("9881050653");
        stringList.add("9881050654");
        Mockito.when(shopRepository.findAllById(Mockito.anyIterable())).thenReturn(Flux.error(new Exception()));
        Flux<ShopResistrationModel> allShops=shopService.getShopByIds(stringList);
        allShops.blockFirst();

    }
}
