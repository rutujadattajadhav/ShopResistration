package com.rutuja.shopResistrationn.repo;

import com.rutuja.shopResistrationn.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ReactiveCrudRepository<UserEntity,String> {
}
