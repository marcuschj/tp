package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.SortCommand.MESSAGE_SORT_CONTACT_SUCCESS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.JANE;
import static seedu.address.testutil.TypicalPersons.getRandomTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.CategoryCode;
import seedu.address.model.person.IsFilterablePredicate;
import seedu.address.model.person.IsFindableContainsKeywordsPredicate;
import seedu.address.model.person.Rating;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code SortCommandName}.
 */
public class SortCommandNameTest {

    private Model model = new ModelManager(getRandomTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getRandomTypicalAddressBook(), new UserPrefs());

    // sort empty list
    @Test
    public void execute_emptyList_sorted() {
        // create emptyModel and emptyExpectedModel of empty filteredPersons;
        AddressBook ab = new AddressBook();

        Model emptyModel = new ModelManager(ab, new UserPrefs());
        Model emptyExpectedModel = new ModelManager(ab, new UserPrefs());

        String expectedMessage = String.format(MESSAGE_SORT_CONTACT_SUCCESS, "by name");

        SortCommandName command = new SortCommandName();

        emptyExpectedModel.sortList("name");

        assertCommandSuccess(command, emptyModel, expectedMessage, emptyExpectedModel);

        assertEquals(Collections.emptyList(), emptyModel.getFilteredPersonList());
    }

    // sort list with one contact
    @Test
    public void execute_listWithOneElement_sorted() {
        // create ab with one element
        AddressBook ab = new AddressBook();
        ab.addPerson(HOON);

        Model oneElementModel = new ModelManager(ab, new UserPrefs());
        Model oneElementExpectedModel = new ModelManager(ab, new UserPrefs());

        String expectedMessage = String.format(MESSAGE_SORT_CONTACT_SUCCESS, "by name");

        SortCommandName command = new SortCommandName();

        oneElementExpectedModel.sortList("name");

        assertCommandSuccess(command, oneElementModel, expectedMessage, oneElementExpectedModel);

        assertEquals(Arrays.asList(HOON), oneElementModel.getFilteredPersonList());
    }

    // sort original list
    @Test
    public void execute_originalList_sorted() {
        String expectedMessage = String.format(MESSAGE_SORT_CONTACT_SUCCESS, "by name");

        SortCommandName command = new SortCommandName();

        expectedModel.sortList("name");

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, JANE),
            model.getFilteredPersonList());
    }


    // sort list after filter command
    @Test
    public void execute_listAfterFilterCommand_sorted() {
        // filter model by c/att
        IsFilterablePredicate predicate = new IsFilterablePredicate(
                Collections.singleton(new CategoryCode("com")),
                new Rating("5"),
                Collections.emptySet());
        model.updateFilteredPersonList(predicate);

        String expectedMessage = String.format(MESSAGE_SORT_CONTACT_SUCCESS, "by name");

        SortCommandName command = new SortCommandName();

        // filter expectedModel by c/att
        expectedModel.updateFilteredPersonList(predicate);

        expectedModel.sortList("name");

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        assertEquals(Arrays.asList(FIONA, JANE), model.getFilteredPersonList());
    }

    // sort list after find command
    @Test
    public void execute_listAfterFindCommand_sorted() {
        // find "Meier" in model
        IsFindableContainsKeywordsPredicate predicate =
                new IsFindableContainsKeywordsPredicate(Arrays.asList("Meier".split("\\s+")));

        model.updateFilteredPersonList(predicate);

        String expectedMessage = String.format(MESSAGE_SORT_CONTACT_SUCCESS, "by name");

        SortCommandName command = new SortCommandName();

        // find "Meier" in expectedModel
        expectedModel.updateFilteredPersonList(predicate);

        expectedModel.sortList("name");

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        assertEquals(Arrays.asList(BENSON, DANIEL), model.getFilteredPersonList());
    }


    // sort list after sort rating command
    @Test
    public void execute_listAfterSortRatingCommand_sorted() {
        // sort model by rating
        model.sortList("rating");

        String expectedMessage = String.format(MESSAGE_SORT_CONTACT_SUCCESS, "by name");

        SortCommandName command = new SortCommandName();

        model.sortList("rating");
        expectedModel.sortList("name");

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, JANE),
            model.getFilteredPersonList());

    }

    // sort list after sort name command
    @Test
    public void execute_listAfterSortNameCommand_sorted() {
        // sort model by name
        model.sortList("name");

        String expectedMessage = String.format(MESSAGE_SORT_CONTACT_SUCCESS, "by name");

        SortCommandName command = new SortCommandName();

        expectedModel.sortList("name");
        expectedModel.sortList("name");

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, JANE),
            model.getFilteredPersonList());
    }
}
