package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.format.DateTimeFormatter;

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
    }

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
        if (dueDatePicker.getValue() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            dueDate = dueDatePicker.getValue().format(formatter);
        }

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

    //shows the warning pop ups
    private void showAlert(String message) {
        Alert alert =  new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Task List");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

