package seedu.address.logic.commands.imports;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import seedu.address.model.Model;
import seedu.address.model.person.ContactContainsRoomPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

//@@author kengwoon

/**
 * Imports XML file as CCA list to update database.
 */
public class ImportCcaList {

    private List<String> roomsList;
    private Document doc;
    private Model model;
    private String cca;

    public ImportCcaList(Document doc, Model model) {
        this.roomsList = new ArrayList<>();
        this.doc = doc;
        this.model = model;
        this.cca = null;
    }

    /**
     * Executes ImportCcaList
     */
    public void execute() {
        List<Person> originalList = new ArrayList<>();
        List<Person> editedList = new ArrayList<>();
        NodeList nList = doc.getElementsByTagName("cca");
        for (int i = 0; i < nList.getLength(); i++) {
            List<Person> fullList = model.getAddressBook().getPersonList();
            originalList.clear();
            editedList.clear();
            roomsList.clear();
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                this.cca = element.getAttribute("name");
                NodeList nodeList = element.getElementsByTagName("room");
                for (int j = 0; j < nodeList.getLength(); j++) {
                    roomsList.add(nodeList.item(j).getTextContent());
                }
            }
            ContactContainsRoomPredicate predicate = new ContactContainsRoomPredicate(roomsList);
            for (Person p : fullList) {
                if (predicate.test(p)) {
                    originalList.add(p);
                    editedList.add(addCcaToPerson(this.cca, p));
                }
            }
            if (!originalList.isEmpty()) {
                model.updateMultiplePersons(originalList, editedList);
            }
        }
    }

    /**
     * Adds specified cca to specified person.
     *
     * @param cca
     * @param p
     * @return Edited person with updated ccas.
     */
    private Person addCcaToPerson(String cca, Person p) {
        Set<Tag> newTags = new HashSet<>();
        newTags.addAll(p.getTags());
        newTags.add(new Tag(cca));
        Person editedPerson = new Person(p.getName(), p.getPhone(), p.getEmail(), p.getRoom(),
            p.getSchool(), newTags);
        return editedPerson;
    }
}
