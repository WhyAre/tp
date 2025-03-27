package seedu.address.model.tutorial;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;

import seedu.address.model.uniquelist.Identifiable;

/**
 * Object that represents an {@code Assignment}
 *
 * @param name
 *            name of assignment
 * @param dueDate
 *            due date of assignment
 */
public record Assignment(String name, Optional<LocalDateTime> dueDate) implements Identifiable<Assignment> {
    @Override
    public boolean hasSameIdentity(Assignment other) {
        return this.name.equals(other.name);
    }

    @Override
    public String toString() {
        return dueDate.map(due -> {
            var formattedDate = due.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
            return "%s (Due: %s)".formatted(name, formattedDate);
        }).orElse(name);
    }
}
