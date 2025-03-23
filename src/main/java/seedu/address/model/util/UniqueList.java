package seedu.address.model.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.IntStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Wrapper around a list of {@link Identifiable}. List cannot contain duplicate,
 * or null.
 * <p>
 * Duplicate detection checked by {@link Identifiable#hasSameIdentity}
 */
public class UniqueList<T extends Identifiable<T>> implements List<T> {

    private final ObservableList<T> internalList = FXCollections.observableArrayList();
    private final ObservableList<T> internalUnmodifiableList = FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if {@code items} contains only unique entities.
     */
    private boolean areItemsUnique(List<T> items) {
        return IntStream.range(0, items.size()).noneMatch(i -> IntStream.range(i + 1, items.size())
                        .anyMatch(j -> items.get(i).hasSameIdentity(items.get(j))));
    }

    /**
     * Checks whether the list contains the entity. This check uses
     * {@link Identifiable#hasSameIdentity}
     */
    public boolean containsIdentity(T toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::hasSameIdentity);
    }

    /**
     * Replaces the contents of this list with {@code items}. {@code items} must not
     * contain duplicates
     */
    public void setAll(List<T> items) {
        requireAllNonNull(items);
        if (!areItemsUnique(items)) {
            throw new IllegalStateException("Students list contains duplicate students");
        }

        internalList.setAll(items);
    }

    public void setAll(UniqueList<T> replacement) {
        setAll(replacement.internalList);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<T> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Removes entity from the list
     */
    public boolean remove(T toRemove) {
        return internalList.remove(toRemove);
    }

    @Override
    public T remove(int i) {
        return internalList.remove(i);
    }

    @Override
    public boolean remove(Object o) {
        return internalList.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int i, Collection<? extends T> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return internalList.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return internalList.retainAll(collection);
    }

    @Override
    public void clear() {
        internalList.clear();
    }

    @Override
    public int size() {
        return internalList.size();
    }

    @Override
    public boolean isEmpty() {
        return internalList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return internalList.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return internalList.iterator();
    }

    @Override
    public Object[] toArray() {
        return internalList.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] t1s) {
        return internalList.toArray(t1s);
    }

    @Override
    public boolean equals(Object otherObject) {
        if (otherObject == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(otherObject instanceof UniqueList<?> other)) {
            return false;
        }

        return internalList.equals(other.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public T get(int i) {
        return internalList.get(i);
    }

    /**
     * Replaces the old with new {@code target} must exist in the list. The student
     * identity of {@code editedStudent} must not be the same as another existing
     * student in the list.
     */
    public void set(T oldItem, T newItem) {
        requireAllNonNull(oldItem, newItem);

        int index = internalList.indexOf(oldItem);
        if (index == -1) {
            throw new IllegalStateException("Item not exists");
        }

        if (!oldItem.hasSameIdentity(newItem) && containsIdentity(newItem)) {
            throw new IllegalStateException("Item already exists");
        }

        internalList.set(index, newItem);
    }

    @Override
    public T set(int i, T t) {
        throw new UnsupportedOperationException();
    }

    /**
     * Adds an item into the list; Item must not already exist in the list
     */
    @Override
    public boolean add(T toAdd) {
        requireNonNull(toAdd);
        if (containsIdentity(toAdd)) {
            return false;
        }
        return internalList.add(toAdd);
    }

    @Override
    public void add(int i, T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        return internalList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return internalList.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return internalList.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int i) {
        return internalList.listIterator(i);
    }

    @Override
    public List<T> subList(int i, int i1) {
        return internalList.subList(i, i1);
    }

    @Override
    public String toString() {
        return internalList.toString();
    }
}
