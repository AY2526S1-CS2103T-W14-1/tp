package seedu.edubook.ui;

/**
 * Displays any errors that users should be aware of.
 */
public interface ErrorDisplayable {

    /**
     * Displays alerts to any unexpected events within EduBook.
     *
     * @param message the alert message to be displayed
     */
    void showErrorAlert(String message);
}
