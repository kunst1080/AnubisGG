package anubisgg2;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.sun.org.apache.xml.internal.serializer.OutputPropertiesFactory;

public class HtmlUtility {

	private HtmlUtility(Document document, String title) {
		this.document = document;
		this.root = createHtmlRoot(title);
	}

	private Document document;
	private Element root;

	public static HtmlUtility create(String title) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		}
		Document document = builder.newDocument();
		HtmlUtility htmlUtility = new HtmlUtility(document, title);
		return htmlUtility;
	}

	private Element createHtmlRoot(String title) {
		Element html = document.createElement("html");
		document.appendChild(html);

		Element head = document.createElement("head");
		html.appendChild(head);

		Element titleNode = createTextElement("title", title);
		head.appendChild(titleNode);

		Element body = document.createElement("body");
		html.appendChild(body);
		return body;
	}

	public Element getRoot() {
		return root;
	}

	public Element createElement(String tagName) {
		return document.createElement(tagName);
	}

	public Element createTextElement(String tagName, String text) {
		Element node = document.createElement(tagName);
		Text textNode = document.createTextNode(text);
		node.appendChild(textNode);
		return node;
	}

	public Element createTRHeader(String... headers) {
		Element tr = createElement("tr");
		for (String header : headers) {
			Element th = createTextElement("th", header);
			tr.appendChild(th);
		}
		return tr;
	}

	public Element createTD(String text, int rowspan) {
		Element td = createTextElement("td", text);
		if (rowspan > 0) {
			td.setAttribute("rowspan", String.valueOf(rowspan));
		}
		return td;
	}

	public String toHtmlString() {
		StringWriter sw = new StringWriter();
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = factory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.METHOD, "html");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputPropertiesFactory.S_KEY_INDENT_AMOUNT, "2");
			transformer.transform(new DOMSource(document), new StreamResult(sw));
			return sw.toString();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
			return "";
		} catch (TransformerException e) {
			e.printStackTrace();
			return "";
		}
	}
}
