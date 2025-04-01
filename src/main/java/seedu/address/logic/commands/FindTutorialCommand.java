package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.NavigationMode;
import seedu.address.model.tutorial.TutorialContainsKeywordsPredicate;

/**
 * Finds and lists all tutorial in address book whose tutorial name contains any
 * of the argument keywords. Keyword matching is case insensitive.
 */
public class FindTutorialCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tutorials whose names contain any of "
                    + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
                    + "Parameters: [ ]...\n" + "Example: " + COMMAND_WORD + " tutorial find";

    private final TutorialContainsKeywordsPredicate tutorialPredicate;

    public FindTutorialCommand(TutorialContainsKeywordsPredicate tutorialPredicate) {
        this.tutorialPredicate = tutorialPredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTutorialList(tutorialPredicate);

        assert model.check();
        return new CommandResult(String.format(Messages.MESSAGE_TUTORIALS_LISTED_OVERVIEW,
                        model.getFilteredTutorialList().size()), NavigationMode.TUTORIAL);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindTutorialCommand)) {
            return false;
        }

        FindTutorialCommand otherFindTutorialCommand = (FindTutorialCommand) other;
        return (tutorialPredicate.equals(otherFindTutorialCommand.tutorialPredicate));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("tutorialPredicate", tutorialPredicate).toString();
    }
}
