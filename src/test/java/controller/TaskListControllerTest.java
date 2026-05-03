package controller;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListControllerTest {
    //tests that the date is formatted in MM/dd/yyy
    @Test
    public void formatDateCorrectly() {
        //sample date
        LocalDate date = LocalDate.of(2026, 4, 26);

        //call helper methos from TaskListController
        String formatdate = TaskListController.formatDueDate(date);

        //makes sure date is displayed in correct form
        assertEquals("04/26/2026", formatdate);
    }

    //makes sure that empty dates will not crash app and shows default text
    @Test
    public void nullDate() {
        //if ate is null, pretend user is not choosing a date
        String formatDate = TaskListController.formatDueDate(null);

        //make sure default message is returned
        assertEquals("No due date", formatDate);
    }

}
