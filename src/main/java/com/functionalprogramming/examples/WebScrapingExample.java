package com.functionalprogramming.examples;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * Web Scraping and Data Extraction Example
 * 
 * This example demonstrates how functional programming can be used for
 * web scraping, data extraction, and content processing tasks.
 */
public class WebScrapingExample {
    
    public static void main(String[] args) {
        System.out.println("=== Web Scraping and Data Extraction ===\n");
        
        // Example 1: HTML Content Processing
        demonstrateHtmlProcessing();
        
        // Example 2: Data Extraction Pipeline
        demonstrateDataExtraction();
        
        // Example 3: Content Analysis
        demonstrateContentAnalysis();
        
        // Example 4: URL Processing
        demonstrateUrlProcessing();
        
        // Example 5: Data Validation and Cleaning
        demonstrateDataValidation();
    }
    
    /**
     * Example 1: HTML Content Processing
     * 
     * Processing HTML content using functional programming patterns.
     */
    private static void demonstrateHtmlProcessing() {
        System.out.println("1. HTML Content Processing:");
        
        List<String> htmlLines = Arrays.asList(
            "<html>",
            "<head><title>Sample Page</title></head>",
            "<body>",
            "<h1>Welcome to our site</h1>",
            "<p>This is a sample paragraph with <strong>bold text</strong>.</p>",
            "<div class='content'>",
            "  <p>Another paragraph with <em>italic text</em>.</p>",
            "  <ul>",
            "    <li>Item 1</li>",
            "    <li>Item 2</li>",
            "    <li>Item 3</li>",
            "  </ul>",
            "</div>",
            "</body>",
            "</html>"
        );
        
        // Extract text content from HTML
        List<String> textContent = htmlLines.stream()
            .filter(line -> !line.trim().startsWith("<"))
            .map(String::trim)
            .filter(line -> !line.isEmpty())
            .collect(Collectors.toList());
        
        System.out.println("  Text content: " + textContent);
        
        // Extract HTML tags
        List<String> tags = htmlLines.stream()
            .filter(line -> line.contains("<") && line.contains(">"))
            .map(line -> line.replaceAll(".*<([^>]+)>.*", "$1"))
            .filter(tag -> !tag.contains("/"))
            .collect(Collectors.toList());
        
        System.out.println("  HTML tags: " + tags);
        
        // Count tag frequency
        Map<String, Long> tagFrequency = tags.stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        
        System.out.println("  Tag frequency: " + tagFrequency);
        System.out.println();
    }
    
    /**
     * Example 2: Data Extraction Pipeline
     * 
     * Creating a pipeline for extracting structured data from unstructured content.
     */
    private static void demonstrateDataExtraction() {
        System.out.println("2. Data Extraction Pipeline:");
        
        List<String> rawData = Arrays.asList(
            "Name: John Doe, Email: john@example.com, Phone: 555-1234",
            "Name: Jane Smith, Email: jane@test.com, Phone: 555-5678",
            "Name: Bob Johnson, Email: bob@company.org, Phone: 555-9012",
            "Invalid data: missing email",
            "Name: Alice Brown, Email: alice@domain.net, Phone: 555-3456"
        );
        
        // Create extraction pipeline
        List<Person> extractedPersons = rawData.stream()
            .map(WebScrapingExample::extractPerson)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
        
        System.out.println("  Extracted persons: " + extractedPersons);
        
        // Group by domain
        Map<String, List<Person>> byDomain = extractedPersons.stream()
            .collect(Collectors.groupingBy(person -> 
                person.getEmail().substring(person.getEmail().indexOf("@") + 1)));
        
        System.out.println("  Grouped by domain: " + byDomain);
        
        // Extract phone numbers
        List<String> phoneNumbers = extractedPersons.stream()
            .map(Person::getPhone)
            .collect(Collectors.toList());
        
        System.out.println("  Phone numbers: " + phoneNumbers);
        System.out.println();
    }
    
    /**
     * Example 3: Content Analysis
     * 
     * Analyzing content for various metrics and patterns.
     */
    private static void demonstrateContentAnalysis() {
        System.out.println("3. Content Analysis:");
        
        String content = "The quick brown fox jumps over the lazy dog. " +
                        "The fox is very quick and agile. " +
                        "The dog is lazy but friendly. " +
                        "Both animals are interesting creatures.";
        
        // Word frequency analysis
        Map<String, Long> wordFrequency = Arrays.stream(content.toLowerCase().split("\\W+"))
            .filter(word -> word.length() > 2)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        
        System.out.println("  Word frequency: " + wordFrequency);
        
        // Find most common words
        List<Map.Entry<String, Long>> mostCommon = wordFrequency.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(5)
            .collect(Collectors.toList());
        
        System.out.println("  Most common words: " + mostCommon);
        
        // Sentence analysis
        List<String> sentences = Arrays.stream(content.split("\\."))
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .collect(Collectors.toList());
        
        System.out.println("  Sentences: " + sentences);
        
        // Average sentence length
        double avgSentenceLength = sentences.stream()
            .mapToInt(sentence -> sentence.split("\\s+").length)
            .average()
            .orElse(0.0);
        
        System.out.println("  Average sentence length: " + avgSentenceLength);
        System.out.println();
    }
    
    /**
     * Example 4: URL Processing
     * 
     * Processing and analyzing URLs using functional programming.
     */
    private static void demonstrateUrlProcessing() {
        System.out.println("4. URL Processing:");
        
        List<String> urls = Arrays.asList(
            "https://www.example.com/page1?param=value",
            "http://subdomain.test.org/path/to/resource",
            "https://api.service.com/v1/users?limit=10&offset=0",
            "ftp://files.example.net/downloads/file.zip",
            "https://www.invalid-url"
        );
        
        // Extract domains
        List<String> domains = urls.stream()
            .map(WebScrapingExample::extractDomain)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
        
        System.out.println("  Domains: " + domains);
        
        // Group by protocol
        Map<String, List<String>> byProtocol = urls.stream()
            .collect(Collectors.groupingBy(url -> 
                url.substring(0, url.indexOf("://"))));
        
        System.out.println("  Grouped by protocol: " + byProtocol);
        
        // Extract query parameters
        List<Map<String, String>> queryParams = urls.stream()
            .map(WebScrapingExample::extractQueryParams)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
        
        System.out.println("  Query parameters: " + queryParams);
        
        // Validate URLs
        List<String> validUrls = urls.stream()
            .filter(WebScrapingExample::isValidUrl)
            .collect(Collectors.toList());
        
        System.out.println("  Valid URLs: " + validUrls);
        System.out.println();
    }
    
    /**
     * Example 5: Data Validation and Cleaning
     * 
     * Validating and cleaning extracted data.
     */
    private static void demonstrateDataValidation() {
        System.out.println("5. Data Validation and Cleaning:");
        
        List<String> rawEmails = Arrays.asList(
            "john@example.com",
            "jane@test.org",
            "invalid-email",
            "bob@company.net",
            "not-an-email",
            "alice@domain.com"
        );
        
        // Validate and clean emails
        List<String> validEmails = rawEmails.stream()
            .filter(WebScrapingExample::isValidEmail)
            .map(String::toLowerCase)
            .distinct()
            .sorted()
            .collect(Collectors.toList());
        
        System.out.println("  Valid emails: " + validEmails);
        
        // Clean and normalize data
        List<String> rawNames = Arrays.asList(
            "  John Doe  ",
            "JANE SMITH",
            "bob johnson",
            "  Alice Brown  ",
            "CHARLIE WILSON"
        );
        
        List<String> cleanNames = rawNames.stream()
            .map(String::trim)
            .map(name -> Arrays.stream(name.split("\\s+"))
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" ")))
            .collect(Collectors.toList());
        
        System.out.println("  Clean names: " + cleanNames);
        
        // Data quality metrics
        long totalEmails = rawEmails.size();
        long validEmailCount = validEmails.size();
        double emailQuality = (double) validEmailCount / totalEmails * 100;
        
        System.out.println("  Email quality: " + String.format("%.1f%%", emailQuality));
        System.out.println();
    }
    
    // Helper methods
    
    private static Optional<Person> extractPerson(String line) {
        try {
            String[] parts = line.split(", ");
            String name = extractValue(parts, "Name");
            String email = extractValue(parts, "Email");
            String phone = extractValue(parts, "Phone");
            
            if (name != null && email != null && phone != null) {
                return Optional.of(new Person(name, email, phone));
            }
        } catch (Exception e) {
            // Invalid data
        }
        return Optional.empty();
    }
    
    private static String extractValue(String[] parts, String key) {
        return Arrays.stream(parts)
            .filter(part -> part.startsWith(key + ":"))
            .findFirst()
            .map(part -> part.substring(key.length() + 2))
            .orElse(null);
    }
    
    private static Optional<String> extractDomain(String url) {
        try {
            if (url.contains("://")) {
                String domain = url.substring(url.indexOf("://") + 3);
                if (domain.contains("/")) {
                    domain = domain.substring(0, domain.indexOf("/"));
                }
                return Optional.of(domain);
            }
        } catch (Exception e) {
            // Invalid URL
        }
        return Optional.empty();
    }
    
    private static Optional<Map<String, String>> extractQueryParams(String url) {
        try {
            if (url.contains("?")) {
                String queryString = url.substring(url.indexOf("?") + 1);
                Map<String, String> params = new HashMap<>();
                
                Arrays.stream(queryString.split("&"))
                    .filter(param -> param.contains("="))
                    .forEach(param -> {
                        String[] keyValue = param.split("=", 2);
                        params.put(keyValue[0], keyValue[1]);
                    });
                
                return Optional.of(params);
            }
        } catch (Exception e) {
            // Invalid query string
        }
        return Optional.empty();
    }
    
    private static boolean isValidUrl(String url) {
        return url.matches("^https?://[\\w\\-]+(\\.[\\w\\-]+)+([\\w\\-\\.,@?^=%&:/~\\+#]*[\\w\\-\\@?^=%&/~\\+#])?$");
    }
    
    private static boolean isValidEmail(String email) {
        return email.matches("^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}$");
    }
    
    // Data classes
    
    public static class Person {
        private final String name;
        private final String email;
        private final String phone;
        
        public Person(String name, String email, String phone) {
            this.name = name;
            this.email = email;
            this.phone = phone;
        }
        
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getPhone() { return phone; }
        
        @Override
        public String toString() {
            return String.format("Person{name='%s', email='%s', phone='%s'}", name, email, phone);
        }
    }
    
    /**
     * Key Takeaways:
     * 
     * 1. Functional programming excels at data transformation and extraction
     * 2. Streams provide powerful tools for processing unstructured data
     * 3. Optional helps handle missing or invalid data gracefully
     * 4. Collectors enable complex data aggregation and grouping
     * 5. Pipeline patterns make data processing more maintainable
     * 6. Error handling can be integrated into functional pipelines
     * 7. Real-world data often requires cleaning and validation
     */
}
