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

/**
 * Adds students to a tutorial slot.
 */
public class MarkAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = "Usage: attendance mark TUTORIAL_NAME w/WEEK s/STUDENT_INDEX...";

    public static final String MESSAGE_SUCCESS = "Attendance marked!";

    public static final String MESSAGE_TUTORIAL_NOT_FOUND = "Tutorial not found";
    public static final String MESSAGE_STUDENT_NOT_FOUND = "Student not found";

    private final Tutorial tutorial;
    private final int week;
    private final int index;

    /**
     * Creates a {@link MarkAttendanceCommand} to mark the specified student's attendance {@code Attendance}
     */
    public MarkAttendanceCommand(Tutorial tutorial, int week, int index) {
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

        String tutorialName = tutorial.name();
        model.markAttendance(tutorialName, week, index);

        return new CommandResult(MESSAGE_SUCCESS, NavigationMode.UNCHANGED);
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

        return tutorial.equals(otherMarkAttendanceCommand.tutorial) && week == otherMarkAttendanceCommand.week && index == otherMarkAttendanceCommand.index;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("tutorial", tutorial).add("week", week).add("index", index).toString();
    }
}