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
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Room;
import seedu.address.model.person.School;
import seedu.address.model.tag.Tag;

//@@author kengwoon

/**
 * Imports XML file as contacts list to update database.
 */
public class ImportAddressBook {

    private Document doc;
    private Model model;
    private List<Person> personList;
    private Set<Tag> tags;


    public ImportAddressBook(Document doc, Model model) {
        this.doc = doc;
        this.model = model;
        this.personList = new ArrayList<>();
        this.tags = new HashSet<>();
    }

    /**
     * Executes ImportAddressBook
     */
    public void execute() {
        List<Person> fullList = model.getAddressBook().getPersonList();
        personList.clear();
        NodeList nList = doc.getElementsByTagName("persons");
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            tags.clear();
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                Name name = new Name(element.getElementsByTagName("name").item(0).getTextContent());
                Phone phone = new Phone(element.getElementsByTagName("phone").item(0).getTextContent());
                Email email = new Email(element.getElementsByTagName("email").item(0).getTextContent());
                Room room = new Room(element.getElementsByTagName("room").item(0).getTextContent());
                School school = new School(element.getElementsByTagName("school").item(0).getTextContent());
                NodeList tagged = element.getElementsByTagName("tagged");
                if (tagged.getLength() != 0) {
                    for (int j = 0; j < tagged.getLength(); j++) {
                        tags.add(new Tag(tagged.item(j).getTextContent()));
                    }
                }
                Person temp = new Person(name, phone, email, room, school, tags);
                if (!fullList.contains(temp)) {
                    personList.add(temp);
                }
            }
        }
        model.addMultiplePersons(personList);
    }
}
