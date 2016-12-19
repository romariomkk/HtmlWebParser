package main;

import db_handler.DBInsertionThread;
import db_handler.MongoDBHandler;
import parser.Parser;
import parser.custom_entity.TagValuePair;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by romariomkk on 14.12.2016.
 */
@WebServlet("/mainServlet")
public class MainServlet extends HttpServlet {

    Logger logger = Logger.getLogger(MainServlet.class.getSimpleName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        MongoDBHandler dbHandler = new MongoDBHandler();

        URL[] url = retrieveURLParams(request);
        ExecutorService pool = Executors.newFixedThreadPool(url.length);

        List<Future<List<TagValuePair>>> futures = new ArrayList<>();
        for (URL urlElem : url) {
            futures.add(pool.submit(new Parser(urlElem)));
        }

        try {
            for (Future<List<TagValuePair>> future : futures) {
                //dbHandler.executeInsertion(future.get());
                pool.execute(new DBInsertionThread(dbHandler, future.get()));
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.log(Level.WARNING, "Execution error occurred", e);
        }

        if (!pool.isTerminated()) {
            pool.shutdownNow();
        }

        List<List<TagValuePair>> forwardList = new ArrayList<>();
        for (URL urlElem : url) {
            forwardList.add(dbHandler.retrieveCollection(urlElem.toString()));
        }

        request.setAttribute("listOfLists", forwardList);
        request.getRequestDispatcher("result.jsp").forward(request, response);
    }

    private URL[] retrieveURLParams(HttpServletRequest request) throws MalformedURLException {
        return new URL[]{new URL(request.getParameter("urlN1")),
                new URL(request.getParameter("urlN2")),
                new URL(request.getParameter("urlN3"))};
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
}
