package com.MegaFlixTV.MegaFlix.mapper;

import com.MegaFlixTV.MegaFlix.controller.request.userRequests.ChangeUserDataRequest;
import com.MegaFlixTV.MegaFlix.controller.request.userRequests.CreateUserRequest;
import com.MegaFlixTV.MegaFlix.controller.response.UserResponse;
import com.MegaFlixTV.MegaFlix.entity.User;
import com.MegaFlixTV.MegaFlix.entity.UserRole;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public static UserResponse mapToResponse (User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }

    public static User mapToEntity (CreateUserRequest createUserRequest) {
        return User.builder()
                .username(createUserRequest.username())
                .password(createUserRequest.password())
                .email(createUserRequest.email())
                .role(UserRole.ROLE_USER)
                .build();
    }

    public static User mapChangesToEntity (ChangeUserDataRequest changeUserDataRequest) {
        return User.builder()
                .username(changeUserDataRequest.username())
                .email(changeUserDataRequest.email())
                .build();
    }

}
