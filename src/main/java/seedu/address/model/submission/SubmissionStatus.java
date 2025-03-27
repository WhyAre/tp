package seedu.address.model.submission;

import java.util.HashMap;
import java.util.Optional;

/**
 * Represents the status of a submission
 */
public enum SubmissionStatus {
    NOT_SUBMITTED, SUBMITTED, GRADED;

    /**
     * Creates a {@link SubmissionStatus} enum based on string value
     */
    public static SubmissionStatus parse(String value) {
        var map = new HashMap<String, SubmissionStatus>();
        map.put("not-submitted", SubmissionStatus.NOT_SUBMITTED);
        map.put("submitted", SubmissionStatus.SUBMITTED);
        map.put("graded", SubmissionStatus.GRADED);

        var allStatus = String.join(", ", map.keySet());

        return Optional.ofNullable(map.get(value.toLowerCase())).orElseThrow((
        ) -> new IllegalArgumentException("Invalid status, valid statuses are: %s".formatted(allStatus)));
    }
}
