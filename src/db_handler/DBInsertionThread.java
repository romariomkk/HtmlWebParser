package db_handler;

import parser.custom_entity.TagValuePair;

import java.util.List;

/**
 * Created by romariomkk on 19.12.2016.
 */
public class DBInsertionThread implements Runnable {
    private MongoDBHandler handler;

    private List<TagValuePair> list;

    public DBInsertionThread(MongoDBHandler mongoDBHandler, List<TagValuePair> toInsert){
        handler = mongoDBHandler;
        list = toInsert;
    }

    @Override
    public void run() {
        handler.executeInsertion(list);
    }
}
