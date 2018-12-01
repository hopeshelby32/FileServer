package chat_client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
	
	public static BufferedReader get;
	public static DataOutputStream out;
	static HostThread host;
	static ClientThread client;
	static Socket socket;
	
	public static void main(String[] args) throws Exception{

		boolean valid;
		Scanner scan = new Scanner(System.in);
		
		//get connection information
				System.out.println("Please enter the IP address of the host \n"
						+ "Please do not include port number yet \n"
						+ "ex: 127.0.0.1");
				String IP = scan.nextLine();
				System.out.println("Please enter Port number");
				int port = scan.nextInt();
				
				//establish socket
				socket = new Socket(IP, port);
				
				
				//establish input and output
				DataInputStream inp = new DataInputStream(socket.getInputStream());
				get = new BufferedReader(new InputStreamReader(inp));
				out = new DataOutputStream(socket.getOutputStream());
				
				String line = new String();
				
				
				while(!(line = get.readLine()).isEmpty())
				{
					System.out.println(line);
				}
				
				valid = false;
				
				int answer;
				while(!valid)
				{
					answer = scan.nextInt();
					String response;
					if(answer == 1)
					{
						valid = true;
						response = "1\r\n";
						out.write(response.getBytes());
						NewUser();
					}
					else if(answer == 2)
					{
						valid = true;
						response = "2\r\n";
						out.write(response.getBytes());
						ExtUser();
					}
					else if(answer == 3)
					{
						valid = true;
						response = "3\r\n";
						out.write(response.getBytes());
						logout();
					}
					else
					{
						while(!(line = get.readLine()).isEmpty())
						{
							System.out.println(line);
						}
					}
					
				}
				
				

	}
	
	//out.write("LOGGEDIN\n\r\n".getBytes());
	
	public static void NewUser() throws Exception
	{
		String line = new String();
		boolean valid;
		Scanner scan = new Scanner(System.in);
		
		
		while(!(line = get.readLine()).isEmpty())
		{
			System.out.println(line);
		}
		
		valid = false;
		String input = new String();
		
		while(!valid)
		{

			boolean format = false;
			
			input = scan.nextLine();
			
			Scanner parse = new Scanner(input).useDelimiter("[*]");
			
			while(!format)
			{
				String word;
				if(parse.hasNext())
					word = parse.next();
				if(parse.hasNext())
				{
					word = parse.next();
					format = true;
				}
				if(parse.hasNext())
					format = false;
				if(!format)
				{
					System.out.println("INCORRECT FORMAT! \n"
							+ "Please input username and password separated by a \"*\" \n"
							+ "Do not use any spaces");
				}
			}
			
			out.write((input + "\n\r\n").getBytes());
			
			while(!(line = get.readLine()).isEmpty())
			{
				if(line.equals("LOGGEDIN"))
				{
					valid = true;
				}
				System.out.println(line);
			}
			
		}
		Choose();
	}
	public static void ExtUser() throws Exception
	{
		boolean valid = false;
		String line;
		
		while(!(line = get.readLine()).isEmpty())
		{
			System.out.println(line);
		}
		
		String input;
		Scanner scan = new Scanner(System.in);
		while(!valid)
		{

			boolean format = false;
			
			input = scan.nextLine();
			
			Scanner parse = new Scanner(input).useDelimiter("[*]");
			
			while(!format)
			{
				String word;
				if(parse.hasNext())
					word = parse.next();
				if(parse.hasNext())
				{
					word = parse.next();
					format = true;
				}
				if(parse.hasNext())
					format = false;
				if(!format)
				{
					System.out.println("INCORRECT FORMAT! \n"
							+ "Please input username and password separated by a \"*\" \n"
							+ "Do not use any spaces");
				}
			}
			
			out.write((input + "\r\n").getBytes());
			
			while(!(line = get.readLine()).isEmpty())
			{
				if(line.equals("LOGGEDIN"))
				{
					valid = true;
				}
				System.out.println(line);
			}
			
		}
		Choose();
	}
	public static void Choose() throws Exception
	{
		String line;
		Scanner scan = new Scanner(System.in);
		String answer;
		boolean valid = false;
		while(!(line = get.readLine()).isEmpty())
		{
			System.out.println(line);
		}
		
		
		
		
		while(!valid)
		{
			while(!(line = get.readLine()).isEmpty())
			{
				System.out.println(line);
			}
			answer = scan.nextLine();
			
			if(answer.equals("1"))
			{
				Connect();
				valid = true;
			}
			else if(answer.equals("2"))
			{
				Connect();
				valid = true;
			}
			else if(answer.equals("3"))
			{
				logout();
				valid = true;
			}
		}
	}
	public static void Connect() throws Exception
	{
		String line;
		while(!(line = get.readLine()).isEmpty())
		{
			System.out.println(line);
		}
		
		String ip, temp;
		
		Scanner parse;
		
		Integer myport, port;
		
		temp = get.readLine();
		parse = new Scanner(temp);
		myport = parse.nextInt();
		parse.close();
		
		
		ip = get.readLine();
		
		
		temp = get.readLine();
		parse = new Scanner(temp);
		port = parse.nextInt();
		parse.close();
		
		host = new HostThread(myport);
		client = new ClientThread(ip, port);
		
		host.start();
		
		
	}
	public static void logout() throws Exception
	{
		host.logout();
		client.logout();
		socket.close();
	}

}
