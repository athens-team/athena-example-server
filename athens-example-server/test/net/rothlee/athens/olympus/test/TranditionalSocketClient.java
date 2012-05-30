package net.rothlee.athens.olympus.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TranditionalSocketClient {

	public static void main(String[] args) throws IOException {

		Socket clientSocket = new Socket();
		clientSocket.connect(new InetSocketAddress("localhost", 7070));

		while (true) {
			try {
				int readData = clientSocket.getInputStream().read();
				clientSocket.getOutputStream().write(readData);
			} catch (IOException e) {
				try {
					clientSocket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

		}
	}

}
