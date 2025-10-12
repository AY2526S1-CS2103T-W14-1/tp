package seedu.edubook.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.edubook.commons.util.StringUtil;
import seedu.edubook.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class PersonNameContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public PersonNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonNameContainsKeywordsPredicate)) {
            return false;
        }

        PersonNameContainsKeywordsPredicate otherNameContainsKeywordsPredicate =
                (PersonNameContainsKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
