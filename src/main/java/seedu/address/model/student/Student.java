package seedu.address.model.student;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.model.uniquelist.Identifiable;

/**
 * Represents a Student in the address book. Guarantees: details are present and
 * not null, field values are validated, immutable.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Student implements Identifiable<Student> {

    // Identity fields
    private final Name name;
    private final StudentID studentId;
    private final Phone phone;
    private final Email email;
    private final TelegramHandle handle;

    // Data fields
    private Set<Tutorial> tutorials;

    /**
     * Every field must be present and not null.
     */
    public Student(
        @JsonProperty("name") Name name, @JsonProperty("studentId") StudentID studentId,
        @JsonProperty("phone") Phone phone, @JsonProperty("email") Email email,
        @JsonProperty("handle") TelegramHandle handle,
        @JsonProperty("tutorials") Set<Tutorial> tutorials) {
        requireAllNonNull(name, studentId, phone, email, handle, tutorials);
        this.name = name;
        this.studentId = studentId;
        this.phone = phone;
        this.email = email;
        this.handle = handle;
        this.tutorials = tutorials;
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

    /**
     * Returns an immutable tutorial set, which throws
     * {@code UnsupportedOperationException} if modification is attempted.
     */
    public Set<Tutorial> getTutorials() {
        return Collections.unmodifiableSet(new HashSet<>(tutorials));
    }

    /**
     * Updates the tutorial set
     */
    public void setTutorials(Set<Tutorial> tutorials) {
        this.tutorials = new HashSet<>(tutorials);
    }

    public void addTutorial(Tutorial t) {
        this.tutorials.add(t);
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
     * @param validTuts Set of valid tutorials
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
        return Objects.hash(name, phone, email, handle);
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
