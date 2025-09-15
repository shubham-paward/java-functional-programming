package com.functionalprogramming.intermediate;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;

/**
 * Collectors - Reducing Streams to Collections
 * 
 * Collectors are implementations of the Collector interface that implement
 * various useful reduction operations, such as accumulating elements into
 * collections, summarizing elements according to various criteria, etc.
 * 
 * Key Benefits:
 * - Transform streams into different collection types
 * - Group and partition data
 * - Calculate statistics and summaries
 * - Customize collection behavior
 */
public class CollectorsExamples {
    
    public static void main(String[] args) {
        System.out.println("=== Collectors Examples ===\n");
        
        // Example 1: Basic collection operations
        demonstrateBasicCollections();
        
        // Example 2: Grouping operations
        demonstrateGrouping();
        
        // Example 3: Partitioning operations
        demonstratePartitioning();
        
        // Example 4: Statistical operations
        demonstrateStatistics();
        
        // Example 5: Custom collectors
        demonstrateCustomCollectors();
    }
    
    /**
     * Example 1: Basic Collection Operations
     * 
     * Converting streams to different collection types.
     */
    private static void demonstrateBasicCollections() {
        System.out.println("1. Basic Collection Operations:");
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "Diana", "Eve");
        
        // Collect to List
        List<String> upperNames = names.stream()
            .map(String::toUpperCase)
            .collect(Collectors.toList());
        System.out.println("  To List: " + upperNames);
        
        // Collect to Set
        Set<String> uniqueNames = names.stream()
            .map(String::toLowerCase)
            .collect(Collectors.toSet());
        System.out.println("  To Set: " + uniqueNames);
        
        // Collect to specific collection type
        TreeSet<String> sortedNames = names.stream()
            .collect(Collectors.toCollection(TreeSet::new));
        System.out.println("  To TreeSet: " + sortedNames);
        
        // Join strings
        String joined = names.stream()
            .collect(Collectors.joining(", "));
        System.out.println("  Joined: " + joined);
        
        // Join with prefix and suffix
        String joinedWithBrackets = names.stream()
            .collect(Collectors.joining(", ", "[", "]"));
        System.out.println("  Joined with brackets: " + joinedWithBrackets);
        
        System.out.println();
    }
    
    /**
     * Example 2: Grouping Operations
     * 
     * Grouping elements by a classification function.
     */
    private static void demonstrateGrouping() {
        System.out.println("2. Grouping Operations:");
        
        List<Employee> employees = Arrays.asList(
            new Employee("Alice", 25, 50000, "Engineering"),
            new Employee("Bob", 30, 60000, "Marketing"),
            new Employee("Charlie", 35, 70000, "Engineering"),
            new Employee("Diana", 28, 55000, "HR"),
            new Employee("Eve", 32, 65000, "Engineering"),
            new Employee("Frank", 29, 58000, "Marketing"),
            new Employee("Grace", 31, 62000, "HR")
        );
        
        // Group by department
        Map<String, List<Employee>> byDepartment = employees.stream()
            .collect(Collectors.groupingBy(Employee::getDepartment));
        
        System.out.println("  Grouped by department:");
        byDepartment.forEach((dept, emps) -> {
            System.out.println("    " + dept + ": " + emps.size() + " employees");
            emps.forEach(emp -> System.out.println("      " + emp.getName()));
        });
        
        // Group by department with counting
        Map<String, Long> countByDepartment = employees.stream()
            .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
        
        System.out.println("  Count by department: " + countByDepartment);
        
        // Group by department with average salary
        Map<String, Double> avgSalaryByDept = employees.stream()
            .collect(Collectors.groupingBy(
                Employee::getDepartment,
                Collectors.averagingInt(Employee::getSalary)
            ));
        
        System.out.println("  Average salary by department:");
        avgSalaryByDept.forEach((dept, avg) -> 
            System.out.println("    " + dept + ": $" + String.format("%.0f", avg)));
        
        // Group by department with max salary
        Map<String, Optional<Employee>> maxSalaryByDept = employees.stream()
            .collect(Collectors.groupingBy(
                Employee::getDepartment,
                Collectors.maxBy(Comparator.comparing(Employee::getSalary))
            ));
        
        System.out.println("  Highest paid in each department:");
        maxSalaryByDept.forEach((dept, emp) -> 
            System.out.println("    " + dept + ": " + emp.get().getName() + " ($" + emp.get().getSalary() + ")"));
        
        System.out.println();
    }
    
    /**
     * Example 3: Partitioning Operations
     * 
     * Partitioning elements into two groups based on a predicate.
     */
    private static void demonstratePartitioning() {
        System.out.println("3. Partitioning Operations:");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // Partition by even/odd
        Map<Boolean, List<Integer>> partitioned = numbers.stream()
            .collect(Collectors.partitioningBy(n -> n % 2 == 0));
        
        System.out.println("  Even numbers: " + partitioned.get(true));
        System.out.println("  Odd numbers: " + partitioned.get(false));
        
        // Partition with counting
        Map<Boolean, Long> countPartitioned = numbers.stream()
            .collect(Collectors.partitioningBy(n -> n % 2 == 0, Collectors.counting()));
        
        System.out.println("  Count of even: " + countPartitioned.get(true));
        System.out.println("  Count of odd: " + countPartitioned.get(false));
        
        // Partition with sum
        Map<Boolean, Integer> sumPartitioned = numbers.stream()
            .collect(Collectors.partitioningBy(n -> n % 2 == 0, 
                Collectors.summingInt(Integer::intValue)));
        
        System.out.println("  Sum of even: " + sumPartitioned.get(true));
        System.out.println("  Sum of odd: " + sumPartitioned.get(false));
        
        System.out.println();
    }
    
    /**
     * Example 4: Statistical Operations
     * 
     * Calculating statistics and summaries.
     */
    private static void demonstrateStatistics() {
        System.out.println("4. Statistical Operations:");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // Sum
        int sum = numbers.stream()
            .collect(Collectors.summingInt(Integer::intValue));
        System.out.println("  Sum: " + sum);
        
        // Average
        double average = numbers.stream()
            .collect(Collectors.averagingInt(Integer::intValue));
        System.out.println("  Average: " + average);
        
        // Min
        Optional<Integer> min = numbers.stream()
            .collect(Collectors.minBy(Integer::compareTo));
        System.out.println("  Min: " + min.orElse(0));
        
        // Max
        Optional<Integer> max = numbers.stream()
            .collect(Collectors.maxBy(Integer::compareTo));
        System.out.println("  Max: " + max.orElse(0));
        
        // Count
        long count = numbers.stream()
            .collect(Collectors.counting());
        System.out.println("  Count: " + count);
        
        // Summary statistics
        IntSummaryStatistics stats = numbers.stream()
            .collect(Collectors.summarizingInt(Integer::intValue));
        
        System.out.println("  Summary Statistics:");
        System.out.println("    Count: " + stats.getCount());
        System.out.println("    Sum: " + stats.getSum());
        System.out.println("    Min: " + stats.getMin());
        System.out.println("    Max: " + stats.getMax());
        System.out.println("    Average: " + stats.getAverage());
        
        System.out.println();
    }
    
    /**
     * Example 5: Custom Collectors
     * 
     * Creating custom collectors for specific needs.
     */
    private static void demonstrateCustomCollectors() {
        System.out.println("5. Custom Collectors:");
        
        List<String> words = Arrays.asList("apple", "banana", "cherry", "date", "elderberry");
        
        // Custom collector to find the longest word
        String longestWord = words.stream()
            .collect(Collector.of(
                () -> new String[1], // supplier
                (result, word) -> {  // accumulator
                    if (result[0] == null || word.length() > result[0].length()) {
                        result[0] = word;
                    }
                },
                (result1, result2) -> { // combiner
                    if (result1[0] == null) return result2;
                    if (result2[0] == null) return result1;
                    return result1[0].length() > result2[0].length() ? result1 : result2;
                },
                result -> result[0] // finisher
            ));
        
        System.out.println("  Longest word: " + longestWord);
        
        // Custom collector to create a frequency map
        Map<Character, Long> charFrequency = words.stream()
            .flatMap(word -> word.chars().mapToObj(c -> (char) c))
            .collect(Collectors.groupingBy(
                Function.identity(),
                Collectors.counting()
            ));
        
        System.out.println("  Character frequency:");
        charFrequency.entrySet().stream()
            .sorted(Map.Entry.<Character, Long>comparingByValue().reversed())
            .forEach(entry -> System.out.println("    " + entry.getKey() + ": " + entry.getValue()));
        
        // Custom collector to create a custom summary
        WordSummary summary = words.stream()
            .collect(Collector.of(
                WordSummary::new, // supplier
                WordSummary::add, // accumulator
                WordSummary::combine, // combiner
                Function.identity() // finisher
            ));
        
        System.out.println("  Word Summary:");
        System.out.println("    Total words: " + summary.getCount());
        System.out.println("    Total characters: " + summary.getTotalLength());
        System.out.println("    Average length: " + summary.getAverageLength());
        System.out.println("    Longest word: " + summary.getLongestWord());
        System.out.println("    Shortest word: " + summary.getShortestWord());
        
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
            return name + "($" + salary + ")";
        }
    }
    
    /**
     * Custom class for word summary
     */
    static class WordSummary {
        private int count = 0;
        private int totalLength = 0;
        private String longestWord = "";
        private String shortestWord = "";
        
        public void add(String word) {
            count++;
            totalLength += word.length();
            
            if (longestWord.isEmpty() || word.length() > longestWord.length()) {
                longestWord = word;
            }
            
            if (shortestWord.isEmpty() || word.length() < shortestWord.length()) {
                shortestWord = word;
            }
        }
        
        public WordSummary combine(WordSummary other) {
            WordSummary result = new WordSummary();
            result.count = this.count + other.count;
            result.totalLength = this.totalLength + other.totalLength;
            result.longestWord = this.longestWord.length() > other.longestWord.length() 
                ? this.longestWord : other.longestWord;
            result.shortestWord = this.shortestWord.isEmpty() ? other.shortestWord
                : (other.shortestWord.isEmpty() ? this.shortestWord
                : (this.shortestWord.length() < other.shortestWord.length() 
                    ? this.shortestWord : other.shortestWord));
            return result;
        }
        
        public int getCount() { return count; }
        public int getTotalLength() { return totalLength; }
        public double getAverageLength() { return count > 0 ? (double) totalLength / count : 0; }
        public String getLongestWord() { return longestWord; }
        public String getShortestWord() { return shortestWord; }
    }
    
    /**
     * Key Takeaways:
     * 
     * 1. Collectors transform streams into collections
     * 2. Use Collectors.toList(), toSet(), toMap() for basic collections
     * 3. Use Collectors.groupingBy() for grouping operations
     * 4. Use Collectors.partitioningBy() for binary partitioning
     * 5. Use Collectors.joining() for string concatenation
     * 6. Use Collectors.summarizingInt() for statistics
     * 7. Create custom collectors for specific needs
     * 8. Collectors can be chained and combined
     * 
     * Common Collectors:
     * - toList(): collects to List
     * - toSet(): collects to Set
     * - toMap(): collects to Map
     * - toCollection(): collects to specific collection type
     * - joining(): joins strings
     * - groupingBy(): groups by classification function
     * - partitioningBy(): partitions by predicate
     * - counting(): counts elements
     * - summingInt(): sums integer values
     * - averagingInt(): averages integer values
     * - summarizingInt(): provides summary statistics
     * - maxBy(): finds maximum element
     * - minBy(): finds minimum element
     */
}
