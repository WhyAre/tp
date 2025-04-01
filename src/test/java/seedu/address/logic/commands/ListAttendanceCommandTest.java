package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.NavigationMode;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * ListAttendanceCommand.
 */
public class ListAttendanceCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListAttendanceCommand(Optional.empty()), model,
                        ListAttendanceCommand.MESSAGE_SUCCESS.formatted("all"), NavigationMode.ATTENDANCE,
                        expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverythingForStudent() {
        model.setNavigationMode(NavigationMode.STUDENT);
        expectedModel.setNavigationMode(NavigationMode.STUDENT);
        assertCommandSuccess(new ListAttendanceCommand(Optional.of(Index.fromOneBased(1))), model,
                        ListAttendanceCommand.MESSAGE_SUCCESS.formatted("Alice Pauline"), NavigationMode.ATTENDANCE,
                        expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverythingForTutorial() {
        model.setNavigationMode(NavigationMode.TUTORIAL);
        expectedModel.setNavigationMode(NavigationMode.TUTORIAL);
        assertCommandSuccess(new ListAttendanceCommand(Optional.of(Index.fromOneBased(1))), model,
                        ListAttendanceCommand.MESSAGE_SUCCESS.formatted("CS2103-T1"), NavigationMode.ATTENDANCE,
                        expectedModel);
    }

    @Test
    public void execute_listIsFiltered_failure() {
        model.setNavigationMode(NavigationMode.ATTENDANCE);
        expectedModel.setNavigationMode(NavigationMode.ATTENDANCE);
        assertCommandFailure(new ListAttendanceCommand(Optional.of(Index.fromOneBased(1))), model,
                        ListAttendanceCommand.MESSAGE_INVALID_VIEW);
    }

    @Test
    public void execute_equalslistAttendance_successSameObj() {
        ListAttendanceCommand listAttendanceCommand = new ListAttendanceCommand(Optional.of(Index.fromOneBased(1)));
        assertEquals(listAttendanceCommand, listAttendanceCommand);
    }
}
