package seedu.edubook.ui;

import javafx.stage.Stage;

/**
 * API of UI component
 */
public interface Ui {

    /** Starts the UI (and the App).  */
    void start(Stage primaryStage);

    /**
     * Alerts users to any unexpected events within EduBook
     * This method should only be called after the UI has been initialized,
     * as it requires the primary stage to be available. Calling it before the UI is ready
     * may result in a NullPointerException.
     *
     * @param message the alert message to be displayed
     */
    void showErrorAlert(String message);
}
