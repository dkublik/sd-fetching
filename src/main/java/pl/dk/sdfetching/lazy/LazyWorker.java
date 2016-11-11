package pl.dk.sdfetching.lazy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.dk.sdfetching.unit.Unit;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
class LazyWorker {

    @Id
    private String personalId;

    private String surname;

    @ManyToOne(fetch = LAZY)
    private Unit unit;
}
