package com.jakubowskiartur.knowyourprotein.computing.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
public class Dataset {

    @Id
    @GeneratedValue
    private UUID id;

    @NonNull private double[] x;
    @NonNull private double[] y;

    @JsonIgnore
    public double[][] get2DArray() {

        double[] x = this.getX();
        double[] y = this.getY();

        if(x.length != y.length) throw new IllegalArgumentException("Data series must have the same length.");

        double[][] arr = new double[x.length][2];
        for (int i = 0; i < x.length; i++) {
            arr[i][0] = x[i];
            arr[i][1] = y[i];
        }
        return arr;
    }

    public static Dataset merge(double[] x, double[] y) {
        if(x.length != y.length) throw new IllegalArgumentException("Arrays must have the same length");
        return new Dataset(x, y);
    }
}
