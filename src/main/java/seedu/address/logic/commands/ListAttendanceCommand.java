package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ATTENDANCES;
import static seedu.address.model.NavigationMode.STUDENT;
import static seedu.address.model.NavigationMode.TUTORIAL;

import java.util.List;

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

    public static final String MESSAGE_USAGE = "Usage: attendance list s/INDEX"
        + "\nYou must be in STUDENTS or TUTORIAL view";

    public static final String MESSAGE_SUCCESS = "Listed attendances for %s";

    private final Index index;

    public ListAttendanceCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        String name = "";
        requireNonNull(model);
        NavigationMode navigationMode = model.getNavigationMode();
        if (navigationMode.equals(STUDENT)) {
            List<Student> lastShownList = model.getFilteredStudentList();
            Student student = lastShownList.get(index.getZeroBased());
            name = student.getName().fullName;
            model.updateFilteredAttendanceList(x -> x.student().equals(student));
        } else if (navigationMode.equals(TUTORIAL)) {
            List<Tutorial> lastShownList = model.getFilteredTutorialList();
            Tutorial tutorial = lastShownList.get(index.getZeroBased());
            name = tutorial.name();
            model.updateFilteredAttendanceList(x -> x.tutorial().equals(tutorial));
        }
        model.updateFilteredAttendanceList(PREDICATE_SHOW_ALL_ATTENDANCES);
        return new CommandResult(MESSAGE_SUCCESS.formatted(name), NavigationMode.ATTENDANCE);
    }
}
