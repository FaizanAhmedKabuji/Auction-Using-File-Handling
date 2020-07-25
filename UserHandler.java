import java.io.*;
import java.util.*;
import java.net.*;

public class UserHandler extends Thread
{
	Menu me=new Menu();
	Store sto=new Store();
	Balance ba=new Balance();
	Items it=new Items();
	
	Socket so;
	DataInputStream dis;
	DataOutputStream dos;
	
	ArrayList<String> al=new ArrayList<String>();
	String dir="C:\\Users\\lenovo\\Desktop\\Store\\";
	char c='y';
	
	UserHandler(Socket so,DataInputStream dis,DataOutputStream dos)
	{
		this.so=so;
		this.dis=dis;
		this.dos=dos;
	}
	
	@Override
	public void run()
	{
		do
		{
			if(so.isClosed()||!Thread.currentThread().isAlive())
			{
				break;
			}
			File fi=new File(dir,"forSale.txt");
			
			if(!fi.exists())
				try{fi.createNewFile();}catch(Exception e) {};

				
			al.add("login");
			al.add("register");
			al.add("end the thread");
			
			switch(me.generateVertical(al,dis,dos))
			{
			case 0:
			{
				login(dis,dos);
			}
			break;
			case 1:
			{
				register(dis,dos);
				al.clear();
			}
			break;
			case 2:
			{
				try{dos.writeUTF("have a nice day");}catch(Exception e) {};
				try{so.close();}catch (IOException e) {};
			}
			break;
			}
			
			try{dos.writeUTF("all the changes have been saved\ncontinue with the main menu?(y/n)");}catch(Exception e) {};
			try{c=dis.readChar();}catch(Exception e) {};
			al.clear();
		}
		while(c=='y');
		try {so.close();}catch(Exception e){}
	}
	public void initialize(File fi,String st1,String st2)throws Exception
	{
		FileWriter fw=new FileWriter(fi);
		BufferedWriter bw=new BufferedWriter(fw);
		new File(dir+"\\"+st1,"forSale.txt").createNewFile();
		
		bw.write(st2);
		bw.newLine();
		bw.write("name: "+fi.getName().substring(0,fi.getName().length()-4));
		bw.newLine();
		bw.write("balance: 5000");
		bw.close();
		fw.close();
		
		it.fillItems(fi);
	}
	public void login(DataInputStream dis,DataOutputStream dos)
	{
		
		try{dos.writeUTF("enter your user name");}catch(Exception e) {};
		String st=new String();
		try{st=dis.readUTF();}catch(Exception e) {};
		
		File fi=new File(dir+"\\"+st,st+".txt");
		try{dos.writeUTF("enter your password");}catch(Exception e) {};
		try{st=dis.readUTF();}catch(Exception e) {};
		
		try
		{
			FileReader fr=new FileReader(fi);
			BufferedReader br=new BufferedReader(fr);
			if(br.readLine().compareTo(st)!=0)
			{
				st="the entered password is not correct";
				try{dos.writeUTF(st);}catch(Exception e) {};
				br.close();
				return;
			}
			
			do
			{
				if(so.isClosed()||!Thread.currentThread().isAlive())
				{
					break;
				}
				
				al.clear();
				al.add("get the account details.");
				al.add("check your balance.");
				al.add("go to the store.");
				al.add("logout.");
				
				switch(me.generateVertical(al, dis, dos))
				{
				case 0:
				{
					Scanner sc=new Scanner(fi);
					st=new String();
					
					while(sc.hasNextLine())
					{
						st+=sc.nextLine()+System.lineSeparator();
					}
					try{dos.writeUTF(st+"\npress any key");}catch(Exception e) {};
					try{c=dis.readChar();}catch(Exception e) {};
					sc.close();
				}
				break;
				case 1:
				{
					int f=ba.getBalance(fi);
					try{dos.writeUTF("the balance: "+f+"\npress any key");}catch(Exception e) {};
					try{c=dis.readChar();}catch(Exception e) {};
				}
				break;
				case 2:
				{
					sto.welcome(fi,dis,dos);
					al.clear();
				}
				break;
				case 3:
				{
					al.clear();
					try{dos.writeUTF("have a nice day");}catch(Exception e) {};
					try{so.close();}catch (IOException e) {};
				}
				break;
				}
				
				try{dos.writeUTF("do you want to continue with login menu?(y/n)");}catch(Exception e) {};
				try{c=dis.readChar();}catch(Exception e) {};
				
				al.clear();
				br.close();
				fr.close();
			}
			while (c=='y');
		}
		catch(Exception e)
		{
			
		}
	}
	public void register(DataInputStream dis,DataOutputStream dos)
	{
		try{dos.writeUTF("enter your name");}catch(Exception e) {};
		
		String st1=new String();
		try{st1=dis.readUTF();}catch(Exception e) {};
		
		if(st1.length()!=6)
		{
			try{dos.writeUTF("the length is not valid");}catch(Exception e) {};
			return;
		}
		
		String st2=new String();
		try{dos.writeUTF("enter your password");}catch(Exception e) {};
		try{st2=dis.readUTF();}catch(Exception e) {};
		
		if(st2.length()!=6)
		{
			try{dos.writeUTF("the length is not valid\npress 'y' to continue");}catch(Exception e) {};
			
			return;
		}
		
		new File(dir+"\\"+st1).mkdirs();
		
		File fi=new File(dir+"\\"+st1,st1+".txt");
		
		try{fi.createNewFile();}catch(Exception e) {};
		try
		{
			initialize(fi,st1,st2);
		}
		catch(Exception e)
		{
			
		}
		st1=new String();
	}
}
