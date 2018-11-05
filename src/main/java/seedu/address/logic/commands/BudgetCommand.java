package seedu.address.logic.commands;

import static seedu.address.logic.commands.UpdateCommand.MESSAGE_NON_EXISTENT_CCA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowBudgetViewEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cca.CcaName;

//@author ericyjw
/**
 * View the budget of the whole hall or by each CCA.
 *
 * @author ericyjw
 */
public class BudgetCommand extends Command {
    public static final String COMMAND_WORD = "budget";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View the budget of the whole hall or by each CCA\n"
        + "Parameters: no parameters\n"
        + "Example: " + COMMAND_WORD + "\n"
        + "or\n"
        + "Parameters: " + PREFIX_TAG + "CCA\n"
        + "Example: " + COMMAND_WORD + " basketball\n";
    public static final String SHOWING_BUDGET_MESSAGE = "Display budget.";

    private final CcaName ccaName;

    /**
     * Creates a BudgetCommand to view the budget of a specific Cca
     * @param ccaName the name of the Cca to have its budget viewed
     */
    public BudgetCommand(CcaName ccaName) {
        this.ccaName = ccaName;
    }

    /**
     * Creates a BudgetCommand to view the overall budget
     */
    public BudgetCommand() {
        ccaName = null;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (ccaName != null && !model.hasCca(ccaName)) {
            throw new CommandException(MESSAGE_NON_EXISTENT_CCA);
        }

        EventsCenter.getInstance().post(new ShowBudgetViewEvent(ccaName));
        return new CommandResult(SHOWING_BUDGET_MESSAGE);
    }
}
