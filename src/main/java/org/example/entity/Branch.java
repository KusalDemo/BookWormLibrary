package org.example.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Branch {
    private int branchId;
    private String branchName;
    private String location;

}
