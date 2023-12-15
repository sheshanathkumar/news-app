package com.sk.news.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "wc_user")
public class WcUser {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "user_id")
    public Integer userId;

    @Column(name = "user_name")
    public String username;

    @Column(name = "user_mail")
    public String email;

    @Column(name = "password")
    public String password;

    @Column(name = "role")
    public String role;

}
