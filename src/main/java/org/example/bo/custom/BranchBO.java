package org.example.bo.custom;

import org.example.bo.SuperBO;
import org.example.dto.BranchDto;

import java.util.ArrayList;

public interface BranchBO extends SuperBO {
    boolean saveBranch(BranchDto branchDto) throws ClassNotFoundException;

    boolean updateBranch(BranchDto branchDto) throws ClassNotFoundException;

    boolean deleteBranch(String branchId) throws ClassNotFoundException;

    ArrayList<BranchDto> getAllBranches() throws ClassNotFoundException;
}
