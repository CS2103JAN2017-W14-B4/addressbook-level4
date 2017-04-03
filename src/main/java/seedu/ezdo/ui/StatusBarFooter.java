package seedu.ezdo.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import org.controlsfx.control.StatusBar;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.ezdo.commons.core.LogsCenter;
import seedu.ezdo.commons.events.model.EzDoChangedEvent;
import seedu.ezdo.commons.events.storage.EzDoDirectoryChangedEvent;
import seedu.ezdo.commons.util.FxViewUtil;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    private static final String MESSAGE_SAVE_LOCATION_TOOLTIP = 
            "ezDo Directory Box\nDisplays the directory of ezDo data file.";
    private static final String MESSAGE_STATUS_BAR_TOOLTIP = 
            "Status Box\nDisplays when ezDo data file is last updated.";

    private static final Logger logger = LogsCenter.getLogger(StatusBarFooter.class);

    @FXML
    private StatusBar syncStatus;
    @FXML
    private StatusBar saveLocationStatus;

    private static final String FXML = "StatusBarFooter.fxml";

    public StatusBarFooter(AnchorPane placeHolder, String saveLocation) {
        super(FXML);
        syncStatus.setTooltip(new Tooltip(MESSAGE_STATUS_BAR_TOOLTIP));
        saveLocationStatus.setTooltip(new Tooltip(MESSAGE_SAVE_LOCATION_TOOLTIP));
        addToPlaceholder(placeHolder);
        setSyncStatus("Not updated yet in this session");
        setSaveLocation(saveLocation);
        registerAsAnEventHandler(this);
    }

    private void addToPlaceholder(AnchorPane placeHolder) {
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolder.getChildren().add(getRoot());
    }

    private void setSaveLocation(String location) {
        this.saveLocationStatus.setText(location);
    }

    private void setSyncStatus(String status) {
        this.syncStatus.setText(status);
    }

    @Subscribe
    public void handleEzDoChangedEvent(EzDoChangedEvent ezce) {
        String lastUpdated = (new SimpleDateFormat("dd/MM/YYYY (E) h:mm:ss a")).format(new Date());
        logger.info(LogsCenter.getEventHandlingLogMessage(ezce, "Setting last updated status to " + lastUpdated));
        setSyncStatus("Last Updated: " + lastUpdated);
    }
  //@@author A0139248X
    @Subscribe
    public void handleEzDoDirectoryChangedEvent(EzDoDirectoryChangedEvent ezce) {
        String lastUpdated = (new Date()).toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(ezce, "Setting last updated status to " + lastUpdated));
        logger.info(LogsCenter.getEventHandlingLogMessage(ezce, "Setting save location to " + ezce.getPath()));
        setSyncStatus("Last Updated: " + lastUpdated);
        setSaveLocation(ezce.getPath());
    }
}
