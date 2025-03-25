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
import seedu.address.model.uniquelist.UniqueList;
import seedu.address.model.uniquelist.exceptions.DuplicateItemException;
import seedu.address.model.uniquelist.exceptions.ItemNotFoundException;
import seedu.address.testutil.StudentBuilder;

public class UniqueListTest {

    private final UniqueList<Student> uniqueStudentList = new UniqueList<>();

    @Test
    public void containsIdentity_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
        ) -> uniqueStudentList.containsIdentity(null));
    }

    @Test
    public void containsIdentity_entityNotInList_returnsFalse() {
        assertFalse(uniqueStudentList.containsIdentity(ALICE));
    }

    @Test
    public void containsIdentity_entityInList_returnsTrue() {
        uniqueStudentList.add(ALICE);
        assertTrue(uniqueStudentList.containsIdentity(ALICE));
    }

    @Test
    public void containsIdentity_entityWithSameIdentityFieldsInList_returnsTrue() {
        uniqueStudentList.add(ALICE);
        Student editedAlice = new StudentBuilder(ALICE).withHandle(VALID_HANDLE_BOB).withTutorials(VALID_TUTORIAL_2)
                        .build();
        assertTrue(uniqueStudentList.containsIdentity(editedAlice));
    }

    @Test
    public void add_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
        ) -> uniqueStudentList.add(null));
    }

    @Test
    public void add_duplicateEntity_returnFalse() {
        uniqueStudentList.add(ALICE);
        assertFalse(uniqueStudentList.add(ALICE));
    }

    @Test
    public void set_nullOldItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
        ) -> uniqueStudentList.set(null, ALICE));
    }

    @Test
    public void set_nullNewItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
        ) -> uniqueStudentList.set(ALICE, null));
    }

    @Test
    public void set_oldItemNotInList_throwsItemNotFoundException() {
        assertThrows(ItemNotFoundException.class, (
        ) -> uniqueStudentList.set(ALICE, ALICE));
    }

    @Test
    public void set_newEntityIsSameEntity_success() throws DuplicateItemException, ItemNotFoundException {
        uniqueStudentList.add(ALICE);
        uniqueStudentList.set(ALICE, ALICE);
        var expectedUniqueStudentList = new UniqueList<Student>();
        expectedUniqueStudentList.add(ALICE);
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void set_newEntityHasSameIdentity_success() throws DuplicateItemException, ItemNotFoundException {
        uniqueStudentList.add(ALICE);
        Student editedAlice = new StudentBuilder(ALICE).withHandle(VALID_HANDLE_BOB).withTutorials(VALID_TUTORIAL_2)
                        .build();
        uniqueStudentList.set(ALICE, editedAlice);
        var expectedUniqueStudentList = new UniqueList<Student>();
        expectedUniqueStudentList.add(editedAlice);
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void set_newEntityHasDifferentIdentity_success() throws DuplicateItemException, ItemNotFoundException {
        uniqueStudentList.add(ALICE);
        uniqueStudentList.set(ALICE, BOB);
        var expectedUniqueStudentList = new UniqueList<Student>();
        expectedUniqueStudentList.add(BOB);
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void set_newEntityHasNonUniqueIdentity_throwsDuplicateItemException() {
        uniqueStudentList.add(ALICE);
        uniqueStudentList.add(BOB);
        assertThrows(DuplicateItemException.class, (
        ) -> uniqueStudentList.set(ALICE, BOB));
    }

    @Test
    public void set_newEntityHasNonUniqueIdentity1_throwsDuplicateItemException() {
        uniqueStudentList.add(ALICE);
        uniqueStudentList.add(BOB);

        var fakeAlice = new StudentBuilder(ALICE).withHandle(VALID_HANDLE_BOB).withTutorials(VALID_TUTORIAL_2).build();

        assertThrows(DuplicateItemException.class, (
        ) -> uniqueStudentList.set(ALICE, fakeAlice));
    }

    @Test
    public void remove_null_returnFalse() {
        assertFalse(uniqueStudentList.remove(null));
    }

    @Test
    public void remove_entityDoesNotExist_returnFalse() {
        assertFalse(uniqueStudentList.remove(ALICE));
    }

    @Test
    public void remove_existingEntity_success() {
        uniqueStudentList.add(ALICE);
        uniqueStudentList.remove(ALICE);
        var expectedUniqueStudentList = new UniqueList<Student>();
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setAll_nullEntityList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
        ) -> uniqueStudentList.setAll(null));
    }

    @Test
    public void setAll_uniqueEntityList_replacesOwnListWithProvidedList() throws DuplicateItemException {
        uniqueStudentList.add(ALICE);
        var expectedUniqueStudentList = new UniqueList<Student>();
        expectedUniqueStudentList.add(BOB);
        uniqueStudentList.setAll(expectedUniqueStudentList);
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setAll_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
        ) -> uniqueStudentList.setAll((List<Student>) null));
    }

    @Test
    public void setAll_list_replacesOwnListWithProvidedList() throws DuplicateItemException {
        uniqueStudentList.add(ALICE);
        List<Student> studentList = Collections.singletonList(BOB);
        uniqueStudentList.setAll(studentList);
        var expectedUniqueStudentList = new UniqueList<Student>();
        expectedUniqueStudentList.add(BOB);
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setAll_listWithDuplicateEntities_throwsDuplicateItemException() {
        List<Student> listWithDuplicateStudents = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateItemException.class, (
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
