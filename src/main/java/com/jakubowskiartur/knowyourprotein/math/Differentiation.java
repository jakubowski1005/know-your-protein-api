package com.jakubowskiartur.knowyourprotein.math;

class Differentiation {

    static Dataset diff(Dataset dataset, int order) {

        if (order < 1) throw new IllegalArgumentException("Order must be higher than 0.");

        while(order > 0) {
            dataset = diff(dataset);
            order--;
        }
        return dataset;
    }

    private static Dataset diff(Dataset dataset) {

        double[] wavelengths = dataset.getX();
        double[] values = dataset.getY();

        int size = values.length;
        double[] diffValues = new double[size];

        diffValues[0] = (values[1] - values[0]) / (wavelengths[1] - wavelengths[0]);
        diffValues[size-1] = (values[size-1] - values[size-2]) / (wavelengths[size-1] - wavelengths[size-2]);
        for (int i = 1; i < size-1; i++) {
            diffValues[i] = (values[i+1] - values[i-1]) / (wavelengths[i+1] - wavelengths[i-1]);
        }
        return Dataset.merge(wavelengths,diffValues);
    }
}
