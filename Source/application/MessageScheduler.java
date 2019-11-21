package application;

import java.util.Timer;
import java.util.TimerTask;

import application.MessageScheduler.ScheduleMessage;

/**
 * This is the MessageScheduler class, and it handles the setting of delays to the message sent
 * through the network.
 * @author Chudalu Ezenwafor
 *@version 2018.3.26
 */

public class MessageScheduler {
	
	Timer timer;
	Nodes node2 = new Nodes();
	String messagednode;
	
	public MessageScheduler(String node, int seconds ) {
		//set a timer schedule to set the message delay 
		//and call the run() function when the delay time has elapsed
		messagednode = node;
		timer = new Timer();
		timer.schedule(new ScheduleMessage(node), seconds + 900);
		
	}
	
	
	class ScheduleMessage extends TimerTask {
		String noder;
		Push push = new Push();
		ScheduleMessage(String node){
			
			noder = node;
		}
		//set the node to have message 
		public void run() {
			node2.setNodetohaveMessage(noder);
			push.setOutput(noder);
		}
		
	}

}
