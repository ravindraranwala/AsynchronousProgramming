package org.paumard.async;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

public class HttpClientExampleTwo {
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();

		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("https://www.amazon.com")).build();
		CompletableFuture<HttpResponse<String>> future = client.sendAsync(request, HttpResponse.BodyHandler.asString());
		future.thenAcceptAsync(response -> {
			String body = response.body();
			System.out.println("body = " + body.length() + " [" + Thread.currentThread().getName() + "]");
		}, executor).thenRun(() -> System.out.println("Done!")).join();

		// int length = future.join().body().length();
		// System.out.println("Length = " + length);
		
		executor.shutdown();
	}
}
