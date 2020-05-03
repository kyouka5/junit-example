import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TimeFormatterTest {
    private TimeFormatter underTest;

    @BeforeEach
    public void setUp() {
        underTest = new TimeFormatter();
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetHours() {
        // Given
        Duration time = Duration.ofMinutes(320);
        // When
        underTest.setTime(time);
        // Then
        assertEquals(5, underTest.getHours());
    }

    @Test
    public void testGetMinutes() {
        // Given
        Duration time = Duration.ofSeconds(540);
        // When
        underTest.setTime(time);
        // Then
        assertEquals(9, underTest.getMinutes());
    }

    @Test
    public void testGetSeconds() {
        // Given
        Duration time = Duration.ofSeconds(20);
        // When
        underTest.setTime(time);
        // Then
        assertEquals(20, underTest.getSeconds());
    }

    @Test
    public void testTimeToStringWithMinutes() {
        // Given
        Duration time = Duration.ofMinutes(45).plus(Duration.ofSeconds(10));
        // When
        underTest.setTime(time);
        // Then
        assertEquals("45:10", underTest.timeToString());
    }

    @Test
    public void testTimeToStringWithHours() {
        // Given
        Duration time = Duration.ofHours(2).plus(Duration.ofMinutes(30).plus(Duration.ofSeconds(40)));
        // When
        underTest.setTime(time);
        // Then
        assertEquals("02:30:40", underTest.timeToString());
    }

    @Test
    public void testSetTimeShouldThrowIllegalArgumentExceptionWhenTheDurationIsNegative() {
        Duration time = Duration.ofHours(-10);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            underTest.setTime(time);
        });
        assertEquals("Negative duration", exception.getMessage());
    }

    @Test
    public void testSetTimeShouldThrowIllegalArgumentExceptionWhenTheDurationIsLongerThanADay() {
        Duration time = Duration.ofDays(1);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            underTest.setTime(time);
        });
        assertEquals("Duration is longer than a day", exception.getMessage());
    }

}
