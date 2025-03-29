package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_ASSIGNMENT_NOT_FOUND;
import static seedu.address.logic.Messages.MESSAGE_STUDENT_NOT_FOUND;
import static seedu.address.logic.Messages.MESSAGE_TUTORIAL_NOT_FOUND;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_ERROR;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.student.Student;
import seedu.address.model.submission.Submission;
import seedu.address.model.submission.SubmissionStatus;
import seedu.address.model.tutorial.Assignment;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.model.tutorial.TutorialWithStudents;
import seedu.address.model.uniquelist.UniqueList;
import seedu.address.model.uniquelist.exceptions.DuplicateItemException;
import seedu.address.model.uniquelist.exceptions.ItemNotFoundException;

/**
 * Wraps all data at the address-book level Duplicates are not allowed (by
 * .isSameStudent comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueList<Student> students;
    private final UniqueList<Tutorial> tutorials;
    private final UniqueList<Attendance> attendances;
    private final UniqueList<Submission> submissions;

    /*
     * The 'unusual' code block below is a non-static initialization block,
     * sometimes used to avoid duplication between constructors. See
     * https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other
     * ways to avoid duplication among constructors.
     */
    {
        students = new UniqueList<>();
        tutorials = new UniqueList<>();
        attendances = new UniqueList<>();
        submissions = new UniqueList<>();
    }

    public AddressBook() {
    }

    /**
     * Creates an AddressBook using the students in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the student list with {@code students}.
     * {@code students} must not contain duplicate students.
     */
    public void setStudents(List<Student> students) throws DuplicateItemException {
        this.students.setAll(students);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        try {
            setStudents(newData.getStudentList());
            setTutorials(newData.getTutorialList());
            setAttendances(newData.getAttendanceList());
            setSubmissions(newData.getSubmissionList());
        } catch (DuplicateItemException e) {
            // Since it's coming from an address book, these errors shouldn't be thrown
            throw new IllegalStateException(MESSAGE_UNKNOWN_ERROR);
        }
    }

    //// student-level operations

    /**
     * Returns true if a student with the same identity as {@code student} exists in
     * the address book.
     */
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return students.containsIdentity(student);
    }

    /**
     * Adds a student to the address book. The student must not already exist in the
     * address book.
     */
    public void addStudent(Student p) { // Check that tutorial slots exists
        var student = p.clone();
        student.removeInvalidTutorials(new HashSet<>(tutorials));

        students.add(student);

        for (var tutorial : student.getTutorials()) {
            assert tutorials.containsIdentity(tutorial);
            assert students.containsIdentity(student);
            try {
                addAttendance(tutorial, student);
            } catch (ItemNotFoundException e) {
                // Both tutorial and student will exist in the address book, this shouldn't
                // happen
                throw new IllegalStateException(MESSAGE_UNKNOWN_ERROR);
            }
        }
    }

    /**
     * Replaces the given student {@code target} in the list with
     * {@code editedstudent}. {@code target} must exist in the address book. The
     * student identity of {@code editedstudent} must not be the same as another
     * existing student in the address book.
     */
    public void setStudent(Student target, Student editedstudent) throws DuplicateItemException, ItemNotFoundException {
        requireNonNull(editedstudent);

        students.set(target, editedstudent);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}. {@code key} must exist in
     * the address book.
     */
    public void removeStudent(Student key) {
        // Remove student from the following:
        // - submissions
        // - attendances

        students.remove(key);
        deleteStudentFromAttendances(key);
        deleteStudentFromSubmissions(key);
    }

    //// Tutorial operations

    /**
     * Adds a tutorial slot
     */
    public void addTutorial(Tutorial tutorial) {
        tutorials.add(tutorial);
    }

    /**
     * Deletes a tutorial slot
     */
    public void deleteTutorial(Tutorial tutorial) {
        Tutorial toDelete = tutorials.find(tutorial).orElse(tutorial);
        tutorials.remove(toDelete);
    }

    /**
     * Deletes a tutorial from all students that were allocated to it
     */
    public void deleteTutorialFromStudents(Tutorial tutorial) {
        List<Student> currentList = this.getStudentList();

        for (Student student : currentList) {
            if (!student.hasTutorial(tutorial)) {
                continue;
            }
            Student editedstudent = student.clone();
            Set<Tutorial> newTutorials = new HashSet<>(editedstudent.getTutorials());
            newTutorials.remove(tutorial);
            editedstudent.setTutorials(newTutorials);

            // Assertions
            // - editedStudent always be unique
            assert students.contains(student);

            try {
                this.setStudent(student, editedstudent);
            } catch (DuplicateItemException | ItemNotFoundException e) {
                throw new IllegalStateException(MESSAGE_UNKNOWN_ERROR);
            }
        }
    }

    public void deleteStudentFromSubmissions(Student stu) {
        submissions.removeIf(s -> s.student().hasSameIdentity(stu));
    }

    public void deleteStudentFromAttendances(Student stu) {
        attendances.removeIf(a -> a.student().hasSameIdentity(stu));
    }

    /**
     * Checks whether tutorial exists in the address book
     */
    public boolean hasTutorial(Tutorial tutorial) {
        requireNonNull(tutorial);
        return tutorials.containsIdentity(tutorial);
    }

    /**
     * Replaces the contents of the tutorial list with {@code tutorials}.
     * {@code tutorials} must not contain duplicate tutorials.
     */
    public void setTutorials(List<Tutorial> tutorials) throws DuplicateItemException {
        requireNonNull(tutorials);
        this.tutorials.setAll(tutorials);
    }

    public void setTutorial(Tutorial oldTut, Tutorial newTut) throws DuplicateItemException, ItemNotFoundException {
        requireNonNull(newTut);

        tutorials.set(oldTut, newTut);
    }

    public void addAssignment(Assignment assignment) throws ItemNotFoundException {
        // Resolve tutorial
        var tut = tutorials.find(assignment.tutorial()).orElseThrow((
        ) -> new ItemNotFoundException(MESSAGE_TUTORIAL_NOT_FOUND.formatted(assignment.tutorial())));

        tut.addAssignment(assignment);
    }

    public void setSubmissionStatus(String tutorialName, String assignmentName, Student student,
                    SubmissionStatus status) throws ItemNotFoundException {
        var tut = tutorials.find(new Tutorial(tutorialName)).orElseThrow((
        ) -> new IllegalArgumentException(MESSAGE_TUTORIAL_NOT_FOUND.formatted(tutorialName)));
        var assign = tut.findAssignment(new Assignment(assignmentName)).orElseThrow((
        ) -> new IllegalArgumentException(MESSAGE_ASSIGNMENT_NOT_FOUND.formatted(assignmentName)));

        setSubmissionStatus(new Submission(assign, student, status));
    }

    /**
     * Sets submission status on a submission, identified by tutorialName,
     * assignmentName and student
     */
    public void setSubmissionStatus(Submission submission) throws ItemNotFoundException {
        // Resolve assignment
        var tut = tutorials.find(submission.assignment().tutorial()).orElseThrow((
        ) -> new ItemNotFoundException(MESSAGE_TUTORIAL_NOT_FOUND.formatted(submission.assignment().tutorial())));
        var assignment = tut.findAssignment(submission.assignment()).orElseThrow((
        ) -> new ItemNotFoundException(MESSAGE_ASSIGNMENT_NOT_FOUND.formatted(submission.assignment())));

        // Resolve student
        var studentInList = students.find(submission.student()).orElseThrow((
        ) -> new ItemNotFoundException(MESSAGE_STUDENT_NOT_FOUND.formatted(submission.student())));

        // Add to submissions list
        var newSubmission = new Submission(submission).setAssignment(assignment).setStudent(studentInList);

        var submissionInList = submissions.find(newSubmission);
        if (submissionInList.isEmpty()) {
            assignment.addSubmission(newSubmission);
            studentInList.addSubmission(newSubmission);
            submissions.add(newSubmission);
        } else {
            var existingSubmission = submissionInList.orElseThrow();
            existingSubmission.setStatus(submission.status());
        }
    }

    /**
     * Creates attendance record for a student in specified tutorial
     */
    public void addAttendance(Tutorial tutorial, Student student) throws ItemNotFoundException {
        setAttendance(new Attendance(tutorial, student));
    }

    /**
     * Marks students attendance
     */
    public void addAttendance(Tutorial tutorial, int week, Student student, boolean isPresent)
                    throws DuplicateItemException, ItemNotFoundException {
        requireNonNull(tutorial);
        requireNonNull(student);

        for (Attendance attendance : attendances) {
            if (attendance.tutorial().hasSameIdentity(tutorial) && attendance.student().hasSameIdentity(student)) {
                attendance.setAttendance(week, isPresent);
                attendances.set(attendance, attendance);
                break;
            }
        }
    }

    /**
     * Marks students attendance
     */
    public void setAttendance(Attendance attendance) throws ItemNotFoundException {
        // Fetch tutorial from tutorial list
        Tutorial tutorialFromList = tutorials.find(attendance.tutorial()).orElseThrow((
        ) -> new ItemNotFoundException(MESSAGE_TUTORIAL_NOT_FOUND.formatted(attendance.tutorial())));

        // Fetch student from student list
        Student studentFromList = students.find(attendance.student()).orElseThrow((
        ) -> new ItemNotFoundException(MESSAGE_STUDENT_NOT_FOUND.formatted(attendance.student())));

        // Check whether existing attendance exists in the addressbook
        var maybeAttendance = attendances.find(attendance);

        if (maybeAttendance.isEmpty()) {
            var newAttendance = new Attendance(attendance).setTutorial(tutorialFromList).setStudent(studentFromList);
            tutorialFromList.addAttendance(newAttendance);
            studentFromList.addAttendance(newAttendance);
            attendances.add(newAttendance);
        } else {
            var existingAttendance = maybeAttendance.orElseThrow();
            existingAttendance.setAttendances(attendance.attendances());
        }
    }

    /**
     * Replaces the contents of the attendance list with {@code attendances}.
     */
    public void setAttendances(List<Attendance> attendances) throws DuplicateItemException {
        requireNonNull(attendances);
        this.attendances.setAll(attendances);
    }

    public void setSubmissions(List<Submission> submissions) throws DuplicateItemException {
        requireNonNull(submissions);
        this.submissions.setAll(submissions);
    }

    /**
     * Marks student as present
     */
    public void markAttendance(Tutorial tutorial, int week, Student student)
                    throws DuplicateItemException, ItemNotFoundException {
        addAttendance(tutorial, week, student, true);
    }

    /**
     * Unmarks a student's attendance
     */
    public void unmarkAttendance(Tutorial tutorial, int week, Student student)
                    throws DuplicateItemException, ItemNotFoundException {
        addAttendance(tutorial, week, student, false);
    }

    /// / util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("students", students).add("tutorials", tutorials).toString();
    }

    @Override
    public ObservableList<Student> getStudentList() {
        return students.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Tutorial> getTutorialList() {
        return tutorials.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Attendance> getAttendanceList() {
        return attendances.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Submission> getSubmissionList() {
        return submissions.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook otherAddressBook)) {
            return false;
        }

        return students.equals(otherAddressBook.students) && tutorials.equals(otherAddressBook.tutorials)
                        && attendances.equals(otherAddressBook.attendances)
                        && submissions.equals(otherAddressBook.submissions);
    }

    @Override
    public int hashCode() {
        return students.hashCode();
    }

    /**
     * Returns an observable list of {@code TutorialWithStudents}, each pairing a
     * tutorial with the students enrolled in that tutorial.
     *
     * @return An observable list of {@code TutorialWithStudents}.
     */
    public ObservableList<TutorialWithStudents> getTutorialWithStudentsList() {
        return tutorials.stream().map(tutorial -> new TutorialWithStudents(tutorial, getStudentsInTutorial(tutorial)))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    /**
     * Retrieves the list of students enrolled in a specific tutorial. This method
     * is a placeholder and should be implemented to return the actual students
     * enrolled in the given tutorial.
     *
     * @param tutorial
     *            The tutorial for which the enrolled students are to be retrieved.
     * @return A list of students enrolled in the given tutorial.
     */
    private List<Student> getStudentsInTutorial(Tutorial tutorial) {
        return this.getStudentList().stream().filter(student -> student.getTutorials().contains(tutorial))
                        .collect(Collectors.toList());
    }
}
