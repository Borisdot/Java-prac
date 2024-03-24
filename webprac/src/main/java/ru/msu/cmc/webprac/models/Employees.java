package ru.msu.cmc.webprac.models;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "employees")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Employees implements CommonEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "employee_id")
    private Long id;

    @Column(nullable = false, name = "employee_name")
    @NonNull
    private String name;

    @Column(nullable = false, name = "address")
    @NonNull
    private String address;

    @Column(nullable = false, name = "phone")
    @NonNull
    private String phone;

    @Column(nullable = false, name = "email")
    @NonNull
    private String email;

    @Column(nullable = false, name = "function_")
    @NonNull
    private String function_;
}
