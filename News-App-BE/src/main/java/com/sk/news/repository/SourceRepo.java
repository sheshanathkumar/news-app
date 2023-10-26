package com.sk.news.repository;

import com.sk.news.entity.SourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceRepo extends JpaRepository<SourceEntity, Integer> {

    public SourceEntity findBySourceId (Integer id);
}
