package com.example.students.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "study_groups")
public class StudyGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название группы обязательно")
    @Size(max = 50, message = "Название не длиннее 50 символов")
    private String name;

    @NotNull(message = "Год поступления обязателен")
    @Min(value = 2000, message = "Год не раньше 2000")
    @Max(value = 2100, message = "Год не позже 2100")
    @Column(name = "admission_year")
    private Integer admissionYear;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getAdmissionYear() { return admissionYear; }
    public void setAdmissionYear(Integer admissionYear) { this.admissionYear = admissionYear; }
}
