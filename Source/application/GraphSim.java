package application;
import java.util.HashMap;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

/**
 * This is the GraphSim class, and it handles the generation of a simulated graph network
 * visualizing the process of the gossip algorithm working on the given network.
 * @author Chudalu Ezenwafor
 *@version 2018.3.26
 */

public class GraphSim {
	
	Node[] nodes;
	Edge[] edges;
	Graph graph;
	int edgeiter;
	//the HashMap created to host all the edges.
	HashMap<String, Edge>Graphedges;
	int size;
	public GraphSim() {
		Graphedges = new HashMap<>();
	    graph = new SingleGraph("Push Protocol");
		graph.addAttribute("ui.antialias");
		graph.addAttribute("ui.stylesheet", styleSheet);
		graph.setStrict(false);
        graph.setAutoCreate(true);
        edgeiter = 0;
	}
	//display the graph simulation when called
	public void displayGraph() {
		graph.display();
	}

	//this function adds the edges to the Hashmaps
	//this edges are used to automatically generate the nodes and construct the graph network.
    public void AddEdges(String node, String[] edge) {
    	//the nodes and its corresponding connected nodes
	     String addedNode = node;
	    String[] addedEdges = edge;
	
	    //for every connections in-between nodes, create an edge and add it to the Hashmap.
	  for (int i=0; i<addedEdges.length; i++) {
	     	if(!addedEdges[i].equals("null")) {
	     		Graphedges.put( addedNode+addedEdges[i], graph.addEdge(addedNode+addedEdges[i], addedNode, addedEdges[i]));
		     }
	   }
	  //for every node derived from the edges, set the IDs to each node.
	   for (Node nodes : graph) {
	        nodes.addAttribute("ui.label", nodes.getId());
	    }
      }

    //if node is messaged, changed its class to marked. which changes the colour.
    public void graphSetnode(String node) {
    	for (Node nodes : graph) {
    		if (node.equals(nodes.getId())) {
    			nodes.setAttribute("ui.class", "marked");
    		}
	    }
	    
    }
	//set the normal node colour  and colour for marked nodes.
    protected static String styleSheet =
            "node {" +
            "	fill-color: red;" + 
            "}" +
            "node.marked {" +
            "	fill-color: green;" +
            "}";
     

}
