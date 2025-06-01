package org.example.capstoneproject.entity;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private String password;
    private String role_id;
}
