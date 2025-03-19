package seedu.address.model.tutorial;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Tutorial}'s name matches any of the given keywords.
 * given.
 */
public class StudentContainsTutorialKeywordsPredicate implements Predicate<Tutorial> {
    private final List<String> keywords;

    public StudentContainsTutorialKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Tutorial tutorial) {
        return keywords.stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase(tutorial.name(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentContainsTutorialKeywordsPredicate)) {
            return false;
        }

        StudentContainsTutorialKeywordsPredicate otherStudentContainsTutorialKeywordsPredicate =
                (StudentContainsTutorialKeywordsPredicate) other;
        return keywords.equals(otherStudentContainsTutorialKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
