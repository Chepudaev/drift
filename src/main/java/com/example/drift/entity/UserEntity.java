package com.example.drift.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    @NotBlank(message = "Имя обязательно для заполнения")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotBlank(message = "Фамилия обязательна для заполнения")
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "Некорректный формат email")
    @NotBlank(message = "Email обязателен для заполнения")
    private String email;

    @Column(name = "phone", nullable = false)
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Некорректный формат телефона")
    @NotBlank(message = "Телефон обязателен для заполнения")
    private String phone;

    @Column(name = "instagram")
    private String instagram;

    @Column(name = "profile_photo_url")
    private String profilePhotoUrl;

    @Column(name = "motto", length = 500)
    private String motto;

    @Column(name = "official_photo_url")
    private String officialPhotoUrl;

    @Column(name = "sponsors", length = 1000)
    private String sponsors;

    @Column(name = "username", nullable = false, unique = true)
    @NotBlank(message = "Имя пользователя обязательно для заполнения")
    private String username;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "Пароль обязателен для заполнения")
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CarEntity> cars;

}