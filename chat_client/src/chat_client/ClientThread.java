package chat_client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientThread extends Thread{

	
	Socket socket;
	DataOutputStream out, server;
	
	//controls runtime
	AtomicBoolean running = new AtomicBoolean(false);
	
	ClientThread(String ip, int port) throws Exception
	{
		Thread.sleep(1000);
		System.out.println("here 1");
		System.out.println(ip);
		System.out.println(port);
		socket = new Socket(ip, port);
		System.out.println("here 2");
		out = new DataOutputStream(socket.getOutputStream());
		System.out.println("here 3");
		server = Main.out;
		System.out.println("here 4");
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
		String line;
		Scanner scan = new Scanner(System.in);
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

		running.set(false);
		socket.close();
		System.out.println("enter any input line to finish closing");
	}
}
