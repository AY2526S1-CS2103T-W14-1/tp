package seedu.edubook.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.edubook.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.edubook.commons.util.ToStringBuilder;
import seedu.edubook.model.assignment.Assignment;
import seedu.edubook.model.person.exceptions.AssignmentNotFoundException;
import seedu.edubook.model.person.exceptions.DuplicateAssignmentException;
import seedu.edubook.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final PersonName name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final TuitionClass tuitionClass;
    private final Set<Tag> tags = new HashSet<>();
    private final Set<Assignment> assignments = new HashSet<>();

    /**
     * Constructs a {@code Person}
     *
     * <p>This constructor is to be called during user commands, where there is no input assignment</p>
     *
     * @param name Name of person.
     * @param phone Phone number of person.
     * @param email Email-address of person.
     * @param tuitionClass Class the person belongs to.
     * @param tags Tags belonging to the person.
     */
    public Person(PersonName name, Phone phone, Email email, TuitionClass tuitionClass, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, tuitionClass, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.tuitionClass = tuitionClass;
        this.tags.addAll(tags);
    }

    /**
     * Every field must be present and not null.
     */
    public Person(PersonName name,
                  Phone phone,
                  Email email,
                  TuitionClass tuitionClass,
                  Set<Tag> tags,
                  Set<Assignment> assignments) {
        requireAllNonNull(name, phone, email, tuitionClass, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.tuitionClass = tuitionClass;
        this.tags.addAll(tags);
        this.assignments.addAll(assignments);
    }

    public PersonName getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public TuitionClass getTuitionClass() {
        return tuitionClass;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Adds an assignment to the person, and creates a new person with the new assignment set.
     * This ensures immutability of Person objects.
     *
     * @param assignment Assignment to be added.
     * @return The new person object.
     * @throws DuplicateAssignmentException if the assignment is already assigned to the student.
     */
    public Person withAddedAssignment(Assignment assignment) throws DuplicateAssignmentException {
        requireNonNull(assignment);

        if (this.hasAssignment(assignment)) {
            throw new DuplicateAssignmentException();
        }

        Set<Assignment> newAssignments = this.getAssignments();
        newAssignments.add(assignment);

        return new Person(
                this.name,
                this.phone,
                this.email,
                this.tuitionClass,
                this.tags,
                newAssignments
        );
    }

    /**
     * Removes an assignment from the person, and creates a new person with the new assignment set.
     * This ensures immutability of Person objects.
     *
     * @param assignment Assignment to be removed.
     * @return The new person object.
     * @throws AssignmentNotFoundException if the target assignment cannot be found.
     */
    public Person withRemovedAssignment(Assignment assignment) throws AssignmentNotFoundException {
        requireNonNull(assignment);

        if (!this.hasAssignment(assignment)) {
            throw new AssignmentNotFoundException();
        }

        Set<Assignment> newAssignments = this.getAssignments();
        newAssignments.remove(assignment);

        return new Person(
                this.name,
                this.phone,
                this.email,
                this.tuitionClass,
                this.tags,
                newAssignments
        );
    }

    public Set<Assignment> getAssignments() {
        return new HashSet<>(this.assignments);
    }

    /**
     * Checks whether an assignment is already assigned to this person.
     *
     * @param assignment Assignment to be checked.
     * @return a boolean representing whether the assignment is already assigned.
     */
    public boolean hasAssignment(Assignment assignment) {
        return this.assignments.contains(assignment);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && tuitionClass.equals(otherPerson.tuitionClass)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, tuitionClass, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("class", tuitionClass)
                .add("tags", tags)
                .toString();
    }

}
