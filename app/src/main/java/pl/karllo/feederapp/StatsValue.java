package pl.karllo.feederapp;

public class StatsValue {
    long timeValue;
    int amount;


    public StatsValue() {
    }

    public StatsValue(long timeValue, int amount) {
        this.timeValue = timeValue;
        this.amount = amount;
    }

    public long getTimeValue() {
        return timeValue;
    }

    public int getAmount() {
        return amount;
    }

    public void setTimeValue(long timeValue) {
        this.timeValue = timeValue;
    }
}
