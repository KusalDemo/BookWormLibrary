package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Admin {
    @Id
    private int adminId;
    private String username;
    private String email;
    private String password;

    @OneToMany
    private List<Branch> branches;
}
