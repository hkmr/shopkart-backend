package io.shopkart.shopkart.user.service;

import io.shopkart.shopkart.common.exception.UserNotFoundException;
import io.shopkart.shopkart.user.Entity.Address;
import io.shopkart.shopkart.user.Entity.User;
import io.shopkart.shopkart.user.dto.*;
import io.shopkart.shopkart.user.repository.AddressRepository;
import io.shopkart.shopkart.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private AddressRepository addressRepository;

    public UserService(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    // Get logged-in user details
    public UserProfileResponseDto userDetails(Authentication authentication) {

        User user = getUser(authentication);
        return UserProfileResponseDto.from(user);
    }

    // Update user profile
    @Transactional
    public UserProfileResponseDto updateUserDetails(Authentication authentication, UpdateUserProfileRequestDto requestDto) {
        User user = getUser(authentication);
        user.setName(requestDto.name());
        user.setPhone(requestDto.phone());

        user = userRepository.save(user);
        return UserProfileResponseDto.from(user);
    }

    public UserListResponseDto getUserList(Authentication authentication) {
        List<User> userList = userRepository.findAll();
        return UserListResponseDto.from(userList);
    }

    public AddressListResponseDto getUserAddressList(Authentication authentication) {
        User user = getUser(authentication);
        return AddressListResponseDto.from(user);
    }

    @Transactional
    public AddressResponseDto addUserAddress(Authentication authentication, AddressRequestDto addressRequestDto) {

        User user = getUser(authentication);

        Address address = new Address();
        address.setUser(user);
        address.setFullName(addressRequestDto.fullName());
        address.setPhone(addressRequestDto.phone());
        address.setLine1(addressRequestDto.line1());
        address.setLine2(addressRequestDto.line2());
        address.setCity(addressRequestDto.city());
        address.setState(addressRequestDto.state());
        address.setPostalCode(addressRequestDto.postalCode());
        address.setCountry(addressRequestDto.country());
        address.setAddressType(addressRequestDto.addressType());
        address.setDefaultAddress(addressRequestDto.isDefault());

        user.addAddress(address);

        userRepository.save(user);
        address = addressRepository.save(address);

        return AddressResponseDto.from(address);
    }

    public AddressResponseDto updateAddress(Long addressId, AddressRequestDto addressRequestDto) {

        Address address = addressRepository.findById(addressId).orElseThrow(() -> new RuntimeException("Address Not found"));

        address.setFullName(addressRequestDto.fullName());
        address.setPhone(addressRequestDto.phone());
        address.setLine1(addressRequestDto.line1());
        address.setLine2(addressRequestDto.line2());
        address.setCity(addressRequestDto.city());
        address.setState(addressRequestDto.state());
        address.setPostalCode(addressRequestDto.postalCode());
        address.setCountry(addressRequestDto.country());
        address.setAddressType(addressRequestDto.addressType());
        address.setDefaultAddress(addressRequestDto.isDefault());

        addressRepository.save(address);

        return AddressResponseDto.from(address);
    }

    public void deleteAddress(Authentication authentication, Long addressId) {
        User user = getUser(authentication);

        Address address = addressRepository.findById(addressId).orElseThrow(() -> new RuntimeException("Address Not found"));

        if(user.getAddresses().contains(address)) {
            user.removeAddress(addressId);
            userRepository.save(user);
            addressRepository.delete(address);
        } else {
            throw new RuntimeException("Not Authorized");
        }
    }

    private User getUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User Not found"));
    }

}
