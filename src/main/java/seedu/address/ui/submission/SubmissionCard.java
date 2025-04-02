package seedu.address.ui.submission;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.submission.Submission;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.ui.UiPart;

import java.util.List;

// SubmissionTutorialCard.java
public class SubmissionCard extends UiPart<Region> {
    private static final String FXML = "SubmissionComponents/SubmissionCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label studentName;
    @FXML
    private VBox submissionTutorialsContainer;

    public SubmissionCard(SubmissionListPanel.SubmissionInfo submissionInfo, int displayedIndex) {
        super(FXML);

        studentName.setText(submissionInfo.student().getName() + "");
        id.setText(displayedIndex + "");

        for (Tutorial tutorial : submissionInfo.getSortedTutorials()) {
            Label tutorialLabel = new Label("Tutorial: " + tutorial.name());
            tutorialLabel.getStyleClass().add("tutorial-header");

            FlowPane submissionsPane = new FlowPane();
            submissionsPane.getStyleClass().add("submissions-container");

            for (Submission submission : submissionInfo.tutorialSubmissions().get(tutorial)) {
                Label submissionLabel = new Label(submission.assignment().name());
                submissionLabel.getStyleClass().add("submission-status");

                switch (submission.status()) {
                    case SUBMITTED -> submissionLabel.getStyleClass().add("submitted");
                    case GRADED -> submissionLabel.getStyleClass().add("graded");
                    case NOT_SUBMITTED -> submissionLabel.getStyleClass().add("not-submitted");
                }
                submissionsPane.getChildren().add(submissionLabel);
            }

            VBox tutorialBox = new VBox(tutorialLabel, submissionsPane);
            tutorialBox.getStyleClass().add("tutorial-section");
            submissionTutorialsContainer.getChildren().add(tutorialBox);
        }
    }
}