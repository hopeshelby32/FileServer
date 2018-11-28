package chat_server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class thread extends Thread{

	Socket socket;
	DataInputStream in;
	BufferedReader get;
	DataOutputStream out;
	AtomicBoolean running;
	File active, account;
	String user;
	HashMap match;
	
	thread(Socket socket, DataInputStream in, DataOutputStream out)
	{
		this.socket = socket;
		this.in= in;
		this.out = out;
		active = new File("active.txt");
		account = new File("account.txt");
		Main.count++;
		match = new HashMap();
	}
	
	public void run()
	{
		//run until boolean is set to false
				running.set(true);
				while(running.get())
				{
					try {
						menu();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
	}
	
	public void menu() throws Exception
	{
		//output prompt to client
				String answer = "0";
				
				
				String message = "Please enter an option: \n"
						+ "1. New User\n2. Existing User\n3. Disconnect \n\r\n";
				String error = "Please input a valid option:\n"
						+ "1. New User\n2. Existing User\n3. Disconnect \n\r\n";
				
				out.write(message.getBytes());
				
				
				boolean valid = false;
				
				get = new BufferedReader(new InputStreamReader(in));
				

				// verify input is valid
				while(valid == false)
				{
					answer = get.readLine();
					System.out.println(answer);
					if(answer.equals("1"))
						valid = true;
					else if(answer.equals("2"))
						valid = true;
					else if (answer.equals("3"))
						valid = true;
					else
					{
						valid = false;
						out.write(error.getBytes());
					}
				}
				
				// calls appropriate function
				if(answer.equals("1"))
				{
					NewUser();
				}
				else if(answer.equals("2"))
				{
					ExtUser();
				}
				else
				{
					logout();
				}
	}
	public void login() throws Exception
	{
		//output prompt to client
				String answer = "0";
				
				
				String message = "Please enter an option: \n"
						+ "1. New User\n2. Existing User\n3. Disconnect \n\r\n";
				String error = "Please input a valid option:\n"
						+ "1. New User\n2. Existing User\n3. Disconnect \n\r\n";
				
				out.write(message.getBytes());
				
				
				boolean valid = false;
				
				get = new BufferedReader(new InputStreamReader(in));
				

				// verify input is valid
				while(valid == false)
				{
					answer = get.readLine();
					System.out.println(answer);
					if(answer.equals("1"))
						valid = true;
					else if(answer.equals("2"))
						valid = true;
					else if (answer.equals("3"))
						valid = true;
					else
					{
						valid = false;
						out.write(error.getBytes());
					}
				}
				
				// calls appropriate function
				if(answer.equals("1"))
				{
					NewUser();
				}
				else if(answer.equals("2"))
				{
					ExtUser();
				}
				else
				{
					logout();
				}
	}
	public void NewUser() throws Exception
	{

		//prepare to edit file
		FileWriter fstream = new FileWriter(account, true);
		
		BufferedWriter write = new BufferedWriter(fstream);
		
		
		Scanner read;
		
		boolean valid = false;
		//output message
		String message = "You've selcted \"New Member,\"\n "
				+ "Please input your ID & password separated by a \"*\" \n"
				+ "ex: user*123 \r\n";
		String error = "Invalid Input\n"
				+ "Please input your ID & password separated by a \"*\" \n"
				+ "ex: user*123 \r\n";
		
		out.write(message.getBytes());
		Scanner parse;
		String id = null, pass = null, aid, temp;
		String err = null;
		String f = new String();
		read = new Scanner(account);
		//store account.txt in string
		while(read.hasNextLine())
		{
			f = f.concat(read.nextLine());
		}
		//verify account name is valid
		while(!valid)
		{
			read = new Scanner(account);
			String input = get.readLine();
			
			
			parse = new Scanner(input).useDelimiter("[*]");
			
			
			id = parse.next();
			
			System.out.println(id);
			System.out.println(input);
			pass = parse.next();
			
			
			valid = true;
			while(read.hasNext())
			{
				aid = read.next();
				temp = read.nextLine();
				if(id.equals(aid))
				{
					valid = false;
					err = "ID already taken!";
				}
			}
			//verify password is valid
			if(pass.isEmpty())
			{
				valid = false;
				err = "Password is invalid!";
			}
			
			
			if(!valid)
			{
				if(err.isEmpty())
					out.write(error.getBytes());
				else
					out.write((error + "\n" + err).getBytes());
			}
			
		}
		
		//write new account to account.txt
		write.write(id + " " + pass + "\n");
		
		write.flush();
		write.close();
		FileWriter afstream = new FileWriter(active, true);
		BufferedWriter awrite = new BufferedWriter(fstream);
		awrite.append(id + " " + socket.getRemoteSocketAddress().toString());
		
		awrite.flush();
		awrite.close();
		boolean success = false;
		//originally full path was used
		//success = (new File("C:/Users/maupi/eclipse-workspace/file-server/" + id)).mkdir();
		
		//create folder for new user
		success = (new File(id)).mkdir();
		
		if(!success)
			System.out.println("file fail");
		
		
		user = id;
		Main.map.put(user, socket);
		display();
	}
	public void ExtUser() throws Exception
	{
Scanner read;
		
		boolean valid = false;
		
		//display login message
		String message = "Log in\n "
				+ "Please input your ID & password separated by a \"*\" \n"
				+ "ex: user*123 \r\n";
		String error = "Invalid Input\n"
				+ "Please input your ID & password separated by a \"*\" \n"
				+ "ex: user*123 \n"
				+ "enter \"EXIT!\" to disconnect \r\n";
		
		String line = null, cred = null, id = null, pass = null, aid = null, apass = null;
		Scanner parse, check;
		
		out.write(message.getBytes());
		int failcount = 0;
		String incorrect = "Incorrect Username or Password!\n"
				+ "Please try Again\n"
				+ "Please input your ID & password separated by a \"*\" \n"
				+ "ex: user*123 \n"
				+ "enter \"EXIT!\" to disconnect \r\n";
		
		while(valid == false)
		{
			read = new Scanner(account);
			//three strikes, you're out
			if(failcount>=3)
			{
				String derror = "Too many tries\n"
						+ "Disconnecting \r\n";
				out.write(derror.getBytes());
				logout();
			}
			//parse input and compare with info in account.txt
			cred = get.readLine();
			parse = new Scanner(cred).useDelimiter("[*]");
			id = parse.next();
			pass = parse.next();
			if(id.equals("EXIT!"))
				logout();
			if(id.isEmpty() || pass.isEmpty())
				valid = false;
			else
			{
				while(read.hasNextLine())
				{
					line = read.nextLine();
					check = new Scanner(line);
				
					aid = check.next();
					apass = check.next();
				
					if(id.equals(aid) && pass.equals(apass))
					{
						valid = true;
						user = id;
						FileWriter fstream = new FileWriter(active, true);
						BufferedWriter write = new BufferedWriter(fstream);
						write.append(id + " " + socket.getRemoteSocketAddress().toString());
						write.flush();
						write.close();

						Main.map.put(user, socket);
						
						display();
						break;
					}
					else
						valid = false;
				}
			}
			failcount++;
			out.write(incorrect.getBytes());
		}
	}
	public void display() throws Exception
	{
		int answer = 0;
		
		Scanner scan = new Scanner(active);
		
		String prompt = "Please enter the username of who you want to connect to \n";
		String dis =  "Currently Active Users: \n";
		String users = new String();
		int c = 0;
		boolean activeuser = true;
		while(scan.hasNextLine())
		{
			String line = scan.nextLine();
			Scanner parse = new Scanner(line);
			String user = parse.next();
			String IP = parse.next();
			match.put(user, IP);
			users = users.concat(user + "/n");
			
			c++;
			
		}
		if(c < 2)
		{
			users = "No Users Found";
			activeuser = false;
		}
		String message = dis + users + "\n\r\n";
		out.write(message.getBytes());
		String t = new String();
		

		get = new BufferedReader(new InputStreamReader(in));
		
		if(activeuser)
		{
			String query = "If you would like to connect with one of the above listed, \nEnter 1"
					+ "If you would like to wait for another user to connect, \nEnter 2";
			t = get.readLine();
			boolean valid = false;
			while(!valid)
			{
				if(t.equals("1"))
				{
					out.write((prompt + "\n\r\n").getBytes());
					valid = true;
				}
				else if (t.equals("2"))
				{
					chill();
					valid = true;
				}
			}
		}
		else
		{
			String query = "No other users are currently active. \n Enter 1 to wait for another user to connect\n"
					+ "Enter 2 to disconnect";
			t = get.readLine();
			boolean valid = false;
			while(!valid)
			{
				if(t.equals("1"))
				{
					chill();
					valid = true;
				}
				else if (t.equals("2"))
				{
					logout();
					valid = true;
				}
			}
		}
		
		Scanner parse = new Scanner(users);
		boolean valid = false;
		while(!valid)
		{
			String response = get.readLine();
			while(parse.hasNextLine())
			{
				if (response.equals(parse.nextLine()))
				{
					valid = true;
					connect(response);
				}
			}
			if(!valid)
			{
				String error = "reponse not valid \n"+
						"enter an active user\n"+
						users + "\n\r\n";
			}
		}
		
	}
	public void connect(String to) throws Exception
	{
		Socket socket2;
		Scanner scan = new Scanner(active);
		boolean found = false;
		String ip;
		while(scan.hasNextLine() && !found)
		{
			String line = scan.nextLine();
			Scanner parse = new Scanner(line);
			
			if(parse.next().equals(to))
			{
				found = true;
				ip = match.get(to).toString();
				socket2 = (Socket) Main.map.get(to);
			}
		}
		if(!found)
		{
			out.write("ERROR 404 USER NOT FOUND\n\r\n".getBytes());
			logout();
		}
		
		
		
		
	}
	public void chill()
	{
		
	}
	public void logout()
	{
		System.out.println("disconnecting");
		running.set(false);
		try {
			socket.close();
			Main.count--;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
}