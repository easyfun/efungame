package com.efun.game.web.test.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;

public class SocketHttpClientTest {

	@Test
	public void helloWorld() throws UnknownHostException, IOException {
		System.out.println("begin test");
		Socket socket = new Socket("localhost", 8080);

		for (int i = 0; i < 2; i++) {
			System.out.println("i="+i+", os, socket_is_connect: "+socket.isConnected()+", socket_is_close="+socket.isClosed());
			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os);

			pw.write("GET /efg_web_test/hello/world?param=easfun HTTP/1.1\r\n");
			pw.write("Host: 127.0.0.1:8080\r\n");
			pw.write("Connection: keep-alive\r\n");
			pw.write("user-agent: SocketHttpClient easyfun\r\n");
			pw.write("\r\n");
			// pw.write("param=easyfun\r\n");
			// pw.write("\r\n");
			pw.flush();

			System.out.println("i="+i+",socket_is_connect: "+socket.isConnected()+", socket_is_close="+socket.isClosed());
			socket.shutdownOutput();

			InputStream is = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String info = null;
			while (null != (info = br.readLine())) {
				System.out.println(info);
			}

//			br.close();
//			is.close();
//			pw.close();
//			os.close();
			
			System.out.println("i="+i+",socket_is_connect: "+socket.isConnected()+", socket_is_close="+socket.isClosed());
			
		}

		socket.close();
		System.out.println("end test");
	}
}
