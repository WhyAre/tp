package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.NavigationMode;
import seedu.address.model.submission.SubmissionStatus;
import seedu.address.model.uniquelist.exceptions.ItemNotFoundException;

/**
 * Creates a new submission
 */
public class SetSubmissionCommand extends Command {

    public static final String COMMAND_WORD = "set";

    public static final String MESSAGE_SUCCESS = "Submission successfully set";
    private static final String MESSAGE_STUDENT_NOT_FOUND = "Cannot find student";

    private final String tutorialName;
    private final String assignmentName;
    private final List<Index> studentIdxList;
    private final SubmissionStatus status;

    /**
     * Creates a {@link SetSubmissionCommand} object
     */
    public SetSubmissionCommand(String tutorialName, String assignmentName, List<Index> studentIdxList,
                    SubmissionStatus status) {
        Objects.requireNonNull(tutorialName);
        Objects.requireNonNull(assignmentName);
        Objects.requireNonNull(studentIdxList);

        this.tutorialName = tutorialName;
        this.assignmentName = assignmentName;
        this.studentIdxList = studentIdxList;
        this.status = status;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        var errMsg = new StringBuilder();
        for (var idx : studentIdxList) {
            var idxZeroBased = idx.getZeroBased();

            var students = model.getFilteredStudentList();
            if (idxZeroBased >= students.size()) {
                errMsg.append("Student at index %d is out of bounds\n".formatted(idx.getOneBased()));
                continue;
            }

            var student = model.getFilteredStudentList().get(idxZeroBased);
            if (student == null) {
                throw new CommandException(MESSAGE_STUDENT_NOT_FOUND);
            }

            try {
                model.setSubmissionStatus(tutorialName, assignmentName, student, status);
            } catch (ItemNotFoundException e) {
                throw new CommandException(e.getMessage());
            }
        }

        var msg = (errMsg.isEmpty()) ? MESSAGE_SUCCESS : "Warning: %s".formatted(errMsg.toString());
        return new CommandResult(msg, NavigationMode.UNCHANGED);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SetSubmissionCommand otherAddCommand)) {
            return false;
        }

        return tutorialName.equals(otherAddCommand.tutorialName)
                        && assignmentName.equals(otherAddCommand.assignmentName)
                        && studentIdxList.equals(otherAddCommand.studentIdxList) && status == otherAddCommand.status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("tutorialName", tutorialName).add("assignmentName", assignmentName)
                        .add("studentIdxList", studentIdxList).add("status", status).toString();

    }
}
