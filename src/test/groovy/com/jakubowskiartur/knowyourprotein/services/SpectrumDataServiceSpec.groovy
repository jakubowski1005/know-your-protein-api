package com.jakubowskiartur.knowyourprotein.services

import com.jakubowskiartur.knowyourprotein.computing.pojos.StructureModel
import com.jakubowskiartur.knowyourprotein.payloads.ServerResponse
import com.jakubowskiartur.knowyourprotein.pojos.SpectrumData
import com.jakubowskiartur.knowyourprotein.pojos.User
import com.jakubowskiartur.knowyourprotein.repos.SpectrumDataRepository
import com.jakubowskiartur.knowyourprotein.security.CustomEndpointAuthorization
import org.springframework.http.HttpStatus
import spock.lang.Specification

class SpectrumDataServiceSpec extends Specification {

    SpectrumDataRepository repository
    CustomEndpointAuthorization auth
    SpectrumDataService service

    void setup() {
        repository = Stub(SpectrumDataRepository)
        auth = Stub(CustomEndpointAuthorization)
        service = new SpectrumDataService(repository, auth)
    }

    void 'should retrieve data by id'() {
        given:
        SpectrumData data = new SpectrumData('data', new ArrayList<StructureModel>())
        repository.findById(1L) >> Optional.of(data)

        when:
        ServerResponse response = service.retrieveByID(1L)

        then:
        response.getBody() == data
        response.getHttp() == HttpStatus.OK
    }

    void 'should throw exception when resources dont exist'() {
        when:
        repository.findById(1L) >> Optional.empty()
        service.retrieveByID(1L)

        then:
        thrown SecurityException
    }

    void 'should retrieve all user data'() {
        given:
        User user = new User('username', 'email@gmail.com', 'pass123')
        auth.getAuthenticatedUser() >> user
        repository.getAllByUser(user) >> Arrays.asList(
                new SpectrumData('data1', new ArrayList<StructureModel>()),
                new SpectrumData('data1', new ArrayList<StructureModel>())
        )

        when:
        ServerResponse response = service.retrieveAllByUser()

        then:
        response.getHttp() == HttpStatus.OK
        response.getBody().size() == 2
        response.getBody().get(0).getName() == 'data1'
    }

    void 'should create data'() {
        given:
        User user = new User('username', 'email@gmail.com', 'pass123')
        SpectrumData data = new SpectrumData('data1', new ArrayList<StructureModel>())
        auth.getAuthenticatedUser() >> user

        when:
        ServerResponse response = service.createSpectrum(data)

        then:
        response.getHttp() == HttpStatus.CREATED
        response.getBody().getName() == 'data1'
        response.getBody().getUser() == user
    }

    void 'should update data'() {
        given:
        User user = new User('username', 'email@gmail.com', 'pass123')
        SpectrumData oldData = new SpectrumData('data1', new ArrayList<StructureModel>())
        SpectrumData newData = new SpectrumData('data2', new ArrayList<StructureModel>())
        auth.getAuthenticatedUser() >> user
        repository.findById(1L) >> Optional.of(oldData)

        when:
        ServerResponse response = service.updateSpectrum(1L, newData)

        then:
        response.getHttp() == HttpStatus.OK
        response.getBody().getName() == 'data2'
        response.getBody().getUser() == user
    }

    void 'should delete data by id'() {
        given:
        SpectrumData data = new SpectrumData('data1', new ArrayList<StructureModel>())
        repository.findById(1L) >> Optional.of(data)

        when:
        ServerResponse response = service.deleteSpectrumByID(1L)

        then:
        response.getHttp() == HttpStatus.NO_CONTENT
        response.getMessage() == "Spectrum data removed successfully."
        response.getSuccess()
    }

    void 'should delete all user data'() {
        given:
        User user = new User('username', 'email@gmail.com', 'pass123')
        SpectrumData data1 = new SpectrumData('data1', new ArrayList<StructureModel>())
        SpectrumData data2 = new SpectrumData('data2', new ArrayList<StructureModel>())
        auth.getAuthenticatedUser() >> user
        repository.getAllByUser(user) >> Arrays.asList(data1, data2)

        when:
        ServerResponse response = service.deleteAll()

        then:
        response.getHttp() == HttpStatus.NO_CONTENT
        response.getMessage() == "All spectras removed successfully."
        response.getSuccess()
    }

    void 'should returned resources not found response'() {
        given:
        User user = new User('username', 'email@gmail.com', 'pass123')
        SpectrumData newData = new SpectrumData('data2', new ArrayList<StructureModel>())
        auth.getAuthenticatedUser() >> user
        repository.findById(1L) >> Optional.empty()

        when:
        ServerResponse response = service.updateSpectrum(1L, newData)

        then:
        response.getHttp() == HttpStatus.NOT_FOUND
        response.getMessage() == "Resources with given ID does not exist."
        !response.getSuccess()
    }
}
