package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.NavigationMode;
import seedu.address.model.student.Student;
import seedu.address.model.tutorial.Tutorial;

/**
 * Unmarks a student's attendance
 */
public class UnmarkAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = "Usage: attendance unmark TUTORIAL_NAME w/WEEK s/STUDENT_INDEX...";

    public static final String MESSAGE_SUCCESS = "Attendance unmarked!";

    public static final String MESSAGE_TUTORIAL_NOT_FOUND = "Tutorial not found";

    public static final String MESSAGE_INVALID_WEEK = "Weeks are from 3 to 13. If you are making up for tutorials, "
                    + "enter the week that is being accounted for.";
    public static final String MESSAGE_STUDENT_NOT_FOUND = "Student not found";

    public static final int START_WEEK = 3;
    public static final int END_WEEK = 13;

    private final Tutorial tutorial;
    private final int week;
    private final Index index;

    /**
     * Creates an {@link UnmarkAttendanceCommand} to unmark the specified student's
     * attendance {@code Attendance}
     */
    public UnmarkAttendanceCommand(Tutorial tutorial, int week, Index index) {
        this.tutorial = tutorial;
        this.week = week;
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.hasTutorial(tutorial)) {
            throw new CommandException(MESSAGE_TUTORIAL_NOT_FOUND);
        }

        List<Student> lastShownList = model.getFilteredStudentList();

        Student studentToEdit = lastShownList.get(index.getZeroBased());
        assert model.hasStudent(studentToEdit);

        assert week >= START_WEEK;
        assert week <= END_WEEK;
        model.unmarkAttendance(tutorial, week, studentToEdit);

        return new CommandResult(MESSAGE_SUCCESS, NavigationMode.UNCHANGED);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnmarkAttendanceCommand otherUnmarkAttendanceCommand)) {
            return false;
        }

        return tutorial.equals(otherUnmarkAttendanceCommand.tutorial) && week == otherUnmarkAttendanceCommand.week
                        && index == otherUnmarkAttendanceCommand.index;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("tutorial", tutorial).add("week", week).add("index", index).toString();
    }
}
