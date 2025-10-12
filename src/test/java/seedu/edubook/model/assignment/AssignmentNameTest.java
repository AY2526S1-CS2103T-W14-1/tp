package seedu.edubook.model.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edubook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AssignmentNameTest {

    @Test
    public void constructor_validName_returnsName() {
        String validName = "Alex Yeoh";
        AssignmentName name = new AssignmentName(validName);
        assertEquals(validName, name.fullName);
    }

    @Test
    public void isValidLength_nullInput_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> AssignmentName.isValidLength(null));
    }

    @Test
    public void isValidLength_invalidInput_returnsFalse() {
        String invalidLength = String.join("", java.util.Collections.nCopies(50, "test"));
        assertFalse(AssignmentName.isValidLength(invalidLength));
    }

    @Test
    public void isValidLength_validInput_returnsTrue() {
        assertTrue(AssignmentName.isValidLength("peter jack"));
    }

    @Test
    public void getMessageConstraints_returnsCorrectMessage() {
        AssignmentName assignmentName = new AssignmentName("Alex Yeoh"); // any valid name
        String expectedMessage = "An Assignment Name should only contain alphanumeric characters and spaces, "
                + "and it should not be blank. ";

        assertEquals(expectedMessage, assignmentName.getMessageConstraints());
    }
}
