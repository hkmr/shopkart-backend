package io.shopkart.shopkart.user.Entity;

import io.shopkart.shopkart.common.entity.BaseEntity;
import io.shopkart.shopkart.user.enums.AddressType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

@Table(name = "addresses")
@Entity
public class Address extends BaseEntity {

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "line1", nullable = false)
    private String line1;

    private String line2;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "address_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    private boolean defaultAddress = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
