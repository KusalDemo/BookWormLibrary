package org.example.dto;

import lombok.*;
import org.example.entity.Branch;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookDto {
    private String id;
    private String title;
    private String author;
    private String genre;
    private boolean availability;
    private Branch branch;
}
