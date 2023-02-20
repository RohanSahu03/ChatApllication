import java.net.*;
import java.io.*;

public class Server {

	ServerSocket server;
	Socket socket;

	BufferedReader br;
	PrintWriter out;

	public Server() {
		try {
			server = new ServerSocket(7777);
			System.out.println("server is ready to accept connection..");
			System.out.println("waiting.....");
			socket = server.accept();

			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());

			StartReading();
			StartWriting();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	void StartReading() {

		// thread
		Runnable r1 = () -> {
			System.out.println("Reader Started..");

			try {
				while (true) {

					String msg = br.readLine();
					if (msg.equals("exit")) {
						System.out.println("client terminated the chat");
						socket.close();
						break;
					}
					System.out.println("Client : " + msg);

				}
			} catch (Exception e) {
				//System.out.println(e);
				System.out.println("connection closed..");
			}

		};
		new Thread(r1).start();
	}

	void StartWriting() {

		Runnable r2 = () -> {
			System.out.println("writer started..");
			try {
			while (!socket.isClosed()) {

			
					BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
					String content = br1.readLine();
					
					out.println(content);
					out.flush();
					if (content.contentEquals("exit")) {
						socket.close();
						break;
					}
				
			}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("CONNECTION CLOSED");
			}

		};
		new Thread(r2).start();
	}

	public static void main(String[] args) {
		System.out.println("this is server going to start..");
		new Server();
	}

}
