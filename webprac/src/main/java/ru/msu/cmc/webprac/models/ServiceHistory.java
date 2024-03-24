package ru.msu.cmc.webprac.models;

import javax.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name = "servicehistory")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class ServiceHistory implements CommonEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    @NonNull
    private Clients client_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employees employee_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id")
    private Services service_id;

    @Column(name = "begin_")
    private Date begin_;

    @Column(name = "end_")
    private Date end_;
}
