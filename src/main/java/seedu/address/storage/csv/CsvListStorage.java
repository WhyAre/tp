package seedu.address.storage.csv;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.CsvUtil;
import seedu.address.commons.util.FileUtil;

/**
 * Handles storage of a list of objects in CSV format.
 */
public class CsvListStorage<T> {

    private static final Logger logger = LogsCenter.getLogger(CsvListStorage.class);
    private final Path filePath;

    public CsvListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public void saveCsvList(List<T> csvList) throws IOException {
        saveCsvList(csvList, filePath);
    }

    /**
     * Saves the list to a CSV file.
     *
     * @param filePath
     *            location of the data. Cannot be null.
     */
    public void saveCsvList(List<T> csvList, Path filePath) throws IOException {
        requireNonNull(csvList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);

        CsvSerializableList<?> serializableList = new CsvSerializableList<>(csvList);
        CsvUtil.saveCsvFile(serializableList, filePath);
    }
}
