package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBookInclTutorials;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.NameContainsKeywordsPredicate;
import seedu.address.model.tutorial.StudentContainsTutorialKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for
 * {@code FindCommand}.
 */
public class FindCommandTest {
    private static final NameContainsKeywordsPredicate emptyNamePredicate = new NameContainsKeywordsPredicate(
                    Collections.emptyList());
    private static final StudentContainsTutorialKeywordsPredicate emptyT = new StudentContainsTutorialKeywordsPredicate(
                    Collections.emptyList());

    private Model model = new ModelManager(getTypicalAddressBookInclTutorials(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBookInclTutorials(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstNamePredicate = new NameContainsKeywordsPredicate(
                        Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondNamePredicate = new NameContainsKeywordsPredicate(
                        Collections.singletonList("second"));
        StudentContainsTutorialKeywordsPredicate firstTutorialPredicate = new StudentContainsTutorialKeywordsPredicate(
                        Collections.singletonList("tutorial1"));
        StudentContainsTutorialKeywordsPredicate secondTutorialPredicate = new StudentContainsTutorialKeywordsPredicate(
                        Collections.singletonList("tutorial2"));

        FindCommand findFirstCommand = new FindCommand(firstNamePredicate, firstTutorialPredicate);
        FindCommand findSecondCommand = new FindCommand(secondNamePredicate, secondTutorialPredicate);

        // same object -> ok
        assertEquals(findFirstCommand, findFirstCommand);

        // same values -> ok
        FindCommand findFirstCommandCopy = new FindCommand(firstNamePredicate, firstTutorialPredicate);
        assertEquals(findFirstCommand, findFirstCommandCopy);

        // different types -> fail
        assertNotEquals(findFirstCommand, 1);

        // null -> fail
        assertNotNull(findFirstCommand);

        // different student -> fail
        assertNotEquals(findFirstCommand, findSecondCommand);
    }

    @Test
    public void execute_zeroKeywords_noStudentFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate namePredicate = emptyNamePredicate;
        StudentContainsTutorialKeywordsPredicate tutorialPredicate = emptyT;
        FindCommand command = new FindCommand(namePredicate, tutorialPredicate);
        expectedModel.updateFilteredStudentList(namePredicate);
        assertEquals(Collections.emptyList(), expectedModel.getFilteredStudentList());
        expectedModel.updateFilteredStudentsByTutorialList(tutorialPredicate);
        assertEquals(Collections.emptyList(), expectedModel.getFilteredStudentList());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleKeywords_multipleStudentsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = prepareNamePredicate("Kurz Elle Kunz");
        FindCommand command = new FindCommand(predicate, null);
        expectedModel.updateFilteredStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeEmptyNameOneTutorialMultipleStudentsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        StudentContainsTutorialKeywordsPredicate tutorialPredicate = prepareTutorialPredicate("CS2103-T1");
        FindCommand command = new FindCommand(null, tutorialPredicate);
        expectedModel.updateFilteredStudentsByTutorialList(tutorialPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeEmptyNameTwoTutorialsMultipleStudentsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        StudentContainsTutorialKeywordsPredicate tutorialPredicate = prepareTutorialPredicate("CS2103-T1 CS2106-T37");
        FindCommand command = new FindCommand(null, tutorialPredicate);
        expectedModel.updateFilteredStudentsByTutorialList(tutorialPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeNameAliceTutorialCS2103T23SingleStudentFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate("Alice");
        StudentContainsTutorialKeywordsPredicate tutorialPredicate = prepareTutorialPredicate("CS2103-T1");
        FindCommand command = new FindCommand(namePredicate, tutorialPredicate);
        expectedModel.updateFilteredStudentList(
                        namePredicate.and(student -> student.getTutorials().stream().anyMatch(tutorialPredicate)));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
        StudentContainsTutorialKeywordsPredicate tutorialPredicate = new StudentContainsTutorialKeywordsPredicate(
                        Arrays.asList("tutorial1"));
        FindCommand findCommand = new FindCommand(namePredicate, tutorialPredicate);
        String expected = FindCommand.class.getCanonicalName() + "{namePredicate=" + namePredicate
                        + ", tutorialPredicate=" + tutorialPredicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate prepareNamePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a
     * {@code StudentContainsTutorialKeywordsPredicate}.
     */
    private StudentContainsTutorialKeywordsPredicate prepareTutorialPredicate(String userInput) {
        return new StudentContainsTutorialKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
