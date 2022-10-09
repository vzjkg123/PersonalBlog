package com.tiancai.personalblog.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@Setter
public class User {
    String name;
    @TableId(type = IdType.AUTO)
    BigInteger account;
    String password;
    String email;
    String tel;
    String time;
    String role;

    public User() {
    }

    public User(String name, String password, String email) {
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public User(String name, String password, String email, String role) {
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public User(String role) {
        this.role = role;
    }

}
