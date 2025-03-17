package com.ram.hackerland;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ThroughputHttpServer {

    private static final String INPUT_FILE = "war_and_peace.txt";

    private static final int NUMBER_OF_THREADS = 20;
    public static void main(String[] args) throws IOException, URISyntaxException {

        String text = new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource(INPUT_FILE).toURI())));
        startServer(text);
    }

    public static void startServer(String text) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000),0);
        server.createContext("/search", new WordCountHandler(text));
        Executor executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        server.setExecutor(executor);
        server.start();
    }

    private static class  WordCountHandler implements HttpHandler {
        String text;
        public WordCountHandler(String text) {
            this.text = text;
        }
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String query = exchange.getRequestURI().getQuery();
            String[] keyValue = query.split("=");
            String action = keyValue[0];
            String word = keyValue[1];
            if(!action.equals("word")) {
                exchange.sendResponseHeaders(400,0);
            }
            long count = wordCount(word);
            byte[] response =Long.toString(count).getBytes();
            exchange.sendResponseHeaders(200, response.length);
            OutputStream outputStream = exchange.getResponseBody();
            outputStream.write(response);
            outputStream.close();
        }

        private long wordCount(String word) {
            System.out.println("Searching for word:" + word);
            int index = 0;
            long count = 0;
            while(index >= 0) {
                index = text.indexOf(word, index);
                if(index >= 0) {
                    index++;
                    count++;
                }
            }
            return count;
        }
    }



}
