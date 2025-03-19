package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.student.NameContainsKeywordsPredicate;
import seedu.address.model.tutorial.StudentContainsTutorialKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand = new FindCommand(
                        new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")), null);
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);

        // tutorial keywords only (using 't/' as prefix)
        FindCommand expectedFindCommandTutorial = new FindCommand(null,
                        new StudentContainsTutorialKeywordsPredicate(Arrays.asList("t1", "t2")));
        assertParseSuccess(parser, "t/t1 t/t2", expectedFindCommandTutorial);

        // mixed name and tutorial keywords
        FindCommand expectedMixedFindCommand = new FindCommand(
                        new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")),
                        new StudentContainsTutorialKeywordsPredicate(Arrays.asList("t1", "t2")));
        assertParseSuccess(parser, "Alice Bob t/t1 t/t2", expectedMixedFindCommand);
    }

}
