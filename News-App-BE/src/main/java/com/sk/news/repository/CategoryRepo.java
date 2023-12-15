package com.sk.news.repository;

import com.sk.news.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CategoryRepo extends JpaRepository<CategoryEntity, Integer> {
    CategoryEntity findByCatId (Integer catId);

    @Modifying
    @Transactional
    @Query(value = "insert into category (cat_name) values ( ?1 )", nativeQuery = true)
    void saveCategories (String s);
}
