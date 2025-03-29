package seedu.address.storage.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalAddressBook.ALICE;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.testutil.TypicalAddressBook;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_FILE_NAME = TEST_DATA_FOLDER.resolve("typicalAddressBook.json");
    private static final Path DUPLICATE_STUDENT_FILE = TEST_DATA_FOLDER.resolve("duplicateStudentAddressBook.json");

    @Test
    public void toModelType_typical_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil
                        .readJsonFile(TYPICAL_FILE_NAME, JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalStudentsAddressBook = TypicalAddressBook.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalStudentsAddressBook);
    }

    @Test
    public void toModelType_missingAlice() throws Exception {
        Path filepath = TEST_DATA_FOLDER.resolve("missingAlice.json");
        var ab = TypicalAddressBook.getTypicalAddressBook();
        ab.removeStudent(ALICE);

        var dataFromFile = JsonUtil.readJsonFile(filepath, JsonSerializableAddressBook.class).get().toModelType();

        assertEquals(dataFromFile, ab);
    }

    @Test
    public void toModelType_missingTutorial() throws Exception {
        Path filepath = TEST_DATA_FOLDER.resolve("missingCS2103T1.json");
        var ab = TypicalAddressBook.getTypicalAddressBook();
        ab.deleteTutorial(new Tutorial("CS2103-T1"));

        var dataFromFile = JsonUtil.readJsonFile(filepath, JsonSerializableAddressBook.class).get().toModelType();

        assertEquals(dataFromFile, ab);
    }

    @Test
    public void toModelType_duplicateStudents_throwsIllegalValueException() throws Exception {
        Path filepath = TEST_DATA_FOLDER.resolve("duplicateAlice.json");
        var ab = TypicalAddressBook.getTypicalAddressBook();

        var dataFromFile = JsonUtil.readJsonFile(filepath, JsonSerializableAddressBook.class).get().toModelType();

        assertEquals(dataFromFile, ab);
    }
}
