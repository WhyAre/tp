package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.NavigationMode;
import seedu.address.model.student.Student;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.model.uniquelist.exceptions.DuplicateItemException;
import seedu.address.model.uniquelist.exceptions.ItemNotFoundException;

/**
 * Adds students to a tutorial slot.
 */
public class AddStudentToTutorialCommand extends Command {

    public static final String COMMAND_WORD = "add-student";

    public static final String MESSAGE_USAGE = "Usage: tutorial add-student TUTORIAL_NAME s/STUDENT_INDEX...";

    public static final String MESSAGE_SUCCESS = "Students added to tutorial!";

    public static final String MESSAGE_TUTORIAL_NOT_FOUND = "Tutorial not found";
    public static final String MESSAGE_STUDENT_NOT_FOUND = "Student not found";

    private final List<Index> indices;
    private final Tutorial tutorial;

    /**
     * Creates an {@link AddStudentToTutorialCommand} to add the specified
     * {@code Tutorial}
     */
    public AddStudentToTutorialCommand(List<Index> indices, Tutorial tutorial) {
        requireNonNull(indices);
        requireNonNull(tutorial);

        this.indices = indices;
        this.tutorial = tutorial;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.hasTutorial(tutorial)) {
            throw new CommandException(MESSAGE_TUTORIAL_NOT_FOUND);
        }

        List<Student> lastShownList = model.getFilteredStudentList();

        for (Index index : indices) {
            // Check that index is in bounds.
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Student studentToEdit = lastShownList.get(index.getZeroBased());
            Student editedStudent = studentToEdit.clone();
            Set<Tutorial> tutorials = new HashSet<>(studentToEdit.getTutorials());
            tutorials.add(tutorial);
            editedStudent.setTutorials(tutorials);

            assert model.hasStudent(studentToEdit);
            assert studentToEdit.hashCode() == editedStudent.hashCode();
            try {
                model.setStudent(studentToEdit, editedStudent);
                model.addStudentAttendance(tutorial.name(), editedStudent.getName().toString());
            } catch (DuplicateItemException | ItemNotFoundException e) {
                throw new IllegalStateException(Messages.MESSAGE_UNKNOWN_ERROR);
            }
        }

        return new CommandResult(MESSAGE_SUCCESS, NavigationMode.UNCHANGED);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddStudentToTutorialCommand otherAddCommand)) {
            return false;
        }

        return indices.equals(otherAddCommand.indices) && tutorial.equals(otherAddCommand.tutorial);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("indices", indices).add("tutorial", tutorial).toString();
    }
}
