package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.testutil.TypicalAddressBook;

public class AttendanceCommandTest {
    private static final Model modelStub = new ModelManager(TypicalAddressBook.getTypicalAddressBook(),
                    new UserPrefs());
    private static final int START_WEEK = 3;
    private static final int END_WEEK = 13;
    private static final int TEST_WEEK = 5;

    private void execute_setAttendance_successfulSingle(boolean isMark) throws Exception {
        CommandResult commandResult;
        List<Integer> testAttendances = new ArrayList<>(Collections.nCopies(11, 0));
        List<Index> indices = List.of(Index.fromOneBased(1));
        testAttendances.set(1, 1);
        testAttendances.set(2, 1);

        if (isMark) {
            commandResult = new MarkAttendanceCommand(TypicalAddressBook.T1, TEST_WEEK, indices).execute(modelStub);
            assertEquals(MarkAttendanceCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
        } else {
            testAttendances.set(2, 0);
            commandResult = new UnmarkAttendanceCommand(TypicalAddressBook.T1, TEST_WEEK, indices).execute(modelStub);
            assertEquals(UnmarkAttendanceCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
        }
        Attendance attendance = modelStub.getAddressBook().getAttendanceList().get(0);
        assertArrayEquals(testAttendances.toArray(), attendance.attendances().toArray());
    }

    @Test
    public void execute_markAttendance_successfulSingle() throws Exception {
        execute_setAttendance_successfulSingle(true);
    }

    @Test
    public void execute_unmarkAttendance_successfulSingle() throws Exception {
        execute_setAttendance_successfulSingle(false);
    }

    private void execute_setAttendance_successfulMultiple(boolean isMark) throws Exception {
        CommandResult commandResult;
        List<Integer> firstTestAttendances = new ArrayList<>(Collections.nCopies(11, 0));
        List<Integer> secondTestAttendances = new ArrayList<>(Collections.nCopies(11, 0));
        List<Index> indices = List.of(Index.fromOneBased(1), Index.fromOneBased(2));
        firstTestAttendances.set(1, 1);
        firstTestAttendances.set(2, 1);
        secondTestAttendances.set(2, 1);

        if (isMark) {
            commandResult = new MarkAttendanceCommand(TypicalAddressBook.T1, TEST_WEEK, indices).execute(modelStub);
            assertEquals(MarkAttendanceCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
        } else {
            firstTestAttendances.set(2, 0);
            secondTestAttendances.set(2, 0);
            commandResult = new UnmarkAttendanceCommand(TypicalAddressBook.T1, TEST_WEEK, indices).execute(modelStub);
            assertEquals(UnmarkAttendanceCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
        }

        Attendance attendanceOne = modelStub.getAddressBook().getAttendanceList().get(0);
        Attendance attendanceTwo = modelStub.getAddressBook().getAttendanceList().get(2);

        assertArrayEquals(firstTestAttendances.toArray(), attendanceOne.attendances().toArray());
        assertArrayEquals(secondTestAttendances.toArray(), attendanceTwo.attendances().toArray());
    }

    @Test
    public void execute_markAttendance_successfulMultiple() throws Exception {
        execute_setAttendance_successfulMultiple(true);
    }

    @Test
    public void execute_unmarkAttendance_successfulMultiple() throws Exception {
        execute_setAttendance_successfulMultiple(false);
    }

    private void execute_setAttendance_invalidWeek(int week, boolean isMark) throws Exception {
        List<Index> indices = List.of(Index.fromOneBased(1));
        if (isMark) {
            MarkAttendanceCommand cmd = new MarkAttendanceCommand(TypicalAddressBook.T1, week, indices);

            assertThrows(AssertionError.class, (
            ) -> cmd.execute(modelStub));
        } else {
            UnmarkAttendanceCommand cmd = new UnmarkAttendanceCommand(TypicalAddressBook.T1, week, indices);

            assertThrows(AssertionError.class, (
            ) -> cmd.execute(modelStub));
        }
    }

    @Test
    public void execute_markAttendance_invalidWeekLow() throws Exception {
        execute_setAttendance_invalidWeek(START_WEEK - 1, true);
    }

    @Test
    public void execute_unmarkAttendance_invalidWeekLow() throws Exception {
        execute_setAttendance_invalidWeek(START_WEEK - 1, false);
    }

    @Test
    public void execute_markAttendance_invalidWeekHigh() throws Exception {
        execute_setAttendance_invalidWeek(END_WEEK + 1, true);
    }

    @Test
    public void execute_unmarkAttendance_invalidWeekHigh() throws Exception {
        execute_setAttendance_invalidWeek(END_WEEK + 1, false);
    }

    private void execute_setAttendance_invalidTutorial(boolean isMark) throws Exception {
        List<Index> indices = List.of(Index.fromOneBased(1));
        Tutorial tutorial = new Tutorial("CS000");
        if (isMark) {
            MarkAttendanceCommand cmd = new MarkAttendanceCommand(tutorial, TEST_WEEK, indices);

            assertThrows(CommandException.class, MarkAttendanceCommand.MESSAGE_TUTORIAL_NOT_FOUND, (
            ) -> cmd.execute(modelStub));
        } else {
            UnmarkAttendanceCommand cmd = new UnmarkAttendanceCommand(tutorial, TEST_WEEK, indices);

            assertThrows(CommandException.class, UnmarkAttendanceCommand.MESSAGE_TUTORIAL_NOT_FOUND, (
            ) -> cmd.execute(modelStub));
        }
    }

    @Test
    public void execute_markAttendance_invalidTutorial() throws Exception {
        execute_setAttendance_invalidTutorial(true);
    }

    @Test
    public void execute_unmarkAttendance_invalidTutorial() throws Exception {
        execute_setAttendance_invalidTutorial(false);
    }

    private void execute_setAttendance_successfWithInvalidIndex(boolean isMark) throws Exception {
        CommandResult commandResult;
        String errorMsg = "Student at index 100 is out of bounds\n";
        List<Integer> testAttendances = new ArrayList<>(Collections.nCopies(11, 0));
        List<Index> indices = List.of(Index.fromOneBased(1), Index.fromOneBased(100));
        testAttendances.set(1, 1);
        testAttendances.set(2, 1);

        if (isMark) {
            commandResult = new MarkAttendanceCommand(TypicalAddressBook.T1, TEST_WEEK, indices).execute(modelStub);
            assertEquals("Warning: %s".formatted(errorMsg), commandResult.getFeedbackToUser());
        } else {
            testAttendances.set(2, 0);
            commandResult = new UnmarkAttendanceCommand(TypicalAddressBook.T1, TEST_WEEK, indices).execute(modelStub);
            assertEquals("Warning: %s".formatted(errorMsg), commandResult.getFeedbackToUser());
        }

        Attendance attendanceOne = modelStub.getAddressBook().getAttendanceList().get(0);

        assertArrayEquals(testAttendances.toArray(), attendanceOne.attendances().toArray());
    }

    @Test
    public void execute_markAttendance_successfWithInvalidIndex() throws Exception {
        execute_setAttendance_successfWithInvalidIndex(true);
    }

    @Test
    public void execute_unmarkAttendance_successfWithInvalidIndex() throws Exception {
        execute_setAttendance_successfWithInvalidIndex(false);
    }

    private void execute_equalsAttendance_successSameObject(Command command) {
        assertEquals(command, command);
    }

    @Test
    public void execute_markAttendance_successSameObject() {
        execute_equalsAttendance_successSameObject(
                        new MarkAttendanceCommand(TypicalAddressBook.T1, TEST_WEEK, List.of(Index.fromOneBased(1))));
    }

    @Test
    public void execute_unmarkAttendance_successSameObject() {
        execute_equalsAttendance_successSameObject(
                        new UnmarkAttendanceCommand(TypicalAddressBook.T1, TEST_WEEK, List.of(Index.fromOneBased(1))));
    }

    private void execute_equalsAttendance_successSameFields(Command command, Command other) {
        assertEquals(command, other);
    }

    @Test
    public void execute_markAttendance_successSameFields() {
        execute_equalsAttendance_successSameFields(
                        new MarkAttendanceCommand(TypicalAddressBook.T1, TEST_WEEK, List.of(Index.fromOneBased(1))),
                        new MarkAttendanceCommand(TypicalAddressBook.T1, TEST_WEEK, List.of(Index.fromOneBased(1))));
    }

    @Test
    public void execute_unmarkAttendance_successSameFields() {
        execute_equalsAttendance_successSameFields(
                        new UnmarkAttendanceCommand(TypicalAddressBook.T1, TEST_WEEK, List.of(Index.fromOneBased(1))),
                        new UnmarkAttendanceCommand(TypicalAddressBook.T1, TEST_WEEK, List.of(Index.fromOneBased(1))));
    }

    private void execute_equalsAttendance_failureNotSameClass(Command command, Command other) {
        assertNotEquals(command, other);
    }

    @Test
    public void execute_markAttendance_failureNotSameClass() {
        execute_equalsAttendance_failureNotSameClass(
                        new MarkAttendanceCommand(TypicalAddressBook.T1, TEST_WEEK, List.of(Index.fromOneBased(1))),
                        new UnmarkAttendanceCommand(TypicalAddressBook.T1, TEST_WEEK, List.of(Index.fromOneBased(1))));
    }

    @Test
    public void execute_unmarkAttendance_failureNotSameClass() {
        execute_equalsAttendance_failureNotSameClass(
                        new UnmarkAttendanceCommand(TypicalAddressBook.T1, TEST_WEEK, List.of(Index.fromOneBased(1))),
                        new MarkAttendanceCommand(TypicalAddressBook.T1, TEST_WEEK, List.of(Index.fromOneBased(1))));
    }

    private void execute_equalsAttendance_failureNotSameDetails(Command command, Command other) {
        assertNotEquals(command, other);
    }

    @Test
    public void execute_markAttendance_failureNotSameDetails() {
        MarkAttendanceCommand markAttendanceCommand = new MarkAttendanceCommand(TypicalAddressBook.T1, TEST_WEEK,
                        List.of(Index.fromOneBased(1)));
        execute_equalsAttendance_failureNotSameDetails(markAttendanceCommand,
                        new MarkAttendanceCommand(TypicalAddressBook.T2, TEST_WEEK, List.of(Index.fromOneBased(1))));
        execute_equalsAttendance_failureNotSameDetails(markAttendanceCommand, new MarkAttendanceCommand(
                        TypicalAddressBook.T1, TEST_WEEK + 1, List.of(Index.fromOneBased(1))));
        execute_equalsAttendance_failureNotSameDetails(markAttendanceCommand,
                        new MarkAttendanceCommand(TypicalAddressBook.T1, TEST_WEEK, List.of(Index.fromOneBased(2))));
    }

    @Test
    public void execute_unmarkAttendance_failureNotSameDetails() {
        UnmarkAttendanceCommand unmarkAttendanceCommand = new UnmarkAttendanceCommand(TypicalAddressBook.T1, TEST_WEEK,
                        List.of(Index.fromOneBased(1)));
        execute_equalsAttendance_failureNotSameDetails(unmarkAttendanceCommand,
                        new MarkAttendanceCommand(TypicalAddressBook.T2, TEST_WEEK, List.of(Index.fromOneBased(1))));
        execute_equalsAttendance_failureNotSameDetails(unmarkAttendanceCommand, new MarkAttendanceCommand(
                        TypicalAddressBook.T1, TEST_WEEK + 1, List.of(Index.fromOneBased(1))));
        execute_equalsAttendance_failureNotSameDetails(unmarkAttendanceCommand,
                        new MarkAttendanceCommand(TypicalAddressBook.T1, TEST_WEEK, List.of(Index.fromOneBased(2))));
    }
}
