package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INCORRECT_NAVIGATION_MODE;
import static seedu.address.logic.Messages.MESSAGE_TUTORIAL_NOT_FOUND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_IDX;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.NavigationMode;
import seedu.address.model.tutorial.Assignment;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.model.uniquelist.exceptions.ItemNotFoundException;

/**
 * Deletes an assignment from the address book.
 */
public class DeleteAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = "Usage: assignment delete ASSIGNMENT_NAME %sTUTORIAL_INDEX..."
                    .formatted(PREFIX_TUTORIAL_IDX);

    public static final String MESSAGE_SUCCESS = "Successfully deleted assignment";

    private final Assignment toDelete;
    private final List<Index> indices;

    /**
     * Creates a {@link DeleteAssignmentCommand} to delete the specified
     * {@code Assignment}
     */
    public DeleteAssignmentCommand(List<Index> indices, Assignment assignment) {
        requireNonNull(indices);
        requireNonNull(assignment);
        toDelete = assignment;
        this.indices = indices;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getNavigationMode() != NavigationMode.TUTORIAL) {
            throw new CommandException(MESSAGE_INCORRECT_NAVIGATION_MODE.formatted(NavigationMode.TUTORIAL));
        }

        for (Index idx : indices) {
            int idxZeroBased = idx.getZeroBased();

            List<Tutorial> tutorials = model.getFilteredTutorialList();
            if (idxZeroBased >= tutorials.size()) {
                throw new CommandException(MESSAGE_TUTORIAL_NOT_FOUND.formatted(idx.getOneBased()));
            }

            Tutorial tutorial = tutorials.get(idxZeroBased);
            if (tutorial == null) {
                throw new CommandException(MESSAGE_TUTORIAL_NOT_FOUND.formatted(idx.getOneBased()));
            }

            try {
                model.removeAssignment(toDelete.setTutorial(tutorial));
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
        if (!(other instanceof DeleteAssignmentCommand otherDeleteAssignmentCommand)) {
            return false;
        }

        return indices.equals(otherDeleteAssignmentCommand.indices)
                        && toDelete.equals(otherDeleteAssignmentCommand.toDelete);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("indices", indices).add("toDelete", toDelete).toString();
    }
}
