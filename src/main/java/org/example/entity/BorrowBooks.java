package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "borrow_books")
@Entity
public class BorrowBooks{
    @Id
    private String id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Book book;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private String status;
}
