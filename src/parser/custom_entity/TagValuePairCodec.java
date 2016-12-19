package parser.custom_entity;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class TagValuePairCodec implements Codec<TagValuePair> {
    @Override
    public TagValuePair decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();
        reader.readObjectId();
        String tag = reader.readString(TagValuePair.TAG);
        String value = reader.readString(TagValuePair.VALUE);
        reader.readEndDocument();
        return new TagValuePair(tag, value);
    }

    @Override
    public void encode(BsonWriter writer, TagValuePair value, EncoderContext encoderContext) {
        writer.writeStartDocument();
        writer.writeString(TagValuePair.TAG, value.getTag());
        writer.writeString(TagValuePair.VALUE, value.getValue());
        writer.writeEndDocument();
    }

    @Override
    public Class<TagValuePair> getEncoderClass() {
        return TagValuePair.class;
    }

}
