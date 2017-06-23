package de.vanhck.restservice;

import de.vanhck.data.DrivingResult;
import de.vanhck.data.KeyValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Lotti on 6/23/2017.
 */
public class FileParser {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public DrivingResult createDrivingResultFromXML(InputStream is) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(is);
        doc.getDocumentElement().normalize();
        DrivingResult drivingResult = getGeneralData(doc);

        NodeList value = doc.getElementsByTagName("value");
        Node currentNode;
        for (int i = 0; i <= value.getLength(); i++) {
            currentNode = value.item(i);
            if (currentNode.getNodeType() != Node.ELEMENT_NODE) {
                log.error("Node of values is not a Element");
                break;
            }
            if (evaluentKeyElement(drivingResult, (Element) currentNode)) break;

        }

        return drivingResult;
    }

    private boolean evaluentKeyElement(DrivingResult drivingResult, Element currentNode) {
        Element currentElement;
        KeyValues keyName;
        double valueOfKey;
        currentElement = currentNode;
        String nameOfKey = currentElement.getElementsByTagName("name").item(0).getTextContent();
        keyName = KeyValues.valueOf(nameOfKey);
        if (keyName == null) {
            log.error("Key isn't in list of supported values, now we ignore it");
            return true;
        }
        try {
            valueOfKey = Double.valueOf(currentElement.getElementsByTagName("value").item(0).getTextContent());
            drivingResult.addValue(keyName, valueOfKey);
        } catch (NumberFormatException e) {
            log.error(String.format("Value of key %s isn't parsable to double, we ignore this key", keyName.name()));
            return true;
        }
        return false;
    }

    private DrivingResult getGeneralData(Document doc) {
        NodeList generalList = doc.getElementsByTagName("general");
        if (generalList.getLength() != 1) {
            throw new IllegalArgumentException("More then one general Node in File.");
        }
        if (generalList.item(0).getNodeType() != Node.ELEMENT_NODE) {
            throw new IllegalArgumentException("General Node is not a Element!");
        }

        Element generalElement = (Element) generalList.item(0);
        String fin = generalElement.getElementsByTagName("fin").item(0).getTextContent();
        String user = generalElement.getElementsByTagName("user").item(0).getTextContent();
        Double drivenKM = Double.valueOf(generalElement.getElementsByTagName("drivenKM").item(0).getTextContent());
        return new DrivingResult(fin, user, drivenKM);
    }
}
