package org.paumard.async;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.paumard.async.model.User;

public class FirstChainingEx {

	public static void main(String[] args) {
		Supplier<List<Long>> supplyIDs = () -> {
			sleep(200);
			return Arrays.asList(1L, 2L, 3L);
		};

		Function<List<Long>, List<User>> fetchUsers = ids -> {
			sleep(300);
			return ids.stream().map(User::new).collect(Collectors.toList());
		};

		Consumer<List<User>> displayer = users -> users.forEach(System.out::println);
		CompletableFuture<List<Long>> completableFuture = CompletableFuture.supplyAsync(supplyIDs);
		completableFuture.thenApply(fetchUsers).thenAccept(displayer);

		sleep(1_000);
	}

	private static void sleep(int timeout) {
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
		}
	}
}
