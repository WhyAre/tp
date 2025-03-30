package seedu.address.ui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.student.Student;
import seedu.address.model.tutorial.Tutorial;

/**
 * An UI component that displays information of a {@code Tutorial}.
 */
public class TutorialCard extends UiPart<Region> {

    private static final String FXML = "TutorialListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved
     * keywords in JavaFX. As a consequence, UI elements' variable names cannot be
     * set to such keywords or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The
     *      issue on AddressBook level 4</a>
     */

    public final Tutorial tutorial;
    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label assignments;
    @FXML
    private Label id;
    @FXML
    private FlowPane students;

    /**
     * Creates a {@code TutorialCard} with the given {@code Tutorial} and index to
     * display.
     */
    public TutorialCard(Tutorial tutorial, int displayedIndex) {
        super(FXML);
        this.tutorial = tutorial;
        id.setText(displayedIndex + ". ");
        name.setText(tutorial.name());

        var assignList = tutorial.assignments().stream().map("- %s"::formatted).collect(Collectors.joining("\n"));
        if (assignList.isEmpty()) {
            assignments.setManaged(false);
            assignments.setVisible(false);
        } else {
            assignments.setText(assignList);
        }
    }
}
