package com.sk.news.repository;

import com.sk.news.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<CategoryEntity, Integer> {
    CategoryEntity findByCatId (Integer catId);
}
