package seedu.address.logic.commands.export;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.student.Student;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.storage.csv.CsvListStorage;

/**
 * Exports student lists.
 */
public class ExportStudentsCommand extends Command {

    public static final String COMMAND_WORD = "students";

    public static final String MESSAGE_USAGE = "Usage: export students [TUTORIAL_NAME]";

    public static final String MESSAGE_SUCCESS = "Successfully exported students";
    public static final String MESSAGE_TUTORIAL_DOES_NOT_EXIST = "%1$s doesn't exist";

    private final Optional<Tutorial> tutorial;
    private List<Student> toExport;

    /**
     * Creates a {@link ExportStudentsCommand} to export all students
     */
    public ExportStudentsCommand() {
        this.tutorial = Optional.empty();
    }

    /**
     * Creates a {@link ExportStudentsCommand} to export students from specified
     * {@code Tutorial}
     */
    public ExportStudentsCommand(Tutorial tutorial) {
        requireNonNull(tutorial);
        this.tutorial = Optional.of(tutorial);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        Path addressBookFilePath = model.getAddressBookFilePath();

        Path exportStudentFilePath;
        List<Student> studentList = new ArrayList<>();

        if (tutorial.isPresent()) {
            exportStudentFilePath = addressBookFilePath
                            .resolveSibling(String.format("students-%s.csv", tutorial.get().name()));
            if (model.hasTutorial(tutorial.get())) {
                studentList.addAll(model.getStudentsInTutorial(tutorial.get()));
            } else {
                return new CommandResult(String.format(MESSAGE_TUTORIAL_DOES_NOT_EXIST, tutorial.get().name()));
            }
        } else {
            exportStudentFilePath = addressBookFilePath.resolveSibling("students.csv");
            studentList.addAll(model.getFilteredStudentList());
        }

        CsvListStorage<Student> exportStudentStorage = new CsvListStorage<Student>(exportStudentFilePath);
        try {
            exportStudentStorage.saveCsvList(studentList);
        } catch (AccessDeniedException e) {
            return new CommandResult(String.format(ExportCommand.FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()));
        } catch (IOException ioe) {
            return new CommandResult(String.format(ExportCommand.FILE_OPS_ERROR_FORMAT, ioe.getMessage()));
        }

        return new CommandResult(MESSAGE_SUCCESS);

    }

}
