package pl.dk.sdfetching.eager

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
class EagerWorkerRepositorySpec extends Specification {

    static final String PERSONAL_ID = '11111111111'

    @Autowired
    UnitRepository unitRepository

    @Autowired
    QueryStatistics queryStatistics

    @Autowired
    @Subject
    EagerWorkerRepository workerRepository

    def setup() {
        Unit unit = unitRepository.save(new Unit(1L))
        workerRepository.save(new EagerWorker(PERSONAL_ID, 'Eager', unit))
        queryStatistics.clear()
        println('--- initialized')
    }

    def "should create one query when calling by findOne"() {
        when:
            EagerWorker worker = workerRepository.findOne(PERSONAL_ID)
            Unit workersUnit = worker.unit
        then:
            workersUnit != null
            queryStatistics.nrOfQueries() == 1
    }

    def "should create two queries when calling by findBySurname"() {
        when:
            workerRepository.findBySurname('Eager')
        then:
            queryStatistics.nrOfQueries() == 2
    }

    def "should create two queries when calling by JPQL"() {
        when:
            workerRepository.findBySurnameJPQL('Eager')
        then:
            queryStatistics.nrOfQueries() == 2
    }

    def "should create one query when calling by JPQL when fetching unit"() {
        when:
            EagerWorker worker = workerRepository.findBySurnameJPQLFetchingUnit('Eager')
            Unit workersUnit = worker.unit
        then:
            workersUnit != null
            queryStatistics.nrOfQueries() == 1
    }

}
