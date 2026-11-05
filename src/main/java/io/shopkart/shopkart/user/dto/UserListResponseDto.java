package io.shopkart.shopkart.user.dto;

import io.shopkart.shopkart.user.Entity.User;

import java.util.List;

public record UserListResponseDto(
        List<UserProfileResponseDto> users
) {

    public static UserListResponseDto from(List<User> userList) {
        List<UserProfileResponseDto> users = userList.stream().map(user -> UserProfileResponseDto.from(user)).toList();
        return new UserListResponseDto(users);
    }
}
