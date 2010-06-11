/**
 * 
 */
package network;

import eventmanager.SendMessageListener;
import eventmanager.StopListener;
import messages.*;
import java.lang.Thread;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.lang.Exception;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Queue;


/**
 * @author clemens
 * 
 * History
 * 		11.6.2010 -cm	-Init Version
 *
 */
public class OutgoingTraffic extends Thread implements SendMessageListener, StopListener {
	
	private Queue<Message> highPriority;
	private Queue<Message> lowPriority;
	private Socket socket;
	private ObjectOutputStream out;
	
	private boolean stop;
	
	/**
	 * @param s
	 */
	public OutgoingTraffic(Socket s)
	{
		this.socket = s;
		
		this.highPriority = new ConcurrentLinkedQueue<Message>();
		this.lowPriority = new ConcurrentLinkedQueue<Message>();
		
		try
		{
			this.out = new ObjectOutputStream(this.socket.getOutputStream());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		this.stop  = false;
	}
	
	/**
	 * 
	 */
	public void run()
	{
		while (!stop)
		{
			if (this.highPriority.size() > 0)
			{
				try
				{
					out.writeObject( this.highPriority.poll() );
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * @param message Die zu versendende Nachricht
	 */
	public void sendMessage(Message message)
	{
		this.highPriority.add(message);
	}
	
	public void stopExecution()
	{
		this.stop = true;
	}

}
