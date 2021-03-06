package com.benberg.universe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

//testing3


public class main {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	
	public static void main(String[] args) throws IOException  {
		
	
		main m = new main();
		m.start();
		//Testing
		
	}

	private void start() throws IOException 
	
	{
		WriteLog("Starting Universe Monitor");
		Timer _timerquotes = new Timer ();
		final String _current = getIP();
		TimerTask _hourlyTaskquotes = new TimerTask () {
		    @Override
		    public void run () 
		    {
		    	try{
		    		WriteLog("Checking services ");
		    		String _service = "http://"+_current+":5123/web?wsdl";
		    		String _website = "http://"+_current+"/WebTest.php";
		    		String _Soap = "http://"+_current+"/QuickNews3.php";
		    		String _directConnect = "http://ben512.no-ip.org/";
		    			  
		    		  testurl(_service);
		    		  testurl(_website);
		    		  testurl(_Soap);
		    		  testurl(_directConnect);
		    	
		    		  
				}
		    
		    	catch (Exception e)
		    	{
		    	
		    		WriteLog(">> "+_current + " Could not be reached");
					WriteLog(">> "+e.toString());
				try {
					Runtime.getRuntime().exec("sh restart.sh");
					WriteLog("Restarting System...");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				
		  	    
		    		
		    		
		    	}
		    }
		};
		_timerquotes.schedule(_hourlyTaskquotes, 01,30*60*1000);
		
		
				
		
		
	
	
		
		
	}
		
		
		
		
	
	
	
	private  void testurl(String URL) throws IOException
	{
	//	try
		//{
	//	_current = URL;
		  URL _url = new URL(URL);
		  URLConnection connection = _url.openConnection();
		  connection.setConnectTimeout(5000);
		  connection.setReadTimeout(5000);
		  BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		  WriteLog(URL+ " Is UP");
		
//		catch(IOException e)
//		{
		
			
			
	//	}
		
		
	}
	private void restart() throws IOException
	{
		WriteLog("Attempting a restart");
		Runtime.getRuntime().exec("sh restart.sh");
		
	}

	private String getIP()
	{
		// TODO Auto-generated method stub
				NetworkInterface iface = null;
				String ethr;
				String myip = "";
				String regex = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +	"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
				try
				{
					for(Enumeration ifaces = NetworkInterface.getNetworkInterfaces();ifaces.hasMoreElements();)
					{
						iface = (NetworkInterface)ifaces.nextElement();
						ethr = iface.getDisplayName();

						if (Pattern.matches("eth[0-9]", ethr))
						{
							WriteLog("Interface:" + ethr);
							InetAddress ia = null;
							for(Enumeration ips = iface.getInetAddresses();ips.hasMoreElements();)
							{
								ia = (InetAddress)ips.nextElement();
								if (Pattern.matches(regex, ia.getCanonicalHostName()))
								{
									myip = ia.getCanonicalHostName();
									return myip;
								}
							}
						}
					}
				}
				catch (SocketException e){
				return null;
				}
		
		
		return null;
	}
	
	  public void WriteLog(String Message)
	    {
	    	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			DateFormat dateFormat_log = new SimpleDateFormat("yyyy.MM.dd");
			Date date = new Date();
			System.out.println(dateFormat.format(date)+" : "+Message);
	 		//System.out.printf("%D %R : ",date + Message);
		
			try {
				 
			
	 
				File file = new File("/home/pi/logs/UniverseMonitor/"+dateFormat_log.format(date)+".umon.txt");
				//File file = new File("c:\\"+dateFormat_log.format(date)+".PiUniverseMonitor.log.txt");
				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}
	 
				FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(dateFormat.format(date)+" : "+Message+"\n");
				bw.close();
	 
			
	 
			} catch (IOException e) {
				System.out.println(e.toString());
			}
	    	
	    	
	    	
	    	
	    	
	    }





}
	
	


