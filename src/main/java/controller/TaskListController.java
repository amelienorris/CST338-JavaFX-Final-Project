package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate; //for testing
import java.util.Date;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

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

    //displays save changes button for when users edit
    @FXML
    private Button saveChangesButton;

    //stores the task currently being edited
    private int editing_task  = -1; //-1 no task currently being edited

    //displays list of tasks on screen
    @FXML
    private ListView<String> taskListView;

    //lets users choose prioroty level fo task
    @FXML
    private void initialize() {
        priorityBox.getItems().addAll("Low", "Medium", "High");

        sortBox.getItems().addAll("Sort by Priority", "Sort by Due Date");

        //bolds task title
        boldTaskTitle();

        //when user clicks the task all task information loaded back to input fields to be edited
        taskListView.getSelectionModel().selectedItemProperty().addListener((task_list, old_task, new_task) -> {
            //if new_task is not null user clicked on a task on the list
            if (new_task != null) {
                //load the details of the task back into the fields so they can edit it
                load_task_in_fields(new_task);
            }
        });
    }

    //loads the task back intp the input fields so they can be edited
    private void load_task_in_fields(String task_words) {
        //splits the parts into their category task title, task description, date and priority
        String[] task_parts = task_words.split("\\|");

        //loads the task title in ythe title field
        if (task_parts.length > 0) {
            titleField.setText(task_parts[0].trim());
        }

        //loads task description into the description fields
        if (task_parts.length > 1) {
            descriptionArea.setText(task_parts[1].trim());
        }

        //loads the due date in due date field
        if (task_parts.length > 2) {
            String due_date = task_parts[2].replace("Due:", "").trim();

            //if the original task did not have a due date, keep due date field clear
            if (due_date.equals("No due date")) {
                dueDatePicker.setValue(null);
            }//if there is a date keep the format
            else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                LocalDate due_date_replace = LocalDate.parse(due_date, formatter);
                dueDatePicker.setValue(due_date_replace);
            }
        }

        //loads priority into back into the dropdown
        if (task_parts.length > 3) {
            String priority_text = task_parts[3].replace("Priority:", "").trim();

            //user can change or add priority here
            if (priority_text.equals("No priority")) {
                priorityBox.setValue(null);
            }
            else {
                priorityBox.setValue(priority_text); //show the chosen priority
            }
        }
    }

    //function will bold the task title onlty
    private void boldTaskTitle() {
        taskListView.setCellFactory(listView -> new ListCell<String>() {
            @Override
            //protected because im updating the task title to be bold, not changing what it says
            protected void updateItem(String task_title, boolean empty) {
                //calls the original task title
                super.updateItem(task_title, empty);

                //if there's no task clear the cell so old tasks dont show up
                if (empty || task_title == null) {
                    setText(null);
                    setGraphic(null);
                    return;
                }

                /*have to do this instead of bolding the title because it is a cell
                * and ListView<String> displays the whole string as one whole item
                * i needed a custom cell to just take a part of it need to have */

                /*split the whole cell of task list, description, due date and priority
                * split using the first | since the task title comes before the first one
                * */
                String[] task_parts = task_title.split("\\|", 2);

                //gets the task title using index
                String title = task_parts[0].trim();

                //empty string incase there is no description or extra details
                String remaining_task = "";

                //keep the remaining text after the first | the same
                if (task_parts.length > 1) {
                    remaining_task = " | " + task_parts[1].trim();
                }

                //have to creat a new text object so the rest of the task remains not bold
                Text bolded_title = new Text(title);

                bolded_title.setStyle("-fx-font-weight: bold;");

                //new object for the rest of the cell so they remain not bold
                Text rest_task = new Text(remaining_task);

                /*TextFlow allows muliple text obkects on the samel cell so we can have multiple items
                allows the title to be bold while the rest stay default
                */
                TextFlow text_flow = new TextFlow(bolded_title, rest_task);

                //clear the normal cell to display the boldes text
                setText(null);

                //diplays the bolded text
                setGraphic(text_flow);
            }
        });
    }

    //enables dropdown so users can decide how to sort tasks
    @FXML
    private ComboBox<String> sortBox;

    //adds new task to top of list
    @FXML
    private void handleAddTask() {
        String task_text = buildTaskText();

        //if buildTaskText() returns null, validation faild lol
        if (task_text == null) {
            return;
        }

        //add new task at index 0 so newest task appears first
        taskListView.getItems().add(0, task_text);
        clearFields();
    }

    //updates currently selected task
    @FXML private void handleEditTask() {
        int selected_index = taskListView.getSelectionModel().getSelectedIndex();

        if (selected_index == -1) {
            showAlert("Please select task to edit");
            return;
        }

        //stores task being edited
        editing_task = selected_index;

        //retrieve selected task from the list
        String selected_task = taskListView.getItems().get(selected_index);

        //load the selected task back to its proper field to be edited
        load_task_in_fields(selected_task);

        //show save changes button ONLY when user is editing
        saveChangesButton.setVisible(true);
        saveChangesButton.setManaged(true);

        /*
        commented out because since SaveChnages will now handle the saving of tasks
        String task_text = buildTaskText();

        if (task_text == null) {
            return;
        }

        taskListView.getItems().set(selected_index, task_text);
        clearFields();*/
    }

    //saves the edited task after user clicks saves changes
    @FXML
    private void handleSaveChanges() {
        //checks that user clicks edit task first
        if (editing_task == -1) {
            showAlert("Please choose Edit Task before saving changes.");
            return;
        }

        //puts updated task in the input field
        String updated_task = buildTaskText();

        if (updated_task == null) {
            return;
        }

        //replace old task with the updated task
        taskListView.getItems().set(editing_task, updated_task);

        //keep edited task selected
        taskListView.getSelectionModel().select(editing_task);

        //stop editing
        editing_task = -1;

        //makes save changes button disappear after editing
        saveChangesButton.setVisible(false);
        saveChangesButton.setManaged(false);

        //clears input fields after user saves their changes
        clearFields();

    }

    //deletes cure ntly selected task
    @FXML
    private void handleDeleteTask() {
        int selected_index = taskListView.getSelectionModel().getSelectedIndex();

        if (selected_index == -1) {
            showAlert("Please select a task to delete.");
            return;
        }

        taskListView.getItems().remove(selected_index);
        clearFields();
    }

    //will sort task based on selected priority sorting option
    @FXML
    private void handleSortTasks() {
        //gets the selected option from the drop box
        String selected_sort = sortBox.getValue();

        //if user did not choose sorting option, do nothing
        if (selected_sort == null) {
            return;
        }

        //if user did select an option function call and sort the tasks
        if (selected_sort.equals("Sort by Priority")) {
            sortbyPriority();
        }

        //if user chose to sort task by due date call the function to sort bu due date
        if (selected_sort.equals("Sort by Due Date")) {
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

    private LocalDate getduedateValue(String due_date) {
        try {
            //looks for the dur date in the app
            String marker = "Due: ";
            int begin_index = due_date.indexOf(marker);

            //if Due Date: not found, return null because no valid date
            if (begin_index == -1) {
                return null;
            }

            //moves the index to start after  "Due Date: " because that's the date
            begin_index += marker.length();

            //find end of date right where priority section begins
            int end_index = due_date.indexOf(" | Priority:", begin_index);

            //if the priority labe is not fiunf retun null because wrong format
            if (end_index == -1) {
                return null;
            }

            //gets the due date from the full task list
            String recorded_date = due_date.substring(begin_index, end_index).trim();

            if (recorded_date.equals("No due date")) {
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

    //shows the warning popups
    private void showAlert(String message) {
        Alert alert =  new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Task List");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

