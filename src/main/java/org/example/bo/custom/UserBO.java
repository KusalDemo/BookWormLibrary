package org.example.bo.custom;

import org.example.bo.SuperBO;
import org.example.dto.UserDto;

public interface UserBO extends SuperBO {
    boolean saveUser(UserDto userDto) throws ClassNotFoundException;
    boolean updateUser(UserDto userDto);
    boolean deleteUser(String username);
    UserDto searchUser(String username);
    boolean updatePassword(String username, String password);
}
