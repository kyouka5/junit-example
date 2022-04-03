import java.time.Duration;

public class TimeFormatter {

    private Duration time;

    public TimeFormatter() {
    }

    public Duration getTime() {
        return time;
    }

    public void setTime(Duration time) {
        if (time.isZero()) {
            throw new IllegalArgumentException("Zero duration was given");
        }
        if (time.isNegative()) {
            throw new IllegalArgumentException("Negative duration was given");
        }
        if (time.toDaysPart() > 0) {
            throw new IllegalArgumentException("Given duration is longer than a day");
        }
        this.time = time;
    }

    public String timeToString() {
        if (getHours() > 0) {
            return String.format("%02d:%02d:%02d", getHours(), getMinutes(), getSeconds());
        }
        return String.format("%02d:%02d", getMinutes(), getSeconds());
    }

    public long getHours() {
        return getTime().toHoursPart();
    }

    public long getMinutes() {
        return getTime().toMinutesPart();
    }

    public long getSeconds() {
        return getTime().toSecondsPart();
    }
}