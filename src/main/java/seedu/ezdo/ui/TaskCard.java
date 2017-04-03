package seedu.ezdo.ui;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.ezdo.logic.parser.DateParser;
import seedu.ezdo.model.todo.ReadOnlyTask;

//@@author A0139177W
public class TaskCard extends UiPart<Region> {

    private String priorityInString;

    private static final String DEFAULT_PRIORITY_NUMBER = "";
    private static final String DEFAULT_PRIORITY_COLOR = "transparent";

    private static final String HIGH_PRIORITY_NUMBER = "1";
    private static final String HIGH_PRIORITY_COLOR = "red";

    private static final String MEDIUM_PRIORITY_NUMBER = "2";
    private static final String MEDIUM_PRIORITY_COLOR = "orange";

    private static final String LOW_PRIORITY_NUMBER = "3";
    private static final String LOW_PRIORITY_COLOR = "green";

    private static final String FXML = "TaskListCard.fxml";
    private static final String CSS_BACKGROUND_COLOR_PROPERTY = "-fx-background-color: ";

    private static final String CSS_STARTDATE_PAST_CURRENT_DATE_COLOR =
            "-fx-text-fill: darkgreen; -fx-font-weight: bold";

    private static final String CSS_OVERDUE_COLOR =
            "-fx-text-fill: red; -fx-font-weight: bold";

    private static final String CSS_ABOUT_TO_DUE_COLOR =
            "-fx-text-fill: orangered; -fx-font-weight: bold";

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat(DateParser.USER_DATE_OUTPUT_FORMAT);

    public static final HashMap<String, String> PRIORITY_COLOR_HASHMAP = new HashMap<>();

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
        setStartDate(task);
        setDueDate(task);
        initTags(task);
    }

    // ========================= STARTDATE ============================ //

    private void setStartDate(ReadOnlyTask task) {
        Date currentDate = new Date();
        startDate.setText(task.getStartDate().value);
        setStartDateColor(currentDate, CSS_STARTDATE_PAST_CURRENT_DATE_COLOR);
    }

    private void setStartDateColor(Date dateReference, String cssColor) {

        try {
            if (dateReference.after(DATE_FORMAT.parse(startDate.getText()))) {
                startDate.setStyle(cssColor);
            }
        } catch (ParseException pe) {
            // Do nothing as the start date is optional
            // and cannot be parsed as Date object
        }
    }

    // ========================= DUEDATE ============================ //

    private void setDueDate(ReadOnlyTask task) {
        Date currentDate = new Date();
        Date dateSevenDaysInAdvance = createDateSevenDaysInAdvance();

        dueDate.setText(task.getDueDate().value);
        setDueDateColor(dateSevenDaysInAdvance, CSS_ABOUT_TO_DUE_COLOR);
        setDueDateColor(currentDate, CSS_OVERDUE_COLOR);
    }

    private void setDueDateColor(Date dateReference, String cssColor) {

        try {
            if (dateReference.after(DATE_FORMAT.parse(dueDate.getText()))) {
                startDate.setStyle(null);
                dueDate.setStyle(cssColor);
            }
        } catch (ParseException pe) {
            // Do nothing as the due date is optional
            // and cannot be parsed as Date object
        }
    }

    private Date createDateSevenDaysInAdvance() {
        int weekIncrement = 7;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, weekIncrement);
        return cal.getTime();
    }

    // ========================= PRIORITY ============================ //

    public void setPriorityInString(String priorityInString) {
        this.priorityInString = priorityInString;
    }

    private void setPriorityColorHashMap() {
        PRIORITY_COLOR_HASHMAP.put(DEFAULT_PRIORITY_NUMBER, DEFAULT_PRIORITY_COLOR);
        PRIORITY_COLOR_HASHMAP.put(LOW_PRIORITY_NUMBER, LOW_PRIORITY_COLOR);
        PRIORITY_COLOR_HASHMAP.put(MEDIUM_PRIORITY_NUMBER, MEDIUM_PRIORITY_COLOR);
        PRIORITY_COLOR_HASHMAP.put(HIGH_PRIORITY_NUMBER, HIGH_PRIORITY_COLOR);
    }

    private void setPriority(ReadOnlyTask task) {
        setPriorityInString(task.getPriority().value);
        priority.setText(priorityInString); // Invisible in UI (for testing
                                            // purposes)
        priorityColor.setStyle(CSS_BACKGROUND_COLOR_PROPERTY + PRIORITY_COLOR_HASHMAP.get(priorityInString));
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
