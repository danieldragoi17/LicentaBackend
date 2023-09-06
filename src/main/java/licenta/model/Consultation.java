package licenta.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "consultations")
public class Consultation {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "id_analiza")
    Long idAnaliza;

    @Column(name = "detalii")
    String details;

    @Column(name = "diagnostic")
    String diagnostic;

    @Column(name = "medication")
    String medication;

    @Column(name = "id_doctor")
    Long idDoctor;
}
