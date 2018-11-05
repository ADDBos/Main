package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.exceptions.DuplicateTagException;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * A list of unique CCA tags.
 */
public class UniqueTagList implements Iterable<Tag> {
    private final ObservableList<Tag> internalTagList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent tag as the given argument.
     */
    public boolean contains(Tag toCheck) {
        requireNonNull(toCheck);
        return internalTagList.stream().anyMatch(toCheck::isSameTag);
    }

    /**
     * Adds a tag to the unique tag list.
     * The person must not already exist in the list.
     */
    public void add(Tag toAdd) {
        if (contains(toAdd)) {
            throw new DuplicateTagException();
        }
        internalTagList.add(toAdd);
    }

    /**
     * Replaces the tag {@code target} in the unique tag list with {@code editedTag}.
     * {@code target} must exist in the unique tag list.
     * The tag identity of {@code editedTag} must not be the same as another existing tag in the list.
     */
    public void setTag(Tag target, Tag editedTag) {
        requireAllNonNull(target, editedTag);

        int index = internalTagList.indexOf(target);
        if (index == -1) {
            throw new TagNotFoundException();
        }

        if (!target.isSameTag(editedTag) && contains(editedTag)) {
            throw new DuplicatePersonException();
        }

        internalTagList.set(index, editedTag);
    }

    /**
     * Removes the equivalent tag from the unique tag list.
     * The tag must exist in the list.
     */
    public void remove(Tag toRemove) {
        requireNonNull(toRemove);
        if (!internalTagList.remove(toRemove)) {
            throw new TagNotFoundException();
        }
    }

    public void setTags(UniqueTagList replacement) {
        requireNonNull(replacement);
        internalTagList.setAll(replacement.internalTagList);
    }

    /**
     * Replaces the contents of this list with {@code tags}.
     * {@code tags} must not contain duplicate tags.
     */
    public void setTags(List<Tag> tags) {
        requireAllNonNull(tags);
        if (!tagsAreUnique(tags)) {
            throw new DuplicateTagException();
        }

        internalTagList.setAll(tags);
    }

    /**
     * Returns true if {@code tags} contains only unique tags.
     */
    private boolean tagsAreUnique(List<Tag> tags) {
        for (int i = 0; i < tags.size() - 1; i++) {
            for (int j = i + 1; j < tags.size(); j++) {
                if (tags.get(i).isSameTag(tags.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Tag> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalTagList);
    }

    @Override
    public Iterator<Tag> iterator() {
        return internalTagList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UniqueTagList // instanceof handles nulls
            && internalTagList.equals(((UniqueTagList) other).internalTagList));
    }

    @Override
    public int hashCode() {
        return internalTagList.hashCode();
    }

}
