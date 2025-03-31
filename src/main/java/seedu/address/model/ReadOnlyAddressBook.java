package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.student.Student;
import seedu.address.model.submission.Submission;
import seedu.address.model.tutorial.Tutorial;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the students list. This list will not contain
     * any duplicate students.
     */
    ObservableList<Student> getStudentList();

    /**
     * Returns an unmodifiable view of the tutorial list. This list will not contain
     * any duplicate tutorials.
     */
    ObservableList<Tutorial> getTutorialList();

    /**
     * Returns an unmodifiable view of the attendance list.
     */
    ObservableList<Attendance> getAttendanceList();

    /**
     * Returns an unmodifiable view of the submission list.
     */
    ObservableList<Submission> getSubmissionList();
}
