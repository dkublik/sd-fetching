package pl.dk.sdfetching.eager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
interface EagerWorkerRepository extends JpaRepository<EagerWorker, String> {

    EagerWorker findBySurname(String surname);

    @Query("select w from EagerWorker w where w.surname = :surname")
    EagerWorker findBySurnameJPQL(@Param("surname") String surname);

    @Query("select w from EagerWorker w join fetch w.unit u where w.surname = :surname")
    EagerWorker findBySurnameJPQLFetchingUnit(@Param("surname") String surname);
}
