package de.vanhck.restservice;

import de.vanhck.Scoring;
import de.vanhck.data.*;
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
import java.util.Date;

/**
 * Created by Lotti on 6/23/2017.
 */
public class FileParser {

    private final DrivingKeyValueDAO drivingKeyValueDAO;
    private final UserDAO userDao;
    private final ScoreDAO scoreDao;
    private final Option option;
    private KeyNameValueDAO keyNameValueDao;
    private DrivingResultDAO resultDAO;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public FileParser(KeyNameValueDAO keyNameValueDao, DrivingResultDAO resultDAO, DrivingKeyValueDAO drivingKeyValueDAO, UserDAO userDAO, ScoreDAO scoreDao, Option option){
        this.keyNameValueDao = keyNameValueDao;
        this.resultDAO = resultDAO;
        this.drivingKeyValueDAO = drivingKeyValueDAO;
        this.userDao = userDAO;
        this.scoreDao = scoreDao;
        this.option = option;
    }

    public boolean createDrivingResultFromXML(InputStream is) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(is);
        doc.getDocumentElement().normalize();
        DrivingResult drivingResult = getGeneralData(doc);

        NodeList value = doc.getElementsByTagName("keyvalue");
        Node currentNode;
        for (int i = 0; i < value.getLength(); i++) {
            currentNode = value.item(i);
            if (currentNode.getNodeType() != Node.ELEMENT_NODE) {
                log.error("Node of values is not a Element");
                break;
            }
            if (evaluateKeyElement(drivingResult, (Element) currentNode)) break;

        }
        resultDAO.save(drivingResult);
        Score score = Scoring.getScore(drivingResult, drivingKeyValueDAO, option);
        scoreDao.save(score);
        drivingResult.getDriver().addScore(score);
        userDao.save(drivingResult.getDriver());
        return true;
    }



    private boolean evaluateKeyElement(DrivingResult drivingResult, Element currentNode) {
        Element currentElement;
        DrivingKeyValue keyName;
        double valueOfKey;
        currentElement = currentNode;
        KeyNameValue keyNameValue;
        String nameOfKey = currentElement.getElementsByTagName("name").item(0).getTextContent().toUpperCase();
        keyNameValue =  keyNameValueDao.findByKeyName(nameOfKey);
        if (keyNameValue == null) {
            log.error(String.format("KeyNameValue %s isn't in list of supported values, now we ignore it",nameOfKey));
            return true;
        }
        try {
            valueOfKey = Double.valueOf(currentElement.getElementsByTagName("value").item(0).getTextContent());
            keyName = new DrivingKeyValue(keyNameValue, valueOfKey);
            keyName.setMatchingResult(drivingResult);
            drivingKeyValueDAO.save(keyName);
            drivingResult.addValue(keyName);

        } catch (NumberFormatException e) {
            log.error(String.format("Value of keyNameValue %s isn't parsable to double, we ignore this keyNameValue", keyNameValue.getKeyName()));
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
        String userName = generalElement.getElementsByTagName("user").item(0).getTextContent();
        User user = userDao.findByName(userName);
        if(user == null){
            throw new IllegalArgumentException("user isn't registered, please register first!");
        }
        Double drivenKM = Double.valueOf(generalElement.getElementsByTagName("drivenKM").item(0).getTextContent());

        Date date = new Date(Long.valueOf(generalElement.getElementsByTagName("dateTime").item(0).getTextContent()));
        return new DrivingResult(fin, user, drivenKM, date);
    }
}
