package seedu.edubook.model.assignment;

import seedu.edubook.model.commons.Name;

public class AssignmentName extends Name {
    
    public static final String MESSAGE_CONSTRAINTS =
            "An Assignment Name should only contain alphanumeric characters and spaces, and it should not be blank";
    
    public static final String MESSAGE_LENGTH_CONSTRAINTS =
            "An Assignment Name should only contain a maximum of 100 characters (including spaces)";
    
    public AssignmentName(String name) {
        super(name);
    }
}
