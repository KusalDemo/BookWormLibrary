package org.example.dto.tm;

import lombok.*;
import org.example.dto.BranchDto;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookTM {
    private String id;
    private String title;
    private String author;
    private String genre;
    private String availability;
    private String branchId;
}
