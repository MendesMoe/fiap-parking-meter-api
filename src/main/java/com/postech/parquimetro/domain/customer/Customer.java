package com.postech.parquimetro.domain.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.postech.parquimetro.domain.enums.PaymentMethod;
import com.postech.parquimetro.domain.vehicle.Vehicle;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Getter
@Setter
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements UserDetails {

    @Id
    private String customerID;

    @NotNull
    private String login;

    @NotNull
    private String password;
    private String firstname;

    @NotNull
    private String lastname;
    private String address1;
    private String address2;

    @Email
    private String email;

    private String phone;

    @NotNull
    private PaymentMethod paymentPreference;

    @DBRef
    private List<Vehicle> vehicles = new ArrayList<>();

    public List<Vehicle> getVehicles() {
        return this.vehicles;
    }

    public void setVehicles(List<Vehicle> vehicle) {
        this.vehicles = vehicle;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return login;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String encryptedPassword) {
        this.password = encryptedPassword;
    }

    public String getId() {
        return customerID;
    }
}
