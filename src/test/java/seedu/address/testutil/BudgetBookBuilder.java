package seedu.address.testutil;

import seedu.address.model.BudgetBook;
import seedu.address.model.cca.Cca;

//@@author ericyjw

/**
 * A utility class to help with building {@code BudgetBook} objects.
 * Example usage: <br>
 * {@code BudgetBook bb = new BudgetBookBuilder().withCca("Track", "Basketball").build();}
 */
public class BudgetBookBuilder {

    private BudgetBook budgetBook;

    public BudgetBookBuilder() {
        budgetBook = new BudgetBook();
    }

    public BudgetBookBuilder(BudgetBook budgetBook) {
        this.budgetBook = budgetBook;
    }

    /**
     * Adds a new {@code Cca} to the {@code BudgetBook} that we are building.
     */
    public BudgetBookBuilder withCca(Cca cca) {
        budgetBook.addCca(cca);
        return this;
    }

    public BudgetBook build() {
        return budgetBook;
    }
}
