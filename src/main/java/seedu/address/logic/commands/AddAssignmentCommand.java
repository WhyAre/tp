package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.NavigationMode;
import seedu.address.model.tutorial.Assignment;
import seedu.address.model.uniquelist.exceptions.DuplicateItemException;
import seedu.address.model.uniquelist.exceptions.ItemNotFoundException;

/**
 * Adds a tutorial to the address book.
 */
public class AddAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = "Usage: assignment add ASSIGNMENT_NAME i/INDEX... [d/DUE_DATE]";

    public static final String MESSAGE_SUCCESS = "New assignment added";
    public static final String MESSAGE_INVALID_NAME = """
                    The only valid characters are: letters (A-Z, a-z), digits (0-9), underscores (_), hyphens (-)""";
    public static final String MESSAGE_DUPLICATE_TUTORIAL = "Assignment already exists in tutorial";
    private static final String MESSAGE_TUTORIAL_NOT_FOUND = "Cannot find tutorial";

    private final Assignment toAdd;
    private final List<Index> tutorialIdxList;

    /**
     * Creates an {@link AddAssignmentCommand} to add the specified {@code Tutorial}
     */
    public AddAssignmentCommand(List<Index> tutorialIdxList, Assignment assignment) {
        requireNonNull(assignment);
        toAdd = assignment;
        this.tutorialIdxList = tutorialIdxList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        for (var idx : tutorialIdxList) {
            var idxZeroBased = idx.getZeroBased();

            var tutorial = model.getFilteredTutorialList().get(idxZeroBased);
            if (tutorial == null) {
                throw new CommandException(MESSAGE_TUTORIAL_NOT_FOUND);
            }

            if (!tutorial.addAssignment(toAdd)) {
                throw new CommandException(MESSAGE_DUPLICATE_TUTORIAL);
            }

            try {
                model.setTutorial(tutorial, tutorial);
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
        if (!(other instanceof AddAssignmentCommand otherAddCommand)) {
            return false;
        }

        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("toAdd", toAdd).toString();
    }
}
