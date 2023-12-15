package com.sk.news.repository;

import com.sk.news.entity.WcUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<WcUser, Integer> {

    public WcUser findByUsername(String username);
    public WcUser findByEmail(String email);

    public WcUser findByUserId(int id);
    public void deleteByUserId(int id);

}
