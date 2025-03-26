package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;

import seedu.address.commons.core.LogsCenter;
import seedu.address.storage.csv.CsvSerializableList;

/**
 * Converts a Java object instance to CSV and vice versa
 */
public class CsvUtil {

    private static final Logger logger = LogsCenter.getLogger(CsvUtil.class);

    /**
     * Serializes a {@link CsvSerializableList} of objects into a CSV file.
     *
     * @param <T>
     *            The type of objects in the list.
     * @param csvFile
     *            The path to the CSV file where data should be written. Cannot be
     *            null.
     * @param objectList
     *            The list of objects to be serialized into CSV format. Cannot be
     *            null.
     * @throws IOException
     *             If an error occurs while writing to the file.
     */
    public static <T> void serializeObjectToCsvFile(Path csvFile, CsvSerializableList<T> objectList)
                    throws IOException {
        requireNonNull(csvFile);
        requireNonNull(objectList);

        try (Writer writer = Files.newBufferedWriter(csvFile)) {
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer).build();
            beanToCsv.write(objectList);
        } catch (CsvException e) {
            throw new IOException("Error writing CSV file: " + e.getMessage(), e);
        }
    }

    /**
     * Saves the Csv object to the specified file. Overwrites existing file if it
     * exists, creates a new file if it doesn't.
     *
     * @param csvData
     *            cannot be null
     * @param filePath
     *            cannot be null
     * @throws IOException
     *             if there was an error during writing to the file
     */
    public static <T> void saveCsvFile(CsvSerializableList<T> csvData, Path filePath) throws IOException {
        requireNonNull(filePath);
        requireNonNull(csvData);

        serializeObjectToCsvFile(filePath, csvData);
    }
}
