package seedu.ezdo.ui;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.ezdo.commons.util.FxViewUtil;

public class TaskCardHeader extends UiPart<Region> {

    private static final String FXML = "TaskCardHeader.fxml";

    public TaskCardHeader(AnchorPane taskCardHeaderPlaceHolder) {
        super(FXML);
        addToPlaceholder(taskCardHeaderPlaceHolder);
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
    }

}
