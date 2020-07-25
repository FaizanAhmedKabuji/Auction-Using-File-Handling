import java.io.*;
import java.util.*;

public class Menu 
{
	char c;
	public int generateVertical(ArrayList<String> al,DataInputStream dis,DataOutputStream dos) 
	{
		int sel;
		sel = 0;
		String menu=new String();
		String lines="\n\n\n\n\n\n\n\n\n\n";
		String tabs="\t\t\t\t\t";
		
		try 
		{
			
			while(c!='f')
			{
				for(int i=0;i<al.size();i++)
				{
					if(i==sel)
						menu+=tabs+"-->"+al.get(i)+"\n";
					else
						menu+=tabs+"   "+al.get(i)+"\n";
				}
				dos.writeUTF(lines+menu+lines);
				menu=new String();
				c=dis.readChar();
				
				if(c=='w'&&sel==0)
				{
					sel=al.size()-1;
					continue;
				}
				if(c=='s'&&sel==al.size()-1)
				{
					sel=0;
					continue;
				}
				if(c=='w')
					sel--;
				if(c=='s')
					sel++;
				if(c=='f')
					break;
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		c='\u0000';
		return sel;
	}
}
