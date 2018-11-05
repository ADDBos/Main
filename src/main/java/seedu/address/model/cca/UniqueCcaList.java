package seedu.address.model.cca;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.cca.exceptions.CcaNotFoundException;
import seedu.address.model.cca.exceptions.DuplicateCcaException;

//@@author ericyjw
/**
 * A list of unique Ccas.
 *
 * @author ericyjw
 */
public class UniqueCcaList implements Iterable<Cca> {
    private final ObservableList<Cca> internalCcaList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent Cca as the given argument.
     *
     * @param toCheck the Cca to check
     */
    public boolean contains(Cca toCheck) {
        requireNonNull(toCheck);
        return internalCcaList.stream().anyMatch(toCheck::isSameCca);
    }

    /**
     * Returns true if the list contains an equivalent Cca name as the given argument.
     *
     * @param ccaName the name of the Cca to check
     */
    public boolean contains(String ccaName) {
        requireNonNull(ccaName);
        Cca toCheck = new Cca(ccaName);
        return internalCcaList.stream().anyMatch(toCheck::isSameCca);
    }


    /**
     * Adds a Cca to the unique Cca list.
     * The Cca must not already exist in the list.
     *
     * @param toAdd the cca to add
     */
    public void add(Cca toAdd) {
        if (contains(toAdd)) {
            throw new DuplicateCcaException();
        }
        internalCcaList.add(toAdd);
    }

    /**
     * Replaces the {@code target} Cca in the unique Cca list with {@code editedCca}.
     * {@code target} Cca must exist in the unique Cca list.
     * The Cca identity of {@code editedCca} must not be the same as another existing Cca in the list.
     *
     * @param target the Cca to be replaced
     * @param editedCca the Cca to replace the existing Cca
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

    /**
     * Replaces the whole {@code UniqueCcaList}.
     *
     * @param replacement the unique Cca list to replace the existing one
     */
    public void setCca(UniqueCcaList replacement) {
        requireNonNull(replacement);
        internalCcaList.setAll(replacement.internalCcaList);
    }

    /**
     * Replaces the contents of this list with {@code Ccas}.
     * {@code Ccas} must not contain duplicate CCAs.
     *
     * @param ccas the list of Ccas that does not contain any duplicate Ccas
     */
    public void setCcas(List<Cca> ccas) {
        requireAllNonNull(ccas);
        if (!ccasAreUnique(ccas)) {
            throw new DuplicateCcaException();
        }

        internalCcaList.setAll(ccas);
    }

    /**
     * Removes the equivalent Cca from the unique Cca list.
     * The Cca must exist in the list.
     *
     * @param toRemove the Cca to be removed
     */
    public void remove(Cca toRemove) {
        requireNonNull(toRemove);
        if (!internalCcaList.remove(toRemove)) {
            throw new CcaNotFoundException();
        }
    }

    /**
     * Returns true if the list of {@code Ccas} contains only unique Ccas.
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
