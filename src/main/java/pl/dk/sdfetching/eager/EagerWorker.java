package pl.dk.sdfetching.eager;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.dk.sdfetching.unit.Unit;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
class EagerWorker {

    @Id
    private String personalId;

    private String surname;

    @ManyToOne
    private Unit unit;
}
