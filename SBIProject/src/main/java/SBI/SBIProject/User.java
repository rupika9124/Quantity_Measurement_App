package SBI.SBIProject;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @NotBlank(message = "Username is required")
    String username;
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter the valid Email")
    @Column(unique = true)
    String email;

    @NotBlank(message = "Mobile number is required")
    @Column(unique = true)
    String mobile_num;

    double balance;

    @Column(unique = true)
    String accountNumber;

    boolean hasDebit = false;
    boolean hasChequeB = false;
    boolean hasPassB = false;

    String role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile_num() {
        return mobile_num;
    }

    public void setMobile_num(String mobile_num) {
        this.mobile_num = mobile_num;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public boolean isHasDebit() {
        return hasDebit;
    }

    public void setHasDebit(boolean hasDebit) {
        this.hasDebit = hasDebit;
    }

    public boolean isHasChequeB() {
        return hasChequeB;
    }

    public void setHasChequeB(boolean hasChequeB) {
        this.hasChequeB = hasChequeB;
    }

    public boolean isHasPassB() {
        return hasPassB;
    }

    public void setHasPassB(boolean hasPassB) {
        this.hasPassB = hasPassB;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
