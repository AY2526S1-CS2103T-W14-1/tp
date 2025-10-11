package seedu.edubook.model.person;

import seedu.edubook.model.commons.Name;

public class PersonName extends Name {
    
    public static final String MESSAGE_CONSTRAINTS =
            "A Person Name should only contain alphanumeric characters and spaces, and it should not be blank";
    
    public static final String MESSAGE_LENGTH_CONSTRAINTS =
            "A Person Name should only contain a maximum of 100 characters (including spaces)";
    
    public PersonName(String name) {
        super(name);
    }
}
