package org.example.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Admin {
    private int adminId;
    private String username;
    private String email;
    private String password;
}
