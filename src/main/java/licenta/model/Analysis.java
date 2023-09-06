package licenta.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "analysis")
public class Analysis {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "nume")
    String name;

    @Column(name = "descriere")
    String description;

    @Column(name = "id_pacient")
    Long idPacient;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getIdPacient() {
        return idPacient;
    }

}
