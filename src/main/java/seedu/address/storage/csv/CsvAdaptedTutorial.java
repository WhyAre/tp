package seedu.address.storage.csv;

import com.opencsv.bean.CsvBindByName;

import seedu.address.model.tutorial.Tutorial;

/**
 * CSV-friendly version of {@link Tutorial}.
 */
public class CsvAdaptedTutorial {

    @CsvBindByName
    private final String name;

    /**
     * Constructs a {@code CsvAdaptedTutorial} with the given tutorial details.
     */
    public CsvAdaptedTutorial(String name) {
        this.name = name;
    }

    /**
     * Converts a given {@code Tutorial} into this class for CSV use.
     */
    public CsvAdaptedTutorial(Tutorial tutorial) {
        this.name = tutorial.name();
    }

    /**
     * Getters
     */
    public String getName() {
        return name;
    }
}
