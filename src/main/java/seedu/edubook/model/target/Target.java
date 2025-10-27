package seedu.edubook.model.target;

import java.util.List;

import seedu.edubook.logic.commands.exceptions.CommandException;
import seedu.edubook.model.Model;
import seedu.edubook.model.person.Person;

/**
 * Represents a target that can provide one or more assignees for an assignment.
 * <p>
 * Implementations define how the target is resolved (e.g., by name, by class, by tag).
 */
public interface Target {

    /**
     * Returns a list of persons that this target represents.
     *
     * @param model The model to query for persons.
     * @return List of persons to assign the assignment to.
     * @throws CommandException if no valid persons are found or some error occurs.
     */
    List<Person> getPersons(Model model) throws CommandException;

    /**
     * Returns a displayable name for the target, used in messages/logging.
     *
     * @return The target’s display name.
     */
    String getDisplayName();

    /**
     * Returns whether this target represents exactly one person.
     *
     * @return {@code true} if the target represents a single person, {@code false} otherwise.
     */
    boolean isSinglePersonTarget();

    /**
     * Returns the formatted success message after successfully assigning an assignment to this target.
     *
     * @param assignmentName Name of the assignment.
     * @param successCount Number of successful assignments.
     * @param skippedCount Number of skipped assignment additions.
     * @return Formatted success message.
     */
    String getAssignSuccessMessage(String assignmentName, int successCount, int skippedCount);

    /**
     * Returns the formatted success message after successfully unassigning an assignment to this target.
     *
     * @param assignmentName Name of the assignment.
     * @param successCount Number of successful unassignments.
     * @param skippedCount Number of skipped assignment removals.
     * @return Formatted success message.
     */
    String getUnassignSuccessMessage(String assignmentName, int successCount, int skippedCount);

    /**
     * Returns the formatted success message after successfully assigning a label to this target.
     *
     * @param labelName Name of the label.
     *
     * @return Formatted success message.
     */
    String getLabelSuccessMessage(String labelName);

    /**
     * Returns the formatted success message after successfully unassigning a label to this target.
     *
     *
     * @return Formatted success message.
     */
    String getUnlabelSuccessMessage();

    /**
     * If student does not have any label, label command is not executed.
     *
     * @return Formatted failure message
     */
    String getUnlabelFailureMessage();

    /**
     * Returns the formatted success message after successfully viewing the target.
     *
     * @return Formatted success message.
     */
    String getViewSuccessMessage();

    /**
     * Returns the formatted success message after successfully marking an assignment for this target.
     *
     * @param assignmentName Name of the assignment.
     * @param markedCount Number of successfully marked assignments.
     * @param alreadyMarkedCount Number of assignments already marked.
     * @param notExistCount Number of assignments that do not exist for the target(s).
     * @return Formatted success message.
     */
    String getMarkSuccessMessage(String assignmentName, int markedCount, int alreadyMarkedCount, int notExistCount);

    /**
     * Returns the formatted success message after successfully unmarking an assignment for this target.
     *
     * @param assignmentName Name of the assignment.
     * @param unmarkedCount Number of successfully unmarked assignments.
     * @param alreadyUnmarkedCount Number of assignments already unmarked.
     * @param notExistCount Number of assignments that do not exist for the target(s).
     * @return Formatted success message.
     */
    String getUnmarkSuccessMessage(String assignmentName,
                                   int unmarkedCount,
                                   int alreadyUnmarkedCount,
                                   int notExistCount);

    /**
     * Returns the formatted success message after successfully deleting this target.
     *
     * @return Formatted success message.
     */
    String getDeleteSuccessMessage();
}
