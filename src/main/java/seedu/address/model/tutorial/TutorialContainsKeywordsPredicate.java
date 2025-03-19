package seedu.address.model.tutorial;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Tutorial}'s name matches any of the given keywords.
 * given.
 */
public class TutorialContainsKeywordsPredicate implements Predicate<Tutorial> {
    private final List<String> keywords;

    public TutorialContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Tutorial tutorial) {
        return keywords.stream()
                .anyMatch(keyword -> tutorial.name().toLowerCase().contains(keyword.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TutorialContainsKeywordsPredicate)) {
            return false;
        }

        TutorialContainsKeywordsPredicate otherTutorialContainsKeywordsPredicate =
                (TutorialContainsKeywordsPredicate) other;
        return keywords.equals(otherTutorialContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
