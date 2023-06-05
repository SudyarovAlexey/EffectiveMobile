package net.EffectiveMobile.TestTask.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity {

    @NotBlank(message = "Username is mandatory")
    @UniqueElements(message = "Title has unique constraint")
    @Max(value = 100)
    @Column(name = "username")
    private String username;

    @Email(message = "Email is incorrect")
    @NotBlank(message = "Email is mandatory")
    @Max(value = 255)
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    @NotBlank(message = "Password is mandatory")
    @Max(value = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;
}
