package seedu.address.model.student;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.submission.Submission;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.model.uniquelist.Identifiable;

/**
 * Represents a Student in the address book. Guarantees: details are present and
 * not null, field values are validated, immutable.
 */
public class Student implements Identifiable<Student> {

    // Identity fields
    private final Name name;
    private final StudentID studentId;
    private final Phone phone;
    private final Email email;
    private final TelegramHandle handle;

    // Data fields
    private Set<Tutorial> tutorials;
    private List<Submission> submissions;

    /**
     * Every field must be present and not null.
     */
    public Student(Name name, StudentID studentId, Phone phone, Email email, TelegramHandle handle,
                    Set<Tutorial> tutorials, List<Submission> submissions) {
        requireAllNonNull(name, studentId, phone, email, handle, tutorials);
        this.name = name;
        this.studentId = studentId;
        this.phone = phone;
        this.email = email;
        this.handle = handle;
        this.tutorials = tutorials;
        this.submissions = submissions;
    }

    public Student(Name name, StudentID studentId, Phone phone, Email email, TelegramHandle handle,
                    Set<Tutorial> tutorials) {
        this(name, studentId, phone, email, handle, tutorials, new ArrayList<>());
    }

    public Name getName() {
        return name;
    }

    public StudentID getStudentId() {
        return studentId;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public TelegramHandle getHandle() {
        return handle;
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    /**
     * Returns an immutable tutorial set, which throws
     * {@code UnsupportedOperationException} if modification is attempted.
     */
    public Set<Tutorial> getTutorials() {
        return Collections.unmodifiableSet(tutorials);
    }

    /**
     * Updates the tutorial set
     */
    public void setTutorials(Set<Tutorial> tutorials) {
        this.tutorials = tutorials;
    }

    /**
     * Returns true if the student has the specified tutorial allocated
     */
    public boolean hasTutorial(Tutorial tutorial) {
        return tutorials.contains(tutorial);
    }

    /**
     * Removes invalid tutorials from the student if it doesn't exist in
     * {@code validTuts}
     *
     * @param validTuts
     *            Set of valid tutorials
     */
    public void removeInvalidTutorials(Set<Tutorial> validTuts) {
        tutorials.removeIf(t -> !validTuts.contains(t));
    }

    /**
     * Returns true if both students have the same name. This defines a weaker
     * notion of equality between two students.
     */
    public boolean isSamePerson(Student otherStudent) {
        if (otherStudent == this) {
            return true;
        }

        return otherStudent != null && (otherStudent.getName().equals(getName())
                        || otherStudent.getStudentId().equals(getStudentId())
                        || otherStudent.getPhone().equals(getPhone()) || otherStudent.getEmail().equals(getEmail())
                        || otherStudent.getHandle().equals(getHandle()));
    }

    /**
     * Returns a clone of the current student.
     */
    public Student clone() {
        return new Student(name, studentId, phone, email, handle, new HashSet<>(tutorials));
    }

    /**
     * Returns true if both students have the same identity and data fields. This
     * defines a stronger notion of equality between two students.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Student)) {
            return false;
        }

        Student otherStudent = (Student) other;
        return name.equals(otherStudent.name) && studentId.equals(otherStudent.studentId)
                        && phone.equals(otherStudent.phone) && email.equals(otherStudent.email)
                        && handle.equals(otherStudent.handle) && tutorials.equals(otherStudent.tutorials);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, handle, tutorials);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("name", name).add("studentId", studentId).add("phone", phone)
                        .add("email", email).add("handle", handle).add("tutorials", tutorials).toString();
    }

    @Override
    public boolean hasSameIdentity(Student other) {
        return isSamePerson(other);
    }
}
