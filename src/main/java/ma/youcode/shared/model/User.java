package ma.youcode.shared.model;

import jakarta.persistence.*;
import ma.youcode.shared.enums.Role;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    // Getters & Setters
}

