import java.util.ArrayList;

/**
 * A NoSQL Approach to Web Usage Mining of Frequent Patterns
 * Authors: Ilan Granot, Nick Kim, Sebrianne Ferguson, Mike Wu
 * Copyright © 2021 Granot et. al.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation to
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software.
 *
 * WUMGrapher.java
 * Purpose: provide an API for Web Usage Mining of Frequent Patterns
 */

public class WUMGrapher {

    /**
     * initialize the database
     */
    public WUMGrapher() {}

    /**
     * findPageNode()
     * Determines if the webpage exists as a node in the graph.
     * @param url - URL of page to find
     * @return - True/False depending on whether a node for page was found
     */
    public boolean findPageNode(String url) {
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
    }

    /**
     * createTransition()
     * Adds a transition between two page nodes if the transition does not already exist.
     * @param urlA - the starting node's URL
     * @param urlB - the URL for the node to transition to
     * @return - 0 if all goes smoothly, -1 if there is an issue.
     */
    public int createTransition(String urlA, String urlB) {
        return 0;
    }

    /**
     * updateTransaction()
     * @param urlA - the starting node's URL
     * @param urlB - the URL for the node to transition to
     * @param updateInfo - any information needed to update
     * @return - 0 if all goes smoothly, -1 if there is an issue
     */
    public int updateTransaction(String urlA, String urlB, String updateInfo) {
        return 0;
    }

    /**
     * retrieveSession()
     * Returns a subgraph based on the given filter category and content.
     * @param filter_category - can be 'i' (user id), 'u' (page url), 'd' (date), 't' (time), 'p' (popularity)
     * @param filter_content - the value that will be used for filtering
     * @return - an ArrayList of sessions with an ArrayList of strings that comprise each page and transition
     */
    public ArrayList<ArrayList<String>> retrieveSession(String filter_category, String filter_content) {
        return null;
    }

}
