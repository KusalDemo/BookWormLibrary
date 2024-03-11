package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Book {
    @Id
    private String id;
    private String title;
    private String author;
    private String genre;
    private boolean availability;

    @ManyToOne
    private Branch branch;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BorrowBooks> borrowBooks;
}
