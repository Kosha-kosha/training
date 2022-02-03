package training.lepeskin.system_data.calculations.statistics.representation;


public class ScalarStatistics {
    private Comparable<?> max;
    private Comparable<?> min;
    private Comparable<?> average;

    public void setMax(Comparable<?> max) {
        this.max = max;
    }

    public void setMin(Comparable<?> min) {
        this.min = min;
    }

    public void setAverage(Comparable<?> average) {
        this.average = average;
    }

    public Comparable<?> getMax() {
        return max;
    }

    public Comparable<?> getMin() {
        return min;
    }

    public Comparable<?> getAverage() {
        return average;
    }

    @Override
    public String toString() {
        return "max=" + max +
                ", min=" + min +
                ", average=" + average;
    }
}
