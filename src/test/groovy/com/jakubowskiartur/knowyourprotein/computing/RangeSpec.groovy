package com.jakubowskiartur.knowyourprotein.computing

import com.jakubowskiartur.knowyourprotein.computing.pojos.Range
import spock.lang.Specification

class RangeSpec extends Specification {

    Range range

    void setup() {
        range = new Range(0.3d, 23.67d)
    }

    void 'is given value in range'() {
        expect:
        range.isInRange(0.3d)
        range.isInRange(23.67d)
        range.isInRange(14.3246d)

        !range.isInRange(0.23d)
        !range.isInRange(23.69d)
        !range.isInRange(-0.34d)
        !range.isInRange(10000d)
    }
}
