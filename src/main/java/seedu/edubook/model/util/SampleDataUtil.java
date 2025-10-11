package seedu.edubook.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.edubook.model.AddressBook;
import seedu.edubook.model.ReadOnlyAddressBook;
import seedu.edubook.model.person.Email;
import seedu.edubook.model.commons.Name;
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
            new Person(new PersonName("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new TuitionClass("Class 1-A"),
                getTagSet("friends")),
            new Person(new PersonName("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new TuitionClass("Class 1-A"),
                getTagSet("colleagues", "friends")),
            new Person(new PersonName("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new TuitionClass("Class 2-B"),
                getTagSet("neighbours")),
            new Person(new PersonName("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new TuitionClass("Class 2-B"),
                getTagSet("family")),
            new Person(new PersonName("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new TuitionClass("W-14"),
                getTagSet("classmates")),
            new Person(new PersonName("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new TuitionClass("T-02"),
                getTagSet("colleagues"))
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

}
