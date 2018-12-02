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
				out.write((answer + "\n").getBytes());
				//String name = scan.nextLine();
				
				Connect();
				valid = true;
			}
			else if(answer.equals("2"))
			{
				out.write((answer + "\n").getBytes());
				Connect();
				valid = true;
			}
			else if(answer.equals("3"))
			{
				out.write((answer + "\n").getBytes());
				logout();
				valid = true;
			}
		}
	}
	public static void Connect() throws Exception
	{
		
		System.out.println("1");
		
		String line;
		while(!(line = get.readLine()).isEmpty())
		{
			System.out.println(line);
		}
		
		System.out.println("2");
		
		String ip, temp;
		
		Scanner parse;
		
		Integer myport = 0, port = 0;
		
		System.out.println("3");
		
		Scanner scan = new Scanner(System.in);
		boolean coolio = false;
		while(!coolio)
		{
			String name = scan.nextLine();
			out.write((name + "\n").getBytes());
			String t = get.readLine();
			if(t.equals("coolio"))
				coolio = true;
		}
		
		System.out.println("made it here");
		
		temp = get.readLine();
		//temp = get.readLine();
		//parse = new Scanner(temp);
		System.out.println("dough");
		//myport = parse.nextInt();
		System.out.println(temp);
		myport = myport.parseInt(temp);
		//parse.close();
		
		System.out.println("4");
		
		String temp2 = get.readLine();
		
		System.out.println("5");
		
		temp = get.readLine();
		//parse = new Scanner(temp);
		//port = parse.nextInt();
		port = port.parseInt(temp);
		//parse.close();
		
		Scanner fix = new Scanner(temp2).skip("/").useDelimiter("[:]");
		
		ip = fix.next();
		
		System.out.println(ip);
		
		System.out.println("6");
		host = new HostThread(myport);
		System.out.println("7");
		
		host.start();
		
		client = new ClientThread(ip, port);
		
		System.out.println("8");
		
		
		client.start();
		
		
	}
	public static void logout() throws Exception
	{
		host.logout();
		client.logout();
		socket.close();
	}

}
