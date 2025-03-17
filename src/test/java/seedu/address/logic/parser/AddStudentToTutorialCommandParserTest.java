package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_1;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddStudentToTutorialCommand;
import seedu.address.model.tutorial.Tutorial;

public class AddStudentToTutorialCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddStudentToTutorialCommand.MESSAGE_USAGE);

    private AddStudentToTutorialCommandParser parser = new AddStudentToTutorialCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, VALID_TUTORIAL_1 + " s/5", new AddStudentToTutorialCommand(
                        List.of(Index.fromOneBased(5)), new Tutorial(VALID_TUTORIAL_1)));
    }

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_TUTORIAL_1, MESSAGE_INVALID_FORMAT);

        // no tutorial name specified
        assertParseFailure(parser, "s/1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // zero index
        assertParseFailure(parser, VALID_TUTORIAL_1 + " s/0", MESSAGE_INVALID_INDEX);
    }
}
