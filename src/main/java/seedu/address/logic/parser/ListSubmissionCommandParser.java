package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;

import seedu.address.logic.commands.ListSubmissionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parser for submission list command
 */
public class ListSubmissionCommandParser implements Parser<ListSubmissionCommand> {
    @Override
    public ListSubmissionCommand parse(String userInput) throws ParseException {
        // Workaround, a space must be prepended if not it can't detect the flag nicely
        // Will have to look into tokenize method to fix it
        var multimap = ArgumentTokenizer.tokenize(" " + userInput, PREFIX_STUDENT_NAME, PREFIX_ASSIGNMENT_NAME,
                        PREFIX_TUTORIAL_NAME);

        var tutorialName = multimap.getValue(PREFIX_TUTORIAL_NAME).orElse("");
        var studentName = multimap.getValue(PREFIX_STUDENT_NAME).orElse("");
        var assignmentName = multimap.getValue(PREFIX_ASSIGNMENT_NAME).orElse("");

        return new ListSubmissionCommand(tutorialName, studentName, assignmentName);
    }
}
