package com.functionalprogramming.intermediate;

import java.util.*;
import java.util.function.*;

/**
 * Optional - Handling Null Values Safely
 * 
 * Optional is a container object that may or may not contain a non-null value.
 * It's designed to help developers write more robust code by explicitly
 * handling the possibility of null values.
 * 
 * Key Benefits:
 * - Eliminates NullPointerException
 * - Makes null handling explicit
 * - Encourages defensive programming
 * - Provides functional-style operations
 */
public class OptionalExamples {
    
    public static void main(String[] args) {
        System.out.println("=== Optional Examples ===\n");
        
        // Example 1: Creating Optional objects
        demonstrateCreatingOptional();
        
        // Example 2: Basic operations
        demonstrateBasicOperations();
        
        // Example 3: Chaining operations
        demonstrateChainingOperations();
        
        // Example 4: Working with collections
        demonstrateCollectionOperations();
        
        // Example 5: Real-world scenarios
        demonstrateRealWorldScenarios();
    }
    
    /**
     * Example 1: Creating Optional Objects
     * 
     * Different ways to create Optional instances.
     */
    private static void demonstrateCreatingOptional() {
        System.out.println("1. Creating Optional Objects:");
        
        // Empty Optional
        java.util.Optional<String> empty = java.util.Optional.empty();
        System.out.println("  Empty Optional: " + empty);
        
        // Optional with value
        java.util.Optional<String> present = java.util.Optional.of("Hello World");
        System.out.println("  Present Optional: " + present);
        
        // Optional that might be null
        String nullableString = null;
        java.util.Optional<String> nullable = java.util.Optional.ofNullable(nullableString);
        System.out.println("  Nullable Optional: " + nullable);
        
        // Optional with non-null value
        String nonNullString = "Functional Programming";
        java.util.Optional<String> nonNull = java.util.Optional.ofNullable(nonNullString);
        System.out.println("  Non-null Optional: " + nonNull);
        
        System.out.println();
    }
    
    /**
     * Example 2: Basic Operations
     * 
     * Essential operations for working with Optional.
     */
    private static void demonstrateBasicOperations() {
        System.out.println("2. Basic Operations:");
        
        // isPresent() - check if value is present
        java.util.Optional<String> present = java.util.Optional.of("Hello");
        java.util.Optional<String> empty = java.util.Optional.empty();
        
        System.out.println("  Is 'Hello' present? " + present.isPresent());
        System.out.println("  Is empty present? " + empty.isPresent());
        
        // get() - get the value (throws exception if empty)
        if (present.isPresent()) {
            System.out.println("  Value: " + present.get());
        }
        
        // orElse() - provide default value
        String value1 = present.orElse("Default Value");
        String value2 = empty.orElse("Default Value");
        System.out.println("  Present orElse: " + value1);
        System.out.println("  Empty orElse: " + value2);
        
        // orElseGet() - provide default using Supplier
        String value3 = empty.orElseGet(() -> "Generated Default");
        System.out.println("  Empty orElseGet: " + value3);
        
        // orElseThrow() - throw exception if empty
        try {
            String value4 = empty.orElseThrow(() -> new RuntimeException("No value present"));
        } catch (RuntimeException e) {
            System.out.println("  Exception thrown: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * Example 3: Chaining Operations
     * 
     * Optional provides functional-style operations that can be chained.
     */
    private static void demonstrateChainingOperations() {
        System.out.println("3. Chaining Operations:");
        
        // map() - transform value if present
        java.util.Optional<String> name = java.util.Optional.of("john doe");
        java.util.Optional<String> upperName = name.map(String::toUpperCase);
        System.out.println("  Original: " + name.get());
        System.out.println("  Mapped: " + upperName.get());
        
        // filter() - filter value if present
        java.util.Optional<String> filtered = name.filter(n -> n.length() > 5);
        System.out.println("  Filtered (length > 5): " + filtered);
        
        // flatMap() - flatten nested Optionals
        java.util.Optional<String> flatMapped = name.flatMap(n -> 
            n.length() > 3 ? java.util.Optional.of(n.toUpperCase()) : java.util.Optional.empty());
        System.out.println("  FlatMapped: " + flatMapped);
        
        // Chaining multiple operations
        java.util.Optional<String> result = java.util.Optional.of("  hello world  ")
            .map(String::trim)
            .map(String::toUpperCase)
            .filter(s -> s.length() > 5);
        
        System.out.println("  Chained result: " + result);
        
        // ifPresent() - perform action if value is present
        java.util.Optional.of("Functional Programming")
            .ifPresent(value -> System.out.println("  Processing: " + value));
        
        // ifPresentOrElse() - perform action based on presence
        java.util.Optional.empty()
            .ifPresentOrElse(
                value -> System.out.println("  Value present: " + value),
                () -> System.out.println("  No value present")
            );
        
        System.out.println();
    }
    
    /**
     * Example 4: Working with Collections
     * 
     * Optional works well with collections and streams.
     */
    private static void demonstrateCollectionOperations() {
        System.out.println("4. Working with Collections:");
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "Diana");
        
        // Find first name starting with 'C'
        java.util.Optional<String> firstC = names.stream()
            .filter(name -> name.startsWith("C"))
            .findFirst();
        
        System.out.println("  First name starting with 'C': " + firstC.orElse("Not found"));
        
        // Find longest name
        java.util.Optional<String> longest = names.stream()
            .max(Comparator.comparing(String::length));
        
        System.out.println("  Longest name: " + longest.orElse("No names"));
        
        // Find name with specific length
        java.util.Optional<String> specificLength = names.stream()
            .filter(name -> name.length() == 5)
            .findAny();
        
        System.out.println("  Name with length 5: " + specificLength.orElse("Not found"));
        
        // Transform and collect
        List<String> upperNames = names.stream()
            .map(String::toUpperCase)
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        
        System.out.println("  Upper case names: " + upperNames);
        
        System.out.println();
    }
    
    /**
     * Example 5: Real-world Scenarios
     * 
     * Practical examples showing how Optional solves real problems.
     */
    private static void demonstrateRealWorldScenarios() {
        System.out.println("5. Real-world Scenarios:");
        
        // Scenario 1: User service
        UserService userService = new UserService();
        
        // Find user by ID
        java.util.Optional<User> user = userService.findUserById(1);
        System.out.println("  User 1: " + user.map(User::getName).orElse("Not found"));
        
        // Find user by email
        java.util.Optional<User> userByEmail = userService.findUserByEmail("alice@example.com");
        System.out.println("  User by email: " + userByEmail.map(User::getName).orElse("Not found"));
        
        // Get user's department name safely
        String departmentName = userService.findUserById(1)
            .map(User::getDepartment)
            .map(Department::getName)
            .orElse("Unknown Department");
        
        System.out.println("  Department: " + departmentName);
        
        // Scenario 2: Configuration
        Configuration config = new Configuration();
        
        String dbHost = config.getDatabaseHost().orElse("localhost");
        int dbPort = config.getDatabasePort().orElse(3306);
        String dbName = config.getDatabaseName().orElse("defaultdb");
        
        System.out.println("  Database URL: " + dbHost + ":" + dbPort + "/" + dbName);
        
        // Scenario 3: Safe method chaining
        String result = userService.findUserById(1)
            .map(User::getProfile)
            .map(Profile::getSettings)
            .map(Settings::getTheme)
            .orElse("default");
        
        System.out.println("  User theme: " + result);
        
        System.out.println();
    }
    
    /**
     * Helper classes for examples
     */
    static class User {
        private String name;
        private String email;
        private Department department;
        private Profile profile;
        
        public User(String name, String email, Department department, Profile profile) {
            this.name = name;
            this.email = email;
            this.department = department;
            this.profile = profile;
        }
        
        public String getName() { return name; }
        public String getEmail() { return email; }
        public Department getDepartment() { return department; }
        public Profile getProfile() { return profile; }
    }
    
    static class Department {
        private String name;
        
        public Department(String name) {
            this.name = name;
        }
        
        public String getName() { return name; }
    }
    
    static class Profile {
        private Settings settings;
        
        public Profile(Settings settings) {
            this.settings = settings;
        }
        
        public Settings getSettings() { return settings; }
    }
    
    static class Settings {
        private String theme;
        
        public Settings(String theme) {
            this.theme = theme;
        }
        
        public String getTheme() { return theme; }
    }
    
    static class UserService {
        private Map<Integer, User> users = new HashMap<>();
        
        public UserService() {
            users.put(1, new User("Alice", "alice@example.com", 
                new Department("Engineering"), 
                new Profile(new Settings("dark"))));
            users.put(2, new User("Bob", "bob@example.com", 
                new Department("Marketing"), 
                new Profile(new Settings("light"))));
        }
        
        public java.util.Optional<User> findUserById(int id) {
            return java.util.Optional.ofNullable(users.get(id));
        }
        
        public java.util.Optional<User> findUserByEmail(String email) {
            return users.values().stream()
                .filter(user -> email.equals(user.getEmail()))
                .findFirst();
        }
    }
    
    static class Configuration {
        private Map<String, String> config = new HashMap<>();
        
        public Configuration() {
            config.put("db.host", "prod-db.example.com");
            config.put("db.port", "5432");
            // db.name is not set, so it will be null
        }
        
        public java.util.Optional<String> getDatabaseHost() {
            return java.util.Optional.ofNullable(config.get("db.host"));
        }
        
        public java.util.Optional<Integer> getDatabasePort() {
            String port = config.get("db.port");
            return java.util.Optional.ofNullable(port)
                .map(Integer::parseInt);
        }
        
        public java.util.Optional<String> getDatabaseName() {
            return java.util.Optional.ofNullable(config.get("db.name"));
        }
    }
    
    /**
     * Key Takeaways:
     * 
     * 1. Optional eliminates NullPointerException
     * 2. Use Optional.of() for non-null values
     * 3. Use Optional.ofNullable() for potentially null values
     * 4. Use Optional.empty() for empty values
     * 5. Always check isPresent() before calling get()
     * 6. Use orElse() for default values
     * 7. Use map() and flatMap() for transformations
     * 8. Use filter() for conditional operations
     * 9. Chain operations for complex logic
     * 10. Don't use Optional for collections (use empty collections instead)
     * 
     * Best Practices:
     * - Don't use Optional as method parameters
     * - Don't use Optional for collections
     * - Don't use Optional.get() without checking isPresent()
     * - Use Optional for return types when null is a valid result
     * - Prefer orElse() over orElseGet() for simple defaults
     * - Use orElseGet() for expensive default computations
     */
}
