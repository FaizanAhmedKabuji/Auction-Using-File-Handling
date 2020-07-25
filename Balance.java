import java.io.*;
import java.util.*;

public class Balance
{
	FileWriter fw;
	BufferedWriter bw;
	Scanner sc;
	String st1;
	int m;

	public int getBalance(File fi)throws IOException
	{
		sc=new Scanner(fi);
		
		st1=new String();
		for(int i=0;sc.hasNextLine();i++)
		{
			if(i==2)
			{
				st1=sc.nextLine();
				break;
			}			
			sc.nextLine();
		}
		sc.close();
		
		return Integer.parseInt(st1.substring(9,13));
	}
	public void addBalance(File fi,int price)throws FileNotFoundException,IOException
	{
		m=getBalance(fi);
		m=m+price;
		sc=new Scanner(fi);
		st1=new String();
		
		while(sc.hasNextLine())
		{
			st1+=sc.nextLine()+System.lineSeparator();
		}
		
		st1=st1.substring(0,31)+Integer.toString(m)+st1.substring(35,st1.length());
		
		fw=new FileWriter(fi);
		bw=new BufferedWriter(fw);
		
		bw.write(st1);
		
		bw.close();
		fw.close();
		sc.close();
		
		System.out.println("the balance hass been updated");
	}
	public void deductBalance(File fi,int price)throws FileNotFoundException,IOException
	{
		m=getBalance(fi);
		m=m-price;
		
		sc=new Scanner(fi);
		st1=new String();
		
		while(sc.hasNextLine())
		{
			st1+=sc.nextLine()+System.lineSeparator();
		}
		
		st1=st1.substring(0,31)+Integer.toString(m)+st1.substring(35	,st1.length());
		
		fw=new FileWriter(fi);
		bw=new BufferedWriter(fw);
		
		bw.write(st1);
		
		bw.close();
		fw.close();
		sc.close();
		
		System.out.println("the balance hass been updated");
	}
	public boolean feasible(File fi,int price)throws FileNotFoundException,IOException
	{
		boolean result=false;
		
		sc=new Scanner(fi);
		
		m=getBalance(fi);
		
		if(m-price>0)
			result=true;
		
		return result;
	}
}
