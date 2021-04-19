# NoSQL-WUM-FP
Based on the findings of "A NoSQL approach to Web Usage Mining of Frequent Patterns" NoSQL-WUM-FP is a Java API that allows the organization and analaysis of webpage navigation data into sessions, which essentially represent pages commonly visited together in sequence during a limited time period. The underlying NoSQL database used for this is Neo4j, which enables storing data and relations graphically.


## Constructor Summary
### WUMGrapher(String uri, String user, String password)
Allows the establishment of a connection to the Neo4j database, given a valid uri, username, and password are provided.


## All Methods
### void close()
Closes the driver connection.
### int createPageNode(String url)
Creates a new node in the database with the given URL if a node with that URL does not already exist. Returns 0 if the node is created successfully, 1 if the node already exists in the database, and -1 if the node does not already exist but an error occured duirng creation.
### int createTransition(String urlA, String urlB, String tinfo)
Creates an edge between two nodes with the given URLS and labels the edge with the given information.  Returns 0 if the edge is created successfully and -1 if an error occurs.
### boolean findPageNode(String url)
Looks to see if a webpage node with the given URL exists in the database. Returns true if one exists and false if there is no such node.
### boolean findTransition(String urlA, String urlB)
Looks to see if a trasition between nodes with the given URLS exists in the database. Returns true if one exists and false if there is no such edge.
### ArrayList<ArrayList<String>> retrieveSession(String filter_category, String filter_content)
Returns an ArrayList of session ArrayLists. Each session ArrayList is an ArrayList of strings that contain information about the nodes and edges. filter_category must be 'i' (user ID), 'u' (page URL), 'd' (date), 't' (time), or 'p' (populariy, meaning the number of users who engaged in the session compared to other sessions). filter_content is a string value used for filtering.
### int updateTransition(String urlA, String urlB, String tinfo)
Alters the label information for an edge between two nodes. If the edge does not exist already between two nodes, creation of the edge will be attempted. Returns 0 if the edge is updated successfully and -1 if an error occurs.
