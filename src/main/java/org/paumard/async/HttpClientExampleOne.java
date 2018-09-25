package org.paumard.async;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

public class HttpClientExampleOne {

	public static void main(String[] args) throws IOException, InterruptedException {
		HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();

		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("https://www.amazon.com")).build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandler.asString());
		int length = response.body().length();
		System.out.println("Length = " + length);
	}
}
