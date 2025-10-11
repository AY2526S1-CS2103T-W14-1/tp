package seedu.edubook.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Person Prefix definitions */
    public static final Prefix PREFIX_PERSON_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_CLASS = new Prefix("c/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    /* Assignment Prefix definitions */
    public static final Prefix PREFIX_ASSIGNMENT_NAME = new Prefix("a/");
}
