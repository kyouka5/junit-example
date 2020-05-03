import java.time.Duration;

public class TimeFormatter {

    private Duration time;

    public TimeFormatter() {
    }

    public String timeToString() {
        if (getHours() > 0) {
            return String.format("%02d:%02d:%02d", getHours(), getMinutes(), getSeconds());
        } else {
            return String.format("%02d:%02d", getMinutes(), getSeconds());
        }
    }

    public int getHours() {
        return getTime().toHoursPart();
    }

    public int getMinutes() {
        return getTime().toMinutesPart();
    }

    public int getSeconds() {
        return getTime().toSecondsPart();
    }

    public Duration getTime() {
        return time;
    }

    public void setTime(Duration time) {
        if (time.isNegative()) {
            throw new IllegalArgumentException("Negative duration");
        }
        if (time.toDaysPart() > 0) {
            throw new IllegalArgumentException("Duration is longer than a day");
        }
        this.time = time;
    }
}