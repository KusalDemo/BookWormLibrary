package org.example.dto.tm;

import lombok.*;
import org.example.dto.BookDto;
import org.example.dto.UserDto;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BorrowBookTM {
    private String id;
    private String userId;
    private String bookId;
    private String borrowDate;
    private String returnDate;
    private String status;
}
