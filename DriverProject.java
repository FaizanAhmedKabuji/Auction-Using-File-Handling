import java.util.*;
import java.io.*;
import java.net.*;

public class DriverProject 
{
	public static void main(String[] zima)
	{
		
		try 
		{
			ServerSocket ss=new ServerSocket(4569);
			Socket so=null;
			DataInputStream dis;
			DataOutputStream dos;
			
			
			while(true) 
			{
				so=ss.accept();
				System.out.println("a new user connected :"+Thread.currentThread().getName());
				
				dis=new DataInputStream(so.getInputStream());
				dos=new DataOutputStream(so.getOutputStream());
				
				Thread th=new UserHandler(so,dis,dos);
				
				th.start();
			}
		} 
		catch (IOException e)
		{
			System.out.println("connection closed");
		}
	}
}
