package com.jakubowskiartur.knowyourprotein.math

import spock.lang.Specification

class PeakFinderSpec extends Specification {

    void 'should find peaks'() {
        given:
        double[] x = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17] as double[]
        double[] y = [3, 4, 5, 6, 4, 3, 4, 5, 4, 3, 2, 3, 1, 6, 7, 8, 7] as double[]
        Dataset dataset = new Dataset(x, y)

        when:
        int[] mins = PeakFinder.findMinimums(dataset)

        then:
        mins.length == 3
        mins[0] == 5
        mins[1] == 10
        mins[2] == 12
    }
}
