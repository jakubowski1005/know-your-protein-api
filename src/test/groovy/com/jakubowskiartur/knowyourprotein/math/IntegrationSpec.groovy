package com.jakubowskiartur.knowyourprotein.math

import spock.lang.Specification

class IntegrationSpec extends Specification {

    void 'should integrate properly'() {
        given:
        double[] x = [1, 2, 3, 4, 5, 6, 7, 8] as double[]
        double[] y = [1, 3, 2, 1, 4, 1, 2, 3] as double[]
        Dataset dataset = new Dataset(x, y)

        when:
        double res = Integration.integrate(dataset)
        System.out.println(res)

        then:
        res == 15
    }
}
