package application;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * This is the Nodes class containing the Gossip algorithm 
 * there are 2 Gossip algorithm methods
 * one for normal operation (q1, q2) and the other for operation with probability (q3).
 * The nodes are created and stored in the Nodeswithconnect Hashmap along with their
 * mapped nodes.
 * an optional Hashmap is used to set Messaged nodes to have message
 * @author Chudalu Ezenwafor
 *@version 2018.3.26
 */

public class Nodes {
	private HashMap<String, String[]> Nodeswithconnect;
	private HashMap<String, String> Nodeandnetwork;
    Network networkk;
    String MESSAGE, startnode;
    String[] messagednodes;
    int numofnodewithmessage;
    int iter;
    int iterate;
    double probability = 0.05;
    long Startime, Endtime, outputer;
    ScatterPlot plot;
    static double[][] dataforPlot = new double [2][20];
    
    
    public Nodes() {
    	 Nodeswithconnect = new HashMap<>();
    	 Nodeandnetwork = new HashMap<>();
    	 networkk = new Network();
    	 MESSAGE = "19966";
    	 startnode = null;
    	 messagednodes = new String[Push.nodeNumber];
    	 Arrays.fill(messagednodes, "null");
    	 this.iterate = 0;
    	 this.iter = 0;
    }
    
    //creates new nodes and corresponding connections when called
	public void Nodemap (String addedNode, String[] connectNode) {
		Nodeswithconnect.put(addedNode, connectNode);
	}
	//sets nodes to have message when called
	private void NodeandMessage (String nodes, String Message) {
		Nodeandnetwork.put(nodes, Message);
	}
	
	
	//gives the number of connections (edges) in the network
	public int numofConnections() {
		int numberofconnection = 0;
		String node;
		for (String key : Nodeswithconnect.keySet()) {
			for (int i = 0; i < Nodeswithconnect.get(key).length; i++) {
				node = Nodeswithconnect.get(key)[i];
				if (!node.equals("null")) {
					numberofconnection++;
				}
			}
		}
		return numberofconnection;
	}
	
	
	//gives the number of nodes in the network
	public int Sizeofnetwork() {
		return Nodeswithconnect.size();
	}
	
	
	//sets the start node
	public void StartNode (String addedNode, String message) {
		
		String node = addedNode;
		NodeandMessage(node, message);
		networkk.resetMessage();
		networkk.setMessage(node);
		setNodetohaveMessage(node);
	}
	
	public void setNodetohaveMessage(String node) {
		NodeandMessage(node, MESSAGE);
	}
	
	
	//Gossip algorithm method, disseminates the messages
	public void Gossipalgorithm() {
		int T = numofConnections();
		int t = 0;
		int rounds = 0;
		int size = Sizeofnetwork();
		int connections;
		String[] nodeswithMessage, nodetorecieve;
		nodeswithMessage = new String [size];
		
		Arrays.fill(nodeswithMessage, "null");
		
		//while the number of nodes with message is not equal to the number of nodes
		while ((rounds != size)) {
			connections = 0;
			numofnodewithmessage = 0;
			int ab = 0;
			//update on which nodes has recieved the message
			Arrays.fill(nodeswithMessage, "null");
			for (String nodes : Push.outputNodes) {
				nodeswithMessage[ab] = nodes;
				ab++;
			}
			//how many nodes has recieved message
			for (int l=0; l<nodeswithMessage.length; l++) {
				if(!nodeswithMessage[l].equals("null")) {
					numofnodewithmessage++;
				}
			}
			//set array to host nodes with message
			nodetorecieve = new String[numofnodewithmessage];
			int flip = 0;
			for (int a = 0; a < nodeswithMessage.length; a++) {
				if (!nodeswithMessage[a].equals("null")) {
					nodetorecieve[flip] = nodeswithMessage[a];
					flip++;
				}
			}
			//for all the nodes with message, randomly choose a node in its connected nodes mapping, specified in the 
			//Nodeswithconnect Hashmap to send the message to
			for (int i = 0; i < nodetorecieve.length; i++) {
				connections = 0;
				for (int p=0; p < (Nodeswithconnect.get(nodetorecieve[i])).length; p++) {
					connections++;
				}
				int rand = new Random().nextInt(connections);
				networkk.setMessage(Nodeswithconnect.get(nodetorecieve[i])[rand]);
				
			}
			//update the number of nodes with message.
			rounds = numofnodewithmessage;
			//delay for some interval
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	//Gossip algorithm method with Probability
	public void GossipalgorithmwithP() {
		Startime = System.nanoTime();
		int T = numofConnections();
		int t = 0;
		int rounds = 0;
		int iterr = 0;
		int size = Sizeofnetwork();
		int connections;
		double value = 0;
		String[] nodeswithMessage, nodetorecieve;
		nodeswithMessage = new String [size];
		Arrays.fill(nodeswithMessage, "null");
		
		//while the probability is less the 1
		while (probability <= 1) {
			//while the number of nodes with message is not equal to the number of nodes
			while ((rounds != size)) {
				connections = 0;
				numofnodewithmessage = 0;
				int ab = 0;
				//update on which nodes has recieved the message
				Arrays.fill(nodeswithMessage, "null");
				for (String nodes : Push.outputNodes) {
					nodeswithMessage[ab] = nodes;
					ab++;
				}
				
				//how many nodes has recieved message
				for (int l=0; l<nodeswithMessage.length; l++) {
					if(!nodeswithMessage[l].equals("null")) {
						numofnodewithmessage++;
					}
				}
				//set array to host nodes with message
				nodetorecieve = new String[numofnodewithmessage];
				int flip = 0;
				for (int a = 0; a < nodeswithMessage.length; a++) {
					if (!nodeswithMessage[a].equals("null")) {
						nodetorecieve[flip] = nodeswithMessage[a];
						flip++;
					}
				}
				//for all the nodes with message, randomly choose a node in its connected nodes mapping, specified in the 
				//Nodeswithconnect Hashmap to send the message to
				for (int i = 0; i < nodetorecieve.length; i++) {
					connections = 0;
					for (int p=0; p < (Nodeswithconnect.get(nodetorecieve[i])).length; p++) {
							String validnode = Nodeswithconnect.get(nodetorecieve[i])[p];
							if(!validnode.equals("null")) {
								connections++;
						     }
					}
					//for the specified probability
					//if the random value out of 100, chosen is not greater than the probabilty, dont send
					//until true, send message
					double probableValue = 100 * probability;
					int probablesending = (int) Math.round(probableValue);
					int send = new Random().nextInt(100);
					if(send > probablesending) {
						int rand = new Random().nextInt(connections);
						networkk.setMessage(Nodeswithconnect.get(nodetorecieve[i])[rand]);
					}
					
				}
				//wait for some interval
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//update the number of nodes with message.
				rounds = numofnodewithmessage;
			}
			//for the specified probability, print out the node ID in the order in which the nodes received message
			System.out.println("For Probability of: " +probability);
			for(String nodes : Push.outputNodes) {
				System.out.println(nodes);
			}
			//clear the outoutNode arrayList and start-over
			Push.outputNodes.clear();
			Endtime = System.nanoTime();
			outputer = Endtime - Startime;
			double time = outputer/1000000;
			//get the time each process ended and its probability
			//add it to array data for scatter plot
			dataforPlot [0][iterr] = probability;
			dataforPlot [1][iterr] = time;
			rounds = 0;
			probability = probability + 0.05;
			//set start node again to have message
			StartNode(startnode, MESSAGE);
			
			iterr++;
		}
		//when the algorithm has finished, plot scatter-plot
		plot = new ScatterPlot(dataforPlot);
	}
	
	
		
}

