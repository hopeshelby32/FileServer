//Travis Maupin
//Hope Rangel
package chat_client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

// sending thread
public class ClientThread extends Thread{

	
	Socket socket;
	DataOutputStream out, server;
	
	//controls runtime
	AtomicBoolean running = new AtomicBoolean(false);
	
	ClientThread(String ip, int port) throws Exception
	{
		//gives other client host thread ample time to set up before attempting to connect
		Thread.sleep(1000);
		socket = new Socket(ip, port);
		out = new DataOutputStream(socket.getOutputStream());
		server = Main.out;
	}
	
	@Override
	public void run() {
		// run until thread is killed
		running.set(true);
		while(running.get())
		{
			try {
				send();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void send() throws Exception
	{
		//notifies about connection
		System.out.println("CONNECTED");
		System.out.println("To Disconnect:\n"
				+ "Enter: \"DISCONNECT\"");
		String line;
		Scanner scan = new Scanner(System.in);
		
		//sends every line to other client in p2p and to server so it can know progress
		//Disconnects and triggers disconnection on all other platforms upon sending "DISCONNECT"
		while(running.get())
		{
			line = scan.nextLine();
			out.write((line + "\n").getBytes());
			server.write((line + "\n").getBytes());
			
			if(line.equals("DISCONNECT"))
			{
				Main.logout();
			}
		}
	}
	
	public void logout() throws Exception
	{

		//kills all processes
		running.set(false);
		socket.close();
		//the way program is designed, the user who does not enter "DISCONNECT" will need to enter one more line
		//in order to finish the last loop of the input cycle
		System.out.println("you may need to enter any input line to finish closing");
	}
}
