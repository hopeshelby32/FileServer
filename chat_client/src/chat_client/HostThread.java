package chat_client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class HostThread extends Thread {

	
	ServerSocket server;
	DataInputStream in;
	
	//controls runtime
	AtomicBoolean running = new AtomicBoolean(false);
	
	HostThread(int port) throws Exception
	{
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
				e.printStackTrace();
			}
		}
		
	}
	
	
	public void recieve() throws Exception
	{
		Socket socket = server.accept();
		in = new DataInputStream(socket.getInputStream());
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		
		String line;
		while(running.get())
		{
			line = reader.readLine();
			System.out.println(line);
			
		}
	}
	
	public void logout() throws Exception
	{
		running.set(false);
		server.close();
	}
}
