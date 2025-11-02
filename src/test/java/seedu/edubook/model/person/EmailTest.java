package seedu.edubook.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edubook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EmailTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Email(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        assertThrows(IllegalArgumentException.class, () -> new Email(invalidEmail));
    }

    @Test
    public void isValidEmail() {
        // null email
        assertThrows(NullPointerException.class, () -> Email.isValidEmail(null));

        // blank email
        assertFalse(Email.isValidEmail("")); // empty string
        assertFalse(Email.isValidEmail(" ")); // spaces only

        // missing parts
        assertFalse(Email.isValidEmail("@example.com")); // missing local part
        assertFalse(Email.isValidEmail("peterjackexample.com")); // missing '@' symbol
        assertFalse(Email.isValidEmail("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Email.isValidEmail("peterjack@-")); // invalid domain name
        assertFalse(Email.isValidEmail("peterjack@example")); // invalid domain name
        assertFalse(Email.isValidEmail("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(Email.isValidEmail("peter jack@example.com")); // spaces in local part
        assertFalse(Email.isValidEmail("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(Email.isValidEmail(" peterjack@example.com")); // leading space
        assertFalse(Email.isValidEmail("peterjack@example.com ")); // trailing space
        assertFalse(Email.isValidEmail("peterjack@@example.com")); // double '@' symbol
        assertFalse(Email.isValidEmail("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(Email.isValidEmail("-peterjack@example.com")); // local part starts with a hyphen
        assertFalse(Email.isValidEmail("peterjack-@example.com")); // local part ends with a hyphen
        assertFalse(Email.isValidEmail("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(Email.isValidEmail("peterjack@.example.com")); // domain name starts with a period
        assertFalse(Email.isValidEmail("peterjack@example.com.")); // domain name ends with a period
        assertFalse(Email.isValidEmail("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(Email.isValidEmail("peterjack@example.com-")); // domain name ends with a hyphen
        assertFalse(Email.isValidEmail("peterjack@example.c")); // top level domain has less than two chars
        assertFalse(Email.isValidEmail("_peterjack@example.com")); // starts with invalid character
        assertFalse(Email.isValidEmail("peterjack+@example.com")); // ends with invalid character

        // valid email
        assertTrue(Email.isValidEmail("PeterJack_1190@example.com")); // underscore in local part
        assertTrue(Email.isValidEmail("PeterJack.1190@example.com")); // period in local part
        assertTrue(Email.isValidEmail("PeterJack+1190@example.com")); // '+' symbol in local part
        assertTrue(Email.isValidEmail("PeterJack-1190@example.com")); // hyphen in local part
        assertTrue(Email.isValidEmail("a@bc.com")); // minimal
        assertTrue(Email.isValidEmail("test@localhost.com")); // alphabets only
        assertTrue(Email.isValidEmail("123@145.com")); // numeric local part and domain name
        assertTrue(Email.isValidEmail("a1+be.d@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(Email.isValidEmail("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(Email.isValidEmail("if.you.dream.it_you.can.do.it@example.com")); // long local part
        assertTrue(Email.isValidEmail("e1234567@u.nus.edu")); // more than one period in domain
    }

    @Test
    void testEmailValidation() {
        String[] validEmails = {
                "a@b.co",                                   // minimal
                "abc@xyz.com",                              // simple
                "john.doe@example.com",                     // dot in local-part
                "john-doe@example.com",                     // hyphen in local-part
                "john_doe@example.com",                     // underscore in local-part
                "john+doe@example.com",                     // plus in local-part
                "user123@example123.com",                   // numeric local and domain
                "a_b-c.d+e@example-domain.co.uk",           // mix of specials and domain
                "if.you.dream.it_you.can.do.it@example.com", // long local-part
                "e1234567@u.nus.edu",                       // multiple dots in domain
                "test+_.-ajj@example.com",                  // mixed consecutive specials
                "peter..jack@example.com",                  // consecutive dots
                "peter__jack@example.com",                  // consecutive underscores
                "peter.-_jack@example.com",                 // mixed specials
                "A@B.CD",                                   // minimal valid with uppercase
                "user-name_+tag@example-domain-123.com",    // mixed specials and numbers
                "user@sub.domain.com",                       // multiple domain levels
                "user@sub-domain.example.com",               // hyphen in domain
                "longlocalpartthatisstillunder100chars+ok@example.com", // long local-part
                "user@ex--ample.com"
        };

        for (String email : validEmails) {
            assertTrue(Email.isValidEmail(email), "Should accept: " + email);
        }

        String[] invalidEmails = {
                "",                                         // empty
                "   ",                                      // whitespace only
                "@example.com",                             // missing local-part
                "abcexample.com",                           // missing @
                "abc@@example.com",                         // double @
                "peter@jack@example.com",                   // @ in local-part
                ".abc@example.com",                         // starts with special
                "abc.@example.com",                         // ends with special
                "_abc@example.com",                         // starts with underscore
                "abc_@example.com",                         // ends with underscore
                "-abc@example.com",                         // starts with hyphen
                "abc-@example.com",                         // ends with hyphen
                "+abc@example.com",                         // starts with plus
                "abc+@example.com",                         // ends with plus
                "abc#def@example.com",                       // invalid char
                "abc()def@example.com",                      // parentheses
                "abc@domain",                               // missing TLD
                "abc@domain.c",                             // TLD too short
                "abc@-domain.com",                           // domain starts with hyphen
                "abc@domain-.com",                           // domain ends with hyphen
                "abc@exa_mple.com",                          // underscore in domain
                "abc@.example.com",                           // domain starts with dot
                "abc@example..com",                           // double dot in domain
                "abc@example.com-",                           // domain ends with hyphen
                "abc@exam ple.com",                           // space in domain
                " abc@example.com",                            // leading space
                "abc@example.com ",                            // trailing space
                "abc@example.com.",                            // ends with dot
        };

        for (String email : invalidEmails) {
            assertFalse(Email.isValidEmail(email), "Should reject: " + email);
        }
    }

    @Test
    void testEmailEdgeCases() {
        String[] validEmails = {
                "a@b.co",                                   // minimal
                "abc@xyz.com",                              // simple
                "john.doe@example.com",                     // dot in local-part
                "john-doe@example.com",                     // hyphen in local-part
                "john_doe@example.com",                     // underscore in local-part
                "john+doe@example.com",                     // plus in local-part
                "user123@example123.com",                   // numeric local and domain
                "a_b-c.d+e@example-domain.co.uk",           // mix of specials and domain
                "if.you.dream.it_you.can.do.it@example.com", // long local-part
                "e1234567@u.nus.edu",                       // multiple dots in domain
                "test+_.-ajj@example.com",                  // mixed consecutive specials
                "peter..jack@example.com",                  // consecutive dots
                "peter__jack@example.com",                  // consecutive underscores
                "peter.-_jack@example.com",                 // mixed specials
                "A@B.CD",                                   // minimal valid with uppercase
                "user-name_+tag@example-domain-123.com",    // mixed specials and numbers
                "user@sub.domain.com",                       // multiple domain levels
                "user@sub-domain.example.com",               // hyphen in domain
                "longlocalpartthatisstillunder100chars+ok@example.com" // long local-part
        };

        String[] invalidEmails = {
                "",                                         // empty
                "   ",                                      // whitespace only
                "@example.com",                             // missing local-part
                "abcexample.com",                           // missing @
                "abc@@example.com",                         // double @
                "peter@jack@example.com",                   // @ in local-part
                ".abc@example.com",                         // starts with special char
                "abc.@example.com",                         // ends with special char
                "_abc@example.com",                         // starts with underscore
                "abc_@example.com",                         // ends with underscore
                "-abc@example.com",                         // starts with hyphen
                "abc-@example.com",                         // ends with hyphen
                "+abc@example.com",                         // starts with plus
                "abc+@example.com",                         // ends with plus
                "abc#def@example.com",                       // invalid char
                "abc()def@example.com",                      // parentheses
                "abc@domain",                               // missing TLD
                "abc@domain.c",                             // TLD too short
                "abc@-domain.com",                           // domain starts with hyphen
                "abc@domain-.com",                           // domain ends with hyphen
                "abc@exa_mple.com",                          // underscore in domain
                "abc@.example.com",                           // domain starts with dot
                "abc@example..com",                           // double dot in domain
                "abc@example.com-",                           // domain ends with hyphen
                "abc@exam ple.com",                           // space in domain
                " abc@example.com",                            // leading space
                "abc@example.com ",                            // trailing space
                "user@1.2.3.4",                               // numeric domain
                "user@domain.123",                             // numeric TLD
                "user@sub..domain.com",                        // double dot in subdomain
                "user@sub.-domain.com",                        // subdomain starts with hyphen
                "user@sub.domain-.com",                        // subdomain ends with hyphen
                "\"quoted.local.part\"@example.com",          // quoted local-part (regex rejects)
                "user\\@name@example.com",                     // escaped character in local-part
                "user@[123.123.123.123]",                      // literal IPv4 domain
                "用户@例子.公司",                                // Unicode local-part/domain
                " user@example.com",                           // leading space
                "user@example.com ",                           // trailing space
                "user @example.com",                           // space inside local-part
                "user@ example.com",                           // space inside domain
        };

        // Valid emails
        for (String email : validEmails) {
            assertTrue(Email.isValidEmail(email), "Should accept: " + email);
        }

        // Invalid emails
        for (String email : invalidEmails) {
            assertFalse(Email.isValidEmail(email), "Should reject: " + email);
        }
    }

    @Test
    public void equals() {
        Email email = new Email("valid@email.com");

        // same values -> returns true
        assertTrue(email.equals(new Email("valid@email.com")));

        // same object -> returns true
        assertTrue(email.equals(email));

        // null -> returns false
        assertFalse(email.equals(null));

        // different types -> returns false
        assertFalse(email.equals(5.0f));

        // different values -> returns false
        assertFalse(email.equals(new Email("other.valid@email.com")));
    }
}
