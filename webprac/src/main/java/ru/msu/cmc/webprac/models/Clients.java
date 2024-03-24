package ru.msu.cmc.webprac.models;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "clients")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Clients implements CommonEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "client_id")
    private Long id;

    @Column(nullable = false, name = "client_name")
    @NonNull
    private String name;

    @Column(nullable = false, name = "contact_person")
    @NonNull
    private String contact;

    @Column(nullable = false, name = "address")
    @NonNull
    private String address;

    @Column(nullable = false, name = "phone")
    @NonNull
    private String phone;

    @Column(nullable = false, name = "email")
    @NonNull
    private String email;
}
