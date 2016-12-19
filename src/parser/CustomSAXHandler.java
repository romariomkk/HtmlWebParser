package parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import parser.custom_entity.TagValuePair;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by romariomkk on 17.12.2016.
 */
public class CustomSAXHandler extends DefaultHandler {

    private static final Logger logger = Logger.getLogger(CustomSAXHandler.class.getSimpleName());

    private List<TagValuePair> tagValuePairList = new ArrayList<>();
    private URL url;

    CustomSAXHandler(URL u) {
        url = u;
    }

    public List<TagValuePair> getTagValuePairList() {
        return tagValuePairList;
    }

    @Override
    public void startDocument() throws SAXException {
        logger.log(Level.FINE, "START");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        List<String> valuesList = new ArrayList<>();
        for (int i = 0; i < attributes.getLength(); i++) {
            String value = attributes.getValue(i);
            valuesList.add(attributes.getLocalName(i) +
                    ((value != null) ? "=" + value : ""));
        }

        String value = valuesList.stream().collect(Collectors.joining(" "));

        tagValuePairList.add(new TagValuePair(qName, value));
    }
}