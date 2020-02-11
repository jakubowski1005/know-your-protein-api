package com.jakubowskiartur.knowyourprotein.repos

import com.jakubowskiartur.knowyourprotein.computing.pojos.StructureModel
import com.jakubowskiartur.knowyourprotein.pojos.SpectrumData
import com.jakubowskiartur.knowyourprotein.pojos.User
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import javax.inject.Inject

@ContextConfiguration
@DataJpaTest
class SpectrumDataRepositorySpec extends Specification{

    @Inject
    SpectrumDataRepository repository

    @Inject
    TestEntityManager entityManager

    Long id
    User user
    List<SpectrumData> spectras
    SpectrumData data1
    SpectrumData data2

    void setup() {
        id = 1L
        user = new User('username', 'email@gmail.com', 'pass1234')
        spectras = new ArrayList<>()
        data1 = new SpectrumData('uno', new ArrayList<StructureModel>())
        data1.setUser(user)
        data2 = new SpectrumData('do', new ArrayList<StructureModel>())
        data2.setUser(user)
        spectras << data1 << data2
        user.setSpectras(spectras)
        id = (Long) entityManager.persistAndGetId(user)
    }

    void 'should return all users data by its id'() {
        when:
        List<SpectrumData> found = repository.getAllByUser_Id(id)

        then:
        found == spectras
        found.size() == 2
        found.get(0) == spectras.get(0)
    }

    void 'should return all users data by user'() {
        when:
        List<SpectrumData> found = repository.getAllByUser(user)

        then:
        found == spectras
        found.size() == 2
        found.get(0) == spectras.get(0)
    }

}
