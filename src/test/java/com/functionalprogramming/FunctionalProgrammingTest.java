package com.functionalprogramming;

import com.functionalprogramming.basics.LambdaExpressions;
import com.functionalprogramming.basics.MethodReferences;
import com.functionalprogramming.basics.FunctionalInterfaces;
import com.functionalprogramming.intermediate.Streams;
import java.util.Optional;
import java.util.stream.Collectors;
import com.functionalprogramming.examples.DataProcessing;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.assertj.core.api.Assertions.*;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;

/**
 * Comprehensive Test Suite for Functional Programming Examples
 * 
 * This test class demonstrates how to test functional programming code
 * and validates the correctness of all examples.
 */
public class FunctionalProgrammingTest {
    
    private List<String> testData;
    private List<Integer> numbers;
    
    @BeforeEach
    void setUp() {
        testData = Arrays.asList("Alice", "Bob", "Charlie", "Diana", "Eve");
        numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    }
    
    @Test
    void testLambdaExpressions() {
        // Test basic lambda functionality
        Predicate<String> isLong = s -> s.length() > 4;
        assertThat(isLong.test("Alice")).isTrue();
        assertThat(isLong.test("Bob")).isFalse();
        
        // Test function composition
        Function<String, String> toUpper = String::toUpperCase;
        Function<String, Integer> getLength = String::length;
        
        Function<String, Integer> composed = toUpper.andThen(getLength);
        assertThat(composed.apply("hello")).isEqualTo(5);
    }
    
    @Test
    void testMethodReferences() {
        // Test static method reference
        Function<String, Integer> parseInt = Integer::parseInt;
        assertThat(parseInt.apply("123")).isEqualTo(123);
        
        // Test instance method reference
        List<String> names = Arrays.asList("alice", "bob", "charlie");
        List<String> upperNames = names.stream()
            .map(String::toUpperCase)
            .collect(Collectors.toList());
        
        assertThat(upperNames).containsExactly("ALICE", "BOB", "CHARLIE");
    }
    
    @Test
    void testFunctionalInterfaces() {
        // Test custom functional interface
        FunctionalInterfaces.Calculator calculator = (a, b, op) -> {
            switch (op) {
                case "add": return a + b;
                case "multiply": return a * b;
                default: return 0;
            }
        };
        
        assertThat(calculator.calculate(5, 3, "add")).isEqualTo(8);
        assertThat(calculator.calculate(5, 3, "multiply")).isEqualTo(15);
        
        // Test validator chaining
        FunctionalInterfaces.Validator<String> isNotEmpty = s -> !s.isEmpty();
        FunctionalInterfaces.Validator<String> isLongEnough = s -> s.length() >= 3;
        
        FunctionalInterfaces.Validator<String> combined = isNotEmpty.and(isLongEnough);
        assertThat(combined.validate("hello")).isTrue();
        assertThat(combined.validate("hi")).isFalse();
        assertThat(combined.validate("")).isFalse();
    }
    
    @Test
    void testStreams() {
        // Test basic stream operations
        List<String> result = testData.stream()
            .filter(name -> name.length() > 4)
            .map(String::toUpperCase)
            .collect(Collectors.toList());
        
        assertThat(result).containsExactly("ALICE", "CHARLIE", "DIANA");
        
        // Test terminal operations
        Optional<String> firstLongName = testData.stream()
            .filter(name -> name.length() > 4)
            .findFirst();
        
        assertThat(firstLongName).isPresent();
        assertThat(firstLongName.get()).isEqualTo("Alice");
        
        // Test reduction
        int sum = numbers.stream()
            .filter(n -> n % 2 == 0)
            .reduce(0, Integer::sum);
        
        assertThat(sum).isEqualTo(30); // 2 + 4 + 6 + 8 + 10
    }
    
    @Test
    void testOptional() {
        // Test empty Optional
        java.util.Optional<String> empty = java.util.Optional.empty();
        assertThat(empty.isPresent()).isFalse();
        assertThat(empty.orElse("default")).isEqualTo("default");
        
        // Test present Optional
        java.util.Optional<String> present = java.util.Optional.of("hello");
        assertThat(present.isPresent()).isTrue();
        assertThat(present.get()).isEqualTo("hello");
        
        // Test map operation
        java.util.Optional<String> mapped = present.map(String::toUpperCase);
        assertThat(mapped.get()).isEqualTo("HELLO");
        
        // Test filter operation
        java.util.Optional<String> filtered = present.filter(s -> s.length() > 3);
        assertThat(filtered.isPresent()).isTrue();
        
        java.util.Optional<String> filteredEmpty = present.filter(s -> s.length() > 10);
        assertThat(filteredEmpty.isPresent()).isFalse();
    }
    
    @Test
    void testCollectors() {
        // Test grouping
        Map<String, List<String>> grouped = testData.stream()
            .collect(Collectors.groupingBy(name -> name.substring(0, 1)));
        
        assertThat(grouped.get("A")).containsExactly("Alice");
        assertThat(grouped.get("B")).containsExactly("Bob");
        assertThat(grouped.get("C")).containsExactly("Charlie");
        
        // Test partitioning
        Map<Boolean, List<Integer>> partitioned = numbers.stream()
            .collect(Collectors.partitioningBy(n -> n % 2 == 0));
        
        assertThat(partitioned.get(true)).containsExactly(2, 4, 6, 8, 10);
        assertThat(partitioned.get(false)).containsExactly(1, 3, 5, 7, 9);
        
        // Test joining
        String joined = testData.stream()
            .collect(Collectors.joining(", "));
        
        assertThat(joined).isEqualTo("Alice, Bob, Charlie, Diana, Eve");
        
        // Test statistics
        IntSummaryStatistics stats = numbers.stream()
            .collect(Collectors.summarizingInt(Integer::intValue));
        
        assertThat(stats.getCount()).isEqualTo(10);
        assertThat(stats.getSum()).isEqualTo(55);
        assertThat(stats.getMin()).isEqualTo(1);
        assertThat(stats.getMax()).isEqualTo(10);
    }
    
    @Test
    void testDataProcessing() {
        // Test order processing
        List<DataProcessing.Order> orders = Arrays.asList(
            new DataProcessing.Order(1, "Alice", Arrays.asList(
                new DataProcessing.OrderItem("Laptop", 999.99, 1),
                new DataProcessing.OrderItem("Mouse", 29.99, 2)
            ))
        );
        
        double total = orders.stream()
            .flatMap(order -> order.getItems().stream())
            .mapToDouble(item -> item.getPrice() * item.getQuantity())
            .sum();
        
        assertThat(total).isCloseTo(1059.97, within(0.01));
        
        // Test employee analytics
        List<DataProcessing.Employee> employees = Arrays.asList(
            new DataProcessing.Employee("Alice", 25, 50000, "Engineering", "Developer"),
            new DataProcessing.Employee("Bob", 30, 60000, "Marketing", "Manager")
        );
        
        Map<String, Double> avgSalary = employees.stream()
            .collect(Collectors.groupingBy(
                DataProcessing.Employee::getDepartment,
                Collectors.averagingInt(DataProcessing.Employee::getSalary)
            ));
        
        assertThat(avgSalary.get("Engineering")).isEqualTo(50000.0);
        assertThat(avgSalary.get("Marketing")).isEqualTo(60000.0);
    }
    
    @Test
    void testParallelStreams() {
        List<Integer> largeNumbers = IntStream.rangeClosed(1, 1000)
            .boxed()
            .collect(Collectors.toList());
        
        // Test parallel processing
        long sum = largeNumbers.parallelStream()
            .mapToLong(Integer::longValue)
            .sum();
        
        assertThat(sum).isEqualTo(500500); // Sum of 1 to 1000
        
        // Test that parallel stream produces same result as sequential
        long sequentialSum = largeNumbers.stream()
            .mapToLong(Integer::longValue)
            .sum();
        
        assertThat(sum).isEqualTo(sequentialSum);
    }
    
    @Test
    void testComplexDataTransformation() {
        // Test complex data transformation pipeline
        List<Map<String, Object>> rawData = Arrays.asList(
            Map.of("name", "Alice", "age", 25, "salary", 50000),
            Map.of("name", "Bob", "age", 30, "salary", 60000),
            Map.of("name", "Charlie", "age", 35, "salary", 70000)
        );
        
        List<String> result = rawData.stream()
            .filter(person -> (Integer) person.get("age") > 25)
            .map(person -> (String) person.get("name"))
            .map(String::toUpperCase)
            .sorted()
            .collect(Collectors.toList());
        
        assertThat(result).containsExactly("BOB", "CHARLIE");
    }
    
    @Test
    void testErrorHandlingWithOptional() {
        // Test safe parsing with Optional
        List<String> rawNumbers = Arrays.asList("1", "2", "invalid", "4", "5");
        
        List<Integer> validNumbers = rawNumbers.stream()
            .map(this::safeParseInt)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
        
        assertThat(validNumbers).containsExactly(1, 2, 4, 5);
    }
    
    @Test
    void testCustomCollector() {
        // Test custom collector for finding longest string
        List<String> words = Arrays.asList("apple", "banana", "cherry", "date");
        
        String longest = words.stream()
            .collect(Collector.of(
                () -> new String[1],
                (result, word) -> {
                    if (result[0] == null || word.length() > result[0].length()) {
                        result[0] = word;
                    }
                },
                (result1, result2) -> {
                    if (result1[0] == null) return result2;
                    if (result2[0] == null) return result1;
                    return result1[0].length() > result2[0].length() ? result1 : result2;
                },
                result -> result[0]
            ));
        
        assertThat(longest).isEqualTo("banana");
    }
    
    @Test
    void testFunctionalComposition() {
        // Test function composition
        Function<String, String> addPrefix = s -> "Mr. " + s;
        Function<String, String> addSuffix = s -> s + " Jr.";
        Function<String, String> toUpper = String::toUpperCase;
        
        Function<String, String> composed = addPrefix
            .andThen(toUpper)
            .andThen(addSuffix);
        
        assertThat(composed.apply("john")).isEqualTo("MR. JOHN JR.");
    }
    
    @Test
    void testStreamLaziness() {
        // Test that intermediate operations are lazy
        List<String> result = new ArrayList<>();
        
        Stream<String> stream = testData.stream()
            .peek(name -> result.add("Processing: " + name))
            .filter(name -> name.length() > 4)
            .map(String::toUpperCase);
        
        // No processing should have happened yet
        assertThat(result).isEmpty();
        
        // Trigger terminal operation
        List<String> finalResult = stream.collect(Collectors.toList());
        
        // Now processing should have happened
        assertThat(result).hasSize(5); // All names were processed
        assertThat(finalResult).containsExactly("ALICE", "CHARLIE", "DIANA");
    }
    
    /**
     * Helper method for safe parsing
     */
    private Optional<Integer> safeParseInt(String s) {
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
    
    /**
     * Key Testing Takeaways:
     * 
     * 1. Test individual functions in isolation
     * 2. Test function composition and chaining
     * 3. Test edge cases and error conditions
     * 4. Test both sequential and parallel streams
     * 5. Test Optional handling for null safety
     * 6. Test custom collectors and reducers
     * 7. Test lazy evaluation behavior
     * 8. Test data transformation pipelines
     * 9. Use AssertJ for fluent assertions
     * 10. Test both positive and negative scenarios
     */
}
