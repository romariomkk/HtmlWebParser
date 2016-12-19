import db_handler.DBInsertionThread;
import db_handler.MongoDBHandler;
import org.junit.Test;
import parser.Parser;
import parser.custom_entity.TagValuePair;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by romariomkk on 18.12.2016.
 */
public class MainClass {

    @Test
    public void mainServletSimulation() throws MalformedURLException, ExecutionException, InterruptedException {
        MongoDBHandler dbHandler = new MongoDBHandler();

        URL[] url = {new URL("https://blog.google/"),
                new URL("https://www.work.ua/"),
                new URL("https://github.com/")};
        ExecutorService pool = Executors.newFixedThreadPool(url.length);

        List<Future<List<TagValuePair>>> futures = new ArrayList<>();
        for (URL urlElem : url) {
            futures.add(pool.submit(new Parser(urlElem)));
        }

        for (Future<List<TagValuePair>> future : futures){
            pool.execute(new DBInsertionThread(dbHandler, future.get()));
        }


        if (!pool.isTerminated()) {
            pool.shutdownNow();
        }

        for (URL urlElem : url){
            List<TagValuePair> list = dbHandler.retrieveCollection(urlElem.toString());
            System.out.println(list);
        }

    }

}
