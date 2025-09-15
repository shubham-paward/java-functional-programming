package com.functionalprogramming.examples;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;

/**
 * Real-world Data Processing Examples
 * 
 * This class demonstrates how functional programming concepts can be applied
 * to solve real-world data processing problems in a clean, readable, and
 * maintainable way.
 */
public class DataProcessing {
    
    public static void main(String[] args) {
        System.out.println("=== Real-world Data Processing Examples ===\n");
        
        // Example 1: E-commerce order processing
        demonstrateOrderProcessing();
        
        // Example 2: Employee analytics
        demonstrateEmployeeAnalytics();
        
        // Example 3: Data validation and transformation
        demonstrateDataValidation();
        
        // Example 4: Performance monitoring
        demonstratePerformanceMonitoring();
        
        // Example 5: Event processing
        demonstrateEventProcessing();
    }
    
    /**
     * Example 1: E-commerce Order Processing
     * 
     * Process orders, calculate totals, apply discounts, and generate reports.
     */
    private static void demonstrateOrderProcessing() {
        System.out.println("1. E-commerce Order Processing:");
        
        List<Order> orders = Arrays.asList(
            new Order(1, "Alice", Arrays.asList(
                new OrderItem("Laptop", 999.99, 1),
                new OrderItem("Mouse", 29.99, 2)
            )),
            new Order(2, "Bob", Arrays.asList(
                new OrderItem("Keyboard", 79.99, 1),
                new OrderItem("Monitor", 299.99, 1)
            )),
            new Order(3, "Charlie", Arrays.asList(
                new OrderItem("Headphones", 149.99, 1),
                new OrderItem("Webcam", 89.99, 1)
            ))
        );
        
        // Calculate total for each order
        Map<Integer, Double> orderTotals = orders.stream()
            .collect(Collectors.toMap(
                Order::getId,
                order -> order.getItems().stream()
                    .mapToDouble(item -> item.getPrice() * item.getQuantity())
                    .sum()
            ));
        
        System.out.println("  Order totals: " + orderTotals);
        
        // Find orders above $1000
        List<Order> highValueOrders = orders.stream()
            .filter(order -> order.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum() > 1000)
            .collect(Collectors.toList());
        
        System.out.println("  High-value orders: " + highValueOrders.size());
        
        // Calculate total revenue
        double totalRevenue = orders.stream()
            .flatMap(order -> order.getItems().stream())
            .mapToDouble(item -> item.getPrice() * item.getQuantity())
            .sum();
        
        System.out.println("  Total revenue: $" + String.format("%.2f", totalRevenue));
        
        // Find most popular product
        Map<String, Long> productCounts = orders.stream()
            .flatMap(order -> order.getItems().stream())
            .collect(Collectors.groupingBy(
                OrderItem::getProductName,
                Collectors.summingLong(OrderItem::getQuantity)
            ));
        
        String mostPopular = productCounts.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("None");
        
        System.out.println("  Most popular product: " + mostPopular);
        
        System.out.println();
    }
    
    /**
     * Example 2: Employee Analytics
     * 
     * Analyze employee data to generate insights and reports.
     */
    private static void demonstrateEmployeeAnalytics() {
        System.out.println("2. Employee Analytics:");
        
        List<Employee> employees = Arrays.asList(
            new Employee("Alice", 25, 50000, "Engineering", "Senior Developer"),
            new Employee("Bob", 30, 60000, "Marketing", "Marketing Manager"),
            new Employee("Charlie", 35, 70000, "Engineering", "Tech Lead"),
            new Employee("Diana", 28, 55000, "HR", "HR Specialist"),
            new Employee("Eve", 32, 65000, "Engineering", "Senior Developer"),
            new Employee("Frank", 29, 58000, "Marketing", "Marketing Specialist"),
            new Employee("Grace", 31, 62000, "HR", "HR Manager")
        );
        
        // Department-wise salary analysis
        Map<String, Double> avgSalaryByDept = employees.stream()
            .collect(Collectors.groupingBy(
                Employee::getDepartment,
                Collectors.averagingInt(Employee::getSalary)
            ));
        
        System.out.println("  Average salary by department:");
        avgSalaryByDept.forEach((dept, avg) -> 
            System.out.println("    " + dept + ": $" + String.format("%.0f", avg)));
        
        // Age distribution
        Map<String, Long> ageDistribution = employees.stream()
            .collect(Collectors.groupingBy(
                emp -> emp.getAge() < 30 ? "Under 30" : "30 and above",
                Collectors.counting()
            ));
        
        System.out.println("  Age distribution: " + ageDistribution);
        
        // Top earners by department
        Map<String, Optional<Employee>> topEarners = employees.stream()
            .collect(Collectors.groupingBy(
                Employee::getDepartment,
                Collectors.maxBy(Comparator.comparing(Employee::getSalary))
            ));
        
        System.out.println("  Top earners by department:");
        topEarners.forEach((dept, emp) -> 
            System.out.println("    " + dept + ": " + emp.get().getName() + " ($" + emp.get().getSalary() + ")"));
        
        // Salary range analysis
        IntSummaryStatistics salaryStats = employees.stream()
            .mapToInt(Employee::getSalary)
            .summaryStatistics();
        
        System.out.println("  Salary statistics:");
        System.out.println("    Min: $" + salaryStats.getMin());
        System.out.println("    Max: $" + salaryStats.getMax());
        System.out.println("    Average: $" + String.format("%.0f", salaryStats.getAverage()));
        
        System.out.println();
    }
    
    /**
     * Example 3: Data Validation and Transformation
     * 
     * Validate and transform data using functional programming.
     */
    private static void demonstrateDataValidation() {
        System.out.println("3. Data Validation and Transformation:");
        
        List<String> rawData = Arrays.asList(
            "alice@example.com,25,50000",
            "bob@example.com,30,60000",
            "invalid-email,35,70000",
            "charlie@example.com,28,55000",
            "diana@example.com,invalid-age,62000",
            "eve@example.com,32,invalid-salary"
        );
        
        // Parse and validate data
        List<ValidatedPerson> validatedPersons = rawData.stream()
            .map(DataProcessing::parsePerson)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
        
        System.out.println("  Valid records: " + validatedPersons.size());
        validatedPersons.forEach(person -> 
            System.out.println("    " + person.getName() + " - " + person.getEmail()));
        
        // Transform and clean data
        List<String> cleanedEmails = rawData.stream()
            .map(line -> line.split(",")[0])
            .filter(email -> email.contains("@"))
            .map(String::toLowerCase)
            .distinct()
            .collect(Collectors.toList());
        
        System.out.println("  Cleaned emails: " + cleanedEmails);
        
        System.out.println();
    }
    
    /**
     * Example 4: Performance Monitoring
     * 
     * Monitor and analyze system performance metrics.
     */
    private static void demonstratePerformanceMonitoring() {
        System.out.println("4. Performance Monitoring:");
        
        List<PerformanceMetric> metrics = Arrays.asList(
            new PerformanceMetric("API", "GET /users", 150, 0.95),
            new PerformanceMetric("API", "POST /users", 200, 0.98),
            new PerformanceMetric("API", "GET /orders", 100, 0.99),
            new PerformanceMetric("Database", "SELECT", 50, 0.99),
            new PerformanceMetric("Database", "INSERT", 75, 0.97),
            new PerformanceMetric("Cache", "GET", 10, 0.99)
        );
        
        // Calculate average response time by service
        Map<String, Double> avgResponseTime = metrics.stream()
            .collect(Collectors.groupingBy(
                PerformanceMetric::getService,
                Collectors.averagingInt(PerformanceMetric::getResponseTime)
            ));
        
        System.out.println("  Average response time by service:");
        avgResponseTime.forEach((service, avg) -> 
            System.out.println("    " + service + ": " + String.format("%.1f", avg) + "ms"));
        
        // Find slow operations
        List<PerformanceMetric> slowOperations = metrics.stream()
            .filter(metric -> metric.getResponseTime() > 100)
            .sorted(Comparator.comparing(PerformanceMetric::getResponseTime).reversed())
            .collect(Collectors.toList());
        
        System.out.println("  Slow operations (>100ms):");
        slowOperations.forEach(metric -> 
            System.out.println("    " + metric.getOperation() + ": " + metric.getResponseTime() + "ms"));
        
        // Calculate overall system health
        double overallAvailability = metrics.stream()
            .mapToDouble(PerformanceMetric::getAvailability)
            .average()
            .orElse(0.0);
        
        System.out.println("  Overall system availability: " + String.format("%.2f", overallAvailability * 100) + "%");
        
        System.out.println();
    }
    
    /**
     * Example 5: Event Processing
     * 
     * Process and analyze events in real-time.
     */
    private static void demonstrateEventProcessing() {
        System.out.println("5. Event Processing:");
        
        List<Event> events = Arrays.asList(
            new Event("user_login", "user123", System.currentTimeMillis() - 1000),
            new Event("page_view", "user123", System.currentTimeMillis() - 500),
            new Event("user_login", "user456", System.currentTimeMillis() - 200),
            new Event("purchase", "user123", System.currentTimeMillis() - 100),
            new Event("page_view", "user456", System.currentTimeMillis() - 50)
        );
        
        // Count events by type
        Map<String, Long> eventCounts = events.stream()
            .collect(Collectors.groupingBy(
                Event::getType,
                Collectors.counting()
            ));
        
        System.out.println("  Event counts by type: " + eventCounts);
        
        // Find recent events (last 200ms)
        long currentTime = System.currentTimeMillis();
        List<Event> recentEvents = events.stream()
            .filter(event -> currentTime - event.getTimestamp() < 200)
            .collect(Collectors.toList());
        
        System.out.println("  Recent events: " + recentEvents.size());
        
        // Group events by user
        Map<String, List<Event>> eventsByUser = events.stream()
            .collect(Collectors.groupingBy(Event::getUserId));
        
        System.out.println("  Events by user:");
        eventsByUser.forEach((userId, userEvents) -> 
            System.out.println("    " + userId + ": " + userEvents.size() + " events"));
        
        // Find users with purchase events
        Set<String> usersWithPurchases = events.stream()
            .filter(event -> "purchase".equals(event.getType()))
            .map(Event::getUserId)
            .collect(Collectors.toSet());
        
        System.out.println("  Users with purchases: " + usersWithPurchases);
        
        System.out.println();
    }
    
    /**
     * Helper method for data validation
     */
    private static Optional<ValidatedPerson> parsePerson(String line) {
        String[] parts = line.split(",");
        if (parts.length != 3) return Optional.empty();
        
        String email = parts[0];
        if (!email.contains("@")) return Optional.empty();
        
        try {
            int age = Integer.parseInt(parts[1]);
            int salary = Integer.parseInt(parts[2]);
            
            if (age < 18 || age > 100) return Optional.empty();
            if (salary < 0) return Optional.empty();
            
            return Optional.of(new ValidatedPerson(email, age, salary));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
    
    /**
     * Helper classes for examples
     */
    public static class Order {
        private int id;
        private String customerName;
        private List<OrderItem> items;
        
        public Order(int id, String customerName, List<OrderItem> items) {
            this.id = id;
            this.customerName = customerName;
            this.items = items;
        }
        
        public int getId() { return id; }
        public String getCustomerName() { return customerName; }
        public List<OrderItem> getItems() { return items; }
        
        @Override
        public String toString() {
            return "Order " + id + " (" + customerName + ")";
        }
    }
    
    public static class OrderItem {
        private String productName;
        private double price;
        private int quantity;
        
        public OrderItem(String productName, double price, int quantity) {
            this.productName = productName;
            this.price = price;
            this.quantity = quantity;
        }
        
        public String getProductName() { return productName; }
        public double getPrice() { return price; }
        public int getQuantity() { return quantity; }
    }
    
    public static class Employee {
        private String name;
        private int age;
        private int salary;
        private String department;
        private String position;
        
        public Employee(String name, int age, int salary, String department, String position) {
            this.name = name;
            this.age = age;
            this.salary = salary;
            this.department = department;
            this.position = position;
        }
        
        public String getName() { return name; }
        public int getAge() { return age; }
        public int getSalary() { return salary; }
        public String getDepartment() { return department; }
        public String getPosition() { return position; }
    }
    
    static class ValidatedPerson {
        private String email;
        private int age;
        private int salary;
        
        public ValidatedPerson(String email, int age, int salary) {
            this.email = email;
            this.age = age;
            this.salary = salary;
        }
        
        public String getEmail() { return email; }
        public String getName() { return email.split("@")[0]; }
        public int getAge() { return age; }
        public int getSalary() { return salary; }
    }
    
    static class PerformanceMetric {
        private String service;
        private String operation;
        private int responseTime;
        private double availability;
        
        public PerformanceMetric(String service, String operation, int responseTime, double availability) {
            this.service = service;
            this.operation = operation;
            this.responseTime = responseTime;
            this.availability = availability;
        }
        
        public String getService() { return service; }
        public String getOperation() { return operation; }
        public int getResponseTime() { return responseTime; }
        public double getAvailability() { return availability; }
    }
    
    static class Event {
        private String type;
        private String userId;
        private long timestamp;
        
        public Event(String type, String userId, long timestamp) {
            this.type = type;
            this.userId = userId;
            this.timestamp = timestamp;
        }
        
        public String getType() { return type; }
        public String getUserId() { return userId; }
        public long getTimestamp() { return timestamp; }
    }
    
    /**
     * Key Takeaways:
     * 
     * 1. Functional programming makes data processing more readable
     * 2. Streams provide a declarative way to process collections
     * 3. Collectors help transform streams into useful data structures
     * 4. Optional helps handle null values safely
     * 5. Method references make code more concise
     * 6. Functional programming encourages immutability
     * 7. Complex operations can be broken down into simple, composable functions
     * 8. Error handling can be integrated into the functional flow
     * 9. Performance can be improved with parallel streams
     * 10. Code becomes more testable and maintainable
     */
}
