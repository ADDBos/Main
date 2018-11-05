package seedu.address.commons.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * To convert XML files to HTML
 *
 * @author ericyjw
 */
public class XmlToHmtl {

    private static final String ccaBook = "./data/ccabook.xml";
    private static final String ccaBookXsl = "./docs/ccabook.xsl";
    private static final String outputHtml = "./data/ccabook.html";

    /**
     * Convert ccabook.xml into ccabook.hmtl to view on the Webview of FXML
     */
    public static void convertCcaBook(String chosenCca) {

        changeChosenCcaTo(chosenCca);
        try {

            TransformerFactory tFactory = TransformerFactory.newInstance();

            File xmlFile = new File(ccaBook);
            File xslFile = new File(ccaBookXsl);
            Transformer transformer = tFactory.newTransformer
                (new StreamSource(xslFile));

            transformer.transform(new javax.xml.transform.stream.StreamSource(xmlFile),
                new StreamResult(new FileOutputStream(outputHtml)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Set the xsl file to display chosen CCA
     *
     * @param chosenCca budget information that the chosen CCA
     */
    private static void changeChosenCcaTo(String chosenCca) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(ccaBookXsl);

            // Set the display information of the chosen CCA
            Node chosen = doc.getElementsByTagName("xsl:if").item(0);
            NamedNodeMap attribute = chosen.getAttributes();
            Node nodeAttr = attribute.getNamedItem("test");
            String expr = "name='" + chosenCca + "'";
            nodeAttr.setTextContent(expr);

            // Write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(ccaBookXsl));
            transformer.transform(source, result);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SAXException sae) {
            sae.printStackTrace();
        }
    }
}
