/**
 * A NoSQL Approach to Web Usage Mining of Frequent Patterns
 * Authors: Ilan Granot, Nick Kim, Sebrianne Ferguson, Mike Wu
 * Copyright © 2021 Granot et. al.
 *
 * This file is subject to the terms and conditions defined in
 * the file 'LICENSE.txt', which is part of the source code package.
 *
 * WUMGrapher.java
 * Purpose: provide an API for Web Usage Mining of Frequent Patterns
 */

import java.util.ArrayList;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.exceptions.Neo4jException;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;
import org.neo4j.driver.Value;
import static org.neo4j.driver.Values.parameters;

public class WUMGrapher implements AutoCloseable {

    private final Driver driver;

    /**
     * initialize the database
     */
    public WUMGrapher(String uri, String user, String password ) {
        driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
    }

    @Override
    public void close() throws Exception
    {
        driver.close();
    }

    /**
     * findPageNode()
     * Determines if the webpage exists as a node in the graph.
     * @param url - URL of page to find
     * @return - True/False depending on whether a node for page was found
     */
    public boolean findPageNode(String url) {
        // TODO: complete this method
        // this should execute a match query
        // if there is something found, return true
        // should handle exceptions being thrown
        // returns false if not found or exception is thrown
        return true;
    }

    /**
     * createPageNode()
     * Adds a new node to the graph if one for the given URL does not exist.
     * @param userID - the ID of the anonymous user, used for creating sessions
     * @param date - date accessed by this particular user
     * @param time - time of access by user
     * @param url - url of the webpage
     * @return - 0 if all goes smoothly, -1 if there is an issue.
     */
    public int createPageNode(int userID, String date, String time, String url) {
        if (findPageNode(url)) {
            return -1;
        }
        // TODO: complete this method
        // should use the passed-in parameters in a CREATE query
        // return -1 if this node is already in graph or exception thrown
        // return 0 if the node is added successfully
        return 0;
    }

    /**
     * findTransition()
     * Determines if a transition between two nodes with the given URLS exists.
     * @param urlA - the starting node's URL
     * @param urlB - the URL for the node to transition to
     * @return - True/False depending on whether the transition was found.
     */
    public boolean findTransition(String urlA, String urlB) {
        return false;
        // TODO: complete the algorithm and implementation of this method
        // not sure what query we would use for this method
        // return true if the edge is found
        // return false if it's not found or an exception was thrown
    }

    /**
     * createTransition()
     * Adds a transition between two page nodes if the transition does not already exist.
     * @param urlA - the starting node's URL
     * @param urlB - the URL for the node to transition to
     * @param tinfo - information relevant to the transition.
     * @return - 0 if all goes smoothly, -1 if there is an issue.
     */
    public int createTransition(String urlA, String urlB, String tinfo) {
        if (findTransition(urlA, urlB)) {
            updateTransition(urlA, urlB, tinfo);
        }
        // TODO: complete this method
        // we can use a similar approach that we used in the design doc for the query
        // if the transition is already there, it will just perform an update
        // if all goes well, should return 0
        return 0;
    }

    /**
     * updateTransition()
     * @param urlA - the starting node's URL
     * @param urlB - the URL for the node to transition to
     * @param tinfo - any information needed to update
     * @return - 0 if all goes smoothly, -1 if there is an issue
     */
    public int updateTransition(String urlA, String urlB, String tinfo) {
        if (!findTransition(urlA, urlB)) {
            createTransition(urlA, urlB, tinfo);
        }
        // TODO: complete this method
        // not sure what query we would use here, but it would
        // probably be fairly similar to createTransition()
        // returns 0 if no errors
        return 0;
    }

    /**
     * retrieveSession()
     * Returns a subgraph based on the given filter category and content.
     * @param filter_category - can be 'i' (user id), 'u' (page url), 'd' (date), 't' (time), 'p' (popularity)
     * @param filter_content - the value that will be used for filtering
     * @return - an ArrayList of sessions with an ArrayList of strings that comprise each page and transition
     *           or null if no results were found.
     */
    public ArrayList<ArrayList<String>> retrieveSession(String filter_category, String filter_content) {
        return null;
        // TODO: complete this method
        // we would have to first take the arguments given and determine if they are valid
        // what that means is the following:
        //     a. filter_category is an 'i', 'u', 'd', 't', or a 'p'
        //     b. filter_content is a string type for 'u', 'd', 't', and int for 'p' and 'i'
        // if the arguments are valid, we then need to find the sessions that the matching nodes belong to
        // we need to convert each node and edge's data to a string and create an list of strings per session
        // if nothing is found, just return null.
    }

}
