import java.net.*;
import java.io.*;

public class Client {

	BufferedReader br;
	PrintWriter out;

	Socket socket;

	public Client() {

		try {
			System.out.println("sending request to server..");
			socket = new Socket("localhost", 7777);
			System.out.println("connection done..");

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
						System.out.println("server terminated the chat");
						socket.close();
						break;
					}
					System.out.println("server : " + msg);

				}
			} catch (Exception e) {
				System.out.println(e);
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
				System.out.println("connection closed..");
			}

		};
		new Thread(r2).start();
	}

	public static void main(String[] args) {

		System.out.println("client is ready..");
		new Client();
	}

}
