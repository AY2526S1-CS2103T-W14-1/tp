package seedu.edubook.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edubook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PersonNameTest {

    @Test
    public void constructor_validName_returnsName() {
        String validName = "Alex Yeoh";
        PersonName name = new PersonName(validName);
        assertEquals(validName, name.fullName);
    }

    @Test
    public void isValidLength_nullInput_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> PersonName.isValidLength(null));
    }

    @Test
    public void isValidLength_invalidInput_returnsFalse() {
        String invalidLength = String.join("", java.util.Collections.nCopies(50, "test"));
        assertFalse(PersonName.isValidLength(invalidLength));
    }

    @Test
    public void isValidLength_validInput_returnsTrue() {
        assertTrue(PersonName.isValidLength("peter jack"));
    }

    @Test
    public void getMessageConstraints_returnsCorrectMessage() {
        PersonName personName = new PersonName("Alex Yeoh"); // any valid name
        String expectedMessage = "A Person Name should only contain alphanumeric characters and spaces, "
                + "and it should not be blank. ";

        assertEquals(expectedMessage, personName.getMessageConstraints());
    }
}
