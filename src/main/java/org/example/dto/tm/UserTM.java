package org.example.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserTM {
    private String userName;
    private String email;
    private String branchId;
}
