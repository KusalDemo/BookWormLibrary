package org.example.bo.custom;

import org.example.bo.SuperBO;
import org.example.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

public interface UserBO extends SuperBO {
    boolean saveUser(UserDto userDto) throws ClassNotFoundException;
    boolean updateUser(UserDto userDto) throws ClassNotFoundException;

    boolean updateUserMinor(UserDto userDto) throws ClassNotFoundException;
    boolean deleteUser(String username) throws ClassNotFoundException;

    List<UserDto> getAllUsers() throws ClassNotFoundException;

    UserDto searchUser(String userEmail) throws ClassNotFoundException;

    UserDto searchUserFromUserName(String userName) throws ClassNotFoundException;
    boolean updatePassword(String username, String password) throws ClassNotFoundException;

}
