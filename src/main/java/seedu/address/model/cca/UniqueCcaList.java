package seedu.address.model.cca;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.cca.exceptions.CcaNotFoundException;
import seedu.address.model.cca.exceptions.DuplicateCcaException;

/**
 * A list of unique CCAs.
 */
public class UniqueCcaList implements Iterable<Cca> {
    private final ObservableList<Cca> internalCcaList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent tag as the given argument.
     */
    public boolean contains(Cca toCheck) {
        requireNonNull(toCheck);
        return internalCcaList.stream().anyMatch(toCheck::isSameCca);
    }

    //TODO: check why directly checking the name doesn't work

    /**
     * Returns true if the list contains an equivalent tag as the given argument.
     *
     * @author ericyjw
     */
    public boolean contains(String ccaName) {
        requireNonNull(ccaName);
        Cca toCheck = new Cca(ccaName);
        // return internalCcaList.stream().anyMatch(ccaName::isSameCcaName);
        return internalCcaList.stream().anyMatch(toCheck::isSameCcaName);

    }


    /**
     * Adds a CCA to the unique CCA list.
     * The CCA must not already exist in the list.
     */
    public void add(Cca toAdd) {
        if (contains(toAdd)) {
            throw new DuplicateCcaException();
        }
        internalCcaList.add(toAdd);
    }

    /**
     * Replaces the CCA {@code target} in the unique CCA list with {@code editedCCA}.
     * {@code target} must exist in the unique CCA list.
     * The CCA identity of {@code editedCCA} must not be the same as another existing CCA in the list.
     */
    public void setCca(Cca target, Cca editedCca) {
        requireAllNonNull(target, editedCca);

        int index = internalCcaList.indexOf(target);
        if (index == -1) {
            throw new CcaNotFoundException();
        }

        if (!target.isSameCca(editedCca) && contains(editedCca)) {
            throw new DuplicateCcaException();
        }

        internalCcaList.set(index, editedCca);
    }

    public void setCca(UniqueCcaList replacement) {
        requireNonNull(replacement);
        internalCcaList.setAll(replacement.internalCcaList);
    }

    /**
     * Replaces the contents of this list with {@code CCAs}.
     * {@code CCAs} must not contain duplicate CCAs.
     */
    public void setCcas(List<Cca> ccas) {
        requireAllNonNull(ccas);
        if (!ccasAreUnique(ccas)) {
            throw new DuplicateCcaException();
        }

        internalCcaList.setAll(ccas);
    }

    /**
     * Removes the equivalent tag from the unique tag list.
     * The tag must exist in the list.
     */
    public void remove(Cca toRemove) {
        requireNonNull(toRemove);
        if (!internalCcaList.remove(toRemove)) {
            throw new CcaNotFoundException();
        }
    }

    /**
     * Returns true if {@code CCAs} contains only unique CCAs.
     */
    private boolean ccasAreUnique(List<Cca> ccas) {
        for (int i = 0; i < ccas.size() - 1; i++) {
            for (int j = i + 1; j < ccas.size(); j++) {
                if (ccas.get(i).isSameCca(ccas.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Cca> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalCcaList);
    }

    @Override
    public Iterator<Cca> iterator() {
        return internalCcaList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UniqueCcaList // instanceof handles nulls
            && internalCcaList.equals(((UniqueCcaList) other).internalCcaList));
    }

    @Override
    public int hashCode() {
        return internalCcaList.hashCode();
    }

}
