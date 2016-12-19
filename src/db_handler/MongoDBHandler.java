package db_handler;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import parser.custom_entity.TagValuePair;
import parser.custom_entity.TagValuePairCodec;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by romariomkk on 15.12.2016.
 */
public class MongoDBHandler {
    private final Logger logger = Logger.getLogger(MongoDBHandler.class.getSimpleName());
    private final String ONLY_DB = "Database";

    private final String PREFIX_COLLECTION = "COLL_";

    private MongoClient client;
    private MongoDatabase db;

    public MongoDBHandler(){
        TagValuePairCodec customCodec = new TagValuePairCodec();
        CodecRegistry registry = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),
                CodecRegistries.fromCodecs(customCodec));
        MongoClientOptions options = MongoClientOptions.builder().codecRegistry(registry).build();

        client = new MongoClient("127.0.0.1:27017", options);
        db = client.getDatabase(ONLY_DB);
    }

    public void dropCollection(String collectionUrl){
        db.getCollection(PREFIX_COLLECTION + collectionUrl).drop();
    }

    public void executeInsertion(List<TagValuePair> list) {
        MongoCollection<TagValuePair> collection =
                //last element was written to be retrieved here as header
                db.getCollection(PREFIX_COLLECTION + list.remove(list.size() - 1).getValue())
                .withDocumentClass(TagValuePair.class);

        collection.insertMany(list);
        logger.log(Level.FINEST, "Collection insertion executed");
    }

    public List<TagValuePair> retrieveCollection(String collectionName){
        MongoCollection<TagValuePair> collection = db.getCollection(PREFIX_COLLECTION + collectionName)
                .withDocumentClass(TagValuePair.class);

        return collection.find().into(new ArrayList<>());
    }


}
