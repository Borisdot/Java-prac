package ru.msu.cmc.webprac.models;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "services")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Services implements CommonEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "service_id")
    private Long id;

    @Column(nullable = false, name = "service_name")
    @NonNull
    private String name;

    @Column(nullable = false, name = "cost")
    @NonNull
    private Float cost;
}
