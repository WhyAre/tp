package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.json.JsonAdaptedTutorial.MESSAGE_INVALID_TUTORIAL_NAME;
import static seedu.address.storage.json.JsonAdaptedTutorial.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.storage.json.JsonAdaptedTutorial;

public class JsonAdaptedTutorialTest {
    private static final String NULL_STRING = null;
    private static final String INVALID_TUTORIAL_NAME = "Tu+or!a/";

    private static final String VALID_TUTORIAL_NAME = "CS2103-F15";
    private static final Tutorial VALID_TUTORIAL = new Tutorial(VALID_TUTORIAL_NAME);

    @Test
    public void toModelType_validTutorial_returnsTutorial() throws Exception {
        JsonAdaptedTutorial tutorial = new JsonAdaptedTutorial(VALID_TUTORIAL_NAME);
        assertEquals(VALID_TUTORIAL, tutorial.toModelType());
    }

    @Test
    public void toModelType_invalidTutorialName_throwsIllegalValueException() {
        JsonAdaptedTutorial tutorial = new JsonAdaptedTutorial(INVALID_TUTORIAL_NAME);
        String expectedMessage = MESSAGE_INVALID_TUTORIAL_NAME;
        assertThrows(IllegalValueException.class, expectedMessage, tutorial::toModelType);
    }

    @Test
    public void toModelType_nullTutorialName_throwsIllegalValueException() {
        JsonAdaptedTutorial tutorial = new JsonAdaptedTutorial(NULL_STRING);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "name");
        assertThrows(IllegalValueException.class, expectedMessage, tutorial::toModelType);
    }

}
