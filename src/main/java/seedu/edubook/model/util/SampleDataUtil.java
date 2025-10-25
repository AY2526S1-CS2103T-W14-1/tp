package seedu.edubook.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.edubook.model.AddressBook;
import seedu.edubook.model.ReadOnlyAddressBook;
import seedu.edubook.model.assignment.Assignment;
import seedu.edubook.model.assignment.AssignmentName;
import seedu.edubook.model.label.Label;
import seedu.edubook.model.person.Email;
import seedu.edubook.model.person.Person;
import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.person.Phone;
import seedu.edubook.model.person.TuitionClass;
import seedu.edubook.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new PersonName("Alex Yeoh"),
                    new Phone("87438807"),
                    new Email("alexyeoh@example.com"),
                    new TuitionClass("Class 1-A"),
                    getTagSet("friends"),
                    getAssignmentSet("Homework 1", "Homework 2"),
                    new Label("Late for class")),
            new Person(new PersonName("Bernice Yu"),
                    new Phone("99272758"),
                    new Email("berniceyu@example.com"),
                    new TuitionClass("Class 1-A"),
                    getTagSet("colleagues", "friends"),
                    getAssignmentSet("Tutorial 1", "Lab 3"),
                    new Label("Allergic to peanuts")),
            new Person(new PersonName("Charlotte Oliveiro"),
                    new Phone("93210283"),
                    new Email("charlotte@example.com"),
                    new TuitionClass("Class 2-B"),
                    getTagSet("neighbours"),
                    getAssignmentSet("Assignment 1", "Assignment 2"),
                    new Label("Best student")),
            new Person(new PersonName("David Li"),
                    new Phone("91031282"),
                    new Email("lidavid@example.com"),
                    new TuitionClass("Class 2-B"),
                    getTagSet("family"),
                    getAssignmentSet("Lab 4", "Mission 7"),
                    new Label("Worst student")),
            new Person(new PersonName("Irfan Ibrahim"),
                    new Phone("92492021"),
                    new Email("irfan@example.com"),
                    new TuitionClass("W-14"),
                    getTagSet("classmates"),
                    getAssignmentSet("Homework 1"),
                    new Label("Tallest student")),
            new Person(new PersonName("Roy Balakrishnan"),
                    new Phone("92624417"),
                    new Email("royb@example.com"),
                    new TuitionClass("T-02"),
                    getTagSet("colleagues"),
                    getAssignmentSet("Homework 1", "Tutorial 4", "Assignment 1", "Lab 3", "Studio 5"),
                    new Label("Completes homework on time"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    public static Set<Assignment> getAssignmentSet(String... names) {
        return Arrays.stream(names)
                .map(name -> new Assignment(new AssignmentName(name)))
                .collect(Collectors.toSet());
    }


}
