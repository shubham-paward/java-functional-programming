package com.functionalprogramming.basics;

import java.util.*;
import java.util.function.*;

/**
 * Method References - Shorthand for Lambda Expressions
 * 
 * Method references are a special type of lambda expressions that refer to methods
 * without invoking them. They provide a more readable way to write lambda expressions
 * when the lambda just calls an existing method.
 * 
 * Types of Method References:
 * 1. Static method reference: Class::staticMethod
 * 2. Instance method reference: instance::instanceMethod
 * 3. Instance method of arbitrary object: Class::instanceMethod
 * 4. Constructor reference: Class::new
 */
public class MethodReferences {
    
    public static void main(String[] args) {
        System.out.println("=== Method References Examples ===\n");
        
        // Example 1: Static method references
        demonstrateStaticMethodReferences();
        
        // Example 2: Instance method references
        demonstrateInstanceMethodReferences();
        
        // Example 3: Instance method of arbitrary object
        demonstrateArbitraryObjectMethodReferences();
        
        // Example 4: Constructor references
        demonstrateConstructorReferences();
        
        // Example 5: Real-world examples
        demonstrateRealWorldExamples();
    }
    
    /**
     * Example 1: Static Method References
     * 
     * When a lambda expression calls a static method, you can use
     * Class::staticMethod syntax.
     */
    private static void demonstrateStaticMethodReferences() {
        System.out.println("1. Static Method References:");
        
        List<String> numbers = Arrays.asList("1", "2", "3", "4", "5");
        
        // Lambda way
        List<Integer> lambdaResult = numbers.stream()
            .map(s -> Integer.parseInt(s))
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        
        // Method reference way (much cleaner!)
        List<Integer> methodRefResult = numbers.stream()
            .map(Integer::parseInt)
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        
        System.out.println("  Lambda way: " + lambdaResult);
        System.out.println("  Method ref way: " + methodRefResult);
        
        // Another example with Math.max
        BinaryOperator<Integer> maxLambda = (a, b) -> Math.max(a, b);
        BinaryOperator<Integer> maxMethodRef = Math::max;
        
        System.out.println("  Max of 10 and 20 (lambda): " + maxLambda.apply(10, 20));
        System.out.println("  Max of 10 and 20 (method ref): " + maxMethodRef.apply(10, 20));
        System.out.println();
    }
    
    /**
     * Example 2: Instance Method References
     * 
     * When a lambda expression calls an instance method of a specific object,
     * you can use instance::methodName syntax.
     */
    private static void demonstrateInstanceMethodReferences() {
        System.out.println("2. Instance Method References:");
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        
        // Create a StringBuilder instance
        StringBuilder sb = new StringBuilder();
        
        // Lambda way
        Consumer<String> lambdaAppender = s -> sb.append(s).append(" ");
        
        // Method reference way
        Consumer<String> methodRefAppender = sb::append;
        
        // Apply both
        System.out.println("  Using lambda:");
        names.forEach(lambdaAppender);
        System.out.println("  Result: " + sb.toString());
        
        // Reset StringBuilder
        sb.setLength(0);
        
        System.out.println("  Using method reference:");
        names.forEach(methodRefAppender);
        System.out.println("  Result: " + sb.toString());
        System.out.println();
    }
    
    /**
     * Example 3: Instance Method of Arbitrary Object
     * 
     * When a lambda expression calls an instance method of an arbitrary object
     * of a particular type, you can use Class::instanceMethod syntax.
     */
    private static void demonstrateArbitraryObjectMethodReferences() {
        System.out.println("3. Instance Method of Arbitrary Object:");
        
        List<String> words = Arrays.asList("hello", "world", "java", "functional");
        
        // Lambda way
        List<String> lambdaResult = words.stream()
            .map(s -> s.toUpperCase())
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        
        // Method reference way
        List<String> methodRefResult = words.stream()
            .map(String::toUpperCase)
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        
        System.out.println("  Original: " + words);
        System.out.println("  Lambda result: " + lambdaResult);
        System.out.println("  Method ref result: " + methodRefResult);
        
        // Another example with length()
        List<Integer> lengths = words.stream()
            .map(String::length)
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        
        System.out.println("  Word lengths: " + lengths);
        System.out.println();
    }
    
    /**
     * Example 4: Constructor References
     * 
     * When a lambda expression just creates an object, you can use
     * Class::new syntax.
     */
    private static void demonstrateConstructorReferences() {
        System.out.println("4. Constructor References:");
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        
        // Lambda way
        List<Person> lambdaPeople = names.stream()
            .map(name -> new Person(name))
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        
        // Constructor reference way
        List<Person> constructorRefPeople = names.stream()
            .map(Person::new)
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        
        System.out.println("  Lambda way: " + lambdaPeople);
        System.out.println("  Constructor ref way: " + constructorRefPeople);
        System.out.println();
    }
    
    /**
     * Example 5: Real-world Examples
     * 
     * Practical examples showing how method references make code more readable.
     */
    private static void demonstrateRealWorldExamples() {
        System.out.println("5. Real-world Examples:");
        
        List<Person> people = Arrays.asList(
            new Person("Alice", 25),
            new Person("Bob", 30),
            new Person("Charlie", 35),
            new Person("Diana", 28)
        );
        
        // Filter and map using method references
        List<String> adultNames = people.stream()
            .filter(Person::isAdult)
            .map(Person::getName)
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        
        System.out.println("  Adult names: " + adultNames);
        
        // Sort using method references
        List<Person> sortedByAge = people.stream()
            .sorted(Comparator.comparing(Person::getAge))
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        
        System.out.println("  Sorted by age: " + sortedByAge);
        
        // Print using method references
        System.out.println("  All people:");
        people.forEach(System.out::println);
        System.out.println();
    }
    
    /**
     * Helper class for examples
     */
    static class Person {
        private String name;
        private int age;
        
        public Person(String name) {
            this.name = name;
            this.age = 0;
        }
        
        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
        
        public String getName() {
            return name;
        }
        
        public int getAge() {
            return age;
        }
        
        public boolean isAdult() {
            return age >= 18;
        }
        
        @Override
        public String toString() {
            return name + "(" + age + ")";
        }
    }
    
    /**
     * Key Takeaways:
     * 
     * 1. Method references make code more readable and concise
     * 2. They are syntactic sugar for lambda expressions
     * 3. Four types: static, instance, arbitrary object, constructor
     * 4. Use when lambda just calls an existing method
     * 5. Perfect for working with streams and functional interfaces
     * 
     * When to use Method References:
     * - When lambda just calls a method: s -> s.toUpperCase() → String::toUpperCase
     * - When lambda just creates an object: s -> new Person(s) → Person::new
     * - When lambda just calls a static method: s -> Integer.parseInt(s) → Integer::parseInt
     * 
     * When NOT to use Method References:
     * - When lambda has complex logic
     * - When lambda calls multiple methods
     * - When lambda has side effects beyond method call
     */
}
