package com.example.bookinventory.entity;



import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user_tbl")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String userName;
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_number")
    private PermRole role;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<PurchaseLog> purchaseLogs = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<ShoppingCart> shoppingCarts = new HashSet<>();

    // Getters and Setters
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public PermRole getRole() { return role; }
    public void setRole(PermRole role) { this.role = role; }

    public Set<PurchaseLog> getPurchaseLogs() { return purchaseLogs; }
    public void setPurchaseLogs(Set<PurchaseLog> purchaseLogs) { this.purchaseLogs = purchaseLogs; }

    public Set<ShoppingCart> getShoppingCarts() { return shoppingCarts; }
    public void setShoppingCarts(Set<ShoppingCart> shoppingCarts) { this.shoppingCarts = shoppingCarts; }
}
