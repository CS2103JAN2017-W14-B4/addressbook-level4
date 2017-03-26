package guitests.guihandles;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.ezdo.model.tag.UniqueTagList;
import seedu.ezdo.model.todo.ReadOnlyTask;
import seedu.ezdo.ui.TaskCard;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String STARTDATE_FIELD_ID = "#startDate";
    private static final String PRIORITY_FIELD_ID = "#priority";
    private static final String DUEDATE_FIELD_ID = "#dueDate";
    private static final String TAGS_FIELD_ID = "#tags";

    private static final String TEST_DEFAULT_PRIORITY_NUMBER = "";
    private static final String TEST_DEFAULT_PRIORITY_COLOR = "transparent";

    private static final String TEST_LOW_PRIORITY_NUMBER = "1";
    private static final String TEST_LOW_PRIORITY_COLOR = "green";

    private static final String TEST_MEDIUM_PRIORITY_NUMBER = "2";
    private static final String TEST_MEDIUM_PRIORITY_COLOR = "orange";

    private static final String TEST_HIGH_PRIORITY_COLOR = "red";
    private static final String TEST_HIGH_PRIORITY_NUMBER = "3";

    public static final HashMap<String, String> TEST_PRIORITY_COLOR_HASHMAP = new HashMap<>(3);

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
        super(guiRobot, primaryStage, null);
        setTestPriorityColorHashMap();
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getFullName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getStartDate() {
        return getTextFromLabel(STARTDATE_FIELD_ID);
    }

    public String getDueDate() {
        return getTextFromLabel(DUEDATE_FIELD_ID);
    }

    public String getPriority() {
        return getTextFromLabel(PRIORITY_FIELD_ID);
    }

    public String getTaskPriorityColor() {
        return TaskCard.PRIORITY_COLOR_HASHMAP.get(getPriority());
    }

    public List<String> getTags() {
        return getTags(getTagsContainer());
    }

    private List<String> getTags(Region tagsContainer) {
        return tagsContainer.getChildrenUnmodifiable().stream().map(node -> ((Labeled) node).getText())
                .collect(Collectors.toList());
    }

    private List<String> getTags(UniqueTagList tags) {
        return tags.asObservableList().stream().sorted((f1, f2) -> f1.tagName.compareTo(f2.tagName))
                .map(tag -> tag.tagName).collect(Collectors.toList());
    }

    private Region getTagsContainer() {
        return guiRobot.from(node).lookup(TAGS_FIELD_ID).query();
    }

    private void setTestPriorityColorHashMap() {
        TEST_PRIORITY_COLOR_HASHMAP.put(TEST_DEFAULT_PRIORITY_NUMBER, TEST_DEFAULT_PRIORITY_COLOR);
        TEST_PRIORITY_COLOR_HASHMAP.put(TEST_LOW_PRIORITY_NUMBER, TEST_LOW_PRIORITY_COLOR);
        TEST_PRIORITY_COLOR_HASHMAP.put(TEST_MEDIUM_PRIORITY_NUMBER, TEST_MEDIUM_PRIORITY_COLOR);
        TEST_PRIORITY_COLOR_HASHMAP.put(TEST_HIGH_PRIORITY_NUMBER, TEST_HIGH_PRIORITY_COLOR);
    }

    public boolean isSameTask(ReadOnlyTask task) {
        List<String> taskTags = getTags(task.getTags());
        List<String> cardTags = getTags();
        boolean equalTags = taskTags.containsAll(cardTags) && cardTags.containsAll(taskTags);

        return getFullName().equals(task.getName().fullName) && getPriority().equals(task.getPriority().value)
                && getTaskPriorityColor().equals(TEST_PRIORITY_COLOR_HASHMAP.get(getPriority()))
                && getStartDate().equals(task.getStartDate().value) && getDueDate().equals(task.getDueDate().value)
                && equalTags;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getFullName().equals(handle.getFullName()) && getPriority().equals(handle.getPriority())
                    && getTaskPriorityColor().equals(TEST_PRIORITY_COLOR_HASHMAP.get(getPriority()))
                    && getStartDate().equals(handle.getStartDate()) && getTags().equals(handle.getTags());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullName() + " " + getStartDate();
    }
}
