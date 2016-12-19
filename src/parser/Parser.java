package parser;

import org.xml.sax.SAXException;
import parser.custom_entity.TagValuePair;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by romariomkk on 15.12.2016.
 */
public class Parser implements Callable<List<TagValuePair>> {
    private static final String DOCUMENT_HEADER = "HEADER_FOR_THE_LIST";
    private Logger logger = Logger.getLogger(Parser.class.getSimpleName());
    private URL url;

    public Parser(URL address) {
        url = address;
    }

    @Override
    public List<TagValuePair> call() {
        SAXParser parser = null;
        CustomSAXHandler customSaxHandler = null;

        try (InputStream urlStream = url.openStream()) {
            parser = SAXParserFactory.newInstance().newSAXParser();
            customSaxHandler = new CustomSAXHandler(url);

            parser.parse(urlStream, customSaxHandler);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Host is unavailable or Internet connection is OFF");
            assert parser != null;
        } catch (ParserConfigurationException e) {
            logger.log(Level.WARNING, "ParserConfiguration exception occurred", e);
        } catch (SAXException e) {
            /*
               detects some non-standard-style code(it is parsable indeed) but anyway throws an exception,
               thus it has to be IGNORED!
               Any exception is needless due to the validity of that html code.
            */
        }

        return concatHeader(customSaxHandler);
    }

    private List<TagValuePair> concatHeader(CustomSAXHandler customSAXHandler){
        List<TagValuePair> listToReturn = customSAXHandler.getTagValuePairList();
        listToReturn.add(new TagValuePair(DOCUMENT_HEADER, url.toString()));
        return listToReturn;
    }
}