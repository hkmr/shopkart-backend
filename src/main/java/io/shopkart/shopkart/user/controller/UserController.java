package io.shopkart.shopkart.user.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/me")
    public void userDetails() {

    }

    @PutMapping("/me")
    public void updateUser() {

    }

    @PutMapping("/me/password")
    public void changeUserPassword() {

    }

    @GetMapping("/me/addresses")
    public void getAddressList() {

    }

    @PostMapping("/me/addresses")
    public void addAddress() {

    }

    @PutMapping("/me/addresses/{addressId}")
    public void updateAddress(@RequestParam Long addressId) {

    }

    @DeleteMapping("/me/addresses/{addressId}")
    public void deleteAddress(@RequestParam Long addressId) {

    }

}
