package org.example.dto;

import jakarta.persistence.Entity;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {
    private String email;
    private String userName;
    private String password;
    // Assuming you want to transfer the ID of the branch associated with the user
    BranchDto branchDto;
    // Assuming you want to transfer the IDs of the books borrowed by the user
}
