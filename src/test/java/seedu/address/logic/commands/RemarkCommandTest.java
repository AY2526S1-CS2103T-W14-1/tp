package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;

public class RemarkCommandTest {
    
    private static final String REMARK_STUB = "Some remark";
    
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    
    @Test
    public void execute_addRemarkUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
    }
}
