package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.student.Student;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.model.tutorial.TutorialWithStudents;

/**
 * Panel containing the list of tutorials.
 */
public class TutorialListPanel extends UiPart<Region> {
    private static final String FXML = "TutorialListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TutorialListPanel.class);

    @FXML
    private ListView<TutorialWithStudents> tutorialListView;

    /**
     * Creates a {@code TutorialListPanel} with the given {@code ObservableList}.
     */
    public TutorialListPanel(ObservableList<TutorialWithStudents> tutorialList) {
        super(FXML);
        tutorialListView.setItems(tutorialList);
        tutorialListView.setCellFactory(listView -> new TutorialListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Tutorial}
     * using a {@code TutorialCard}.
     */
    class TutorialListViewCell extends ListCell<TutorialWithStudents> {
        @Override
        protected void updateItem(TutorialWithStudents tutorialWithStudents, boolean empty) {
            super.updateItem(tutorialWithStudents, empty);

            if (empty || tutorialWithStudents == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TutorialCard(tutorialWithStudents, getIndex() + 1).getRoot());
            }
        }
    }

}
