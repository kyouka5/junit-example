import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TimeFormatterTest {
    private TimeFormatter underTest;

    @BeforeEach
    public void setUp() {
        underTest = new TimeFormatter();
    }

    @ParameterizedTest
    @DisplayName("getHours() should get hours part of time")
    @CsvSource({"10,0", "320,5", "610,10", "1390,23"})
    public void testGetHours(Long minutes, Long expected) {
        // Given
        Duration time = Duration.ofMinutes(minutes);
        // When
        underTest.setTime(time);
        // Then
        assertEquals(expected, underTest.getHours());
    }

    @ParameterizedTest
    @DisplayName("getMinutes() should get minutes part of time")
    @CsvSource({"59,0", "610,10", "3599,59"})
    public void testGetMinutes(Long seconds, Long expected) {
        Duration time = Duration.ofSeconds(seconds);
        underTest.setTime(time);
        assertEquals(expected, underTest.getMinutes());
    }

    @ParameterizedTest
    @DisplayName("getSeconds() should get seconds part of time")
    @CsvSource({"1,1", "20,20", "59,59"})
    public void testGetSeconds(Long seconds, Long expected) {
        Duration time = Duration.ofSeconds(seconds);
        underTest.setTime(time);
        assertEquals(expected, underTest.getSeconds());
    }

    @ParameterizedTest
    @DisplayName("timeToString() should convert time to a two-part string if time is less than an hour")
    @CsvSource({"0,1,00:01", "45,10,45:10", "59,59,59:59"})
    public void testTimeToStringWithMinutes(Long minutes, Long seconds, String expected) {
        Duration time = Duration.ofMinutes(minutes).plus(Duration.ofSeconds(seconds));
        underTest.setTime(time);
        assertEquals(expected, underTest.timeToString());
    }

    @ParameterizedTest
    @DisplayName("timeToString() should convert time to a three-part string if time is more than an hour")
    @CsvSource({"1,0,1,01:00:01", "16,45,10,16:45:10", "23,59,59,23:59:59"})
    public void testTimeToStringWithHours(Long hours, Long minutes, Long seconds, String expected) {
        Duration time = Duration.ofHours(hours).plus(Duration.ofMinutes(minutes).plus(Duration.ofSeconds(seconds)));
        underTest.setTime(time);
        assertEquals(expected, underTest.timeToString());
    }

    @Test
    @DisplayName("setTime(Duration) should throw IllegalArgumentException if time is zero")
    public void testSetTimeWithZeroDuration() {
        Duration time = Duration.ofSeconds(0);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> underTest.setTime(time));
        assertEquals("Zero duration was given", exception.getMessage());
    }

    @ParameterizedTest
    @DisplayName("setTime(Duration) should throw IllegalArgumentException if time is negative")
    @ValueSource(longs = {-100000, -6300, -1})
    public void testSetTimeWithNegativeDuration(Long negativeTime) {
        Duration time = Duration.ofHours(negativeTime);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> underTest.setTime(time));
        assertEquals("Negative duration was given", exception.getMessage());
    }

    @ParameterizedTest
    @DisplayName("setTime(Duration) should throw IllegalArgumentException if time is longer than a day")
    @ValueSource(longs = {1, 235, 100000})
    public void testSetTimeWithLongerThanADayDuration(Long tooLongTime) {
        Duration time = Duration.ofDays(tooLongTime);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> underTest.setTime(time));
        assertEquals("Given duration is longer than a day", exception.getMessage());
    }

}
