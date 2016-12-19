import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import parser.Parser;
import parser.custom_entity.TagValuePair;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by romariomkk on 16.12.2016.
 */
public class ParserTest {
    ExecutorService pool;

    @Before
    public void setUp() {
        pool = Executors.newFixedThreadPool(3);
    }

    @Test
    public void testSingleParserThread() throws MalformedURLException, ExecutionException, InterruptedException {
        URL url = new URL("https://play.google.com/store");
        Parser parser = new Parser(url);
        Future<List<TagValuePair>> future = pool.submit(parser);
        List<TagValuePair> list = future.get();
        assert list != null;
    }

    @Test
    public void testMultipleParserThread() throws MalformedURLException, ExecutionException, InterruptedException {
        URL[] url = new URL[]{new URL("https://blog.google/"),
                new URL("https://www.work.ua/"),
                new URL("https://github.com/")};
        for (URL u : url) {
            Future<List<TagValuePair>> future = pool.submit(new Parser(u));
            List<TagValuePair> list = future.get();
            assert list != null;
        }
    }

    @Test (expected = ExecutionException.class)
    public void testSingleParserWithWrongURL() throws MalformedURLException, ExecutionException, InterruptedException {
        URL url = new URL("https://play.google.com/stor");
        Parser parser = new Parser(url);
        Future<List<TagValuePair>> future = pool.submit(parser);
        List<TagValuePair> list = future.get();
    }

    @After
    public void onFinished() {
        if (!pool.isTerminated())
            pool.shutdownNow();
    }
}