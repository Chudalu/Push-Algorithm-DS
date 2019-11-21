package application;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * This is the Network class, and it handles the sending of messages.
 * the sending of message is directly called from the gossip algorithm in Nodes class.
 * @author Chudalu Ezenwafor
 *@version 2018.3.26
 */

public class Network {

	//HashMap holding seperate thread timers for the message sent by the nodes
	private HashMap<String, MessageScheduler> Assignnetworkdelay;
	
	
	String MESSAGE;
    String[] nodeArray; 
    int iter;
    int i;
    int size;
    
   
    public Network() {
    	Assignnetworkdelay = new HashMap<>();
    	this.iter = 0;
    	this.i = 0;
    	this.size = Push.nodeNumber;
    	nodeArray = new String [size];
    	Arrays.fill(nodeArray, "null");
    	MESSAGE = "19966";
    }
    //reset array containing nodes with message
    public void resetMessage() {
    	Arrays.fill(nodeArray, "null");
    	i = 0;
    }
    
    
	//this sets the nodes to have message
    //restricting any node that has previously received message
    //to allow for easier simulation and operation.
	public void setMessage(String node) {
		boolean status = false;
		//don't send message again, if node has message already
		for(int a=0; a<nodeArray.length; a++) {
			if(nodeArray[a].equals(node)) {
				status = true;
			}
		}
		//if not, set node to have message
		if(!(status == true)) {
			nodeArray[i] = node;
			i++;
		}
		//apart from the starting node, set the other nodes to have delayed messaged delivery
		if (!(status == true) && (i>=1)) {
				int rand = new Random().nextInt(200);
				//creates new delay for each message.
				Assignnetworkdelay.put(node, new MessageScheduler(node, rand));
			
		}
		
	}
	
	
	
}
