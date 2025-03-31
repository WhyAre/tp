package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Tutorial}.
 */
public class SubmissionCard extends UiPart<Region> {

    private static final String FXML = "SubmissionListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved
     * keywords in JavaFX. As a consequence, UI elements' variable names cannot be
     * set to such keywords or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The
     *      issue on AddressBook level 4</a>
     */

    public final SubmissionListPanel.SubmissionInfo submissionInfo;
    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private FlowPane submissions;

    /**
     * Creates an {@code AttendanceCard} with the given {@code Attendance} and index
     * to display.
     */
    public SubmissionCard(SubmissionListPanel.SubmissionInfo submissionInfo, int displayedIndex) {
        super(FXML);
        this.submissionInfo = submissionInfo;
        id.setText(displayedIndex + ". ");
        name.setText("%s (%s)".formatted(submissionInfo.student().getName().toString(),
                        submissionInfo.tutorial().name()));

        var submissionList = submissionInfo.submissions();

        for (var submission : submissionList) {
            var label = new Label(submission.assignment().name());

            switch (submission.status()) {
            case SUBMITTED -> label.getStyleClass().add("submitted");
            case GRADED -> label.getStyleClass().add("graded");
            case NOT_SUBMITTED -> label.getStyleClass().add("not-submitted");
            default -> throw new IllegalStateException("Unexpected value: " + submission.status());
            }

            submissions.getChildren().add(label);
        }
    }
}
