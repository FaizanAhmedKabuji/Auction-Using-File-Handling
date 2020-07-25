import java.io.*;
import java.util.*;
import java.net.*;

public class User1
{
	public static void main(String[] zima)
	{
		try 
		{
			Socket so=new Socket("localhost",4569);
			Scanner sc=new Scanner(System.in);
			DataInputStream dis=new DataInputStream(so.getInputStream());
			DataOutputStream dos=new DataOutputStream(so.getOutputStream());
			
			while(true)
			{
				System.out.println(dis.readUTF());
				String st=sc.next();
				if(st.length()==1)
					dos.writeChar(st.charAt(0));
				else 
					dos.writeUTF(st);
			}
		} 
		catch (UnknownHostException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			System.out.println("login again to proceed");
		}
	}
}
