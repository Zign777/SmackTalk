package com.smacktalk.main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;

public class NetworkAdapter {
	Context mContext;
	
	NetworkAdapter(Context c){
	mContext = c;
	}

	InetAddress getBroadcastAddress() throws IOException{
		 WifiManager wifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
		    DhcpInfo dhcp = wifi.getDhcpInfo();
		    // handle null somehow

		    int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
		    byte[] quads = new byte[4];
		    for (int k = 0; k < 4; k++)
		      quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
		    return InetAddress.getByAddress(quads);
		}
	void sendPacket() throws IOException{
			
		WifiManager wifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
	    DhcpInfo dhcp = wifi.getDhcpInfo();
	    // handle null somehow

	    int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
	    byte[] quads = new byte[4];
	    for (int k = 0; k < 4; k++)
	      quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
			 DatagramSocket socket = new DatagramSocket();
			 
	            // send request
	        byte[] buf = new byte[256];
	        InetAddress address = InetAddress.getByName("LegendaryComp");
	        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
	        socket.send(packet);
	     
	            // get response
	        packet = new DatagramPacket(buf, buf.length);
	        socket.receive(packet);
	 
	        // display response
	        String received = new String(packet.getData(), 0, packet.getLength());
	        System.out.println("Quote of the Moment: " + received);
	     
	        socket.close();
		
		}
	}
