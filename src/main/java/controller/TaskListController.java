package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.format.DateTimeFormatter;

import java.time.LocalDate; //for testing
import java.util.Date;

public class TaskListController {
    //input filed for task title
    @FXML
    private TextField titleField;

    //input area for task description
    @FXML
    private TextArea descriptionArea;

    //date picker so users can slect task due date
    @FXML
    private DatePicker dueDatePicker;

    //dropdown so users can select priority level for task
    @FXML
    private ComboBox<String> priorityBox;

    //displays list of tasks on screen
    @FXML
    private ListView<String> taskListView;

    //lets users choose prioroty level fo task
    @FXML
    private void initialize() {
        priorityBox.getItems().addAll("Low", "Medium", "High");

        sortBox.getItems().addAll("Sort by Priority", "Sort by Due Date");
    }

    //enables dropdown so users can decide how to sort tasks
    @FXML
    private ComboBox<String> sortBox;

    //adds new task to top of list
    @FXML
    private void handleAddTask() {
        String taskText = buildTaskText();

        //if buildTaskText returns null, validation faild lol
        if (taskText == null) {
            return;
        }

        //add new task at index 0 so newest task appears first
        taskListView.getItems().add(0, taskText);
        clearFields();
    }

    //updates currently selected task
    @FXML private void handleEditTask() {
        int selectedIndex = taskListView.getSelectionModel().getSelectedIndex();

        if (selectedIndex == -1) {
            showAlert("Please select task to edit");
            return;
        }

        String taskText = buildTaskText();

        if (taskText == null) {
            return;
        }

        taskListView.getItems().set(selectedIndex, taskText);
        clearFields();
    }

    //deletes cure ntly selected task
    @FXML
    private void handleDeleteTask() {
        int selectedIndex = taskListView.getSelectionModel().getSelectedIndex();

        if (selectedIndex == -1) {
            showAlert("Please select a task to delete.");
            return;
        }

        taskListView.getItems().remove(selectedIndex);
        clearFields();
    }

    //will sort task based on selected priority sorting option
    @FXML
    private void handleSortTasks() {
        //gets the selcted option from the drop box
        String selectedSort = sortBox.getValue();

        //if user did not choose sorting option, do nothing
        if (selectedSort == null) {
            return;
        }

        //if user did select an option function call and sort the tasks
        if (selectedSort.equals("Sort by Priority")) {
            sortbyPriority();
        }

        //if user chose to sort task by due date call the function to sort bu due date
        if (selectedSort.equals("Sort by Due Date")) {
            sortbyDueDate();
        }
    }

    //sort task by priory
    private void sortbyPriority() {
        taskListView.getItems().sort((first_task, second_task) -> {
            //gets priority value for the first task
            int first_priority = getPriorityValue(first_task);

            //gets priority value for the second task
            int second_priority = getPriorityValue(second_task);

            //compares the priority so that high appears first, then medium then low
            return Integer.compare(first_priority, second_priority);
        });
    }

    //sort task by priority level
    private int getPriorityValue(String task) {
        if (task.contains("Priority: High")) {
            return 1;
        }

        if (task.contains("Priority: Medium")) {
            return 2;
        }

        if (task.contains("Priority: Low")) {
            return 3;
        }

        //if the task has no priority level it will be shown last
        else {
            return 4;
        }
    }

    //sorts tasks so that closest due date appears more
    private void sortbyDueDate() {

        taskListView.getItems().sort((task1, task2) -> {
            //gets due date of first task
            LocalDate first_date = getduedateValue(task1);

            //gets due date of second task
            LocalDate second_date = getduedateValue(task2);

            //if both tasks have no due date, keep their original order
            if (first_date ==  null && second_date == null) {
                return 0;
            }

            //if the first task does not have a due date, move it lower
            if (first_date == null) {
                return 1;
            }

            //if the second tak has no due date, keep task one higher
            if (second_date == null) {
                return -1;
            }

            //compares both dates so that the earlier date appeara first
            return first_date.compareTo(second_date);
        });
    }

    private LocalDate getduedateValue(String duedate) {
        try {
            //looks for the dur date in the app
            String marker = "Due Date: ";
            int begin_index = duedate.indexOf(marker);

            //if Due Date: not found, return null because no valid date
            if (begin_index == -1) {
                return null;
            }

            //moves the index to start after  "Due Date: " because that's the date
            begin_index += marker.length();

            //find end of date right where priority section begins
            int end_index = duedate.indexOf(" | Priority:", begin_index);

            //if the priority labe is not fiunf retun null because wrong format
            if (end_index == -1) {
                return null;
            }

            //gets the due date from the full task list
            String recorded_date = duedate.substring(begin_index, end_index).trim();

            if (recorded_date.equals("No Due Date")) {
                return null;
            }

            //sets formatter to match the right format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

            //converts the recorded date to loca date so app can comapre them correctly
            return LocalDate.parse(recorded_date, formatter);

        }
        catch (Exception e) {
            //anything wrong return nothing
            return null;

        }
    }


    //build formatted task
    private String buildTaskText() {
        String title = titleField.getText();

        if (title == null || title.trim().isEmpty()) {
            showAlert("Task name is required.");
            return null;
        }

        String description = descriptionArea.getText();
        String priority = priorityBox.getValue();

        if (description == null || description.trim().isEmpty()) {
            description = "No description.";
        }

        if (priority == null) {
            priority = "No priority";
        }

        String dueDate = "No due date";

        //formats date to be MM/dd/yyyy
        //if (dueDatePicker.getValue() != null) {
        //    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        //    dueDate = dueDatePicker.getValue().format(formatter);

        dueDate = formatDueDate(dueDatePicker.getValue());
        //}

        return title.trim()
                + " | " + description.trim()
                + " | Due: " + dueDate
                + " | Priority: " + priority;
    }

    //clears all in out fields after user adds, edit or deletes a task
    private void clearFields() {
        titleField.clear();
        descriptionArea.clear();
        dueDatePicker.setValue(null);
        priorityBox.setValue(null);
    }

    //ADDED FOR TESTING
    //formats using the device date into MM/DD/YYYY for due dates
    public static String formatDueDate(LocalDate date) {
        if (date == null) {
            return "No due date";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return date.format(formatter);
    }

    //shows the warning pop ups
    private void showAlert(String message) {
        Alert alert =  new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Task List");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

