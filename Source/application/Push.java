package application;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import application.Nodes;
/**
 * This is the Push class containing the Main
 * this class handles the reading of .txt files and the writing to
 * .txt files. 
 * The number arguments from the user is used to define the type of operation conducted.
 * this operations are then defined by q1, q2 and q3 first input arguments.
 * the nodes and its connection to other nodes are derived from the .txt file
 * and creates the nodes the nodes in the Nodes class.
 * all the nodes connected to each node are placed in an array and mapped to their
 * consecutive nodes using a HashMap.
 * Methods from other classes are called from here.
 * @author Chudalu Ezenwafor
 * @version 2018.03.26
 */


public class Push {

	//simple message to be passed around the network
	static String MESSAGE = "19966";
	static Nodes nodeObj;
	static int nodeNumber;
	static int connectionNumber;
	static Network network;
	static String[] nodeArray;
	//this Arraylist holds the nodes which has received the message
	//in an order of which received it first
	static ArrayList<String>outputNodes = new ArrayList<String>();
	static GraphSim graphs = new GraphSim();
	static int iterr = 0;
	
	
	//this method is called after the network delay 
	//which is in the MessageScheduler class and sets the nodes to have message
	public void setOutput(String node) {
		outputNodes.add(node);
		graphs.graphSetnode(node);
		
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String[] connectNode;
		String addedNode;
		int numberofnodes = 0;
		String[] arrangedNodes;
		String outputFile = "output.txt";
		
		//Graph simulation
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		
		if(args.length == 0) {
			System.out.println("File name not specified, please input file name and start Node.");
			System.exit(1);
			
		}
		
		
		try {
			int argument = args.length;
			
			if (argument == 3) {
				String arg = args[0];
				//run code for question 1
				if (arg.equals("q1")) {
					
					String filename = args[1];
					String startingnode = args[2];
					//get current path from system
					String directory = System.getProperty("user.dir");
					Path currentDir = Paths.get(directory);
				    Path parentDir = currentDir.getParent();
					String dir = parentDir.toString();
					//set path to the input directory
					String actualDir = dir + File.separator + "input" +  File.separator + filename;
					
					//read file for the first time and count the number of new lines
					//which is the number of nodes
					File filer = new File(actualDir);
					System.out.println("filename = "+ filename +" and starting node ="+ startingnode+" ");
					FileReader file2read = new FileReader(filer);
					BufferedReader br = new BufferedReader(file2read);
					String read;
					while ((read = br.readLine()) != null) {
						numberofnodes++;
					}
					nodeNumber = numberofnodes;
					
					File file = new File(actualDir);
					connectNode = null;
					nodeObj = new Nodes();
					
					//read file again and add the nodes and it corresponding connected nodes
					FileReader fileread = new FileReader(file);
					BufferedReader reader = new BufferedReader(fileread);
					
					String rd;
					while ((rd = reader.readLine()) != null) {
						String[] values = rd.split(":");
						addedNode = values[0];
						connectNode = values[1].split(",");
						nodeObj.Nodemap(addedNode, connectNode);
						
							
					}
					//set starting node with message
					nodeObj.startnode = startingnode;
					nodeObj.StartNode(startingnode, MESSAGE);
					//Start Gossip algorithm from Nodes class
					nodeObj.Gossipalgorithm();
					
					//array to hold nodes that would be written to output.txt file
					arrangedNodes = new String[numberofnodes];
					int aa = 0;
					for (String node : outputNodes) {
						System.out.println("nodes in outputnode array==" +node);
						arrangedNodes[aa] = node;
						aa++;
					}
					//write file into the directory specified by the writeFile variable
					String writeFile = dir + File.separator + "log" +  File.separator + outputFile;
					File filetoWrite = new File(writeFile);
					FileWriter filewriter = new FileWriter(filetoWrite);
					BufferedWriter bufferedwriter = new BufferedWriter(filewriter);
					
					for (int i=0; i < aa; i++) {
						
						bufferedwriter.write(arrangedNodes[i]);
						bufferedwriter.newLine();
					}
					bufferedwriter.close();
					System.out.println("program has ended.... THANK YOU!!!!");
				}
				//run code for question 2
				if (arg.equals("q2")) {
					String filename = args[1];
					String startingnode = args[2];
					
					String directory = System.getProperty("user.dir");
					Path currentDir = Paths.get(directory);
				    Path parentDir = currentDir.getParent();
					String dir = parentDir.toString();
					String actualDir = dir + File.separator + "input" +  File.separator + filename;
					
					File filer = new File(actualDir);
					
					System.out.println("filename = '"+ filename +"' and starting node '"+ startingnode+"' ");
					
					FileReader file2read = new FileReader(filer);
					BufferedReader br = new BufferedReader(file2read);
					String read;
					while ((read = br.readLine()) != null) {
						numberofnodes++;
					}
					nodeNumber = numberofnodes;
					
					File file = new File(actualDir);
					connectNode = null;
					nodeObj = new Nodes();
					
					FileReader fileread = new FileReader(file);
					BufferedReader reader = new BufferedReader(fileread);
					
					//read file, and create the nodes and corresponding connected nodes
					//read file, and create the edges.
					String rd;
					while ((rd = reader.readLine()) != null) {
						String[] values = rd.split(":");
						addedNode = values[0];
						connectNode = values[1].split(",");
						nodeObj.Nodemap(addedNode, connectNode);
						graphs.AddEdges(addedNode, connectNode);
					}
					graphs.displayGraph();
					nodeObj.startnode = startingnode;
					nodeObj.StartNode(startingnode, MESSAGE);
					graphs.graphSetnode(startingnode);
					nodeObj.Gossipalgorithm();
					System.out.println("program has ended.... THANK YOU!!!!");
					
				}
				//run code for question 3
				if (arg.equals("q3")) {
					
					String filename = args[1];
					String startingnode = args[2];
					
					String directory = System.getProperty("user.dir");
					Path currentDir = Paths.get(directory);
				    Path parentDir = currentDir.getParent();
					String dir = parentDir.toString();
					String actualDir = dir + File.separator + "input" +  File.separator + filename;
					
					File filer = new File(actualDir);
					
					System.out.println("filename = "+ filename +" and starting node "+ startingnode+" ");
					
					FileReader file2read = new FileReader(filer);
					BufferedReader br = new BufferedReader(file2read);
					String read;
					while ((read = br.readLine()) != null) {
						numberofnodes++;
					}
					nodeNumber = numberofnodes;
					
					File file = new File(actualDir);
					connectNode = null;
					nodeObj = new Nodes();
					
					FileReader fileread = new FileReader(file);
					BufferedReader reader = new BufferedReader(fileread);
				
					String rd;
					while ((rd = reader.readLine()) != null) {
						String[] values = rd.split(":");
						addedNode = values[0];
						connectNode = values[1].split(",");
						nodeObj.Nodemap(addedNode, connectNode);
					}
					nodeObj.startnode = startingnode;
					nodeObj.StartNode(startingnode, MESSAGE);
					nodeObj.GossipalgorithmwithP();
					System.out.println("program has ended.... THANK YOU!!!!");
				}
			}
			
	
		}
		
		catch (IOException ioexception) {
			System.err.println("Cannot open file.");
			System.exit(1);
		}
		
		
	}
	

}
