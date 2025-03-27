package seedu.address.storage.csv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import seedu.address.model.student.Student;
import seedu.address.model.tutorial.Tutorial;

/**
 * A List of CSV-adaptable objects that is serializable to CSV format.
 */
public class CsvSerializableList<T> extends ArrayList<T> {

    // Map containing type transformations for known types
    private static final Map<Class<?>, Function<List<?>, List<?>>> TYPE_CONVERTERS = new HashMap<>();

    static {
        TYPE_CONVERTERS.put(Student.class, list -> list.stream()
                        .map(student -> new CsvAdaptedStudent((Student) student)).collect(Collectors.toList()));

        TYPE_CONVERTERS.put(Tutorial.class, list -> list.stream()
                        .map(tutorial -> new CsvAdaptedTutorial((Tutorial) tutorial)).collect(Collectors.toList()));
    }

    /**
     * Constructs a {@code CsvSerializableList} with the given list of objects.
     */
    public CsvSerializableList(List<T> list) {
        super(convertList(list));
    }

    private static <T> List<T> convertList(List<T> list) {
        if (list.isEmpty()) {
            return new ArrayList<>();
        }

        Class<?> elementType = list.get(0).getClass();

        if (TYPE_CONVERTERS.containsKey(elementType)) {
            return (List<T>) TYPE_CONVERTERS.get(elementType).apply(list);
        }

        // Default: No conversion needed, return as is
        return list;
    }
}
