---
layout: page
title: User Guide
---

EduBook is a **desktop app for managing student details, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, EduBook can get your student management tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed on your computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2526S1-CS2103T-W14-1/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your EduBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar edubook.jar` command to run the application.<br>
   A GUI similar to the one below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all students.

   * `add n/John Doe p/98765432 e/johnd@example.com c/Class 1-A` : Adds a student named `John Doe` to EduBook.

   * `delete 3` : Deletes the 3rd student shown in the current list.

   * `clear` : Deletes all students.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g. `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items enclosed in curly braces with a vertical bar (e.g. `{A | B}`) indicate that **exactly one of the options must be supplied**.<br>
  e.g. `{n/NAME | c/CLASS}` can be used as `n/John Doe` or as `c/Class 1-A`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

#### Formatting rules for parameters:

| Field      | Max Length | Format / Constraints                                                                                                                                                                                                                            |
|------------|------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name       | 100        | Alphanumeric letters and spaces only; must not be blank                                                                                                                                                                                         |
| Email      | 250        | Format: `local-part@domain`<br>- Local-part: alphanumeric + `+`, `_`, `.`, `-`; cannot start or end with a special character<br>- Domain: labels separated by `.`, start/end with alphanumeric, hyphens allowed internally, last label ≥2 chars |
| Phone      | 20         | Digits only, at least 3 digits long                                                                                                                                                                                                             |
| Class      | 20         | Any value, must not be blank                                                                                                                                                                                                                    |
| Assignment | 100        | Alphanumeric letters and spaces only; must not be blank                                                                                                                                                                                         |
| Tag        | none       | Alphanumeric letters                                                                                                                                                                                                                            | 

<div markdown="span" class="alert alert-primary">:bulb: **Note:**
For identification purposes, all names (student or assignment) and classes are case-sensitive.
</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a student: `add`

Adds a student to EduBook.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL c/CLASS [t/TAG]…​`

* All inputs must adhere to the [Formatting rules for parameters](#formatting-rules-for-parameters).

<div markdown="span" class="alert alert-primary">:bulb: **Note:**
A student can have any number of tags (including 0)
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com c/Class 1-A`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com c/Tutorial Group 2 p/1234567 t/criminal`

### Listing all students : `list`

Shows a list of all students in EduBook.

Format: `list`

### Editing a student : `edit`

Edits an existing student in EduBook.

<<<<<<< Updated upstream
Format: `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [c/CLASS] [t/TAG] [a/ASSIGNMENT]…​`
=======
Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [c/CLASS] [t/TAG]…​ [a/ASSIGNMENT]…​`
>>>>>>> Stashed changes

* Edits the student at the specified `INDEX`. The index refers to the index number shown in the displayed student list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags or/and assignments, the existing tags or/and assignments of the student will be removed i.e adding of tags and assignments are not cumulative.
* You can remove all the student’s tags by typing `t/` without
    specifying any tags after it.
* You can remove all the student’s assignments by typing `a/` without
    specifying any assignments after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st student to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd student to be `Betsy Crower` and clears all existing tags.

### Viewing student information: `view`

Displays the information of a student or all students in a class, depending on the specified parameter.

Format: `view {n/NAME | c/CLASS}`

* You must specify **exactly one** of the two parameters — either `n/NAME` or `c/CLASS`.  
  e.g. `view n/John Doe` or `view c/W14`, but not both.

* If `n/NAME` is used:
    * Displays the student with the specified `NAME`.
    * The search is case-sensitive. e.g. `hans` will not match `Hans`.
    * The order of the keywords matters. e.g. `Hans Bo` will not match `Bo Hans`.
    * The full name is required for the search to be successful. e.g. `Hans` will not return `Hans Bo`.

* If `c/CLASS` is used:
    * Displays all students with the specified `CLASS`.
    * The search is case-sensitive. e.g. `w14` will not match `W14`.
    * The order of the keywords matters. e.g. `Class 14W` will not match `14W Class`.
    * The full class name is required for the search to be successful. e.g. `W14` will not return `Class W14`.

Examples:
* `view n/John` — displays information of the student named `John`
* `view n/alex david` — displays information of the student named `alex david`
* `view c/Class B` — displays all students in `Class B`
* `view c/W14` — displays all students in `W14`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
To restore full view, use the `list` command.
</div>

### Deleting a student : `delete`

Deletes the specified student (identified by name or index) from Edubook.

Format: `delete {INDEX | n/NAME}` 

* The index refers to the index number shown in the **current view**.
* The index must be a **positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd student in the address book.
* `view n/Betsy` followed by `delete 1` deletes the 1st student in the results of the `view` command.
* `delete n/John Doe` deletes the student with the exact name "John Doe"

### Assigning an assignment: `assign`

Assigns an assignment to a specific student or to all students in a class, depending on the specified parameter.

Format: `assign a/ASSIGNMENT_NAME {n/NAME | c/CLASS}`

* You must specify **exactly one** of the two parameters — either `n/NAME` or `c/CLASS`.  
  e.g. `assign a/Homework n/Bob` or `assign a/Homework c/Class 1-A`, but not both.

* If `n/NAME` is used:
    * Assigns the assignment `ASSIGNMENT_NAME` to the student named `NAME`.
    * The student must exist in EduBook.
    * The specified student must not already have the assignment.

* If `c/CLASS` is used:
    * Assigns the assignment `ASSIGNMENT_NAME` to every student in the specified class `CLASS`.
    * Students who already have the assignment are skipped.
    * The class must exist in EduBook (i.e. there is at least one student belonging to the specified class).
    * At least one student in the specified class must not currently have the assignment.

Examples:
* `assign a/Homework n/Bob` — assigns `Homework` to `Bob`
* `assign a/Tutorial 1 n/John Doe` — assigns `Tutorial 1` to `John Doe`
* `assign a/Homework c/Class 1-A` — assigns `Homework` to all students in `Class 1-A`, skipping those who already have it
* `assign a/Tutorial 1 c/Tutorial Group 2` — assigns `Tutorial 1` to all students in `Tutorial Group 2`, skipping those who already have it

### Unassigning an assignment: `unassign`

Unassigns an assignment from a specific student or from all students in a class, depending on the specified parameter.

Format: `unassign a/ASSIGNMENT_NAME {n/NAME | c/CLASS}`

* You must specify **exactly one** of the two parameters — either `n/NAME` or `c/CLASS`.  
  e.g. `unassign a/Homework n/Bob` or `unassign a/Homework c/Class 1-A`, but not both.

* If `n/NAME` is used:
    * Unassigns the assignment `ASSIGNMENT_NAME` from the student named `NAME`.
    * The student must exist in EduBook.
    * The specified assignment must exist for the specified student.

* If `c/CLASS` is used:
    * Unassigns the assignment `ASSIGNMENT_NAME` from every student in the specified class `CLASS`.
    * Students who do not have the assignment are skipped.
    * The class must exist in EduBook (i.e. there is at least one student belonging to the specified class).
    * At least one student in the specified class must currently have the assignment.

Examples:
* `unassign a/Homework n/Bob` — unassigns `Homework` from `Bob`
* `unassign a/Tutorial 1 n/John Doe` — unassigns `Tutorial 1` from `John Doe`
* `unassign a/Homework c/Class 1-A` — unassigns `Homework` from all students in `Class 1-A`, skipping those who do not have it
* `unassign a/Tutorial 1 c/Tutorial Group 2` — unassigns `Tutorial 1` from all students in `Tutorial Group 2`, skipping those who do not have it

### Marking a student's assignment: `mark`

Marks an assignment of a specified student as completed.

Format: `mark a/ASSIGNMENT_NAME n/NAME`

* Marks assignment, `ASSIGNMENT_NAME`, of student named, `NAME`, as completed.
* The student must exist in EduBook.
* The specified assignment must exist for the specified student.
* The specified assignment must not already be marked.

Examples:
* `mark a/Tutorial 1 n/John Doe` marks `Tutorial 1` of `John Doe` as completed.

### Clearing all entries : `clear`

Clears all entries from EduBook.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

EduBook data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

EduBook data is saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

Please ensure that formatting rules are followed during manual editing.   
See [Formatting rules for parameters](#formatting-rules-for-parameters) above.

<div markdown="span" class="alert alert-warning">
:exclamation: **Caution:**  
If your changes to the data file make its format invalid, EduBook will display an error message.  

To retain your saved file, exit immediately without making changes to the blank file. Revert the changes and reload EduBook. Otherwise, a new empty file will be used.  
![Error Message](images/FileCorruptedExample.png "Error Message for File Corruption")

Furthermore, certain edits can cause the EduBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range).  
Only edit the data file if you are confident that you can update it correctly.
</div>


### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous EduBook home folder. <br>

**Q**: How do I restore to full view after doing `view`? <br>
**A**: Use the `list` command to restore full view. <br>

**Q**: How do I restore my data if I accidentally used `clear`? <br>
**A**: Unfortunately, there is no command to undo the `clear` command. 
We recommend saving a backup file as a precaution to prevent future incidents.


--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

<<<<<<< Updated upstream
| Action       | Format                                                                          | Example                                                                            |
|--------------|---------------------------------------------------------------------------------|------------------------------------------------------------------------------------|
| **Help**     | `help`                                                                          | `help`                                                                             |
| **Add**      | `add n/NAME p/PHONE_NUMBER e/EMAIL c/CLASS [t/TAG]…​`                           | `add n/James Ho p/22224444 e/jamesho@example.com c/Class 10B t/friend t/colleague` |
| **List**     | `list`                                                                          | `list`                                                                             |
| **Edit**     | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [c/CLASS] [t/TAG] [a/ASSIGNMENT]…​` | `edit 2 n/James Lee e/jameslee@example.com`                                        |
| **View**     | `view {n/NAME | c/CLASS}`                                                          | `view n/James Lee`, `view c/Class-B`                                               |
| **Delete**   | `delete {INDEX | n/NAME}`                                                         | `delete 3`, `delete n/John Doe`                                                    |
| **Assign**   | `assign a/ASSIGNMENT_NAME {n/NAME | c/CLASS}`                                    | `assign a/Tutorial 1 n/John Doe`, `assign a/Lab 2 c/Class 10B`                     |
| **Unassign** | `unassign a/ASSIGNMENT_NAME {n/NAME | c/CLASS}`                                 | `unassign a/Tutorial 1 n/John Doe`, `unassign a/Lab 2 c/Class 10B`                 |
| **Mark**     | `mark a/ASSIGNMENT_NAME n/NAME`                                                 | `mark a/Tutorial 1 n/John Doe`                                                     |
| **Clear**    | `clear`                                                                         | `clear`                                                                            |
| **Exit**     | `exit`                                                                          | `exit`                                                                             |
=======
| Action       | Format                                                                                | Example                                                                            |
|--------------|---------------------------------------------------------------------------------------|------------------------------------------------------------------------------------|
| **Help**     | `help`                                                                                | `help`                                                                             |
| **Add**      | `add n/NAME p/PHONE e/EMAIL c/CLASS [t/TAG]…​`                                     | `add n/James Ho p/22224444 e/jamesho@example.com c/Class 10B t/friend t/colleague` |
| **List**     | `list`                                                                                | `list`                                                                             |
| **Edit**     | `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [c/CLASS] [t/TAG]…​ [a/ASSIGNMENT]…​`  | `edit 2 n/James Lee e/jameslee@example.com`                                        |
| **View**     | `view {n/NAME | c/CLASS}`                                                             | `view n/James Lee`, `view c/Class-B`                                               |
| **Delete**   | `delete {INDEX | n/NAME}`                                                             | `delete 3`, `delete n/John Doe`                                                    |
| **Assign**   | `assign a/ASSIGNMENT {n/NAME | c/CLASS}`                                              | `assign a/Tutorial 1 n/John Doe`, `assign a/Lab 2 c/Class 10B`                     |
| **Unassign** | `unassign a/ASSIGNMENT {n/NAME | c/CLASS}`                                            | `unassign a/Tutorial 1 n/John Doe`, `unassign a/Lab 2 c/Class 10B`                 |
| **Mark**     | `mark a/ASSIGNMENT {n/NAME | c/CLASS}`                                                | `mark a/Tutorial 1 n/John Doe`, `mark a/Lab 2 c/Class 10B`                         |
| **Label**    | `label l/LABEL {n/NAME | c/CLASS}`                                                    | `label l/Top student n/John Doe`, `label l/Online class c/Class 10B`               |
| **Unlabel**  | `unlabel {n/NAME | c/CLASS}`                                                          | `unlabel n/John Doe`, `unlabel c/Class 10B`                                        |
| **Clear**    | `clear`                                                                               | `clear`                                                                            |
| **Exit**     | `exit`                                                                                | `exit`                                                                             |
>>>>>>> Stashed changes
