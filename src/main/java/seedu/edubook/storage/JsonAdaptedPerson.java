package seedu.edubook.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.edubook.commons.exceptions.IllegalValueException;
import seedu.edubook.commons.util.StringUtil;
import seedu.edubook.model.assignment.Assignment;
import seedu.edubook.model.commons.Name;
import seedu.edubook.model.label.Label;
import seedu.edubook.model.person.Email;
import seedu.edubook.model.person.Person;
import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.person.Phone;
import seedu.edubook.model.person.TuitionClass;
import seedu.edubook.model.tag.Tag;


/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    @JsonProperty("class")
    private final String tuitionClass;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final List<JsonAdaptedAssignment> assignments = new ArrayList<>();
    private final String label;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name,
                             @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email,
                             @JsonProperty("class") String tuitionClass,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags,
                             @JsonProperty("assignments") List<JsonAdaptedAssignment> assignments,
                             @JsonProperty("label") String label
    ) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.tuitionClass = tuitionClass;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (assignments != null) {
            this.assignments.addAll(assignments);
        }
        this.label = label;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        tuitionClass = source.getTuitionClass().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        assignments.addAll(source.getAssignments().stream()
                .map(JsonAdaptedAssignment::new)
                .collect(Collectors.toList()));
        label = (source.getLabel().isEmpty()) ? "" : source.getLabel().labelContent;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        final List<Assignment> personAssignments = new ArrayList<>();
        for (JsonAdaptedAssignment assignment : assignments) {
            personAssignments.add(assignment.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!StringUtil.isValidLength(name, PersonName.MAX_NAME_LENGTH)) {
            throw new IllegalValueException(PersonName.MESSAGE_LENGTH_CONSTRAINTS);
        }
        if (!PersonName.isValidName(name)) {
            throw new IllegalValueException(PersonName.MESSAGE_CONSTRAINTS);
        }
        final PersonName modelName = new PersonName(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!StringUtil.isValidLength(phone, Phone.MAX_PHONE_LENGTH)) {
            throw new IllegalValueException(Phone.MESSAGE_LENGTH_CONSTRAINTS);
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!StringUtil.isValidLength(email, Email.MAX_EMAIL_LENGTH)) {
            throw new IllegalValueException(Email.MESSAGE_LENGTH_CONSTRAINTS);
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (tuitionClass == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TuitionClass.class.getSimpleName()));
        }
        if (!StringUtil.isValidLength(tuitionClass, TuitionClass.MAX_CLASS_LENGTH)) {
            throw new IllegalValueException(TuitionClass.MESSAGE_LENGTH_CONSTRAINTS);
        }
        if (!TuitionClass.isValidClass(tuitionClass)) {
            throw new IllegalValueException(TuitionClass.MESSAGE_CONSTRAINTS);
        }

        final Label modelLabel = convertLabel(label);

        final TuitionClass modelClass = new TuitionClass(tuitionClass);

        final Set<Tag> modelTags = new HashSet<>(personTags);

        final Set<Assignment> modelAssignments = new HashSet<>(personAssignments);

        return new Person(modelName, modelPhone, modelEmail, modelClass, modelTags, modelAssignments, modelLabel);
    }

    private Label convertLabel(String label) throws IllegalValueException {
        if (label == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Label.class.getSimpleName()));
        }
        if (label.isBlank()) {
            return Label.EMPTY;
        }
        if (!StringUtil.isValidLength(label, Label.MAX_LABEL_LENGTH)) {
            throw new IllegalValueException(Label.MESSAGE_LENGTH_CONSTRAINTS);
        }
        if (!Label.isValidLabel(label)) {
            throw new IllegalValueException(Label.MESSAGE_CONSTRAINTS);
        }

        return new Label(label);
    }

}
