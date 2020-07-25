import java.util.*;
import java.io.*;

public class Store extends Items
{

	Menu me=new Menu();
	Balance ba=new Balance();
	ArrayList<String> al=new ArrayList<String>();
	
	Items it=new Items();
	String dir="C:\\Users\\lenovo\\Desktop\\Store\\";
	int m;
	char c='y';
	File fi;
	FileWriter fw;
	BufferedWriter bw;
	Scanner sc;
	
	public void welcome(File fi,DataInputStream dis,DataOutputStream dos)throws FileNotFoundException,IOException
	{
		do
		{
			al.clear();
			al.add("sell an item.");
			al.add("buy an item.");
			al.add("approve your sale.");
			
			switch(me.generateVertical(al,dis,dos))
			{
			case 0:
			{
				sellItem(fi,dis,dos);
			}
			break;
			case 1:
			{
				buyItem(fi,dis,dos);
			}
			break;
			case 2:
			{
				approveSale(fi,dis,dos);
			}
			break;
			}
			
			al.clear();
			try{dos.writeUTF("do you want to continue with store menu?(y/n)");}catch(Exception e) {};
			try{c=dis.readChar();}catch(Exception e) {};
		}
		while(c=='y');
	}
	public void sellItem(File fi,DataInputStream dis,DataOutputStream dos)throws FileNotFoundException,IOException
	{
		al=ownedItems(fi);
		
		if(al.size()!=0)
		{
			m=me.generateVertical(al,dis,dos);
			String st=getItem(fi,m);
			String temp=new String();
			
			al.clear();
			initialize();
			al=allItems();
			
			
			for(int i=0;i<itAl.size();i++)
			{
				if(st.compareTo(itAl.get(i).name)==0)
				{
					st=al.get(i);
				}
			}
			File sale=new File(dir+fi.getName().substring(0,fi.getName().length()-4),"forSale.txt");
			al.clear();
			sc=new Scanner(sale);
			
			while(sc.hasNextLine())
				temp+=sc.nextLine()+System.lineSeparator();
			
			temp+=st;
			
			fw=new FileWriter(sale);
			bw=new BufferedWriter(fw);
			bw.write(temp);
			bw.close();
			fw.close();
			
			sale=new File(dir,"forSale.txt");
			temp=new String();
			
			sc=new Scanner(sale);
			
			while(sc.hasNextLine())
				temp+=sc.nextLine()+System.lineSeparator();
			
			temp+=st+"\t"+fi.getName().substring(0,fi.getName().length()-4);
			
			fw=new FileWriter(sale);
			bw=new BufferedWriter(fw);
			bw.write(temp);
			bw.close();
			fw.close();
			
			removeItem(fi,m);
			
			al.clear();
		}
		else
		{
			String st="!!!you do not have any items to be sold!!!";
			try{dos.writeUTF(st);}catch(Exception e) {};
		}
		
		try{dos.writeUTF("the bid has been made");}catch(Exception e) {};
		try{c=dis.readChar();}catch(Exception e) {};
		
		al.clear();
	}
	public synchronized void buyItem(File fi,DataInputStream dis, DataOutputStream dos)throws FileNotFoundException,IOException
	{
		File temp=new File(dir,"forSale.txt");
		al.clear();
		
		if(temp.length()==0)
		{
			try{dos.writeUTF("there are no items at present(y/n)");}catch(Exception e) {};
			return;
		}
		
		sc=new Scanner(temp);
		
		while(sc.hasNextLine())
		{
			al.add(sc.nextLine());
		}
		
		m=me.generateVertical(al,dis,dos);
		al.clear();
		
		bid(fi,m);
		al.clear();

		try{dos.writeUTF("the bid has been made");}catch(Exception e) {};
		try{c=dis.readChar();}catch(Exception e) {};
		
	}
	public void bid(File fi,int m) throws FileNotFoundException, IOException
	{
		File temp=new File(dir,"forSale.txt");
		sc=new Scanner(temp);
		
		for(int i=0;i<=m;i++)
			st=sc.nextLine();
		
		if(st.length()<24)
			st+="\t"+fi.getName().substring(0,fi.getName().length()-4)+System.lineSeparator();
		
		st=st.substring(0,9)+(Integer.parseInt(st.substring(9,12))+Integer.parseInt(st.substring(14,16)))+st.substring(12,st.length());
//		String tempString=st;
		
		System.out.println("st is: "+st);
		
		while(sc.hasNextLine())
		{
				st+=sc.nextLine()+System.lineSeparator();
		}
		
//		st+=tempString;
		
		fw=new FileWriter(temp);
		bw=new BufferedWriter(fw);
		bw.write(st);
		bw.close();
		fw.close();
		
		int bid=Integer.parseInt(st.substring(14,16));
		
		ba.deductBalance(fi,bid);
	}
	public void approveSale(File fi,DataInputStream dis, DataOutputStream dos) throws NumberFormatException, IOException
	{
		File temp1=new File(dir+fi.getName().substring(0,fi.getName().length()-4),"forSale.txt");
		
		if(temp1.length()==0)
		{
			try{dos.writeUTF("you do not have any items to be sold\npress any key");}catch(Exception e) {};
			try{c=dis.readChar();}catch(Exception e) {};
			return;
		}
		
		sc=new Scanner(temp1);
		al.clear();
		
		while(sc.hasNextLine())
			al.add(sc.nextLine());
		
		
		m=me.generateVertical(al, dis, dos);
		
		
		for(int i=0;i<=m;i++)
			st=sc.nextLine();
		
		String name=st.substring(st.length()-6);
		String amount=st.substring(9,12);
				
		File temp2=new File(dir,"forSale.txt");
		
		sc=new Scanner(temp2);
		
		while(sc.hasNextLine())
		{
			if(sc.nextLine().substring(9,12).compareTo(amount)==0&&sc.nextLine().substring(sc.nextLine().length()-6).compareTo(name)==0)  
			{
				st=sc.nextLine();
			}
		}
		ba.addBalance(fi,Integer.parseInt(amount));
		
		System.out.println("hello zima");
			
		String tempString=new String();
		
		while(sc.hasNextLine())
		{
			if(sc.nextLine().compareTo(st)==0)
				continue;
			tempString+=sc.nextLine()+System.lineSeparator();
		}
		
		fw=new FileWriter(temp2);
		bw=new BufferedWriter(fw);
		bw.write(tempString);
		
		sc=new Scanner(temp1);
		
		while(sc.hasNextLine())
		{
			if(sc.nextLine().substring(14,16).compareTo(amount)==0&&sc.nextLine().substring(sc.nextLine().length()-6).compareTo(name)==0)  
			{
				st=sc.nextLine();
			}
		}
			
		tempString=new String();
		
		while(sc.hasNextLine())
		{
			if(sc.nextLine().compareTo(st)==0)
				continue;
			tempString+=sc.nextLine()+System.lineSeparator();
		}
		
		fw=new FileWriter(temp2);
		bw=new BufferedWriter(fw);
		bw.write(tempString);
	}
}
