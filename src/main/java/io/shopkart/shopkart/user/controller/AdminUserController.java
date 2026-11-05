package io.shopkart.shopkart.user.controller;

import io.shopkart.shopkart.common.dto.ApiResponse;
import io.shopkart.shopkart.user.Entity.User;
import io.shopkart.shopkart.user.dto.UserListResponseDto;
import io.shopkart.shopkart.user.enums.RoleName;
import io.shopkart.shopkart.user.service.UserService;
import org.springframework.context.annotation.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminUserController {

    private UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<UserListResponseDto>> userList(Authentication authentication) {
        return ResponseEntity.ok(ApiResponse.success(userService.getUserList(authentication)));
    }

}
