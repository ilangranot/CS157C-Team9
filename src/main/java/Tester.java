/**
 * A NoSQL Approach to Web Usage Mining of Frequent Patterns
 * Authors: Ilan Granot, Nick Kim, Sebrianne Ferguson, Mike Wu
 * Copyright © 2021 Granot et. al.
 *
 * This file is subject to the terms and conditions defined in
 * the file 'LICENSE.txt', which is part of the source code package.
 *
 * Tester.java
 * Purpose: test the functionality of the API for Web Usage Mining of Frequent Patterns
 */

public class Tester {

    public static void main(String[] args) {
        try (WUMGrapher w = new WUMGrapher( "bolt://localhost:7687", "neo4j", "1234" ) )
        {
            // create node with google.com as URL
            int status = w.createPageNode("google.com");
            if (status == 0) System.out.println("node created");
            else if (status == 1) System.out.println("Node exists already");
            else System.out.println("Error creating the node.");

            // find node with google.com as URL
            if (w.findPageNode("google.com")) System.out.println("node found");
            else System.out.println("node not found");

            // find node with amazon.com as URL
            if (w.findPageNode("amazon.com")) System.out.println("node found");
            else System.out.println("node not found");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
