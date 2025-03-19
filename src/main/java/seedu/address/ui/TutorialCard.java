package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.student.Student;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.model.tutorial.TutorialWithStudents;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An UI component that displays information of a {@code Tutorial}.
 */
public class TutorialCard extends UiPart<Region> {

    private static final String FXML = "TutorialListCard.fxml";
    private List<Student> emptyStudents = new ArrayList<>();

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved
     * keywords in JavaFX. As a consequence, UI elements' variable names cannot be
     * set to such keywords or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The
     *      issue on AddressBook level 4</a>
     */

    public final TutorialWithStudents tutorialWithStudents;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private FlowPane students;

    /**
     * Creates a {@code TutorialCard} with the given {@code Tutorial} and index to
     * display.
     */
    public TutorialCard(TutorialWithStudents tutorialWithStudents, int displayedIndex) {
        super(FXML);
        this.tutorialWithStudents = tutorialWithStudents;
        id.setText(displayedIndex + ". ");
        name.setText(tutorialWithStudents.getTutorial().name());
        List<Student> studentList = tutorialWithStudents.getStudents();
        studentList.sort(Comparator.comparing(student -> student.getName().fullName));

        studentList.forEach(student -> {
            Label studentLabel = new Label(student.getName().fullName);
            students.getChildren().add(studentLabel);
        });
    }

    /**
     * Displays the list of students in this tutorial as labels inside the FlowPane.
     *
     * @param studentList The list of students to be displayed.
     */
    private void displayStudents(List<Student> studentList) {
        List<Label> studentLabels = studentList.stream()
                .sorted((s1, s2) -> s1.getName().fullName.compareTo(s2.getName().fullName))
                .map(student -> new Label(student.getName().fullName))
                .collect(Collectors.toList());

        students.getChildren().clear();
        students.getChildren().addAll(studentLabels);
    }
}
