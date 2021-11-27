package pl.karllo.feederapp;

public class ScheduleInfo {

    private int feedingHour;
    private int feedingMinute;
    private int amount;

    public ScheduleInfo() {}
///
    public ScheduleInfo(int feedingHour, int feedingMinute, int amount) {
        this.feedingHour = feedingHour;
        this.feedingMinute = feedingMinute;
        this.amount = amount;
    }
///
    public int getFeedingHour() {
        return feedingHour;
    }

    public void setFeedingHour(int feedingHour) {
        this.feedingHour = feedingHour;
    }

    public int getFeedingMinute() {
        return feedingMinute;
    }

    public void setFeedingMinute(int feedingMinute) {
        this.feedingMinute = feedingMinute;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
