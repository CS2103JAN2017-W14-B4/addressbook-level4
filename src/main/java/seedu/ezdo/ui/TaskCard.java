package seedu.ezdo.ui;

import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.ezdo.model.todo.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private String priorityInString;

    private static final String DEFAULT_PRIORITY_NUMBER = new String();
    private static final String DEFAULT_PRIORITY_COLOR = "transparent";

    private static final String LOW_PRIORITY_NUMBER = "1";
    private static final String LOW_PRIORITY_COLOR = "green";

    private static final String MEDIUM_PRIORITY_NUMBER = "2";
    private static final String MEDIUM_PRIORITY_COLOR = "orange";

    private static final String HIGH_PRIORITY_COLOR = "red";
    private static final String HIGH_PRIORITY_NUMBER = "3";

    private static final String FXML = "TaskListCard.fxml";
    private static final String CSS_BACKGROUND_COLOR = "-fx-background-color: ";

    public static final HashMap<String, String> priorityColorHashMap = new HashMap<>(3);

    @FXML
    private HBox cardPane;
    @FXML
    private AnchorPane priorityColor;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label priority;
    @FXML
    private Label startDate;
    @FXML
    private Label dueDate;
    @FXML
    private FlowPane tags;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        setPriorityColorHashMap();
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        setPriority(task);
        startDate.setText(task.getStartDate().value);
        dueDate.setText(task.getDueDate().value);
        initTags(task);
    }

    public void setPriorityInString(String priorityInString) {
        this.priorityInString = priorityInString;
    }

    private void setPriorityColorHashMap() {
        priorityColorHashMap.put(DEFAULT_PRIORITY_NUMBER, DEFAULT_PRIORITY_COLOR);
        priorityColorHashMap.put(LOW_PRIORITY_NUMBER, LOW_PRIORITY_COLOR);
        priorityColorHashMap.put(MEDIUM_PRIORITY_NUMBER, MEDIUM_PRIORITY_COLOR);
        priorityColorHashMap.put(HIGH_PRIORITY_NUMBER, HIGH_PRIORITY_COLOR);
    }

    private void setPriority(ReadOnlyTask task) {
        setPriorityInString(task.getPriority().value);
        priority.setText(priorityInString); // Invisible in UI (for testing
                                            // purposes)
        priorityColor.setStyle(CSS_BACKGROUND_COLOR + priorityColorHashMap.get(priorityInString));
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
