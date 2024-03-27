// XML as a database

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class cCrudsXML implements iCruds
{
	File inputFile;
	String subRootName;
	DocumentBuilderFactory dbFactory;
	DocumentBuilder dBuilder;
	Document doc;
	Element root;

	public cCrudsXML(String fileName, String subRootName) throws Exception
	{
        inputFile = new File(subRootName + "s.xml");
        this.subRootName = subRootName;
	    dbFactory = DocumentBuilderFactory.newInstance();
	    dBuilder = dbFactory.newDocumentBuilder();

        if (inputFile.length() == 0)
        {
        	doc = dBuilder.newDocument();
        	root = doc.createElement(subRootName + "s");
        	doc.appendChild(root);
        	writeChangesToFile();
        }
        else
        {
	        doc = dBuilder.parse(inputFile);
	        doc.getDocumentElement().normalize();
        }

	}
    public int addRecord(cUserInterface record) throws Exception 
    {
        root = doc.getDocumentElement();
        Element newRecord = doc.createElement(subRootName);
        for (int columnCounter = 0; columnCounter < record.fieldsCount; columnCounter++) 
        {
            Element field = doc.createElement(record.fieldNames[columnCounter]);
            field.appendChild(doc.createTextNode(record.fieldValues[columnCounter]));
            newRecord.appendChild(field);
        }

        root.appendChild(newRecord);
		writeChangesToFile();
        return 1;
    }


	public cUserInterface[] fetchAll() throws Exception
	{
		NodeList recordList = doc.getElementsByTagName(subRootName);
		int rowCount = recordList.getLength();
	    cUserInterface[] records = new cUserInterface[rowCount];

        for (int rowCounter = 0; rowCounter < recordList.getLength(); rowCounter++) 
        {
            Element record = (Element) recordList.item(rowCounter);
            records[rowCounter] = new cUserInterface();

            for (int column = 0; column < records[rowCounter].fieldsCount; column++)
            {
	            records[rowCounter].fieldValues[column] = record.getElementsByTagName(records[rowCounter].fieldNames[column]).item(0).getTextContent();
            }

        }

		return records;
	}

	private void writeChangesToFile() throws Exception 
	{
	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer();
	    DOMSource source = new DOMSource(doc);
	    StreamResult result = new StreamResult(inputFile);
	    transformer.transform(source, result);
	}

}




