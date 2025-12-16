import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;

public class Main {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8090), 0);
        server.createContext("/", new MyHandler("index.html", "text/html"));
        server.createContext("/style.css", new MyHandler("style.css", "text/css"));
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Server started on port 8080");
    }

    static class MyHandler implements HttpHandler {
        private final String filePath;
        private final String contentType;

        public MyHandler(String filePath, String contentType) {
            this.filePath = filePath;
            this.contentType = contentType;
        }

        @Override
        public void handle(HttpExchange t) throws IOException {
            File file = new File(filePath);
            t.sendResponseHeaders(200, file.length());
            t.getResponseHeaders().set("Content-Type", contentType);
            try (OutputStream os = t.getResponseBody()) {
                Files.copy(file.toPath(), os);
            }
        }
    }
}
