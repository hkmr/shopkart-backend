package io.shopkart.shopkart.user.repository;

import io.shopkart.shopkart.user.Entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
