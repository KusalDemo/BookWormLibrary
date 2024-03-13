package org.example.bo.custom.impl;

import org.example.bo.custom.AdminBO;
import org.example.dao.DAOFactory;
import org.example.dao.custom.AdminDAO;
import org.example.dto.AdminDto;
import org.example.entity.Admin;

import java.util.ArrayList;
import java.util.List;

public class AdminBOImpl implements AdminBO {

    AdminDAO adminDAO = (AdminDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.ADMIN);
    @Override
    public boolean saveAdmin(AdminDto adminDto) throws ClassNotFoundException {
        return adminDAO.save(new Admin(adminDto.getAdminId(),adminDto.getEmail(),adminDto.getPassword(),adminDto.getUsername()));
    }

    @Override
    public boolean updateAdmin(AdminDto adminDto) throws ClassNotFoundException {
        return adminDAO.update(new Admin(adminDto.getAdminId(),adminDto.getEmail(),adminDto.getPassword(),adminDto.getUsername()));
    }

    @Override
    public boolean deleteAdmin(String email) throws ClassNotFoundException {
        return adminDAO.delete(email);
    }

    @Override
    public AdminDto getAdmin(String email) throws ClassNotFoundException {
        Admin admin = adminDAO.search(email);
        return new AdminDto(admin.getAdminId(),admin.getEmail(),admin.getPassword(),admin.getUsername());
    }

    @Override
    public List<AdminDto> getAllAdmins() throws ClassNotFoundException {
        List<Admin> allAdmins = adminDAO.getAll();
        List<AdminDto> adminDtos = new ArrayList<>();
        for (Admin admin : allAdmins) {
            adminDtos.add(new AdminDto(admin.getAdminId(),admin.getEmail(),admin.getPassword(),admin.getUsername()));
        }
        return adminDtos;
    }
}
