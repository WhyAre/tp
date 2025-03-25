package seedu.address.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.student.Student;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.model.tutorial.TutorialWithStudents;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Student> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Tutorial> PREDICATE_SHOW_ALL_TUTORIALS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a student with the same identity as {@code student} exists in
     * the address book.
     */
    boolean hasStudent(Student student);

    /**
     * Deletes the given student. The student must exist in the address book.
     */
    void deleteStudent(Student target);

    /**
     * Adds the given student. {@code student} must not already exist in the address
     * book.
     */
    void addStudent(Student student);

    /**
     * Replaces the given student {@code target} with {@code editedStudent}.
     * {@code target} must exist in the address book. The student identity of
     * {@code editedStudent} must not be the same as another existing student in the
     * address book.
     */
    void setStudent(Student target, Student editedStudent);

    /**
     * Adds a tutorial slot
     */
    void addTutorial(Tutorial tutorial);

    /**
     * Deletes a tutorial slot
     */
    void deleteTutorial(Tutorial tutorial);

    /**
     * Deletes a tutorial from all students that were allocated to it
     */
    void deleteTutorialFromStudents(Tutorial tutorial);

    /**
     * Checks whether tutorial exists in the address book
     */
    boolean hasTutorial(Tutorial tutorial);

    /**
     * Creates attendance record for a tutorial
     */
    void addAttendance(Attendance attendance);

    /**
     * Creates attendance record for a student in specified tutorial
     */
    void addStudentAttendance(String tutorialName, String studentName);

    /**
     * Marks students attendance
     */
    void markAttendance(String tutorialName, int week, int index);

    /**
     * Returns an unmodifiable view of the list of {@code Student} backed by the
     * internal list of {@code versionedAddressBook}
     */
    ObservableList<Student> getFilteredStudentList();

    /**
     * Updates the filter of the filtered student list to filter by the given
     * {@code predicate}.
     *
     * @throws NullPointerException
     *             if {@code predicate} is null.
     */
    void updateFilteredStudentList(Predicate<Student> predicate);

    /**
     * Updates the filter of the filtered student list to filter by the given
     * {@code predicate} for tutorial group(s).
     *
     * @throws NullPointerException
     *             if {@code predicate} is null.
     */
    void updateFilteredStudentsByTutorialList(Predicate<Tutorial> predicate);

    /**
     * Returns an unmodifiable view of the list of {@code Tutorial} backed by the
     * internal list of {@code versionedAddressBook}
     */
    ObservableList<Tutorial> getFilteredTutorialList();

    /**
     * Updates the filter of the filtered tutorial list to filter by the given
     * {@code predicate}.
     *
     * @throws NullPointerException
     *             if {@code predicate} is null.
     */
    void updateFilteredTutorialList(Predicate<Tutorial> predicate);

    /**
     * Returns an unmodifiable view of the list of TutorialWithStudents by mapping
     * tutorials to their respective students
     *
     * @return An observable list of TutorialWithStudents
     */
    ObservableList<TutorialWithStudents> getFilteredTutorialWithStudents();

    /**
     * Retrieves students who are enrolled in the given tutorial
     *
     * @param tutorial
     *            The tutorial to filter students for.
     * @return A list of students in the given tutorial.
     */
    List<Student> getStudentsInTutorial(Tutorial tutorial);

    /**
     * Updates the filter of the filtered tutorial list to filter by the given
     * {@code predicate} for tutorial group(s), along with retrieving students in
     * each tutorial.
     *
     * @throws NullPointerException
     *             if {@code predicate} is null.
     */
    void updateFilteredTutorialWithStudentsList(Predicate<Tutorial> predicate);
}
