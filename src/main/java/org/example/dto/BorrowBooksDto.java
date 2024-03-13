package org.example.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.example.entity.Book;
import org.example.entity.User;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BorrowBooksDto {
    private String id;
    private UserDto user;
    private BookDto book;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private String status;
}
