import java.io.*;
import java.util.*;

public class Items 
{
	int price;
	String name;
	int bid;
	
	Random rand=new Random();
	Balance ba=new Balance();
	String st;
	Scanner sc;
	FileWriter fw;
	BufferedWriter bw;
	int m;
	
	public ArrayList<Items> itAl=new ArrayList<Items>();
	
	public void initialize()
	{
		Items one=new Items();
		Items two=new Items();
		Items thr=new Items();
		Items fou=new Items();
		Items fiv=new Items();
		Items six=new Items();
		Items sev=new Items();
		Items eig=new Items();
		Items nin=new Items();
		Items ten=new Items();
	
		one.price=300;
		two.price=500;
		thr.price=700;
		fou.price=100;
		fiv.price=800;
		six.price=200;
		sev.price=600;
		eig.price=900;
		nin.price=100;
		ten.price=700;
		
		one.bid=10;
		two.bid=20;
		thr.bid=30;
		fou.bid=40;
		fiv.bid=10;
		six.bid=20;
		sev.bid=30;
		eig.bid=40;	
		nin.bid=10;
		ten.bid=20;
		
		one.name="itemOne";
		two.name="itemTwo";
		thr.name="itemThr";
		fou.name="itemFou";
		fiv.name="itemFiv";
		six.name="itemSix";
		sev.name="itemSev";
		eig.name="itemEig";
		nin.name="itemNin";
		ten.name="itemTen";
		
		itAl.add(one);
		itAl.add(two);
		itAl.add(thr);
		itAl.add(fou);
		itAl.add(fiv);
		itAl.add(six);
		itAl.add(sev);
		itAl.add(eig);
		itAl.add(nin);
		itAl.add(ten);
	}
	public void fillItems(File fi)throws FileNotFoundException,IOException
	{
		initialize();
		sc=new Scanner(fi);
		st=new String();
		
		
		while(sc.hasNextLine())
		{
			st+=sc.nextLine()+System.lineSeparator();
		}
		st+=System.lineSeparator();
		st+="Items:-";
		
		for(int i=0;i<5;i++)
		{
			m=rand.nextInt(9);
			st+=System.lineSeparator()+"\t"+itAl.get(m).name+"\t"+itAl.get(m).price;
		}
		
		fw=new FileWriter(fi);
		bw=new BufferedWriter(fw);
		
		bw.write(st);
		
		bw.close();
		fw.close();
		sc.close();
		itAl.clear();
	}
	public void addItem(File fi,int m)throws FileNotFoundException,IOException
	{
		initialize();
		sc=new Scanner(fi);
		st=new String();
		
		while(sc.hasNextLine())
		{
			st+=sc.nextLine()+System.lineSeparator();
		}
		st+="\t"+itAl.get(m).name+"\t"+itAl.get(m).price;
		
		fw=new FileWriter(fi);
		bw=new BufferedWriter(fw);
		
		bw.write(st);
		
		bw.close();
		fw.close();
		sc.close();
		
		ba.deductBalance(fi,itAl.get(m).price);
		itAl.clear();
	}
	public void removeItem(File fi,int m)throws FileNotFoundException,IOException
	{
		sc=new Scanner(fi);
		st=new String();
		
		for(int i=0;sc.hasNextLine();i++)
		{
			if(i==(m+4))
			{
				st=sc.nextLine();
				sc.close();
				break;
			}
			else
				sc.nextLine();
		}
		
		int price=Integer.parseInt(st.substring(7,12).replace("\t",""));
		
		ba.addBalance(fi,price);
		
		sc=new Scanner(fi);
		st=new String();
		
		for(int i=0;sc.hasNextLine();i++)
		{
			if(i==(m+4))
				sc.nextLine();
			else
				st+=sc.nextLine()+System.lineSeparator();
		}
		
		fw=new FileWriter(fi);
		bw=new BufferedWriter(fw);
		
		bw.write(st);
		
		bw.close();
		fw.close();
		sc.close();
	}
	public ArrayList<String> ownedItems(File fi)throws FileNotFoundException,IOException
	{
		ArrayList<String> result=new ArrayList<String>();
		
		st=new String();
		sc=new Scanner(fi);
		
		
		for(int i=0;sc.hasNextLine();i++)
		{
			if(i>4)
			{
				st=sc.next();
				result.add(st);
				sc.nextLine();
			}
			else
				sc.nextLine();
		}
		sc.close();
		
		return result;
	}
	public String getItem(File fi,int m)throws FileNotFoundException
	{
		sc=new Scanner(fi);
		st=new String();
		
		for(int i=0;sc.hasNextLine();i++)
		{
			if(i==(m+5))
			{
				st=sc.next();
				break;
			}
			else
				sc.nextLine();
		}
		
		return st;
	}
	public ArrayList<String> allItems()
	{
		itAl.clear();
		initialize();
		ArrayList<String> result=new ArrayList<String>();
		
		for(int i=0;i<this.itAl.size();i++)
		{
			result.add(itAl.get(i).name+"\t\t"+itAl.get(i).price+"\t\t"+itAl.get(i).bid);
		}
		
		return result;
	}
}
