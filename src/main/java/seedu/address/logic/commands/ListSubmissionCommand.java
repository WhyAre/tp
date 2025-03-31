package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.NavigationMode;

/**
 * Creates a new submission
 */
public class ListSubmissionCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Successfully listed submissions";

    /**
     * Creates a {@link SetSubmissionCommand} object
     */
    public ListSubmissionCommand() {

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        return new CommandResult(MESSAGE_SUCCESS, NavigationMode.SUBMISSION);
    }

}
