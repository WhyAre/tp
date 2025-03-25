package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.student.Student;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.model.tutorial.TutorialWithStudents;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Student> filteredStudents;
    private final FilteredList<Tutorial> filteredTutorials;
    private final FilteredList<TutorialWithStudents> filteredTutorialWithStudents;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredStudents = new FilteredList<>(this.addressBook.getStudentList());
        filteredTutorials = new FilteredList<>(this.addressBook.getTutorialList());
        filteredTutorialWithStudents = new FilteredList<>(this.addressBook.getTutorialWithStudentsList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    // =========== UserPrefs
    // ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    // =========== AddressBook
    // ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return addressBook.hasStudent(student);
    }

    @Override
    public void deleteStudent(Student target) {
        addressBook.removeStudent(target);
    }

    @Override
    public void addStudent(Student student) {
        addressBook.addStudent(student);
        updateFilteredStudentList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setStudent(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);

        addressBook.setStudent(target, editedStudent);
    }

    @Override
    public void addTutorial(Tutorial t) {
        addressBook.addTutorial(t);
        updateFilteredTutorialList(PREDICATE_SHOW_ALL_TUTORIALS);
    }

    @Override
    public void deleteTutorial(Tutorial t) {
        addressBook.deleteTutorial(t);
    }

    @Override
    public void deleteTutorialFromStudents(Tutorial t) {
        addressBook.deleteTutorialFromStudents(t);
    }

    @Override
    public boolean hasTutorial(Tutorial t) {
        return addressBook.hasTutorial(t);
    }

    @Override
    public void addAttendance(Attendance a) {
        addressBook.addAttendance(a);
    }

    @Override
    public void addStudentAttendance(String t, String s) {
        addressBook.addStudentAttendance(t, s);
    }

    // =========== Filtered Student List Accessors
    // =============================================================

    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return filteredStudents;
    }

    @Override
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
    }

    @Override
    public void updateFilteredStudentsByTutorialList(Predicate<Tutorial> predicate) {
        requireNonNull(predicate);
        Predicate<Student> studentPredicate = student -> student.getTutorials().stream().anyMatch(predicate);
        filteredStudents.setPredicate(studentPredicate);
    }

    // =========== Filtered Tutorial List Accessors
    // =============================================================

    @Override
    public ObservableList<Tutorial> getFilteredTutorialList() {
        return filteredTutorials;
    }

    @Override
    public void updateFilteredTutorialList(Predicate<Tutorial> predicate) {
        requireNonNull(predicate);
        // Hide studentList
        filteredTutorials.setPredicate(predicate);
    }

    public ObservableList<TutorialWithStudents> getFilteredTutorialWithStudents() {
        return filteredTutorialWithStudents;
    }

    @Override
    public List<Student> getStudentsInTutorial(Tutorial tutorial) {
        return this.addressBook.getStudentList().stream().filter(student -> student.getTutorials().contains(tutorial))
                        .collect(Collectors.toList());
    }

    @Override
    public void updateFilteredTutorialWithStudentsList(Predicate<Tutorial> predicate) {
        requireNonNull(predicate);
        List<TutorialWithStudents> tutorialWithStudentsList = addressBook.getTutorialList().stream()
                        .filter(predicate::test)
                        .map(tutorial -> new TutorialWithStudents(tutorial, getStudentsInTutorial(tutorial)))
                        .collect(Collectors.toList());

        Predicate<TutorialWithStudents> isInList = tutorialWithStudents -> tutorialWithStudentsList.stream()
                        .anyMatch(item -> item.equals(tutorialWithStudents));

        filteredTutorialWithStudents.setPredicate(isInList);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook) && userPrefs.equals(otherModelManager.userPrefs)
                        && filteredStudents.equals(otherModelManager.filteredStudents)
                        && filteredTutorials.equals(otherModelManager.filteredTutorials);
    }

}
