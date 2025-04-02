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
 * Marks a student as present.
 */
public class MarkAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = "Usage: attendance mark TUTORIAL_NAME w/WEEK s/STUDENT_INDEX...";

    public static final String MESSAGE_SUCCESS = "Attendance marked!";

    public static final String MESSAGE_TUTORIAL_NOT_FOUND = "Tutorial not found";

    public static final String MESSAGE_INVALID_WEEK = "Weeks are from 3 to 13.\nIf you are making up for tutorials, "
                    + "enter the week that is being accounted for.";
    public static final String MESSAGE_STUDENT_NOT_FOUND = "Student not found";

    public static final int START_WEEK = 3;
    public static final int END_WEEK = 13;

    private final Tutorial tutorial;
    private final int week;
    private final List<Index> indices;

    /**
     * Creates a {@link MarkAttendanceCommand} to mark the specified student's
     * attendance {@code Attendance}
     */
    public MarkAttendanceCommand(Tutorial tutorial, int week, List<Index> indices) {
        this.tutorial = tutorial;
        this.week = week;
        this.indices = indices;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.hasTutorial(tutorial)) {
            throw new CommandException(MESSAGE_TUTORIAL_NOT_FOUND);
        }

        assert week >= START_WEEK;
        assert week <= END_WEEK;

        List<Student> students = model.getFilteredStudentList();

        var errMsg = new StringBuilder();

        for (Index index : indices) {
            if (index.getZeroBased() >= students.size()) {
                errMsg.append("Student at index %d is out of bounds\n".formatted(index.getOneBased()));
                continue;
            }
            Student studentToEdit = students.get(index.getZeroBased());
            assert model.hasStudent(studentToEdit);

            try {
                model.markAttendance(tutorial, week, studentToEdit);
            } catch (ItemNotFoundException e) {
                // This should not be possible
                throw new IllegalStateException(Messages.MESSAGE_UNKNOWN_ERROR);
            }
        }

        if (model.getNavigationMode() != NavigationMode.ATTENDANCE) {
            model.updateFilteredAttendanceList(x -> x.tutorial().hasSameIdentity(tutorial));
        }
        String msg = (errMsg.isEmpty()) ? MESSAGE_SUCCESS : "Warning: %s".formatted(errMsg.toString());

        assert model.check();
        return new CommandResult(msg, NavigationMode.ATTENDANCE);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkAttendanceCommand otherMarkAttendanceCommand)) {
            return false;
        }

        return tutorial.equals(otherMarkAttendanceCommand.tutorial) && week == otherMarkAttendanceCommand.week
                        && indices.equals(otherMarkAttendanceCommand.indices);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("tutorial", tutorial).add("week", week).add("indices", indices).toString();
    }
}
