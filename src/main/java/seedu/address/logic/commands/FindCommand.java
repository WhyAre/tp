package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.student.NameContainsKeywordsPredicate;
import seedu.address.model.tutorial.StudentContainsTutorialKeywordsPredicate;

/**
 * Finds and lists all students in address book whose name contains any of the
 * argument keywords. Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all students whose names contain any of "
                    + "the specified keywords (case-insensitive) or who are in the specified tutorial group(s), "
                    + "and displays them as a list with index numbers.\n" + "Parameters: [" + PREFIX_TUTORIAL_NAME
                    + "TUTORIAL]...\n" + "Example: " + COMMAND_WORD + " alice bob \n\t\t find " + PREFIX_TUTORIAL_NAME
                    + "CS2103_T01 ";

    private final NameContainsKeywordsPredicate namePredicate;

    private final StudentContainsTutorialKeywordsPredicate tutorialPredicate;

    /**
     * Constructs a {@code FindCommand} with the specified
     * {@code NameContainsKeywordsPredicate} and
     * {@code StudentContainsTutorialKeywordsPredicate}.
     *
     * @param namePredicate
     *            A predicate to filter students based on their name
     * @param tutorialPredicate
     *            A predicate to filter students based on their tutorial
     */
    public FindCommand(NameContainsKeywordsPredicate namePredicate,
                    StudentContainsTutorialKeywordsPredicate tutorialPredicate) {
        this.namePredicate = namePredicate;
        this.tutorialPredicate = tutorialPredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (namePredicate != null && tutorialPredicate != null) {
            model.updateFilteredStudentList(
                            namePredicate.and(student -> student.getTutorials().stream().anyMatch(tutorialPredicate)));
        } else if (namePredicate != null) {
            model.updateFilteredStudentList(namePredicate);
        } else if (tutorialPredicate != null) {
            model.updateFilteredStudentsByTutorialList(tutorialPredicate);
        }

        return new CommandResult(
                        String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredStudentList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return Objects.equals(namePredicate, otherFindCommand.namePredicate)
                && Objects.equals(tutorialPredicate, otherFindCommand.tutorialPredicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("namePredicate", namePredicate).add("tutorialPredicate", tutorialPredicate)
                        .toString();
    }
}
