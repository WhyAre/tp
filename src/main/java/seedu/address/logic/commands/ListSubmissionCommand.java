package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.NavigationMode;
import seedu.address.model.submission.SubmissionStatus;
import seedu.address.model.uniquelist.exceptions.ItemNotFoundException;

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
