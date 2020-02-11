package com.jakubowskiartur.knowyourprotein.computing

import com.jakubowskiartur.knowyourprotein.computing.math.Integration
import com.jakubowskiartur.knowyourprotein.computing.pojos.Dataset
import spock.lang.Specification

class IntegrationSpec extends Specification {

    void 'should integrate properly'() {
        given:
        double[] x = [1, 2, 3, 4, 5, 6, 7, 8] as double[]
        double[] y = [1, 3, 2, 1, 4, 1, 2, 3] as double[]
        Dataset dataset = new Dataset(x, y)

        when:
        double res = Integration.integrate(dataset)

        then:
        res == 15
    }
}
