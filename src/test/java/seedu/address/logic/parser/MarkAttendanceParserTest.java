package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AttendanceCommand;
import seedu.address.logic.commands.MarkAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class MarkAttendanceParserTest {
    private MarkAttendanceCommandParser parser = new MarkAttendanceCommandParser();

    @Test
    public void parseCommand_markAttendanceCommand_success() throws ParseException {
        var tutorialName = "Tutorial-_-Name1";
        var cmd = "%s w/%d s/%d".formatted(tutorialName, MarkAttendanceCommand.START_WEEK,
                        MarkAttendanceCommand.END_WEEK);

        var actual = parser.parse(cmd);
        assertTrue(actual instanceof MarkAttendanceCommand);
    }

    @Test
    public void parseCommand_markAttendanceCommand_invalidWeekLow() throws ParseException {
        var tutorialName = "Tutorial-_-Name1";
        var cmd = "%s %s %s w/%d s/%d".formatted(AttendanceCommand.COMMAND_WORD, MarkAttendanceCommand.COMMAND_WORD,
                        tutorialName, MarkAttendanceCommand.START_WEEK - 1, MarkAttendanceCommand.END_WEEK);

        assertThrows(ParseException.class, (
        ) -> parser.parse(cmd));
    }

    @Test
    public void parseCommand_markAttendanceCommand_invalidWeekHigh() throws ParseException {
        var tutorialName = "Tutorial-_-Name1";
        var cmd = "%s %s %s w/%d s/%d".formatted(AttendanceCommand.COMMAND_WORD, MarkAttendanceCommand.COMMAND_WORD,
                        tutorialName, MarkAttendanceCommand.END_WEEK + 1, MarkAttendanceCommand.END_WEEK);

        assertThrows(ParseException.class, (
        ) -> parser.parse(cmd));
    }
}
