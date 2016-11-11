package pl.dk.sdfetching.lazy

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import pl.dk.sdfetching.SDFetching
import pl.dk.sdfetching.querystat.QueryStatistics
import pl.dk.sdfetching.unit.Unit
import pl.dk.sdfetching.unit.UnitRepository
import spock.lang.Specification
import spock.lang.Subject

@ContextConfiguration(classes = [SDFetching])
@SpringBootTest
class LazyWorkerRepositorySpec extends Specification {

    static final String PERSONAL_ID = '11111111111'

    @Autowired
    UnitRepository unitRepository

    @Autowired
    QueryStatistics queryStatistics

    @Autowired
    @Subject
    LazyWorkerRepository workerRepository

    def setup() {
        Unit unit = unitRepository.save(new Unit(1L))
        workerRepository.save(new LazyWorker(PERSONAL_ID, 'Lazy', unit))
        queryStatistics.clear()
        println('--- initialized')
    }

    def "should create one query when calling by findOne"() {
        when:
            LazyWorker worker = workerRepository.findOne(PERSONAL_ID)
            Unit workersUnit = worker.unit
        then:
            workersUnit != null
            queryStatistics.nrOfQueries() == 1
    }

    def "should create one query when calling by findBySurname and not accessing unit"() {
        when:
            workerRepository.findBySurname('Lazy')
        then:
            queryStatistics.nrOfQueries() == 1
    }

    def "should create one queries when calling by JPQL and not accessing unit"() {
        when:
            workerRepository.findBySurnameJPQL('Lazy')
        then:
            queryStatistics.nrOfQueries() == 1
    }

    def "should create one query when calling by JPQL when fetching unit"() {
        when:
            LazyWorker worker = workerRepository.findBySurnameJPQLFetchingUnit('Lazy')
            Unit workersUnit = worker.unit
        then:
            workersUnit != null
            queryStatistics.nrOfQueries() == 1
    }

}
