package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.logic.Messages.MESSAGE_TUTORIALS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.NavigationMode;
import seedu.address.model.UserPrefs;
import seedu.address.model.tutorial.TutorialContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for
 * {@code FindTutorialCommand}.
 */
public class FindTutorialCommandTest {
    private static final TutorialContainsKeywordsPredicate emptyTutorialPredicate =
            new TutorialContainsKeywordsPredicate(
                    Collections.emptyList());
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        TutorialContainsKeywordsPredicate firstTutorialPredicate = new TutorialContainsKeywordsPredicate(
                        Collections.singletonList("first"));
        TutorialContainsKeywordsPredicate secondTutorialPredicate = new TutorialContainsKeywordsPredicate(
                        Collections.singletonList("second"));

        FindTutorialCommand findFirstCommand = new FindTutorialCommand(firstTutorialPredicate);
        FindTutorialCommand findSecondCommand = new FindTutorialCommand(secondTutorialPredicate);

        // same object -> ok
        assertEquals(findFirstCommand, findFirstCommand);

        // same values -> ok
        FindTutorialCommand findFirstCommandCopy = new FindTutorialCommand(firstTutorialPredicate);
        assertEquals(findFirstCommand, findFirstCommandCopy);

        // different types -> fail
        assertNotEquals(findFirstCommand, 1);

        // null -> fail
        assertNotNull(findFirstCommand);

        // different student -> fail
        assertNotEquals(findFirstCommand, findSecondCommand);
    }

    @Test
    public void execute_zeroKeywords_noTutorialFound() {
        String expectedMessage = String.format(MESSAGE_TUTORIALS_LISTED_OVERVIEW, 0);
        TutorialContainsKeywordsPredicate tutorialPredicate = emptyTutorialPredicate;
        FindTutorialCommand command = new FindTutorialCommand(tutorialPredicate);
        expectedModel.updateFilteredTutorialWithStudentsList(tutorialPredicate);
        assertEquals(Collections.emptyList(), expectedModel.getFilteredTutorialWithStudents());
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, NavigationMode.TUTORIAL);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
    }

    // @Test
    // public void execute_multipleKeywords_multipleTutorialsFound() {
    // String expectedMessage = String.format(MESSAGE_TUTORIALS_LISTED_OVERVIEW, 2);
    // TutorialContainsKeywordsPredicate predicate =
    // prepareTutorialPredicate("CS2103-T23 CS2106-T02");
    // FindTutorialCommand command = new FindTutorialCommand(predicate);
    // expectedModel.updateFilteredTutorialWithStudentsList(predicate);
    // CommandResult expectedCommandResult = new CommandResult(expectedMessage,
    // NavigationMode.TUTORIAL);
    // assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
    // }
    //
    // @Test
    // public void execute_singleKeyword_singleTutorialFound() {
    // String expectedMessage = String.format(MESSAGE_TUTORIALS_LISTED_OVERVIEW, 1);
    // TutorialContainsKeywordsPredicate tutorialPredicate =
    // prepareTutorialPredicate("CS2103-T23");
    // FindTutorialCommand command = new FindTutorialCommand(tutorialPredicate);
    // expectedModel.updateFilteredTutorialWithStudentsList(tutorialPredicate);
    // CommandResult expectedCommandResult = new CommandResult(expectedMessage,
    // NavigationMode.TUTORIAL);
    // assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
    // }

    @Test
    public void toStringMethod() {
        TutorialContainsKeywordsPredicate tutorialPredicate = new TutorialContainsKeywordsPredicate(
                        Arrays.asList("tutorial1"));
        FindTutorialCommand findCommand = new FindTutorialCommand(tutorialPredicate);
        String expected = FindTutorialCommand.class.getCanonicalName() + "{tutorialPredicate=" + tutorialPredicate
                        + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code TutorialContainsKeywordsPredicate}.
     */
    private TutorialContainsKeywordsPredicate prepareTutorialPredicate(String userInput) {
        return new TutorialContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
