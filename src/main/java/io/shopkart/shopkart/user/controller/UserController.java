package io.shopkart.shopkart.user.controller;

import io.shopkart.shopkart.common.dto.ApiError;
import io.shopkart.shopkart.common.dto.ApiResponse;
import io.shopkart.shopkart.user.dto.*;
import io.shopkart.shopkart.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponseDto>> userDetails(Authentication authentication) {
        UserProfileResponseDto responseDto = userService.userDetails(authentication);
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponseDto>> updateUser(
            Authentication authentication,
            @RequestBody UpdateUserProfileRequestDto requestDto
    ) {
        UserProfileResponseDto responseDto = userService.updateUserDetails(authentication, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(responseDto));
    }

    @PutMapping("/me/password")
    public void changeUserPassword() {

    }

    @GetMapping("/me/addresses")
    public ResponseEntity<ApiResponse<AddressListResponseDto>> getAddressList(Authentication authentication) {
        return ResponseEntity.ok(ApiResponse.success(userService.getUserAddressList(authentication)));
    }

    @PostMapping("/me/addresses")
    public ResponseEntity<ApiResponse<AddressResponseDto>> addAddress(
            Authentication authentication,
            @Valid @RequestBody AddressRequestDto requestDto
            ) {
        AddressResponseDto responseDto = userService.addUserAddress(authentication, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(responseDto));
    }

    @PutMapping("/me/addresses/{addressId}")
    public ResponseEntity<ApiResponse<AddressResponseDto>> updateAddress(
            @PathVariable Long addressId,
            @RequestBody AddressRequestDto requestDto
    ) {
        AddressResponseDto responseDto = userService.updateAddress(addressId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Address Updated Successfully",responseDto));
    }

    @DeleteMapping("/me/addresses/{addressId}")
    public ResponseEntity<ApiResponse<String>> deleteAddress(
            Authentication authentication,
            @PathVariable Long addressId
    ) {
        userService.deleteAddress(authentication, addressId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success("Address deleted successfully"));
    }

}
