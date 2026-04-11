package com.MegaFlixTV.MegaFlix.mapper;

import com.MegaFlixTV.MegaFlix.controller.request.UserRequest;
import com.MegaFlixTV.MegaFlix.controller.response.UserResponse;
import com.MegaFlixTV.MegaFlix.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public static UserResponse mapToResponse (User user) {

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }

    public static User mapToEntity (UserRequest userRequest) {

        return User.builder()
                .username(userRequest.username())
                .password(userRequest.password())
                .email(userRequest.email())
                .build();
    }

}
