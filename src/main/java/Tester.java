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
        try (WUMGrapher w = new WUMGrapher( "bolt://localhost:7687", "neo4j", "neo4j" ) )
        {
            // blah blah
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
