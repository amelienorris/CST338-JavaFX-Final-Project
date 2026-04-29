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
    public void testUserCanChooseTenMinutes() {
        FocusTimer timer = new FocusTimer(10);
        assertEquals(600, timer.getSecondsLeft());
        assertEquals("10:00", timer.getFormattedTime());
    }

    @Test
    public void testUserCanChooseFiveMinutes() {
        FocusTimer timer = new FocusTimer(5);
        assertEquals(300, timer.getSecondsLeft());
        assertEquals("05:00", timer.getFormattedTime());
    }

    @Test
    public void testUserCanChooseThirtyMinutes() {
        FocusTimer timer = new FocusTimer(30);
        assertEquals(1800, timer.getSecondsLeft());
        assertEquals("30:00", timer.getFormattedTime());
    }
}