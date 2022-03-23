package Server;
import java.util.HashMap;
import java.net.InetAddress;
import java.io.IOException;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.util.Map;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
public class Main {

	public Main() throws UnknownHostException, IOException{
		InetSocketAddress isa = new InetSocketAddress(InetAddress.getLocalHost(), 8865);
		HttpServer http = HttpServer.create(isa, 2);
		
		HttpHandler stopServer = (HttpExchange exchange) -> {
			Map<String, String> map = getQueryString(exchange.getRequestURI().getQuery());
			String codeStr = map.get("code");
			String answer;
			if (codeStr == null) {
				answer = "Unfortunately, an error occured.";
			}
			else {
				answer = String.format("Your authorization code is: %s", codeStr);
			}
			String html = String.format("<html><body><p>Hello! %s</p></body></html>", answer);
			byte[] byteHtml = html.getBytes();
			exchange.sendResponseHeaders(200, byteHtml.length);
			OutputStream os = exchange.getResponseBody();
			os.write(byteHtml, 0, byteHtml.length);
			os.close();
			http.stop(0);
		};
		http.createContext("/callback", stopServer);
		http.start();
		
		
		
	}
	public static void main(String[] args) throws UnknownHostException, IOException {
		new Main();
	}
	public static Map<String, String> getQueryString(String s) {
		HashMap<String, String> hashMap = new HashMap<String, String>(1);
		String[] qs = s.split("&");
		for (String individual: qs) {
			String[] firstKeySecondValue = individual.split("=");
			hashMap.put(firstKeySecondValue[0], firstKeySecondValue[1]);
		}
		return hashMap;
	}

}
