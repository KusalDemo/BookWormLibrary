package org.example.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookDto {
    private int id;
    private String title;
    private String author;
    private String genre;
    private boolean availability;
}
