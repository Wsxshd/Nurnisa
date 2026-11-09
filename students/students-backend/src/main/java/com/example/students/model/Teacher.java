package com.example.students.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "ФИО обязательно")
    @Size(max = 150, message = "ФИО не длиннее 150 символов")
    @Column(name = "full_name")
    private String fullName;

    @NotBlank(message = "Email обязателен")
    @Email(message = "Некорректный email")
    private String email;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
