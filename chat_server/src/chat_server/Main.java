package chat_server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

	public static int count = 0;
	public static HashMap map = new HashMap(),  match = new HashMap();
	
	public static void main(String args[]) throws IOException
	{
		File account = new File("account.txt");
		File active = new File("active.txt");
		//create file if doesnt exist
		if(!account.exists())
		{
			account.createNewFile();
		}
		if(!active.exists())
		{
			active.createNewFile();
		}
		else
		{
			active.delete();
			active.createNewFile();
		}
		//reads account.txt
		Scanner accRead = new Scanner(account);
		
		//Get Server IP
		int port = 9000;
		String IP = InetAddress.getLocalHost().getHostAddress() + ":" + port;
		
		//Set up Server
		ServerSocket server = new ServerSocket(port);
		
		
		
		//runs until stopped
		while(true)
		{
			//accepts and assigns server
			Socket socket = server.accept();
					
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			DataInputStream in = new DataInputStream(socket.getInputStream());
			
			if(count < 100)
			{
				new thread(socket, in, out).start();
			}
			else
			{
				out.write("BUSY\n\r\n".getBytes());
				socket.close();
			}
		}

		
	}
}
