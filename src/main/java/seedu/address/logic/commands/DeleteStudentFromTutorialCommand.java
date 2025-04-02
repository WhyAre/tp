package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.NavigationMode;
import seedu.address.model.student.Student;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.model.uniquelist.exceptions.ItemNotFoundException;

/**
 * Deletes students from a tutorial slot.
 */
public class DeleteStudentFromTutorialCommand extends Command {

    public static final String COMMAND_WORD = "delete-student";

    public static final String MESSAGE_USAGE = "Usage: tutorial delete-student TUTORIAL_NAME s/STUDENT_INDEX...";

    public static final String MESSAGE_SUCCESS = "Students removed from tutorial!";

    public static final String MESSAGE_TUTORIAL_NOT_FOUND = "Tutorial not found";
    public static final String MESSAGE_STUDENT_NOT_FOUND = "Student not found";

    private final List<Index> indices;
    private final Tutorial tutorial;

    /**
     * Creates an {@link DeleteStudentFromTutorialCommand} to add the specified
     * {@code Tutorial}
     */
    public DeleteStudentFromTutorialCommand(List<Index> indices, Tutorial tutorial) {
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
                throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
            }

            Student studentToEdit = lastShownList.get(index.getZeroBased());

            try {
                model.removeStudentFromTutorial(tutorial, studentToEdit);
            } catch (ItemNotFoundException e) {
                throw new CommandException(e.getMessage());
            }
        }

        assert model.check();
        return new CommandResult(MESSAGE_SUCCESS, NavigationMode.UNCHANGED);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteStudentFromTutorialCommand otherAddCommand)) {
            return false;
        }

        return indices.equals(otherAddCommand.indices) && tutorial.equals(otherAddCommand.tutorial);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("indices", indices).add("tutorial", tutorial).toString();
    }
}
