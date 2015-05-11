package com.springapp.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springapp.mvc.model.Shop;

public interface ShopRepository extends JpaRepository<Shop, Integer> {

}
