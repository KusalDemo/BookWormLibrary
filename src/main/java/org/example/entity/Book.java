package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Book {
    @Id
    private int id;
    private String title;
    private String author;
    private String genre;
    private boolean availability;

    @ManyToMany(mappedBy = "books")
    private List<User> users;
}
