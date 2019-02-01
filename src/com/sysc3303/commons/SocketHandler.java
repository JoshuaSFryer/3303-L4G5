package com.sysc3303.commons;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * @author
 *
 */
public class SocketHandler {
	private DatagramPacket sendPacket;
	private DatagramPacket receivePacket;
	private DatagramSocket sendSocket;
	private DatagramSocket receiveSocket;
	
	/**
	 * @param port listens to this
	 */
	public SocketHandler(int port) {
		try {
			sendSocket    = new DatagramSocket();
			receiveSocket = new DatagramSocket(port);
		} catch(SocketException err) {
			err.printStackTrace();
			System.exit(-1);
		}
	}
	
	/**
	 * Without listening port
	 */
	public SocketHandler() {
		try {
			sendSocket = new DatagramSocket();
		} catch (SocketException se) {
			se.printStackTrace();
			System.exit(-1);
		}
	}
	
	/**
	 * Waits for incoming packet and returns data when it receives
	 * @param data
	 * @param waitInSentSocket
	 * @return
	 */
	public byte[] waitForPacket(byte[] data, boolean waitInSentSocket) {
		receivePacket = new DatagramPacket(data, data.length);
		try {
			if(waitInSentSocket) {
				sendSocket.receive(receivePacket);
			}
			else {
				receiveSocket.receive(receivePacket);
			}
		} catch(IOException error) {
			error.printStackTrace();
			System.exit(-1);
		}
		return data;
	}
	
	/**
	 * @return received packet byte length
	 */
	public int getRecievePacketLength() {
		return receivePacket.getLength();
	}
	
	/**
	 * Sends socket to previously received host
	 * @param data
	 */
	public void sendSocketToRecievedHost(byte[] data) {
		sendPacket = new DatagramPacket(data, data.length,
                receivePacket.getAddress(), receivePacket.getPort());
		send();
	}
	
	/**
	 * Sends socket to previously received host
	 * @param data
	 * @param length
	 */
	public void sendSocketToRecievedHost(byte[] data, int length) {
		sendPacket = new DatagramPacket(data, length,
                receivePacket.getAddress(), receivePacket.getPort());
		send();
	}
	
	/**
	 * Sends socket to target address and port
	 * @param data
	 * @param address
	 * @param port
	 */
	public void sendSocket(byte[] data, InetAddress address, int port) {
		sendPacket = new DatagramPacket(data, data.length, address, port);
		send();
	}
	
	/**
	 * Sends socket to target address and port
	 * @param data
	 * @param address
	 * @param port
	 * @param length
	 */
	public void sendSocket(byte[] data, InetAddress address, int port, int length) {
		sendPacket = new DatagramPacket(data, length, address, port);
		send();
	}
	
	/**
	 * Sends sendPacket by sendSocket
	 */
	private void send() {
		try {
			sendSocket.send(sendPacket);
		} catch(IOException error) {
			error.printStackTrace();
			System.exit(-1);
		}
	}
	
	/**
	 * Closes sendSocket open socket
	 */
	public void closeSocket() {
			sendSocket.close();
	}
}
