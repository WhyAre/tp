package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HANDLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID_STUDENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Student;
import seedu.address.model.tutorial.Tutorial;

/**
 * Adds a student to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = "%s: Adds a student to the address book.\n".formatted(COMMAND_WORD)
                    + "Parameters: %sNAME %sSTUDENT_ID %sPHONE %sEMAIL %sHANDLE %sDETAILS [%sTUTORIAL]...\n".formatted(
                                    PREFIX_NAME, PREFIX_ID_STUDENT, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_HANDLE,
                                    PREFIX_DETAILS, PREFIX_TUTORIAL_NAME)
                    + ("Example: %s %sJohn Doe %sA0123456Z %s98765432 %sjohn@example.com %s@john_doe %sJohn has "
                                    + "faster understanding of concepts. ").formatted(COMMAND_WORD, PREFIX_NAME,
                                                    PREFIX_ID_STUDENT, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_HANDLE,
                                                    PREFIX_DETAILS)
                    + "%sCS2103_T01 %sCS2106_T02".formatted(PREFIX_TUTORIAL_NAME, PREFIX_TUTORIAL_NAME);

    public static final String MESSAGE_SUCCESS = "New student added: %1$s";
    public static final String MESSAGE_DUPLICATE_STUDENT = "This student already exists in the address book";
    public static final String MESSAGE_TUTORIAL_NOT_FOUND = "One of the tutorial groups the student "
                    + "is added to does not exist: ";

    private final Student toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Student}
     */
    public AddCommand(Student student) {
        requireNonNull(student);
        toAdd = student;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasStudent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        }

        String resultMessage = MESSAGE_SUCCESS;
        final Set<Tutorial> existingTutorials = new HashSet<>();
        for (Tutorial tutorial : toAdd.getTutorials()) {
            if (!model.hasTutorial(tutorial)) {
                resultMessage += "\n" + MESSAGE_TUTORIAL_NOT_FOUND + tutorial.name();
            } else {
                existingTutorials.add(tutorial);
            }
        }
        toAdd.setTutorials(existingTutorials);

        model.addStudent(toAdd);

        assert model.check();
        return new CommandResult(String.format(resultMessage, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("toAdd", toAdd).toString();
    }
}
