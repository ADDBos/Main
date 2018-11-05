package seedu.address.logic;

import java.util.Set;

import seedu.address.model.cca.Cca;
import seedu.address.model.cca.Outstanding;
import seedu.address.model.cca.Spent;
import seedu.address.model.transaction.Entry;

//@@author ericyjw
/**
 * To update the spent and outstanding amount in a {@code Cca} when a transaction is created, transaction is updated
 * and transaction is deleted.
 * Assumes that the spent and outstanding amount is correct when the application read from the ccabook.xml
 *
 * @author ericyjw
 */
public class TransactionMath {
    /**
     * Update the spent and outstanding amount when there is a change in the transaction list of the Cca.
     * Returns a {@code Cca} with the updated spent and outstanding amount
     *
     * @param toUpdate the Cca with the updated transaction list
     */
    public static Cca updateDetails(Cca toUpdate) {
        Set<Entry> transactionEntries = toUpdate.getEntries();
        int givenBudget = toUpdate.getBudgetAmount();
        int netAmountSpent = 0;

        for (Entry e : transactionEntries) {
            netAmountSpent += e.getAmountValue();
        }

        int spent;
        if (netAmountSpent > 0) {
            spent = 0;
        } else {
            spent = Math.abs(netAmountSpent);
        }

        int outstanding = givenBudget + netAmountSpent;

        Cca updatedCca = new Cca(toUpdate.getName(), toUpdate.getHead(), toUpdate.getViceHead(), toUpdate.getBudget(),
            new Spent(spent), new Outstanding(outstanding), transactionEntries);

        return updatedCca;
    }
}
