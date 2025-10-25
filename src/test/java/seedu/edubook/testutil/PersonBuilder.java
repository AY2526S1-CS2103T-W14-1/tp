package seedu.edubook.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.edubook.model.assignment.Assignment;
import seedu.edubook.model.person.Email;
import seedu.edubook.model.person.Person;
import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.person.Phone;
import seedu.edubook.model.person.TuitionClass;
import seedu.edubook.model.tag.Tag;
import seedu.edubook.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_CLASS = "123";

    private PersonName name;
    private Phone phone;
    private Email email;
    private TuitionClass tuitionClass;
    private Set<Tag> tags;
    private Set<Assignment> assignments;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new PersonName(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        tuitionClass = new TuitionClass(DEFAULT_CLASS);
        tags = new HashSet<>();
        assignments = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        tuitionClass = personToCopy.getTuitionClass();
        tags = new HashSet<>(personToCopy.getTags());
        assignments = new HashSet<>(personToCopy.getAssignments());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new PersonName(name);
        return this;
    }

    /**
     * Parses the {@code assignments} into a {@code Set<Assignment>} and set it to the
     * {@code Person} that we are building.
     */
    public PersonBuilder withAssignments(String ... assignments) {
        this.assignments = SampleDataUtil.getAssignmentSet(assignments);
        return this;
    }
    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code tuitionClass} of the {@code Person} that we are building.
     */
    public PersonBuilder withClass(String tuitionClass) {
        this.tuitionClass = new TuitionClass(tuitionClass);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, tuitionClass, tags, assignments);
    }

}
