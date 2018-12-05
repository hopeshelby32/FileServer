//Travis Maupin
//Hope Rangel
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
	static boolean coolio;
	
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
				
				// ask if new user, existing user, or if user would like to disconnect
				
				while(!(line = get.readLine()).isEmpty())
				{
					System.out.println(line);
				}
				
				valid = false;
				
				
				//get response and validate input
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
						logouts();
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
	
	
	public static void NewUser() throws Exception
	{
		String line = new String();
		boolean valid;
		Scanner scan = new Scanner(System.in);
		
		
		
		//display new user prompt
		while(!(line = get.readLine()).isEmpty())
		{
			System.out.println(line);
		}
		
		valid = false;
		String input = new String();
		
		
		//input validation
		while(!valid)
		{

			boolean format = false;
			
			
			
			while(!format)
			{
				input = scan.nextLine();
				
				Scanner parse = new Scanner(input).useDelimiter("[*]");
				
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
		//choose what new user wants to do
		Choose();
	}
	public static void ExtUser() throws Exception
	{
		boolean valid = false;
		String line;
		
		
		//display existing user prompt
		while(!(line = get.readLine()).isEmpty())
		{
			System.out.println(line);
		}
		
		String input = new String();
		Scanner scan = new Scanner(System.in);
		//validate input and log in user
		while(!valid)
		{

			boolean format = false;
			
			
			
			while(!format)
			{
				input = scan.nextLine();
				
				Scanner parse = new Scanner(input).useDelimiter("[*]");
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
		//let user choose what they want to do
		Choose();
	}
	public static void Choose() throws Exception
	{
		String line;
		Scanner scan = new Scanner(System.in);
		String answer;
		boolean valid = false;
		
		
		//output options for user
		while(!(line = get.readLine()).isEmpty())
		{
			System.out.println(line);
		}
		
		//input validation and option choosing
		while(!valid)
		{
			while(!(line = get.readLine()).isEmpty())
			{
				System.out.println(line);
			}
			answer = scan.nextLine();
			
			//connect to an active user
			if(answer.equals("1"))
			{
				out.write((answer + "\n").getBytes());
				//String name = scan.nextLine();
				coolio = false;
				Connect();
				valid = true;
			}
			//set yourself as active and wait for another user
			else if(answer.equals("2"))
			{
				out.write((answer + "\n").getBytes());
				coolio = true;
				Connect();
				valid = true;
			}
			//disconnect
			else if(answer.equals("3"))
			{
				out.write((answer + "\n").getBytes());
				logouts();
				valid = true;
			}
		}
	}
	public static void Connect() throws Exception
	{
		
		String line;
		//display connect to user prompt
		while(!(line = get.readLine()).isEmpty())
		{
			System.out.println(line);
		}
		
		
		String ip, temp;
		
		Scanner parse;
		
		Integer myport = 0, port = 0;
		
		
		Scanner scan = new Scanner(System.in);
		
		//make sure input is a valid, active user
		while(!coolio)
		{
			String name = scan.nextLine();
			out.write((name + "\n").getBytes());
			String t = get.readLine();
			if(t.equals("coolio"))
				coolio = true;
			else
			{
				System.out.println(t);
				while(!(line = get.readLine()).isEmpty())
				{
					System.out.println(line);
				}
			}
		}
		
		
		//get connection info
		
		
		//get port number that this user will be operating on
		temp = get.readLine();
		//temp = get.readLine();
		//parse = new Scanner(temp);
		//myport = parse.nextInt();
		//System.out.println(temp);
		myport = myport.parseInt(temp);
		//parse.close();
		
		//get IP address of other user
		String temp2 = get.readLine();
		
		
		//get port number other user is operating on
		temp = get.readLine();
		//parse = new Scanner(temp);
		//port = parse.nextInt();
		port = port.parseInt(temp);
		//parse.close();
		
		Scanner fix = new Scanner(temp2).skip("/").useDelimiter("[:]");
		
		ip = fix.next();
		
		//System.out.println(ip);
		
		host = new HostThread(myport);
		
		host.start();
		
		client = new ClientThread(ip, port);
		
		//connect start
		
		client.start();
		
		
	}
	public static void logout() throws Exception
	{
		//logout all processes after connection has been made
		host.logout();
		client.logout();
		socket.close();
	}
	public static void logouts() throws Exception
	{
		//simple logout if connection has not been made
		socket.close();
	}

}
