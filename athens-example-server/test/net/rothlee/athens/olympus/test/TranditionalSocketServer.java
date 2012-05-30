package net.rothlee.athens.olympus.test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TranditionalSocketServer {

	public static void main(String[] args) throws IOException {
		
		ServerSocket serverSocket = new ServerSocket(7070);
		
		while(true) {
			Socket sock = serverSocket.accept();
			
			new SocketThread(sock).run();
		}
	}
	
	static class SocketThread {
		
		private Socket sock;
		
		public SocketThread(Socket sock) {
			this.sock = sock;
		}
		
		private void run() {
			while(true) {
				try {
					int readData = sock.getInputStream().read();
					sock.getOutputStream().write(readData);
				} catch (IOException e) {
					try {
						sock.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				
			}
		}
		
	}
}
