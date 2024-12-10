package com.vijay.vz.dreamshop.service.user;

import com.vijay.vz.dreamshop.dto.UserDto;
import com.vijay.vz.dreamshop.model.User;
import com.vijay.vz.dreamshop.request.CreateUserRequest;
import com.vijay.vz.dreamshop.request.UserUpdateRequest;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);

    User getAuthenticatedUser();
}
