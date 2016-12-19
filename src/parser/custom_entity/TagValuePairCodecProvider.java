package parser.custom_entity;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

@SuppressWarnings("unchecked")
public class TagValuePairCodecProvider implements CodecProvider{

    @Override
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
        if (clazz.equals(TagValuePair.class)){
            return (Codec<T>)new TagValuePairCodec();
        }
        return null;
    }
}