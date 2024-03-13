package org.example.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BranchTM {
    private String branchId;
    private String branchName;
    private String location;
    private String email;
}
