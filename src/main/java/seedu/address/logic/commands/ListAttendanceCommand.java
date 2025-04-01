package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.NavigationMode.STUDENT;
import static seedu.address.model.NavigationMode.TUTORIAL;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.NavigationMode;
import seedu.address.model.student.Student;
import seedu.address.model.tutorial.Tutorial;

/**
 * Lists attendances in the address book to the user.
 */
public class ListAttendanceCommand extends Command {
    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = "Usage: attendance list [INDEX]"
                    + "\nYou must be in STUDENTS or TUTORIAL view";

    public static final String MESSAGE_INVALID_VIEW = "Invalid view. Please switch to STUDENTS or TUTORIAL view first";

    public static final String MESSAGE_SUCCESS = "Listed attendances for %s";

    private final Logger logger = LogsCenter.getLogger(ListAttendanceCommand.class);
    private final Optional<Index> index;

    /**
     * Creates a {@link ListAttendanceCommand} to view the attendance for a specific
     * student or tutorial via an {@code Attendance} object
     */
    public ListAttendanceCommand(Optional<Index> index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        String name;
        requireNonNull(model);

        NavigationMode navigationMode = model.getNavigationMode();

        if (index.isEmpty()) {
            logger.log(Level.INFO, "Showing all attendances");
            model.updateFilteredAttendanceList(Model.PREDICATE_SHOW_ALL_ATTENDANCES);
            name = "all";
        } else if (navigationMode.equals(STUDENT)) {
            logger.log(Level.INFO, "Coming from STUDENT view");
            List<Student> lastShownList = model.getFilteredStudentList();
            Student student = lastShownList.get(index.get().getZeroBased());
            name = student.getName().fullName;
            model.updateFilteredAttendanceList(x -> x.student().hasSameIdentity(student));
        } else if (navigationMode.equals(TUTORIAL)) {
            logger.log(Level.INFO, "Coming from TUTORIAL view");
            List<Tutorial> lastShownList = model.getFilteredTutorialList();
            Tutorial tutorial = lastShownList.get(index.get().getZeroBased());
            name = tutorial.name();
            model.updateFilteredAttendanceList(x -> x.tutorial().hasSameIdentity(tutorial));
        } else {
            throw new CommandException(MESSAGE_INVALID_VIEW);
        }
        return new CommandResult(MESSAGE_SUCCESS.formatted(name), NavigationMode.ATTENDANCE);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListAttendanceCommand otherListAttendanceCommand)) {
            return false;
        }

        return index.equals(otherListAttendanceCommand.index);
    }
}
