package com.sk.news.repository;

import com.sk.news.entity.SourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SourceRepo extends JpaRepository<SourceEntity, Integer> {
    public SourceEntity findBySourceId (Integer id);

    @Modifying
    @Transactional
    @Query(value = "insert into sources (name) values ( ?1 )", nativeQuery = true)
    public void saveSource (String source);

}
