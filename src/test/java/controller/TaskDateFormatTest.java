package controller;
import org.junit.jupiter.api.Test;
import  java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TaskDateFormatTest {
    @Test
    void empty_date() {
        //empty dates should display "No due date"
        String result = TaskListController.formatDueDate(null);
        assertEquals("No due date", result);
    }

    @Test
    void correct_date_format() {
        //checks that dates are showing correctly
        LocalDate date = LocalDate.of(2025, 5, 4);

        String result = TaskListController.formatDueDate(date);

        assertEquals("05/04/2026", result);
    }
}
