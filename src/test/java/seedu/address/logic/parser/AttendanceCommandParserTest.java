package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AttendanceCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ListAttendanceCommand;
import seedu.address.logic.commands.MarkAttendanceCommand;
import seedu.address.logic.commands.UnmarkAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tutorial.Tutorial;

public class AttendanceCommandParserTest {
    private final AttendanceParser attendanceParser = new AttendanceParser();
    private final MarkAttendanceCommandParser markAttendanceParser = new MarkAttendanceCommandParser();
    private final UnmarkAttendanceCommandParser unmarkAttendanceParser = new UnmarkAttendanceCommandParser();
    private final int START_WEEK = 3;
    private final int END_WEEK = 13;
    private final int DUMMY_INDEX_ONE = 1;
    private final int DUMMY_INDEX_TWO = 2;
    private final String MESSAGE_INVALID_WEEK = "Weeks are from 3 to 13.\nIf you are making up for tutorials, "
            + "enter the week that is being accounted for.";

    @Test
    public void parseCommand_attendanceCommand_failure() {
        String errorMsg = "Invalid command format! \nUsage: attendance COMMAND\n\nCOMMAND:\n  - list\n  - mark\n  - unmark";
        assertParseFailure(attendanceParser, "", errorMsg);
        assertParseFailure(attendanceParser, "invalid", errorMsg);
    }

    @Test
    public void parseCommand_attendanceCommand_Success() {
        Tutorial tutorial = new Tutorial("CS2106-T11");
        assertParseSuccess(attendanceParser, "mark CS2106-T11 w/3 s/1", new MarkAttendanceCommand(tutorial, START_WEEK, List.of(Index.fromOneBased(DUMMY_INDEX_ONE))));
        assertParseSuccess(attendanceParser, "unmark CS2106-T11 w/3 s/1", new UnmarkAttendanceCommand(tutorial, START_WEEK, List.of(Index.fromOneBased(DUMMY_INDEX_ONE))));
    }

    private void parseCommand_attendanceCommand_success(Parser<? extends Command> parser, boolean isSingleIndex) {
        var tutorialName = "Tutorial-_-Name1";
        var cmd = "%s w/%d s/%d".formatted(tutorialName, START_WEEK, DUMMY_INDEX_ONE);
        List<Index> indices;
        Command actual;

        if (!isSingleIndex) {
            cmd += " s/%d".formatted(DUMMY_INDEX_TWO);
            indices = List.of(Index.fromOneBased(DUMMY_INDEX_ONE), Index.fromOneBased(DUMMY_INDEX_TWO));
        } else {
            indices = List.of(Index.fromOneBased(DUMMY_INDEX_ONE));
        }

        if (parser instanceof MarkAttendanceCommandParser) {
            actual = new MarkAttendanceCommand(new Tutorial(tutorialName), START_WEEK,
                    indices);
            assertParseSuccess(parser, cmd, actual);
        } else if (parser instanceof UnmarkAttendanceCommandParser) {
            actual = new UnmarkAttendanceCommand(new Tutorial(tutorialName), START_WEEK,
                    indices);
            assertParseSuccess(parser, cmd, actual);
        }
    }

    @Test
    public void parseCommand_markAttendanceCommand_singleSuccess() {
        parseCommand_attendanceCommand_success(markAttendanceParser, true);
    }

    @Test
    public void parseCommand_unmarkAttendanceCommand_singleSuccess() {
        parseCommand_attendanceCommand_success(unmarkAttendanceParser, true);
    }

    @Test
    public void parseCommand_markAttendanceCommand_multipleSuccess() {
        parseCommand_attendanceCommand_success(markAttendanceParser, false);
    }

    @Test
    public void parseCommand_unmarkAttendanceCommand_multipleSuccess() {
        parseCommand_attendanceCommand_success(unmarkAttendanceParser, false);
    }

    private void parseCommand_attendanceCommand_invalidWeek(Parser<? extends Command> parser, int week) {
        var tutorialName = "Tutorial-_-Name1";
        var cmd = "%s w/%d s/%d".formatted(tutorialName, week, DUMMY_INDEX_ONE);
        assertParseFailure(parser, cmd, MESSAGE_INVALID_WEEK);
    }

    @Test
    public void parseCommand_markAttendanceCommand_invalidWeekLow() {
        parseCommand_attendanceCommand_invalidWeek(markAttendanceParser, START_WEEK - 1);
    }

    @Test
    public void parseCommand_unmarkAttendanceCommand_invalidWeekLow() {
        parseCommand_attendanceCommand_invalidWeek(unmarkAttendanceParser, START_WEEK - 1);
    }

    @Test
    public void parseCommand_markAttendanceCommand_invalidWeekHigh()  {
        parseCommand_attendanceCommand_invalidWeek(markAttendanceParser, END_WEEK + 1);
    }

    @Test
    public void parseCommand_unmarkAttendanceCommand_invalidWeekHigh()  {
        parseCommand_attendanceCommand_invalidWeek(unmarkAttendanceParser, END_WEEK + 1);
    }

    private void parseCommand_attendanceCommand_invalidNoTutorial(Parser<? extends Command> parser) {
        var cmd = " w/%d s/%d".formatted(START_WEEK, DUMMY_INDEX_ONE);
        if (parser instanceof MarkAttendanceCommandParser) {
            assertParseFailure(parser, cmd, String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceCommand.MESSAGE_USAGE));
        } else if (parser instanceof UnmarkAttendanceCommandParser){
            assertParseFailure(parser, cmd, String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkAttendanceCommand.MESSAGE_USAGE));
        }
    }

    @Test
    public void parseCommand_markAttendanceCommand_invalidNoTutorial() {
        parseCommand_attendanceCommand_invalidNoTutorial(markAttendanceParser);
    }

    @Test
    public void parseCommand_unmarkAttendanceCommand_invalidNoTutorial() {
        parseCommand_attendanceCommand_invalidNoTutorial(unmarkAttendanceParser);
    }

    private void parseCommand_attendanceCommand_invalidNotComplete(Parser<? extends Command> parser) {
        var cmd = "w/%d s/%d".formatted(START_WEEK, DUMMY_INDEX_ONE);
        if (parser instanceof MarkAttendanceCommandParser) {
            assertParseFailure(parser, cmd, String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceCommand.MESSAGE_USAGE));
        } else if (parser instanceof UnmarkAttendanceCommandParser){
            assertParseFailure(parser, cmd, String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkAttendanceCommand.MESSAGE_USAGE));
        }
    }

    @Test
    public void parseCommand_markAttendanceCommand_invalidNotComplete() {
        parseCommand_attendanceCommand_invalidNotComplete(markAttendanceParser);
    }

    @Test
    public void parseCommand_unmarkAttendanceCommand_invalidNotComplete() {
        parseCommand_attendanceCommand_invalidNotComplete(unmarkAttendanceParser);
    }

    private void parseCommand_attendanceCommand_invalidNoWeek(Parser<? extends Command> parser) {
        var cmd = "CS2106-T10 s/%d".formatted(DUMMY_INDEX_ONE);
        if (parser instanceof MarkAttendanceCommand) {
            assertParseFailure(parser, cmd, String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceCommand.MESSAGE_USAGE));
        } else if (parser instanceof UnmarkAttendanceCommandParser){
            assertParseFailure(parser, cmd, String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkAttendanceCommand.MESSAGE_USAGE));
        }
    }

    @Test
    public void parseCommand_markAttendanceCommand_invalidNoWeek() {
        parseCommand_attendanceCommand_invalidNoWeek(markAttendanceParser);
    }

    @Test
    public void parseCommand_unmarkAttendanceCommand_invalidNoWeek() {
        parseCommand_attendanceCommand_invalidNoWeek(unmarkAttendanceParser);
    }

    private void parseCommand_attendanceCommand_invalidNoStudent(Parser<? extends Command> parser) {
        var cmd = "CS2106-T10 w/%d".formatted(START_WEEK);
        if (parser instanceof MarkAttendanceCommand) {
            assertParseFailure(parser, cmd, String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceCommand.MESSAGE_USAGE));
        } else if (parser instanceof UnmarkAttendanceCommandParser){
            assertParseFailure(parser, cmd, String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkAttendanceCommand.MESSAGE_USAGE));
        }
    }

    @Test
    public void parseCommand_markAttendanceCommand_invalidNoStudent() {
        parseCommand_attendanceCommand_invalidNoStudent(markAttendanceParser);
    }

    @Test
    public void parseCommand_unmarkAttendanceCommand_invalidNoStudent() {
        parseCommand_attendanceCommand_invalidNoStudent(unmarkAttendanceParser);
    }
}
