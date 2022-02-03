package training.lepeskin.system_data.calculations.statistics.representation;

import java.util.Date;

public class TimeIntervalStatistics {
    private Date startTime;
    private Date endTime;
    private Object value;

    public TimeIntervalStatistics() {

    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Start time = " + getStartTime() +
                ", End time = " + getEndTime() +
                ", Value = " + getValue() + "\n";
    }
}
