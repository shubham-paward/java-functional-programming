package com.functionalprogramming.advanced;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * Reactive Programming with Java Streams
 * 
 * This class demonstrates reactive programming concepts using Java's
 * functional programming features, including streams, CompletableFuture,
 * and asynchronous processing patterns.
 */
public class ReactiveProgramming {
    
    public static void main(String[] args) {
        System.out.println("=== Reactive Programming with Java ===\n");
        
        // Example 1: Asynchronous Processing
        demonstrateAsynchronousProcessing();
        
        // Example 2: Event-Driven Programming
        demonstrateEventDrivenProgramming();
        
        // Example 3: Backpressure Handling
        demonstrateBackpressureHandling();
        
        // Example 4: Error Handling in Streams
        demonstrateErrorHandling();
        
        // Example 5: Real-time Data Processing
        demonstrateRealTimeProcessing();
    }
    
    /**
     * Example 1: Asynchronous Processing
     * 
     * Using CompletableFuture for asynchronous operations.
     */
    private static void demonstrateAsynchronousProcessing() {
        System.out.println("1. Asynchronous Processing:");
        
        // Simulate async operations
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            return "Hello";
        });
        
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(1500); } catch (InterruptedException e) {}
            return "World";
        });
        
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(500); } catch (InterruptedException e) {}
            return "!";
        });
        
        // Combine futures
        CompletableFuture<String> combined = future1
            .thenCombine(future2, (s1, s2) -> s1 + " " + s2)
            .thenCombine(future3, (s, s3) -> s + s3);
        
        try {
            String result = combined.get(3, TimeUnit.SECONDS);
            System.out.println("  Combined result: " + result);
        } catch (Exception e) {
            System.out.println("  Error: " + e.getMessage());
        }
        
        // Chain async operations
        CompletableFuture<String> chained = CompletableFuture
            .supplyAsync(() -> "async")
            .thenApply(String::toUpperCase)
            .thenApply(s -> "Result: " + s)
            .thenApply(s -> s + " processed");
        
        try {
            System.out.println("  Chained result: " + chained.get());
        } catch (Exception e) {
            System.out.println("  Error: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * Example 2: Event-Driven Programming
     * 
     * Creating event-driven systems using functional interfaces.
     */
    private static void demonstrateEventDrivenProgramming() {
        System.out.println("2. Event-Driven Programming:");
        
        // Event system
        EventSystem eventSystem = new EventSystem();
        
        // Register event handlers
        eventSystem.on("user.login", event -> 
            System.out.println("  User logged in: " + event.getData()));
        
        eventSystem.on("user.logout", event -> 
            System.out.println("  User logged out: " + event.getData()));
        
        eventSystem.on("data.updated", event -> 
            System.out.println("  Data updated: " + event.getData()));
        
        // Emit events
        eventSystem.emit("user.login", "Alice");
        eventSystem.emit("user.logout", "Bob");
        eventSystem.emit("data.updated", "User profile");
        
        // Chain event handlers
        eventSystem.on("order.created", event -> {
            System.out.println("  Order created: " + event.getData());
            eventSystem.emit("inventory.check", event.getData());
        });
        
        eventSystem.on("inventory.check", event -> {
            System.out.println("  Checking inventory for: " + event.getData());
            eventSystem.emit("payment.process", event.getData());
        });
        
        eventSystem.on("payment.process", event -> {
            System.out.println("  Processing payment for: " + event.getData());
        });
        
        eventSystem.emit("order.created", "Laptop");
        
        System.out.println();
    }
    
    /**
     * Example 3: Backpressure Handling
     * 
     * Managing data flow when producer is faster than consumer.
     */
    private static void demonstrateBackpressureHandling() {
        System.out.println("3. Backpressure Handling:");
        
        // Simulate fast producer, slow consumer
        List<Integer> data = IntStream.rangeClosed(1, 1000)
            .boxed()
            .collect(Collectors.toList());
        
        // Process with backpressure (limit batch size)
        int batchSize = 10;
        List<List<Integer>> batches = data.stream()
            .collect(Collectors.groupingBy(i -> i / batchSize))
            .values()
            .stream()
            .collect(Collectors.toList());
        
        System.out.println("  Total batches: " + batches.size());
        System.out.println("  First batch: " + batches.get(0));
        
        // Process batches with delay (simulating slow consumer)
        batches.stream()
            .limit(3) // Process only first 3 batches
            .forEach(batch -> {
                System.out.println("  Processing batch: " + batch);
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            });
        
        System.out.println();
    }
    
    /**
     * Example 4: Error Handling in Streams
     * 
     * Handling errors gracefully in stream processing.
     */
    private static void demonstrateErrorHandling() {
        System.out.println("4. Error Handling in Streams:");
        
        List<String> data = Arrays.asList("123", "abc", "456", "def", "789");
        
        // Safe parsing with error handling
        List<Result<Integer>> results = data.stream()
            .map(ReactiveProgramming::safeParseInt)
            .collect(Collectors.toList());
        
        System.out.println("  Parsing results:");
        results.forEach(result -> {
            if (result.isSuccess()) {
                System.out.println("    Success: " + result.getValue());
            } else {
                System.out.println("    Error: " + result.getError());
            }
        });
        
        // Filter successful results
        List<Integer> validNumbers = results.stream()
            .filter(Result::isSuccess)
            .map(Result::getValue)
            .collect(Collectors.toList());
        
        System.out.println("  Valid numbers: " + validNumbers);
        
        // Calculate sum of valid numbers
        int sum = validNumbers.stream()
            .mapToInt(Integer::intValue)
            .sum();
        
        System.out.println("  Sum of valid numbers: " + sum);
        System.out.println();
    }
    
    /**
     * Example 5: Real-time Data Processing
     * 
     * Simulating real-time data processing with streams.
     */
    private static void demonstrateRealTimeProcessing() {
        System.out.println("5. Real-time Data Processing:");
        
        // Simulate real-time data stream
        List<DataPoint> dataStream = Arrays.asList(
            new DataPoint("sensor1", 25.5, System.currentTimeMillis()),
            new DataPoint("sensor2", 30.2, System.currentTimeMillis() + 100),
            new DataPoint("sensor1", 26.1, System.currentTimeMillis() + 200),
            new DataPoint("sensor3", 22.8, System.currentTimeMillis() + 300),
            new DataPoint("sensor2", 31.0, System.currentTimeMillis() + 400)
        );
        
        // Process real-time data
        Map<String, Double> averageBySensor = dataStream.stream()
            .collect(Collectors.groupingBy(
                DataPoint::getSensorId,
                Collectors.averagingDouble(DataPoint::getValue)
            ));
        
        System.out.println("  Average by sensor: " + averageBySensor);
        
        // Find anomalies (values > 30)
        List<DataPoint> anomalies = dataStream.stream()
            .filter(dp -> dp.getValue() > 30)
            .collect(Collectors.toList());
        
        System.out.println("  Anomalies: " + anomalies);
        
        // Calculate moving average (simplified)
        List<Double> movingAverage = dataStream.stream()
            .map(DataPoint::getValue)
            .collect(Collectors.toList());
        
        System.out.println("  Values: " + movingAverage);
        System.out.println();
    }
    
    /**
     * Safe parsing utility
     */
    private static Result<Integer> safeParseInt(String s) {
        try {
            return Result.success(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return Result.failure("Invalid number: " + s);
        }
    }
    
    /**
     * Result class for error handling
     */
    public static class Result<T> {
        private final T value;
        private final String error;
        private final boolean success;
        
        private Result(T value, String error, boolean success) {
            this.value = value;
            this.error = error;
            this.success = success;
        }
        
        public static <T> Result<T> success(T value) {
            return new Result<>(value, null, true);
        }
        
        public static <T> Result<T> failure(String error) {
            return new Result<>(null, error, false);
        }
        
        public T getValue() { return value; }
        public String getError() { return error; }
        public boolean isSuccess() { return success; }
    }
    
    /**
     * Event system for event-driven programming
     */
    public static class EventSystem {
        private final Map<String, List<Consumer<Event>>> handlers = new HashMap<>();
        
        public void on(String eventType, Consumer<Event> handler) {
            handlers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(handler);
        }
        
        public void emit(String eventType, Object data) {
            Event event = new Event(eventType, data);
            handlers.getOrDefault(eventType, Collections.emptyList())
                .forEach(handler -> handler.accept(event));
        }
    }
    
    /**
     * Event class
     */
    public static class Event {
        private final String type;
        private final Object data;
        
        public Event(String type, Object data) {
            this.type = type;
            this.data = data;
        }
        
        public String getType() { return type; }
        public Object getData() { return data; }
    }
    
    /**
     * Data point for real-time processing
     */
    public static class DataPoint {
        private final String sensorId;
        private final double value;
        private final long timestamp;
        
        public DataPoint(String sensorId, double value, long timestamp) {
            this.sensorId = sensorId;
            this.value = value;
            this.timestamp = timestamp;
        }
        
        public String getSensorId() { return sensorId; }
        public double getValue() { return value; }
        public long getTimestamp() { return timestamp; }
        
        @Override
        public String toString() {
            return sensorId + ":" + value + "@" + timestamp;
        }
    }
    
    /**
     * Key Takeaways:
     * 
     * 1. CompletableFuture enables asynchronous processing
     * 2. Event-driven programming provides loose coupling
     * 3. Backpressure handling manages data flow rates
     * 4. Error handling in streams prevents failures
     * 5. Real-time processing requires efficient data handling
     * 6. Reactive programming improves system responsiveness
     * 7. Functional programming patterns work well with reactive systems
     */
}
