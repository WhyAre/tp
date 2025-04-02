package seedu.address.ui.submission;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.student.Student;
import seedu.address.model.submission.Submission;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of submissions.
 */
public class SubmissionListPanel extends UiPart<Region> {
    private static final String FXML = "SubmissionComponents/SubmissionListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(SubmissionListPanel.class);

    @FXML
    private ListView<SubmissionInfo> submissionListView;

    record SubmissionInfo(Tutorial tutorial, Student student, List<Submission> submissions) {
    }

    /**
     * Creates an {@code AttendanceListPanel} with the given {@code ObservableList}.
     */
    public SubmissionListPanel(ObservableList<Submission> submissionList) {
        super(FXML);

        ObservableList<SubmissionInfo> submissionInfoList = FXCollections.observableArrayList();
        for (var submission : submissionList) {
            var existingSubmisson = submissionInfoList.stream()
                            .filter(s -> s.student.hasSameIdentity(submission.student())
                                            && s.tutorial().hasSameIdentity(submission.assignment().tutorial()))
                            .findAny();
            if (existingSubmisson.isPresent()) {
                existingSubmisson.orElseThrow().submissions.add(submission);
            } else {
                submissionInfoList.add(new SubmissionInfo(submission.assignment().tutorial(), submission.student(),
                                new ArrayList<>(List.of(submission))));
            }
        }
        submissionList.addListener((ListChangeListener<? super Submission>) (
                        change
        ) -> {
            submissionInfoList.clear();

            var newOne = new ArrayList<SubmissionInfo>();
            for (var submission : submissionList) {
                var existingSubmisson = newOne.stream()
                                .filter(s -> s.student.hasSameIdentity(submission.student())
                                                && s.tutorial().hasSameIdentity(submission.assignment().tutorial()))
                                .findAny();
                if (existingSubmisson.isPresent()) {
                    existingSubmisson.orElseThrow().submissions.add(submission);
                } else {
                    newOne.add(new SubmissionInfo(submission.assignment().tutorial(), submission.student(),
                                    new ArrayList<>(List.of(submission))));
                }
            }

            submissionInfoList.setAll(newOne);
        });

        submissionListView.setItems(submissionInfoList);
        submissionListView.setCellFactory(listView -> new SubmissionListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of an {@code Attendance}
     * using an {@code AttendanceCard}.
     */
    class SubmissionListViewCell extends ListCell<SubmissionInfo> {
        @Override
        protected void updateItem(SubmissionInfo submissionInfo, boolean empty) {
            super.updateItem(submissionInfo, empty);

            if (empty || submissionInfo == null) {
                setGraphic(null);
                setText(null);
            } else {
                var card = new SubmissionCard(submissionInfo, getIndex() + 1).getRoot();
                setGraphic(card);
            }
        }
    }

}
