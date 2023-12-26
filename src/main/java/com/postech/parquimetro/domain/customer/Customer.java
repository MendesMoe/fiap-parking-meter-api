package com.postech.parquimetro.domain.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
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
    private String lastname;
    private String address1;
    private String address2;

    @Email
    private String email;

    private String phone;

 //   // Field desnormalizada
    //    private String paymentpreference;
    //    private int paymentpreferenceid;

    public Customer(DataNewCustomer data) {
        this.login = data.login();
        this.password = data.password();
        this.firstname = data.firstname();
        this.lastname = data.lastname();
        this.address1 = data.address1();
        this.address2 = data.address2();
        this.email = data.email();
        this.phone = data.phone();
        //this.paymentpreferenceid = data.paymentpreferenceid();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
    //public String getPaymentpreference() {
    //        return paymentpreference;
    //    }
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
}
