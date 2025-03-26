package seedu.address.ui;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.student.Student;

import java.util.Comparator;

import static java.util.Objects.requireNonNull;


/**
 * An UI component that displays specific information of a {@code Student}.
 */
public class StudentArea extends UiPart<Region> {

    private static final String FXML = "StudentArea.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved
     * keywords in JavaFX. As a consequence, UI elements' variable names cannot be
     * set to such keywords or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The
     *      issue on AddressBook level 4</a>
     */

    private final Student student;

    @FXML
    private Label studentNameLabel;
    @FXML
    private Label studentIdLabel;
    @FXML
    private Label studentPhoneLabel;
    @FXML
    private Label studentEmailLabel;
    @FXML
    private Label studentHandleLabel;
    @FXML
    private TextArea details;
    @FXML
    private FlowPane tutorials;

    /**
     * Creates a {@code StudentCode} with the given default values to
     * display.
     */
    public StudentArea() {
        super(FXML);
        this.student = null;
    }

    public StudentArea(Student student) {
        super(FXML);
        this.student = student;
    }

    /**
     * Updates the displayed student information.
     *
     * @param student the new student object to display
     */
    public void updateStudent(Student student) {
        if (student != null) {
            studentNameLabel.setText(student.getName().fullName);
            studentIdLabel.setText(student.getStudentId().id);
            studentPhoneLabel.setText(student.getPhone().value);
            studentHandleLabel.setText(student.getHandle().handle);
            studentEmailLabel.setText(student.getEmail().value);
//            details.setText(student.getDetails().value);
//
//            tutorials.getChildren().clear();
//            student.getTutorials().stream()
//                    .sorted(Comparator.comparing(tutorial -> tutorial.name()))
//                    .forEach(tutorial -> tutorials.getChildren().add(new Label(tutorial.name())));
        } else {
            studentNameLabel.setText("");
            studentIdLabel.setText("");
            studentPhoneLabel.setText("");
            studentHandleLabel.setText("");
            studentEmailLabel.setText("");
            details.setText("");
            tutorials.getChildren().clear();
        }
    }
}
