package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class FocusTimerTest {

    @Test
    public void testInitialSeconds() {
        FocusTimer timer = new FocusTimer(25);
        assertEquals(1500, timer.getSecondsLeft());
    }

    @Test
    public void testFormattedTime() {
        FocusTimer timer = new FocusTimer(25);
        assertEquals("25:00", timer.getFormattedTime());
    }

    @Test
    public void testOneMinute() {
        FocusTimer timer = new FocusTimer(1);
        assertEquals(60, timer.getSecondsLeft());
        assertEquals("01:00", timer.getFormattedTime());
    }

    @Test
    public void testZeroTime() {
        FocusTimer timer = new FocusTimer(0);
        assertEquals(0, timer.getSecondsLeft());
        assertEquals("00:00", timer.getFormattedTime());
    }
}