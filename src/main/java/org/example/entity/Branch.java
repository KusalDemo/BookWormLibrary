package org.example.entity;
import jakarta.persistence.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
    private String branchId;
    private String branchName;
    @Column(unique = true)
    private String location;
    private String email;

    @OneToMany(mappedBy = "branch",fetch = FetchType.EAGER)
    List<Book> books;

    @OneToMany(mappedBy = "branch",fetch = FetchType.EAGER)
    List<User> users;
}
