package com.jakubowskiartur.knowyourprotein.math

import spock.lang.Specification

class DatasetSpec extends Specification {

    Dataset workingSet
    Dataset brokenSet

    double[] x1
    double[] x2
    double[] y

    void setup() {
        x1 = [0.1, 0.2, 0.3] as double[]
        x2 = [0.2, 0.4, 0.6, 0.8] as double[]
        y  = [0.11, 0.222, 0.333] as double[]

        workingSet = new Dataset(x1, y)
        brokenSet  = new Dataset(x2, y)
    }

    void 'should throw IllegalArgumentException'() {
        when:
        brokenSet.get2DArray()

        then:
        final IllegalArgumentException exception = thrown()
        exception.getMessage() == "Data series must have the same length."
    }

    void 'should return 2 dimensional array'() {
        when:
        double[][] data = workingSet.get2DArray()

        then:
        data.length == 3
        data[0][1] == 0.11d
        data[2][0] == 0.3d
    }
}
