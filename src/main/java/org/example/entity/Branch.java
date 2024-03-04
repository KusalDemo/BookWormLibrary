package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Branch {
    @Id
    private int branchId;
    private String branchName;
    private String location;
    private String email;

    @OneToMany(mappedBy = "branch",cascade = CascadeType.ALL, orphanRemoval = true)
    List<Book> books;

    @OneToMany(mappedBy = "branch",cascade = CascadeType.ALL, orphanRemoval = true)
    List<User> users;
}
