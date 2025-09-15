package com.functionalprogramming.basics;

import java.util.*;
import java.util.function.*;

/**
 * Functional Interfaces - The Building Blocks of Functional Programming
 * 
 * A functional interface is an interface that contains exactly one abstract method.
 * They are the foundation of lambda expressions and method references in Java.
 * 
 * Java 8 introduced the @FunctionalInterface annotation to mark interfaces
 * that are intended to be functional interfaces.
 */
public class FunctionalInterfaces {
    
    public static void main(String[] args) {
        System.out.println("=== Functional Interfaces Examples ===\n");
        
        // Example 1: Built-in functional interfaces
        demonstrateBuiltInInterfaces();
        
        // Example 2: Creating custom functional interfaces
        demonstrateCustomInterfaces();
        
        // Example 3: Functional interfaces with streams
        demonstrateStreamUsage();
        
        // Example 4: Chaining functional interfaces
        demonstrateChaining();
        
        // Example 5: Real-world application
        demonstrateRealWorldApplication();
    }
    
    /**
     * Example 1: Built-in Functional Interfaces
     * 
     * Java provides many functional interfaces in java.util.function package.
     * These cover most common use cases for functional programming.
     */
    private static void demonstrateBuiltInInterfaces() {
        System.out.println("1. Built-in Functional Interfaces:");
        
        // Predicate<T> - takes one argument, returns boolean
        Predicate<String> isLong = s -> s.length() > 5;
        System.out.println("  Is 'Hello' long? " + isLong.test("Hello"));
        System.out.println("  Is 'Functional' long? " + isLong.test("Functional"));
        
        // Function<T, R> - takes one argument, returns a result
        Function<String, Integer> getLength = String::length;
        System.out.println("  Length of 'Java': " + getLength.apply("Java"));
        
        // Consumer<T> - takes one argument, returns void
        Consumer<String> printer = s -> System.out.println("  Printing: " + s);
        printer.accept("Functional Programming");
        
        // Supplier<T> - takes no arguments, returns a result
        Supplier<Double> randomSupplier = Math::random;
        System.out.println("  Random number: " + randomSupplier.get());
        
        // UnaryOperator<T> - takes one argument of type T, returns T
        UnaryOperator<String> toUpperCase = String::toUpperCase;
        System.out.println("  Uppercase: " + toUpperCase.apply("hello"));
        
        // BinaryOperator<T> - takes two arguments of type T, returns T
        BinaryOperator<Integer> adder = Integer::sum;
        System.out.println("  5 + 3 = " + adder.apply(5, 3));
        System.out.println();
    }
    
    /**
     * Example 2: Creating Custom Functional Interfaces
     * 
     * You can create your own functional interfaces for specific use cases.
     * Always use @FunctionalInterface annotation for clarity.
     */
    private static void demonstrateCustomInterfaces() {
        System.out.println("2. Custom Functional Interfaces:");
        
        // Custom functional interface
        Calculator calculator = (a, b, operation) -> {
            switch (operation) {
                case "add": return a + b;
                case "multiply": return a * b;
                case "subtract": return a - b;
                default: return 0;
            }
        };
        
        System.out.println("  10 + 5 = " + calculator.calculate(10, 5, "add"));
        System.out.println("  10 * 5 = " + calculator.calculate(10, 5, "multiply"));
        System.out.println("  10 - 5 = " + calculator.calculate(10, 5, "subtract"));
        
        // Another custom interface
        Validator<String> emailValidator = email -> email.contains("@") && email.contains(".");
        System.out.println("  Is 'test@example.com' valid? " + emailValidator.validate("test@example.com"));
        System.out.println("  Is 'invalid-email' valid? " + emailValidator.validate("invalid-email"));
        System.out.println();
    }
    
    /**
     * Example 3: Functional Interfaces with Streams
     * 
     * Functional interfaces work seamlessly with Java Streams API.
     */
    private static void demonstrateStreamUsage() {
        System.out.println("3. Functional Interfaces with Streams:");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // Using Predicate for filtering
        Predicate<Integer> isEven = n -> n % 2 == 0;
        Predicate<Integer> isGreaterThan5 = n -> n > 5;
        
        // Using Function for mapping
        Function<Integer, String> numberToString = n -> "Number: " + n;
        
        // Using Consumer for forEach
        Consumer<String> printer = System.out::println;
        
        System.out.println("  Even numbers greater than 5:");
        numbers.stream()
               .filter(isEven.and(isGreaterThan5))  // Chaining predicates
               .map(numberToString)
               .forEach(printer);
        
        System.out.println();
    }
    
    /**
     * Example 4: Chaining Functional Interfaces
     * 
     * Functional interfaces can be chained using default methods
     * like and(), or(), negate(), andThen(), compose().
     */
    private static void demonstrateChaining() {
        System.out.println("4. Chaining Functional Interfaces:");
        
        // Chaining Predicates
        Predicate<String> isNotEmpty = s -> !s.isEmpty();
        Predicate<String> isLongEnough = s -> s.length() >= 3;
        Predicate<String> startsWithA = s -> s.startsWith("A");
        
        Predicate<String> complexPredicate = isNotEmpty
            .and(isLongEnough)
            .and(startsWithA);
        
        List<String> names = Arrays.asList("Alice", "Bob", "Anna", "Amy", "Al");
        
        System.out.println("  Names matching complex criteria:");
        names.stream()
             .filter(complexPredicate)
             .forEach(System.out::println);
        
        // Chaining Functions
        Function<String, String> addPrefix = s -> "Mr. " + s;
        Function<String, String> addSuffix = s -> s + " Jr.";
        
        Function<String, String> fullTransformation = addPrefix.andThen(addSuffix);
        
        System.out.println("  Full transformation: " + fullTransformation.apply("John"));
        System.out.println();
    }
    
    /**
     * Example 5: Real-world Application
     * 
     * Practical example showing how functional interfaces can be used
     * in a real application scenario.
     */
    private static void demonstrateRealWorldApplication() {
        System.out.println("5. Real-world Application:");
        
        List<Employee> employees = Arrays.asList(
            new Employee("Alice", 25, 50000, "Engineering"),
            new Employee("Bob", 30, 60000, "Marketing"),
            new Employee("Charlie", 35, 70000, "Engineering"),
            new Employee("Diana", 28, 55000, "HR"),
            new Employee("Eve", 32, 65000, "Engineering")
        );
        
        // Define business rules using functional interfaces
        Predicate<Employee> isEngineer = emp -> "Engineering".equals(emp.getDepartment());
        Predicate<Employee> isExperienced = emp -> emp.getAge() >= 30;
        Predicate<Employee> isHighEarning = emp -> emp.getSalary() > 60000;
        
        Function<Employee, String> getFormattedInfo = emp -> 
            String.format("%s (%s) - $%,d", emp.getName(), emp.getDepartment(), emp.getSalary());
        
        Consumer<String> reportPrinter = info -> System.out.println("  " + info);
        
        // Find experienced engineers with high earnings
        System.out.println("  Experienced high-earning engineers:");
        employees.stream()
                 .filter(isEngineer.and(isExperienced).and(isHighEarning))
                 .map(getFormattedInfo)
                 .forEach(reportPrinter);
        
        // Calculate total salary for engineers
        Function<Employee, Integer> getSalary = Employee::getSalary;
        BinaryOperator<Integer> sumSalaries = Integer::sum;
        
        int totalEngineerSalary = employees.stream()
                                          .filter(isEngineer)
                                          .map(getSalary)
                                          .reduce(0, sumSalaries);
        
        System.out.println("  Total salary for engineers: $" + totalEngineerSalary);
        System.out.println();
    }
    
    /**
     * Custom Functional Interface
     */
    @FunctionalInterface
    public interface Calculator {
        int calculate(int a, int b, String operation);
    }
    
    /**
     * Another Custom Functional Interface
     */
    @FunctionalInterface
    public interface Validator<T> {
        boolean validate(T item);
        
        // Default method - this is allowed in functional interfaces
        default Validator<T> and(Validator<T> other) {
            return item -> this.validate(item) && other.validate(item);
        }
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
    }
}
