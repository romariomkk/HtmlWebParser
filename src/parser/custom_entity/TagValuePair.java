package parser.custom_entity;

import org.bson.BsonDocument;
import org.bson.BsonDocumentWrapper;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

/**
 * Created by romariomkk on 17.12.2016.
 */
public class TagValuePair implements Bson{
    public final static String TAG = "tag";
    public final static String VALUE = "value";
    String tag;
    String value;

    public TagValuePair(String t, String v) {
        tag = t;
        value = v;
    }

    public String getTag() {
        return tag;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return tag + ((value != null) ? ("=" + value) : "");
    }

    @Override
    public <TDocument> BsonDocument toBsonDocument(Class<TDocument> tDocumentClass, CodecRegistry codecRegistry) {
        return new BsonDocumentWrapper<>(this, codecRegistry.get(TagValuePair.class));
    }
}
