package com.rutuja.shopResistrationn.controller;

import com.rutuja.shopResistrationn.model.ShopResistrationModel;
import com.rutuja.shopResistrationn.service.ShopService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ShopControllerTest {

    @InjectMocks
    private ShopController shopController;

    @Mock
    private ShopService shopService;

  @Test
  public void saveShopSuccess(){
      ShopResistrationModel shopResistrationModel=new ShopResistrationModel();
        shopResistrationModel.setShopName("BStore");
        shopResistrationModel.setShopAddress("pune");
        shopResistrationModel.setShopOwnerName("Bhakti");
        shopResistrationModel.setShopOwnerContact("9881050653");
      Mockito.when(shopService.saveShop(shopResistrationModel)).thenReturn(Mono.just("success"));
      Mono<String> saveShop=shopController.saveShop(shopResistrationModel);
      String result = saveShop.block();
      Assert.assertEquals("success",result);
  }

@Test
  public void updateShopSuccess(){
      ShopResistrationModel shopResistrationModel=new ShopResistrationModel();
      shopResistrationModel.setShopName("BStore");
      shopResistrationModel.setShopAddress("pune");
      shopResistrationModel.setShopOwnerName("Bhakti");
      shopResistrationModel.setShopOwnerContact("9881050653");
      Mockito.when(shopService.updateShop(shopResistrationModel)).thenReturn(Mono.just("success"));
      Mono<String> updateShop=shopController.updateShop(shopResistrationModel);
      String result = updateShop.block();
      Assert.assertEquals("success",result);
  }

    @Test
    public void deleteShopSuccess(){
        Mockito.when(shopService.deleteShop("9881050653")).thenReturn(Mono.just("success"));
        Mono<String> deleteShop=shopController.deleteShop("9881050653");
        String result = deleteShop.block();
        Assert.assertEquals("success",result);
    }

    @Test
    public void getShopByIdSuccess(){
        Mockito.when(shopService.getShopById("9881050653")).thenReturn(Mono.just(new ShopResistrationModel()));
        ShopResistrationModel shopResistrationModel = shopController.getShopById("9881050653").block();
        Assert.assertEquals(new ShopResistrationModel().toString(), shopResistrationModel.toString());
    }

    @Test
    public void getAllShopSuccess(){
        Mockito.when(shopService.getAllShops()).thenReturn(Flux.just(new ShopResistrationModel()));
        Flux<ShopResistrationModel> allShop=shopController.getAllShop();
        Assert.assertEquals(Flux.just(new ShopResistrationModel()).toString(),allShop.toString());
    }

    @Test
    public void getAllShopByIds(){
        List<String> integerList=new ArrayList<>();
        integerList.add("9881050653");
        integerList.add("9881050654");
        Mockito.when(shopService.getShopByIds(Mockito.anyList())).thenReturn(Flux.just(new ShopResistrationModel()));
        Flux<ShopResistrationModel> allShopById=shopController.getAllShopById(integerList);
        Assert.assertEquals(Flux.just(new ShopResistrationModel()).toString(),allShopById.toString());
    }

}
