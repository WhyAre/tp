package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HANDLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_2;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalStudents.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.student.Student;
import seedu.address.testutil.StudentBuilder;

public class UniqueListTest {

    private final UniqueList<Student> uniqueStudentList = new UniqueList<>();

    @Test
    public void isUnique_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
        ) -> uniqueStudentList.containsIdentity(null));
    }

    @Test
    public void isUnique_studentNotInList_returnsFalse() {
        assertFalse(uniqueStudentList.containsIdentity(ALICE));
    }

    @Test
    public void isUnique_studentInList_returnsTrue() {
        uniqueStudentList.add(ALICE);
        assertTrue(uniqueStudentList.containsIdentity(ALICE));
    }

    @Test
    public void isUnique_studentWithSameIdentityFieldsInList_returnsTrue() {
        uniqueStudentList.add(ALICE);
        Student editedAlice = new StudentBuilder(ALICE).withHandle(VALID_HANDLE_BOB).withTutorials(VALID_TUTORIAL_2)
                        .build();
        assertTrue(uniqueStudentList.containsIdentity(editedAlice));
    }

    @Test
    public void add_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
        ) -> uniqueStudentList.add(null));
    }

    @Test
    public void add_duplicateStudent_throwsDuplicateStudentException() {
        uniqueStudentList.add(ALICE);
        assertFalse(uniqueStudentList.add(ALICE));
    }

    @Test
    public void setStudent_nullTargetStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
        ) -> uniqueStudentList.set(null, ALICE));
    }

    @Test
    public void setStudent_nullEditedStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
        ) -> uniqueStudentList.set(ALICE, null));
    }

    @Test
    public void setStudent_targetStudentNotInList_throwsStudentNotFoundException() {
        assertThrows(IllegalStateException.class, (
        ) -> uniqueStudentList.set(ALICE, ALICE));
    }

    @Test
    public void setStudent_editedStudentIsSameStudent_success() {
        uniqueStudentList.add(ALICE);
        uniqueStudentList.set(ALICE, ALICE);
        var expectedUniqueStudentList = new UniqueList<Student>();
        expectedUniqueStudentList.add(ALICE);
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setStudent_editedStudentHasSameIdentity_success() {
        uniqueStudentList.add(ALICE);
        Student editedAlice = new StudentBuilder(ALICE).withHandle(VALID_HANDLE_BOB).withTutorials(VALID_TUTORIAL_2)
                        .build();
        uniqueStudentList.set(ALICE, editedAlice);
        var expectedUniqueStudentList = new UniqueList<Student>();
        expectedUniqueStudentList.add(editedAlice);
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setStudent_editedStudentHasDifferentIdentity_success() {
        uniqueStudentList.add(ALICE);
        uniqueStudentList.set(ALICE, BOB);
        var expectedUniqueStudentList = new UniqueList<Student>();
        expectedUniqueStudentList.add(BOB);
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setStudent_editedStudentHasNonUniqueIdentity_throwsDuplicateStudentException() {
        uniqueStudentList.add(ALICE);
        uniqueStudentList.add(BOB);
        assertThrows(IllegalStateException.class, (
        ) -> uniqueStudentList.set(ALICE, BOB));
    }

    @Test
    public void remove_nullStudent_throwsNullPointerException() {
        assertFalse(uniqueStudentList.remove(null));
    }

    @Test
    public void remove_studentDoesNotExist_throwsStudentNotFoundException() {
        assertFalse(uniqueStudentList.remove(ALICE));
    }

    @Test
    public void remove_existingStudent_removesStudent() {
        uniqueStudentList.add(ALICE);
        uniqueStudentList.remove(ALICE);
        var expectedUniqueStudentList = new UniqueList<Student>();
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setStudents_nullUniqueStudentList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
        ) -> uniqueStudentList.setAll((UniqueList<Student>) null));
    }

    @Test
    public void setStudents_uniqueStudentList_replacesOwnListWithProvidedUniqueStudentList() {
        uniqueStudentList.add(ALICE);
        var expectedUniqueStudentList = new UniqueList<Student>();
        expectedUniqueStudentList.add(BOB);
        uniqueStudentList.setAll(expectedUniqueStudentList);
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setStudents_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
        ) -> uniqueStudentList.setAll((List<Student>) null));
    }

    @Test
    public void setStudents_list_replacesOwnListWithProvidedList() {
        uniqueStudentList.add(ALICE);
        List<Student> studentList = Collections.singletonList(BOB);
        uniqueStudentList.setAll(studentList);
        var expectedUniqueStudentList = new UniqueList<Student>();
        expectedUniqueStudentList.add(BOB);
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setStudents_listWithDuplicateStudents_throwsDuplicateStudentException() {
        List<Student> listWithDuplicateStudents = Arrays.asList(ALICE, ALICE);
        assertThrows(IllegalStateException.class, (
        ) -> uniqueStudentList.setAll(listWithDuplicateStudents));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, (
        ) -> uniqueStudentList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueStudentList.asUnmodifiableObservableList().toString(), uniqueStudentList.toString());
    }
}
