package org.example.capstoneproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private Integer role_id;
}
