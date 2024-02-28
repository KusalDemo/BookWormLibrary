package org.example.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BranchDto {
    private int branchId;
    private String branchName;
    private String location;

}
