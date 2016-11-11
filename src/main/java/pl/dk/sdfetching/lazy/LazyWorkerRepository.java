package pl.dk.sdfetching.lazy;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

interface LazyWorkerRepository extends CrudRepository<LazyWorker, String> {

    LazyWorker findBySurname(String surname);

    @Query("select w from LazyWorker w where w.surname = :surname")
    LazyWorker findBySurnameJPQL(@Param("surname") String surname);

    @Query("select w from LazyWorker w join fetch w.unit u where w.surname = :surname")
    LazyWorker findBySurnameJPQLFetchingUnit(@Param("surname") String surname);
}
