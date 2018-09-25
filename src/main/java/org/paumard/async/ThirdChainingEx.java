package org.paumard.async;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.paumard.async.model.User;

public class ThirdChainingEx {
	public static void main(String[] args) {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Supplier<List<Long>> supplyIDs = () -> {
			sleep(200);
			return Arrays.asList(1L, 2L, 3L);
		};

		Function<List<Long>, CompletableFuture<List<User>>> fetchUsers = ids -> {
			sleep(300);
			Supplier<List<User>> userSupplier = () -> {
				System.out.println("Currently running in " + Thread.currentThread().getName());
				return ids.stream().map(User::new).collect(Collectors.toList());
			};
			return CompletableFuture.supplyAsync(userSupplier);
		};

		Consumer<List<User>> displayer = users -> {
			System.out.println("Running in " + Thread.currentThread().getName());
			users.forEach(System.out::println);
		};

		CompletableFuture<List<Long>> completableFuture = CompletableFuture.supplyAsync(supplyIDs);
		completableFuture.thenCompose(fetchUsers).thenAcceptAsync(displayer, executor);

		sleep(1_000);
		executor.shutdown();
	}

	private static void sleep(int timeout) {
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
		}
	}
}
