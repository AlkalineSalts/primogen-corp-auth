package Server;
import java.net.InetAddress;
import java.io.IOException;
import java.net.UnknownHostException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
public class Main {

	public Main() throws UnknownHostException, IOException{
		InetSocketAddress isa = new InetSocketAddress(InetAddress.getLocalHost(), 8865);
		HttpServer http = HttpServer.create(isa, 2);
		http.start();
		
	}
	public static void main(String[] args) throws UnknownHostException, IOException {
		new Main();
	}

}
