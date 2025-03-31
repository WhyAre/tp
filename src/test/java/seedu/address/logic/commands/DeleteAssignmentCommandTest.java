package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TUTORIAL;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tutorial.Assignment;
import seedu.address.testutil.TypicalAddressBook;

public class DeleteAssignmentCommandTest {

    private static final Model modelStub = new ModelManager(TypicalAddressBook.getTypicalAddressBook(),
                    new UserPrefs());

    @Test
    public void constructor_nullIndicesNullAssignment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
        ) -> new DeleteAssignmentCommand(null, null));
    }

    @Test
    public void constructor_nullIndices_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
        ) -> new DeleteAssignmentCommand(null, new Assignment("new-assignment")));
    }

    @Test
    public void constructor_nullAssignment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
        ) -> new DeleteAssignmentCommand(List.of(INDEX_FIRST_TUTORIAL), null));
    }

    @Test
    public void execute_outOfBoundsTutorialIndex_throwsCommandException() {
        Assignment assignment = new Assignment("new-assignment");
        int numTypicalTutorials = TypicalAddressBook.getTypicalAddressBook().getTutorialList().size();
        List<Index> listOfOutOfBoundsIndex = List.of(Index.fromOneBased(numTypicalTutorials + 1));
        DeleteAssignmentCommand deleteAssignmentCommand = new DeleteAssignmentCommand(listOfOutOfBoundsIndex,
                        assignment);

        assertThrows(CommandException.class, DeleteAssignmentCommand.MESSAGE_TUTORIAL_NOT_FOUND, (
        ) -> deleteAssignmentCommand.execute(modelStub));
    }

    @Test
    public void execute_assignmentDoesNotExist_throwsCommandException() {
        Assignment assignment = new Assignment("nonExistentAssignment");
        DeleteAssignmentCommand deleteAssignmentCommand = new DeleteAssignmentCommand(List.of(INDEX_FIRST_TUTORIAL),
                        assignment);

        assertThrows(CommandException.class,
                        DeleteAssignmentCommand.MESSAGE_ASSIGNMENT_NOT_FOUND.formatted(TypicalAddressBook.T1), (
                        ) -> deleteAssignmentCommand.execute(modelStub));
    }

    @Test
    public void execute_assignmentExists_success() throws Exception {
        Assignment assignment = new Assignment("Week 10 Tasks");
        CommandResult commandResult = new DeleteAssignmentCommand(List.of(INDEX_FIRST_TUTORIAL), assignment)
                        .execute(modelStub);
        List<Assignment> assignments = modelStub.getAddressBook().getTutorialList()
                        .get(INDEX_FIRST_TUTORIAL.getZeroBased()).assignments();

        assertEquals(DeleteAssignmentCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
        assertFalse(assignments.contains(assignment));
    }
}
