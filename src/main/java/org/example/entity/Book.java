package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

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
}
