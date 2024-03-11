package org.example.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BranchDto {
    private String branchId;
    private String branchName;
    private String location;
    private String email;
    // Assuming you want to transfer the IDs of the books and users associated with the branch
    private List<String> bookIds;
    private List<String> userIds;
}
