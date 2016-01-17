/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itp.template;

import Answers.CSVReader;
import static Answers.CSVReader.read;
import com.itp.images.Area;
import com.itp.images.Category;
import com.itp.images.Question;
import com.itp.images.Scatter;
import com.itp.images.Structure;
import com.itp.images.Wheel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Kevan
 */
public class Model {

//    private final String primaryCategoryName;
//    private final int primaryCategoryLevel;
//    private final String secondaryCategory1Name;
//    private final int secondaryCategory1Level;
//    private final String secondaryCategory2Name;
//    private final int secondaryCategory2Level;
    private final Document doc;
    private Category weekestCategory;
    private Category secondary1;
    private Category secondary2;

    public Model(String fileName) throws ParserConfigurationException, SAXException, IOException {
//        this.primaryCategoryName = primaryCategory;
//        this.primaryCategoryLevel = primaryCategoryLevel;
//        this.secondaryCategory1Name = secondaryCategory1;
//        this.secondaryCategory1Level = secondaryCategory1Level;
//        this.secondaryCategory2Name = secondaryCategory2;
//        this.secondaryCategory2Level = secondaryCategory2Level;
        Question[] questions = Question.getQuestions();

        File xml = new File("c:\\itp\\model.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        doc = db.parse(xml);

//        File file = new File(fileName);
//        BufferedReader br;
//        FileReader fr = new FileReader(file);
//        br = new BufferedReader(fr);
//        String line;
//        int questionNo = 0;
//        while ((line = br.readLine()) != null) {
//            String[] split = line.split(",");
//            double score = Double.parseDouble(split[0]);
//            double sd = Double.parseDouble(split[1]);
//            Question question = questions[questionNo];
//            question.setScore(score);
//            question.setSd(sd);
//            //System.out.println(questionNo + " " + question);
//            questionNo++;
//        }
//        fr.close();
//        br.close();
        CSVReader. read("c:\\itp\\Sheet_1.csv", "soltius");
        Category.setRanks();
        weekestCategory = Category.getByRank(0);
        weekestCategory.setAsWeakest();
        secondary1 = Category.getCategory(weekestCategory.getSecondary1index());
        secondary2 = Category.getCategory(weekestCategory.getSecondary2index());
        Scatter scatter = new Scatter(questions);
        scatter.drawAndWrite("C:\\ITP\\Template\\word\\media\\image3.jpeg");
    }

//    expect a csv
//    build questions and scores
//    dont use the member variables above, ask the questions for the answers
    public ArrayList<String> getCharacteristics(String category, int levelNumber) throws XPathExpressionException {
        ArrayList<String> characteristics = new ArrayList<>();
        String levelName = Level.levelNames[levelNumber];
        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList nodes = (NodeList) xPath.evaluate("//categories/category[name = '" + category + "']/levels/level[name = '" + levelName + "']/characteristics/characteristic", doc.getDocumentElement(), XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            Element e = (Element) nodes.item(i);
            String text = e.getTextContent();
            characteristics.add(text);
        }
        return characteristics;
    }

    public ArrayList<String> getActions(String category, int levelNumber) throws XPathExpressionException {
        ArrayList<String> actions = new ArrayList<>();
        String levelName = Level.levelNames[levelNumber];
        XPath xPath = XPathFactory.newInstance().newXPath();
        String x = "//categories/category[name = '" + category + "']/levels/level[name = '" + levelName + "']/actions/action";
        NodeList nodes = (NodeList) xPath.evaluate(x, doc.getDocumentElement(), XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            Element e = (Element) nodes.item(i);
            String text = e.getTextContent();
            actions.add(text);
        }
        return actions;
    }

    public ArrayList<String> getResources(String category) throws XPathExpressionException {
        ArrayList<String> resources = new ArrayList<>();
        XPath xPath = XPathFactory.newInstance().newXPath();
        String x = "//categories/category[name = '" + category + "']/Resources/Resource";
        NodeList nodes = (NodeList) xPath.evaluate(x, doc.getDocumentElement(), XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            Element e = (Element) nodes.item(i);
            String text = e.getTextContent();
            resources.add(text);
        }
        return resources;
    }

    public String getWikiDesc(int levelNumber) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        String x = "/Model/Wikipedia/Descrciption[@level=" + levelNumber + "]";
        NodeList nodes = (NodeList) xPath.evaluate(x, doc.getDocumentElement(), XPathConstants.NODESET);
        Element e = (Element) nodes.item(0);
        String text = e.getTextContent();
        return text;
    }

    public static String getStringFromDoc(Document doc) throws TransformerException {
        DOMSource domSource = new DOMSource(doc);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(domSource, result);
        writer.flush();
        return writer.toString();
    }

    public String replace(String keyWord) throws XPathExpressionException {
        String replacement = "Should replace [[" + keyWord + "]]";
        ArrayList<Structure> structures = Wheel.getStructures();
        DecimalFormat oneDP = new DecimalFormat("0.0");
        DecimalFormat noDP = new DecimalFormat("0");
        ArrayList<String> characteristics;
        ArrayList<String> actions;
        ArrayList<String> resources;
        double score = 0;
        switch (keyWord) {
            case "OrganisationName":
                return "Some Company";
            case "OverallLevelScore":
                for (Structure structure : structures) {
                    score = +structure.getScore();
                }
                return oneDP.format(score);
            case "OverallLevelNumber":
                for (Structure structure : structures) {
                    score = +structure.getScore();
                }
                return noDP.format(Math.round(score));
            case "OverallLevelName":
                for (Structure structure : structures) {
                    score = +structure.getScore();
                }
                return Level.levelNames[(int) Math.round(score)];
            case "WikipediaLevelDescription":
                for (Structure structure : structures) {
                    score = +structure.getScore();
                }
                return getWikiDesc((int) Math.round(score));
            case "OperationLevelScore":
                return oneDP.format(Structure.getScore(Structure.OPERATIONS));
            case "OperationsLevelNumber":
                return noDP.format(Math.round(Structure.getScore(Structure.OPERATIONS)));
            case "ProcessesLevelScore":
                return oneDP.format(Structure.getScore(Structure.PROCESSES));
            case "ProcessesLevelNumber":
                return noDP.format(Math.round(Structure.getScore(Structure.PROCESSES)));
            case "InsightsLevelScore":
                return oneDP.format(Structure.getScore(Structure.INSIGHTS));
            case "InsightsLevelNumber":
                return noDP.format(Math.round(Structure.getScore(Structure.INSIGHTS)));
            case "MinimumAreaScore":
                return oneDP.format(Area.getMinimumScore());
            case "MaximumAreaScore":
                return oneDP.format(Area.getMaximumScore());
            case "StrategyLevelScore":
                return oneDP.format(Area.getScore(Area.STRATEGY));
            case "StrategyLevelNumber":
                return noDP.format(Math.round(Area.getScore(Area.STRATEGY)));
            case "SystemsLevelScore":
                return oneDP.format(Area.getScore(Area.SYSTEMS));
            case "SystemsLevelNumber":
                return noDP.format(Math.round(Area.getScore(Area.SYSTEMS)));
            case "ResourcesLevelScore":
                return oneDP.format(Area.getScore(Area.RESOURCES));
            case "ResourcesLevelNumber":
                return noDP.format(Math.round(Area.getScore(Area.RESOURCES)));
            case "CultureLevelScore":
                return oneDP.format(Area.getScore(Area.CULTURE));
            case "CultureLevelNumber":
                return noDP.format(Math.round(Area.getScore(Area.CULTURE)));
            case "IdeationLevelScore":
                return oneDP.format(Area.getScore(Area.IDEATION));
            case "IdeationLevelNumber":
                return noDP.format(Math.round(Area.getScore(Area.IDEATION)));
            case "KnowledgeLevelScore":
                return oneDP.format(Area.getScore(Area.KNOWLEDGE));
            case "KnowledgeLevelNumber":
                return noDP.format(Math.round(Area.getScore(Area.KNOWLEDGE)));
            case "1stStrongestCategoryName":
                return Category.getByRank(11).getName();
            case "1stStrongestCategoryScore":
                return oneDP.format(Category.getByRank(11).getScore());
            case "2ndStrongestCategoryName":
                return Category.getByRank(10).getName();
            case "2ndStrongestCategoryScore":
                return oneDP.format(Category.getByRank(10).getScore());
            case "3rdStrongestCategoryName":
                return Category.getByRank(9).getName();
            case "3rdStrongestCategoryScore":
                return oneDP.format(Category.getByRank(9).getScore());
            case "1stWeekestCategoryName":
                return Category.getByRank(0).getName();
            case "1stWeekestCategoryScore":
                return oneDP.format(Category.getByRank(0).getScore());
            case "1stWeekestCategoryLevelNumber":
                return noDP.format(Math.round(Category.getByRank(0).getScore()));
            case "1stWeekestCategoryLevelName":
                return Level.levelNames[(int) Math.round(Category.getByRank(0).getScore())];
            case "2ndWeekestCategoryName":
                return Category.getByRank(1).getName();
            case "2ndWeekestCategoryScore":
                return oneDP.format(Category.getByRank(1).getScore());
            case "3rdWeekestCategoryName":
                return Category.getByRank(2).getName();
            case "3rdWeekestCategoryScore":
                return oneDP.format(Category.getByRank(2).getScore());
            case "1stWeekestCategoryNextLevelNumber":
                return noDP.format(Math.round(Category.getByRank(0).getScore()) + 1);
            case "1stWeekestCategoryNextLevelName":
                return Level.levelNames[(int) Math.round(Category.getByRank(0).getScore()) + 1];
            case "1stWeekestCategoryActionResourceLine":
                resources = getResources(weekestCategory.getName());
                replacement = toBullets(resources);
                return replacement;
            case "1stWeekestCategoryCharacteristicLine":
                characteristics = getCharacteristics(weekestCategory.getName(), (int) Math.round(weekestCategory.getScore()));
                replacement = toBullets(characteristics);
                return replacement;
            case "1stWeekestCategoryActionLine":
                actions = getActions(weekestCategory.getName(), (int) Math.round(weekestCategory.getScore()));
                replacement = toBullets(actions);
                return replacement;
            case "1stWeekestCategoryNextLevelCharacteristicLine":
                characteristics = getCharacteristics(weekestCategory.getName(), (int) Math.round(weekestCategory.getScore() + 1));
                replacement = toBullets(characteristics);
                return replacement;
            case "SecondaryCategory1Name":
                return secondary1.getName();
            case "SecondaryCategory1LevelName":
                return Level.levelNames[(int) Math.round(secondary1.getScore())];
            case "SecondaryCategory1LevelNumber":
                return noDP.format(Math.round(secondary1.getScore()));
            case "SecondaryCategory1Score":
                return oneDP.format(secondary1.getScore());
            case "SecondaryCategory1CharacteristicLine":
                characteristics = getCharacteristics(secondary1.getName(), (int) Math.round(secondary1.getScore()));
                replacement = toBullets(characteristics);
                return replacement;
            case "SecondaryCategory1ActionLine":
                actions = getActions(secondary1.getName(), (int) Math.round(secondary1.getScore()));
                replacement = toBullets(actions);
                return replacement;
            case "SecondaryCategory2LevelName":
                return Level.levelNames[(int) Math.round(secondary2.getScore())];
            case "SecondaryCategory2Name":
                return secondary2.getName();
            case "SecondaryCategory2LevelNumber":
                return noDP.format(Math.round(secondary2.getScore()));
            case "SecondaryCategory2Score":
                return oneDP.format(secondary2.getScore());
            case "SecondaryCategory2CharacteristicLine":
                characteristics = getCharacteristics(secondary2.getName(), (int) Math.round(secondary2.getScore()));
                replacement = toBullets(characteristics);
                return replacement;
            case "SecondaryCategory2ActionLine":
                actions = getActions(secondary2.getName(), (int) Math.round(secondary2.getScore()));
                replacement = toBullets(actions);
                return replacement;
            case "HeatMapColor1":
                return Category.getCategory(0).getHeatMapColor();
            case "HeatMapValue1":
                return oneDP.format(Category.getCategory(0).getScore());
            case "HeatMapColor2":
                return Category.getCategory(1).getHeatMapColor();
            case "HeatMapValue2":
                return oneDP.format(Category.getCategory(1).getScore());
            case "HeatMapColor3":
                return Category.getCategory(2).getHeatMapColor();
            case "HeatMapValue3":
                return oneDP.format(Category.getCategory(2).getScore());
            case "HeatMapColor4":
                return Category.getCategory(3).getHeatMapColor();
            case "HeatMapValue4":
                return oneDP.format(Category.getCategory(3).getScore());
            case "HeatMapColor5":
                return Category.getCategory(4).getHeatMapColor();
            case "HeatMapValue5":
                return oneDP.format(Category.getCategory(4).getScore());
            case "HeatMapColor6":
                return Category.getCategory(5).getHeatMapColor();
            case "HeatMapValue6":
                return oneDP.format(Category.getCategory(5).getScore());
            case "HeatMapColor7":
                return Category.getCategory(6).getHeatMapColor();
            case "HeatMapValue7":
                return oneDP.format(Category.getCategory(6).getScore());
            case "HeatMapColor8":
                return Category.getCategory(7).getHeatMapColor();
            case "HeatMapValue8":
                return oneDP.format(Category.getCategory(7).getScore());
            case "HeatMapColor9":
                return Category.getCategory(8).getHeatMapColor();
            case "HeatMapValue9":
                return oneDP.format(Category.getCategory(8).getScore());
            case "HeatMapColor10":
                return Category.getCategory(9).getHeatMapColor();
            case "HeatMapValue10":
                return oneDP.format(Category.getCategory(9).getScore());
            case "HeatMapColor11":
                return Category.getCategory(10).getHeatMapColor();
            case "HeatMapValue11":
                return oneDP.format(Category.getCategory(10).getScore());
            case "HeatMapColor12":
                return Category.getCategory(11).getHeatMapColor();
            case "HeatMapValue12":
                return oneDP.format(Category.getCategory(11).getScore());
            default:
                return "[[Couldn't find replacement for '" + keyWord + "']]";
        }
    }

    private String toBullets(ArrayList<String> lines) {
        String result = "";
        String before
                = "<w:p w:rsidR=\"00BE7668\" w:rsidRDefault=\"00BE7668\" w:rsidP=\"00BE7668\">\n"
                + "    <w:pPr>\n"
                + "        <w:pStyle w:val=\"ListParagraph\"/>\n"
                + "        <w:numPr>\n"
                + "            <w:ilvl w:val=\"0\"/>\n"
                + "            <w:numId w:val=\"38\"/>\n"
                + "        </w:numPr>\n"
                + "    </w:pPr>\n"
                + "    <w:r>\n"
                + "        <w:t>";
        String after
                = "</w:t>\n"
                + "    </w:r>\n"
                + "</w:p>\n";
        for (int lineNo = 0; lineNo < lines.size(); lineNo++) {
            String line = lines.get(lineNo);
            if (lineNo > 0) {
                result += before;
            }
            result += line;
            if (lineNo < lines.size() - 1) {
                result += after;
            }
        }
        return result;
    }

}
