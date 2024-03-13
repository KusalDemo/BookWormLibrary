package org.example.bo.custom;

import org.example.bo.SuperBO;
import org.example.dto.AdminDto;

import java.util.List;

public interface AdminBO extends SuperBO {
    boolean saveAdmin(AdminDto adminDto) throws ClassNotFoundException;
    boolean updateAdmin(AdminDto adminDto) throws ClassNotFoundException;

    boolean deleteAdmin(String email) throws ClassNotFoundException;

    AdminDto getAdmin(String email) throws ClassNotFoundException;

    List<AdminDto> getAllAdmins() throws ClassNotFoundException;

}
