//Travis Maupin
//Hope Rangel
package chat_client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

//receiving thread
public class HostThread extends Thread {

	
	ServerSocket server;
	DataInputStream in;
	DataOutputStream out;
	
	//controls runtime
	AtomicBoolean running = new AtomicBoolean(false);
	
	HostThread(int port) throws Exception
	{
		out = Main.out;
		server = new ServerSocket(port);
	}
	
	@Override
	public void run() {
		// run until thread is killed
		running.set(true);
		while(running.get())
		{
			try {
				recieve();
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
		
	}
	
	
	public void recieve() throws Exception
	{
		if(!server.isClosed())
		{
			Socket socket = server.accept();
			in = new DataInputStream(socket.getInputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		
			String line;
			// print every line received and disconnect upon receiving "DISCONNECT"
			while(running.get())
			{
				line = reader.readLine();
			
				if(line != null)
				{
					System.out.println(line);
					if(line.equals("DISCONNECT"))
					{

						out.write((line + "\n").getBytes());
						Main.logout();
					}
				}
				
			}
		}
	}
	
	public void logout() throws Exception
	{
		//disconnect
		running.set(false);
		server.close();
	}
}
