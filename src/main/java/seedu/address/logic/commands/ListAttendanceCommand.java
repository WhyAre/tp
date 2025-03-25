package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ATTENDANCES;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.NavigationMode;

/**
 * Lists attendances in the address book to the user.
 */
public class ListAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = "Usage: attendance list";

    public static final String MESSAGE_SUCCESS = "Listed attendances";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredAttendanceList(PREDICATE_SHOW_ALL_ATTENDANCES);
        return new CommandResult(MESSAGE_SUCCESS, NavigationMode.ATTENDANCE);
    }
}
