package org.example.entity;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BorrowingBook {
    private String id;
    private String username;
    private int bookId;
    private Date borrowDate;
    private Date dueDate;
}
