package com.functionalprogramming.examples;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * Log Analysis and Monitoring Example
 * 
 * This example demonstrates how functional programming can be used for
 * log analysis, system monitoring, and alerting in production environments.
 */
public class LogAnalysis {
    
    public static void main(String[] args) {
        System.out.println("=== Log Analysis and Monitoring ===\n");
        
        // Example 1: Log Parsing and Filtering
        demonstrateLogParsing();
        
        // Example 2: Error Analysis
        demonstrateErrorAnalysis();
        
        // Example 3: Performance Monitoring
        demonstratePerformanceMonitoring();
        
        // Example 4: Security Analysis
        demonstrateSecurityAnalysis();
        
        // Example 5: Alert Generation
        demonstrateAlertGeneration();
    }
    
    /**
     * Example 1: Log Parsing and Filtering
     * 
     * Parsing and filtering log entries using functional programming.
     */
    private static void demonstrateLogParsing() {
        System.out.println("1. Log Parsing and Filtering:");
        
        List<String> logEntries = Arrays.asList(
            "2024-01-15 10:30:15 INFO [UserService] User login successful: user123",
            "2024-01-15 10:30:16 ERROR [DatabaseService] Connection timeout: DB001",
            "2024-01-15 10:30:17 WARN [CacheService] Cache miss rate high: 85%",
            "2024-01-15 10:30:18 INFO [PaymentService] Payment processed: $150.00",
            "2024-01-15 10:30:19 ERROR [AuthService] Invalid token: token456",
            "2024-01-15 10:30:20 DEBUG [ConfigService] Loading configuration",
            "2024-01-15 10:30:21 INFO [UserService] User logout: user123",
            "2024-01-15 10:30:22 ERROR [EmailService] SMTP connection failed",
            "2024-01-15 10:30:23 WARN [MemoryService] High memory usage: 90%",
            "2024-01-15 10:30:24 INFO [OrderService] Order created: #12345"
        );
        
        // Parse log entries
        List<LogEntry> parsedLogs = logEntries.stream()
            .map(LogAnalysis::parseLogEntry)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
        
        System.out.println("  Parsed logs: " + parsedLogs.size() + " entries");
        
        // Filter by log level
        Map<String, List<LogEntry>> logsByLevel = parsedLogs.stream()
            .collect(Collectors.groupingBy(LogEntry::getLevel));
        
        System.out.println("  Logs by level: " + logsByLevel.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue().size()
            )));
        
        // Filter by service
        Map<String, List<LogEntry>> logsByService = parsedLogs.stream()
            .collect(Collectors.groupingBy(LogEntry::getService));
        
        System.out.println("  Logs by service: " + logsByService.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue().size()
            )));
        
        // Find recent errors
        List<LogEntry> recentErrors = parsedLogs.stream()
            .filter(log -> log.getLevel().equals("ERROR"))
            .sorted(Comparator.comparing(LogEntry::getTimestamp).reversed())
            .limit(3)
            .collect(Collectors.toList());
        
        System.out.println("  Recent errors: " + recentErrors);
        System.out.println();
    }
    
    /**
     * Example 2: Error Analysis
     * 
     * Analyzing error patterns and trends in logs.
     */
    private static void demonstrateErrorAnalysis() {
        System.out.println("2. Error Analysis:");
        
        List<LogEntry> errorLogs = Arrays.asList(
            new LogEntry("2024-01-15 10:30:16", "ERROR", "DatabaseService", "Connection timeout: DB001"),
            new LogEntry("2024-01-15 10:30:19", "ERROR", "AuthService", "Invalid token: token456"),
            new LogEntry("2024-01-15 10:30:22", "ERROR", "EmailService", "SMTP connection failed"),
            new LogEntry("2024-01-15 10:31:16", "ERROR", "DatabaseService", "Connection timeout: DB001"),
            new LogEntry("2024-01-15 10:31:19", "ERROR", "AuthService", "Invalid token: token789"),
            new LogEntry("2024-01-15 10:32:16", "ERROR", "DatabaseService", "Connection timeout: DB001"),
            new LogEntry("2024-01-15 10:32:22", "ERROR", "EmailService", "SMTP connection failed"),
            new LogEntry("2024-01-15 10:33:16", "ERROR", "DatabaseService", "Connection timeout: DB001")
        );
        
        // Count errors by service
        Map<String, Long> errorCounts = errorLogs.stream()
            .collect(Collectors.groupingBy(LogEntry::getService, Collectors.counting()));
        
        System.out.println("  Error counts by service: " + errorCounts);
        
        // Find most common error messages
        Map<String, Long> errorMessages = errorLogs.stream()
            .collect(Collectors.groupingBy(LogEntry::getMessage, Collectors.counting()));
        
        List<Map.Entry<String, Long>> topErrors = errorMessages.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(3)
            .collect(Collectors.toList());
        
        System.out.println("  Top error messages: " + topErrors);
        
        // Calculate error rate over time
        Map<String, Long> errorsByMinute = errorLogs.stream()
            .collect(Collectors.groupingBy(
                log -> log.getTimestamp().substring(0, 16), // Group by minute
                Collectors.counting()
            ));
        
        System.out.println("  Errors by minute: " + errorsByMinute);
        
        // Find error patterns
        List<String> errorPatterns = errorLogs.stream()
            .map(log -> extractErrorPattern(log.getMessage()))
            .distinct()
            .collect(Collectors.toList());
        
        System.out.println("  Error patterns: " + errorPatterns);
        System.out.println();
    }
    
    /**
     * Example 3: Performance Monitoring
     * 
     * Monitoring system performance using log data.
     */
    private static void demonstratePerformanceMonitoring() {
        System.out.println("3. Performance Monitoring:");
        
        List<PerformanceLog> perfLogs = Arrays.asList(
            new PerformanceLog("2024-01-15 10:30:15", "UserService", "login", 150),
            new PerformanceLog("2024-01-15 10:30:16", "DatabaseService", "query", 2500),
            new PerformanceLog("2024-01-15 10:30:17", "CacheService", "get", 50),
            new PerformanceLog("2024-01-15 10:30:18", "PaymentService", "process", 800),
            new PerformanceLog("2024-01-15 10:30:19", "AuthService", "validate", 200),
            new PerformanceLog("2024-01-15 10:30:20", "UserService", "logout", 100),
            new PerformanceLog("2024-01-15 10:30:21", "DatabaseService", "query", 3000),
            new PerformanceLog("2024-01-15 10:30:22", "EmailService", "send", 1200),
            new PerformanceLog("2024-01-15 10:30:23", "OrderService", "create", 600),
            new PerformanceLog("2024-01-15 10:30:24", "DatabaseService", "query", 2800)
        );
        
        // Calculate average response times by service
        Map<String, Double> avgResponseTimes = perfLogs.stream()
            .collect(Collectors.groupingBy(
                PerformanceLog::getService,
                Collectors.averagingDouble(PerformanceLog::getResponseTime)
            ));
        
        System.out.println("  Average response times by service: " + avgResponseTimes);
        
        // Find slow operations
        List<PerformanceLog> slowOperations = perfLogs.stream()
            .filter(log -> log.getResponseTime() > 1000)
            .sorted(Comparator.comparing(PerformanceLog::getResponseTime).reversed())
            .collect(Collectors.toList());
        
        System.out.println("  Slow operations (>1000ms): " + slowOperations);
        
        // Calculate percentiles
        List<Double> responseTimes = perfLogs.stream()
            .mapToDouble(PerformanceLog::getResponseTime)
            .sorted()
            .boxed()
            .collect(Collectors.toList());
        
        double p50 = calculatePercentile(responseTimes, 50);
        double p95 = calculatePercentile(responseTimes, 95);
        double p99 = calculatePercentile(responseTimes, 99);
        
        System.out.println("  Response time percentiles - P50: " + p50 + "ms, P95: " + p95 + "ms, P99: " + p99 + "ms");
        
        // Find performance trends
        Map<String, List<PerformanceLog>> trendsByService = perfLogs.stream()
            .collect(Collectors.groupingBy(PerformanceLog::getService));
        
        trendsByService.forEach((service, logs) -> {
            double trend = calculateTrend(logs);
            System.out.println("  " + service + " performance trend: " + String.format("%.2f", trend) + "ms/min");
        });
        
        System.out.println();
    }
    
    /**
     * Example 4: Security Analysis
     * 
     * Analyzing security-related events in logs.
     */
    private static void demonstrateSecurityAnalysis() {
        System.out.println("4. Security Analysis:");
        
        List<SecurityEvent> securityEvents = Arrays.asList(
            new SecurityEvent("2024-01-15 10:30:15", "LOGIN_SUCCESS", "user123", "192.168.1.100"),
            new SecurityEvent("2024-01-15 10:30:16", "LOGIN_FAILED", "user456", "192.168.1.101"),
            new SecurityEvent("2024-01-15 10:30:17", "LOGIN_FAILED", "user456", "192.168.1.101"),
            new SecurityEvent("2024-01-15 10:30:18", "LOGIN_FAILED", "user456", "192.168.1.101"),
            new SecurityEvent("2024-01-15 10:30:19", "LOGIN_SUCCESS", "user789", "192.168.1.102"),
            new SecurityEvent("2024-01-15 10:30:20", "INVALID_TOKEN", "user123", "192.168.1.100"),
            new SecurityEvent("2024-01-15 10:30:21", "LOGIN_FAILED", "admin", "192.168.1.103"),
            new SecurityEvent("2024-01-15 10:30:22", "LOGIN_FAILED", "admin", "192.168.1.103"),
            new SecurityEvent("2024-01-15 10:30:23", "LOGIN_FAILED", "admin", "192.168.1.103"),
            new SecurityEvent("2024-01-15 10:30:24", "LOGIN_FAILED", "admin", "192.168.1.103")
        );
        
        // Count failed login attempts
        long failedLogins = securityEvents.stream()
            .filter(event -> event.getEventType().equals("LOGIN_FAILED"))
            .count();
        
        System.out.println("  Failed login attempts: " + failedLogins);
        
        // Find suspicious IPs (multiple failed attempts)
        Map<String, Long> failedAttemptsByIP = securityEvents.stream()
            .filter(event -> event.getEventType().equals("LOGIN_FAILED"))
            .collect(Collectors.groupingBy(SecurityEvent::getIpAddress, Collectors.counting()));
        
        List<String> suspiciousIPs = failedAttemptsByIP.entrySet().stream()
            .filter(entry -> entry.getValue() >= 3)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
        
        System.out.println("  Suspicious IPs (3+ failed attempts): " + suspiciousIPs);
        
        // Find brute force attacks
        Map<String, Long> bruteForceAttempts = securityEvents.stream()
            .filter(event -> event.getEventType().equals("LOGIN_FAILED"))
            .collect(Collectors.groupingBy(SecurityEvent::getUsername, Collectors.counting()));
        
        List<String> bruteForceTargets = bruteForceAttempts.entrySet().stream()
            .filter(entry -> entry.getValue() >= 3)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
        
        System.out.println("  Brute force targets: " + bruteForceTargets);
        
        // Calculate security metrics
        long totalEvents = securityEvents.size();
        long successEvents = securityEvents.stream()
            .filter(event -> event.getEventType().equals("LOGIN_SUCCESS"))
            .count();
        
        double successRate = (double) successEvents / totalEvents * 100;
        System.out.println("  Login success rate: " + String.format("%.1f%%", successRate));
        System.out.println();
    }
    
    /**
     * Example 5: Alert Generation
     * 
     * Generating alerts based on log analysis.
     */
    private static void demonstrateAlertGeneration() {
        System.out.println("5. Alert Generation:");
        
        List<LogEntry> recentLogs = Arrays.asList(
            new LogEntry("2024-01-15 10:30:16", "ERROR", "DatabaseService", "Connection timeout: DB001"),
            new LogEntry("2024-01-15 10:30:17", "WARN", "MemoryService", "High memory usage: 90%"),
            new LogEntry("2024-01-15 10:30:18", "ERROR", "DatabaseService", "Connection timeout: DB001"),
            new LogEntry("2024-01-15 10:30:19", "WARN", "MemoryService", "High memory usage: 95%"),
            new LogEntry("2024-01-15 10:30:20", "ERROR", "DatabaseService", "Connection timeout: DB001"),
            new LogEntry("2024-01-15 10:30:21", "WARN", "MemoryService", "High memory usage: 98%"),
            new LogEntry("2024-01-15 10:30:22", "ERROR", "DatabaseService", "Connection timeout: DB001"),
            new LogEntry("2024-01-15 10:30:23", "WARN", "MemoryService", "High memory usage: 99%")
        );
        
        // Generate alerts based on log patterns
        List<Alert> alerts = generateAlerts(recentLogs);
        
        System.out.println("  Generated alerts: " + alerts.size());
        alerts.forEach(alert -> System.out.println("    " + alert));
        
        // Categorize alerts by severity
        Map<Alert.Severity, List<Alert>> alertsBySeverity = alerts.stream()
            .collect(Collectors.groupingBy(Alert::getSeverity));
        
        System.out.println("  Alerts by severity: " + alertsBySeverity.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue().size()
            )));
        
        // Find critical alerts
        List<Alert> criticalAlerts = alerts.stream()
            .filter(alert -> alert.getSeverity() == Alert.Severity.CRITICAL)
            .collect(Collectors.toList());
        
        System.out.println("  Critical alerts: " + criticalAlerts.size());
        System.out.println();
    }
    
    // Helper methods
    
    private static Optional<LogEntry> parseLogEntry(String logLine) {
        try {
            String[] parts = logLine.split(" ", 4);
            if (parts.length >= 4) {
                String timestamp = parts[0] + " " + parts[1];
                String level = parts[2];
                String service = parts[3].substring(1, parts[3].indexOf("]"));
                String message = parts[3].substring(parts[3].indexOf("]") + 2);
                return Optional.of(new LogEntry(timestamp, level, service, message));
            }
        } catch (Exception e) {
            // Invalid log format
        }
        return Optional.empty();
    }
    
    private static String extractErrorPattern(String message) {
        // Extract common error patterns
        if (message.contains("timeout")) return "TIMEOUT";
        if (message.contains("connection")) return "CONNECTION";
        if (message.contains("invalid")) return "INVALID";
        if (message.contains("failed")) return "FAILED";
        return "OTHER";
    }
    
    private static double calculatePercentile(List<Double> values, double percentile) {
        int index = (int) Math.ceil((percentile / 100.0) * values.size()) - 1;
        return values.get(Math.max(0, index));
    }
    
    private static double calculateTrend(List<PerformanceLog> logs) {
        if (logs.size() < 2) return 0.0;
        
        // Simple linear trend calculation
        double firstTime = logs.get(0).getResponseTime();
        double lastTime = logs.get(logs.size() - 1).getResponseTime();
        return lastTime - firstTime;
    }
    
    private static List<Alert> generateAlerts(List<LogEntry> logs) {
        List<Alert> alerts = new ArrayList<>();
        
        // Check for repeated errors
        Map<String, Long> errorCounts = logs.stream()
            .filter(log -> log.getLevel().equals("ERROR"))
            .collect(Collectors.groupingBy(LogEntry::getService, Collectors.counting()));
        
        errorCounts.forEach((service, count) -> {
            if (count >= 3) {
                alerts.add(new Alert(Alert.Severity.CRITICAL, "Repeated errors in " + service, count + " errors"));
            }
        });
        
        // Check for memory warnings
        long memoryWarnings = logs.stream()
            .filter(log -> log.getMessage().contains("High memory usage"))
            .count();
        
        if (memoryWarnings >= 3) {
            alerts.add(new Alert(Alert.Severity.HIGH, "High memory usage detected", memoryWarnings + " warnings"));
        }
        
        // Check for service availability
        Set<String> servicesWithErrors = logs.stream()
            .filter(log -> log.getLevel().equals("ERROR"))
            .map(LogEntry::getService)
            .collect(Collectors.toSet());
        
        if (servicesWithErrors.size() >= 2) {
            alerts.add(new Alert(Alert.Severity.MEDIUM, "Multiple services experiencing issues", servicesWithErrors.size() + " services"));
        }
        
        return alerts;
    }
    
    // Data classes
    
    public static class LogEntry {
        private final String timestamp;
        private final String level;
        private final String service;
        private final String message;
        
        public LogEntry(String timestamp, String level, String service, String message) {
            this.timestamp = timestamp;
            this.level = level;
            this.service = service;
            this.message = message;
        }
        
        public String getTimestamp() { return timestamp; }
        public String getLevel() { return level; }
        public String getService() { return service; }
        public String getMessage() { return message; }
        
        @Override
        public String toString() {
            return String.format("[%s] %s %s: %s", level, service, timestamp, message);
        }
    }
    
    public static class PerformanceLog {
        private final String timestamp;
        private final String service;
        private final String operation;
        private final double responseTime;
        
        public PerformanceLog(String timestamp, String service, String operation, double responseTime) {
            this.timestamp = timestamp;
            this.service = service;
            this.operation = operation;
            this.responseTime = responseTime;
        }
        
        public String getTimestamp() { return timestamp; }
        public String getService() { return service; }
        public String getOperation() { return operation; }
        public double getResponseTime() { return responseTime; }
        
        @Override
        public String toString() {
            return String.format("%s %s.%s: %.0fms", timestamp, service, operation, responseTime);
        }
    }
    
    public static class SecurityEvent {
        private final String timestamp;
        private final String eventType;
        private final String username;
        private final String ipAddress;
        
        public SecurityEvent(String timestamp, String eventType, String username, String ipAddress) {
            this.timestamp = timestamp;
            this.eventType = eventType;
            this.username = username;
            this.ipAddress = ipAddress;
        }
        
        public String getTimestamp() { return timestamp; }
        public String getEventType() { return eventType; }
        public String getUsername() { return username; }
        public String getIpAddress() { return ipAddress; }
        
        @Override
        public String toString() {
            return String.format("%s %s: %s from %s", timestamp, eventType, username, ipAddress);
        }
    }
    
    public static class Alert {
        public enum Severity { LOW, MEDIUM, HIGH, CRITICAL }
        
        private final Severity severity;
        private final String message;
        private final String details;
        
        public Alert(Severity severity, String message, String details) {
            this.severity = severity;
            this.message = message;
            this.details = details;
        }
        
        public Severity getSeverity() { return severity; }
        public String getMessage() { return message; }
        public String getDetails() { return details; }
        
        @Override
        public String toString() {
            return String.format("[%s] %s - %s", severity, message, details);
        }
    }
    
    /**
     * Key Takeaways:
     * 
     * 1. Functional programming excels at log analysis and monitoring
     * 2. Streams provide powerful tools for filtering and aggregating log data
     * 3. Collectors enable complex log analysis and reporting
     * 4. Optional helps handle malformed log entries gracefully
     * 5. Pipeline patterns make log processing more maintainable
     * 6. Real-time analysis requires efficient data processing
     * 7. Alert generation can be automated using functional patterns
     */
}
