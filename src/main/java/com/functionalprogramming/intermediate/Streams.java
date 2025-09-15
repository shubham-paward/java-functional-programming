package com.functionalprogramming.intermediate;

import java.util.*;
import java.util.stream.*;

/**
 * Java Streams - Functional Programming with Collections
 * 
 * Streams provide a functional approach to processing collections of data.
 * They allow you to express complex data processing queries in a declarative way.
 * 
 * Key Characteristics:
 * - Streams are lazy (computation happens only when terminal operation is called)
 * - Streams are not data structures (they don't store data)
 * - Streams are functional in nature (they don't modify the source)
 * - Streams can be processed in parallel
 */
public class Streams {
    
    public static void main(String[] args) {
        System.out.println("=== Java Streams Examples ===\n");
        
        // Example 1: Basic stream operations
        demonstrateBasicOperations();
        
        // Example 2: Intermediate operations
        demonstrateIntermediateOperations();
        
        // Example 3: Terminal operations
        demonstrateTerminalOperations();
        
        // Example 4: Parallel streams
        demonstrateParallelStreams();
        
        // Example 5: Real-world data processing
        demonstrateRealWorldProcessing();
    }
    
    /**
     * Example 1: Basic Stream Operations
     * 
     * Introduction to creating streams and basic operations.
     */
    private static void demonstrateBasicOperations() {
        System.out.println("1. Basic Stream Operations:");
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "Diana", "Eve");
        
        // Creating a stream from a collection
        Stream<String> nameStream = names.stream();
        
        // Basic operations: filter, map, forEach
        System.out.println("  Names starting with 'A':");
        nameStream
            .filter(name -> name.startsWith("A"))
            .map(String::toUpperCase)
            .forEach(name -> System.out.println("    " + name));
        
        // Creating stream from array
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println("  Even numbers:");
        Arrays.stream(numbers)
              .filter(n -> n % 2 == 0)
              .forEach(n -> System.out.println("    " + n));
        
        // Creating stream using Stream.of()
        System.out.println("  Colors:");
        Stream.of("Red", "Green", "Blue", "Yellow")
              .forEach(color -> System.out.println("    " + color));
        
        System.out.println();
    }
    
    /**
     * Example 2: Intermediate Operations
     * 
     * Intermediate operations return a new stream and are lazy.
     * They don't execute until a terminal operation is called.
     */
    private static void demonstrateIntermediateOperations() {
        System.out.println("2. Intermediate Operations:");
        
        List<String> words = Arrays.asList("apple", "banana", "cherry", "date", "elderberry", "fig");
        
        // filter() - filters elements based on a predicate
        System.out.println("  Words longer than 5 characters:");
        words.stream()
             .filter(word -> word.length() > 5)
             .forEach(word -> System.out.println("    " + word));
        
        // map() - transforms each element
        System.out.println("  Word lengths:");
        words.stream()
             .map(String::length)
             .forEach(length -> System.out.println("    " + length));
        
        // distinct() - removes duplicates
        List<String> duplicates = Arrays.asList("apple", "banana", "apple", "cherry", "banana");
        System.out.println("  Unique words:");
        duplicates.stream()
                  .distinct()
                  .forEach(word -> System.out.println("    " + word));
        
        // sorted() - sorts elements
        System.out.println("  Sorted words:");
        words.stream()
             .sorted()
             .forEach(word -> System.out.println("    " + word));
        
        // limit() - limits the number of elements
        System.out.println("  First 3 words:");
        words.stream()
             .limit(3)
             .forEach(word -> System.out.println("    " + word));
        
        // skip() - skips the first n elements
        System.out.println("  Words after skipping first 2:");
        words.stream()
             .skip(2)
             .forEach(word -> System.out.println("    " + word));
        
        System.out.println();
    }
    
    /**
     * Example 3: Terminal Operations
     * 
     * Terminal operations produce a result or side effect and trigger
     * the execution of the stream pipeline.
     */
    private static void demonstrateTerminalOperations() {
        System.out.println("3. Terminal Operations:");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // forEach() - performs an action for each element
        System.out.println("  Numbers:");
        numbers.stream()
               .forEach(n -> System.out.println("    " + n));
        
        // collect() - collects elements into a collection
        List<Integer> evenNumbers = numbers.stream()
                                          .filter(n -> n % 2 == 0)
                                          .collect(Collectors.toList());
        System.out.println("  Even numbers: " + evenNumbers);
        
        // reduce() - reduces stream to a single value
        int sum = numbers.stream()
                        .reduce(0, Integer::sum);
        System.out.println("  Sum of all numbers: " + sum);
        
        // max() and min() - find maximum and minimum
        Optional<Integer> max = numbers.stream().max(Integer::compareTo);
        Optional<Integer> min = numbers.stream().min(Integer::compareTo);
        System.out.println("  Max: " + max.orElse(0));
        System.out.println("  Min: " + min.orElse(0));
        
        // count() - counts elements
        long count = numbers.stream()
                           .filter(n -> n > 5)
                           .count();
        System.out.println("  Count of numbers > 5: " + count);
        
        // anyMatch(), allMatch(), noneMatch() - boolean operations
        boolean hasEven = numbers.stream().anyMatch(n -> n % 2 == 0);
        boolean allPositive = numbers.stream().allMatch(n -> n > 0);
        boolean noneNegative = numbers.stream().noneMatch(n -> n < 0);
        
        System.out.println("  Has even numbers: " + hasEven);
        System.out.println("  All positive: " + allPositive);
        System.out.println("  None negative: " + noneNegative);
        
        // findFirst() and findAny() - find elements
        Optional<Integer> firstEven = numbers.stream()
                                           .filter(n -> n % 2 == 0)
                                           .findFirst();
        System.out.println("  First even number: " + firstEven.orElse(0));
        
        System.out.println();
    }
    
    /**
     * Example 4: Parallel Streams
     * 
     * Parallel streams can process data concurrently using multiple threads.
     * Useful for CPU-intensive operations on large datasets.
     */
    private static void demonstrateParallelStreams() {
        System.out.println("4. Parallel Streams:");
        
        List<Integer> largeNumbers = IntStream.rangeClosed(1, 1000000)
                                            .boxed()
                                            .collect(Collectors.toList());
        
        // Sequential processing
        long startTime = System.currentTimeMillis();
        long sequentialSum = largeNumbers.stream()
                                       .mapToLong(Integer::longValue)
                                       .sum();
        long sequentialTime = System.currentTimeMillis() - startTime;
        
        // Parallel processing
        startTime = System.currentTimeMillis();
        long parallelSum = largeNumbers.parallelStream()
                                     .mapToLong(Integer::longValue)
                                     .sum();
        long parallelTime = System.currentTimeMillis() - startTime;
        
        System.out.println("  Sequential sum: " + sequentialSum + " (Time: " + sequentialTime + "ms)");
        System.out.println("  Parallel sum: " + parallelSum + " (Time: " + parallelTime + "ms)");
        System.out.println("  Speedup: " + (double) sequentialTime / parallelTime + "x");
        
        // Note: Parallel streams are not always faster
        // They have overhead and work best with CPU-intensive tasks
        System.out.println();
    }
    
    /**
     * Example 5: Real-world Data Processing
     * 
     * Practical example showing how streams can be used for
     * real-world data processing scenarios.
     */
    private static void demonstrateRealWorldProcessing() {
        System.out.println("5. Real-world Data Processing:");
        
        List<Employee> employees = Arrays.asList(
            new Employee("Alice", 25, 50000, "Engineering"),
            new Employee("Bob", 30, 60000, "Marketing"),
            new Employee("Charlie", 35, 70000, "Engineering"),
            new Employee("Diana", 28, 55000, "HR"),
            new Employee("Eve", 32, 65000, "Engineering"),
            new Employee("Frank", 29, 58000, "Marketing"),
            new Employee("Grace", 31, 62000, "HR")
        );
        
        // Find average salary by department
        System.out.println("  Average salary by department:");
        employees.stream()
                .collect(Collectors.groupingBy(
                    Employee::getDepartment,
                    Collectors.averagingInt(Employee::getSalary)
                ))
                .forEach((dept, avgSalary) -> 
                    System.out.println("    " + dept + ": $" + String.format("%.0f", avgSalary)));
        
        // Find highest paid employee in each department
        System.out.println("  Highest paid employee in each department:");
        employees.stream()
                .collect(Collectors.groupingBy(
                    Employee::getDepartment,
                    Collectors.maxBy(Comparator.comparing(Employee::getSalary))
                ))
                .forEach((dept, emp) -> 
                    System.out.println("    " + dept + ": " + emp.get().getName() + " ($" + emp.get().getSalary() + ")"));
        
        // Create a summary report
        System.out.println("  Department summary:");
        Map<String, List<Employee>> byDepartment = employees.stream()
                                                           .collect(Collectors.groupingBy(Employee::getDepartment));
        
        byDepartment.forEach((dept, emps) -> {
            int totalSalary = emps.stream().mapToInt(Employee::getSalary).sum();
            double avgAge = emps.stream().mapToInt(Employee::getAge).average().orElse(0);
            System.out.println("    " + dept + ": " + emps.size() + " employees, " +
                             "Total salary: $" + totalSalary + ", Average age: " + String.format("%.1f", avgAge));
        });
        
        System.out.println();
    }
    
    /**
     * Helper class for examples
     */
    static class Employee {
        private String name;
        private int age;
        private int salary;
        private String department;
        
        public Employee(String name, int age, int salary, String department) {
            this.name = name;
            this.age = age;
            this.salary = salary;
            this.department = department;
        }
        
        public String getName() { return name; }
        public int getAge() { return age; }
        public int getSalary() { return salary; }
        public String getDepartment() { return department; }
        
        @Override
        public String toString() {
            return name + "(" + age + ", $" + salary + ", " + department + ")";
        }
    }
    
    /**
     * Key Takeaways:
     * 
     * 1. Streams provide a functional approach to data processing
     * 2. Intermediate operations are lazy and return new streams
     * 3. Terminal operations trigger execution and produce results
     * 4. Streams can be processed in parallel for better performance
     * 5. Streams are immutable and don't modify the source
     * 
     * Common Intermediate Operations:
     * - filter(Predicate<T>): filters elements
     * - map(Function<T,R>): transforms elements
     * - distinct(): removes duplicates
     * - sorted(): sorts elements
     * - limit(long): limits number of elements
     * - skip(long): skips elements
     * 
     * Common Terminal Operations:
     * - forEach(Consumer<T>): performs action for each element
     * - collect(Collector<T,A,R>): collects elements into collection
     * - reduce(BinaryOperator<T>): reduces to single value
     * - count(): counts elements
     * - anyMatch(Predicate<T>): checks if any element matches
     * - allMatch(Predicate<T>): checks if all elements match
     * - noneMatch(Predicate<T>): checks if no elements match
     * - findFirst(): finds first element
     * - findAny(): finds any element
     */
}
