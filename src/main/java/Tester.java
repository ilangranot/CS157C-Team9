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
