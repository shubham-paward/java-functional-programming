package com.functionalprogramming.examples;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * Financial Analysis and Trading Example
 * 
 * This example demonstrates how functional programming can be used for
 * financial data analysis, trading strategies, and risk management.
 */
public class FinancialAnalysis {
    
    public static void main(String[] args) {
        System.out.println("=== Financial Analysis and Trading ===\n");
        
        // Example 1: Stock Price Analysis
        demonstrateStockAnalysis();
        
        // Example 2: Portfolio Management
        demonstratePortfolioManagement();
        
        // Example 3: Risk Assessment
        demonstrateRiskAssessment();
        
        // Example 4: Trading Strategies
        demonstrateTradingStrategies();
        
        // Example 5: Performance Metrics
        demonstratePerformanceMetrics();
    }
    
    /**
     * Example 1: Stock Price Analysis
     * 
     * Analyzing stock price data using functional programming.
     */
    private static void demonstrateStockAnalysis() {
        System.out.println("1. Stock Price Analysis:");
        
        List<StockPrice> prices = Arrays.asList(
            new StockPrice("AAPL", "2024-01-01", 150.0, 1000000),
            new StockPrice("AAPL", "2024-01-02", 152.5, 1200000),
            new StockPrice("AAPL", "2024-01-03", 148.0, 900000),
            new StockPrice("AAPL", "2024-01-04", 155.0, 1500000),
            new StockPrice("AAPL", "2024-01-05", 153.5, 1100000),
            new StockPrice("GOOGL", "2024-01-01", 2800.0, 500000),
            new StockPrice("GOOGL", "2024-01-02", 2850.0, 600000),
            new StockPrice("GOOGL", "2024-01-03", 2750.0, 450000),
            new StockPrice("GOOGL", "2024-01-04", 2900.0, 700000),
            new StockPrice("GOOGL", "2024-01-05", 2880.0, 550000)
        );
        
        // Calculate daily returns
        List<DailyReturn> returns = calculateDailyReturns(prices);
        System.out.println("  Daily returns: " + returns);
        
        // Calculate average return by stock
        Map<String, Double> avgReturns = returns.stream()
            .collect(Collectors.groupingBy(
                DailyReturn::getSymbol,
                Collectors.averagingDouble(DailyReturn::getReturn)
            ));
        
        System.out.println("  Average returns by stock: " + avgReturns);
        
        // Find best and worst performing days
        Optional<DailyReturn> bestDay = returns.stream()
            .max(Comparator.comparing(DailyReturn::getReturn));
        
        Optional<DailyReturn> worstDay = returns.stream()
            .min(Comparator.comparing(DailyReturn::getReturn));
        
        System.out.println("  Best performing day: " + bestDay.orElse(null));
        System.out.println("  Worst performing day: " + worstDay.orElse(null));
        
        // Calculate volatility (standard deviation of returns)
        Map<String, Double> volatility = returns.stream()
            .collect(Collectors.groupingBy(
                DailyReturn::getSymbol,
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    list -> calculateVolatility(list.stream().mapToDouble(DailyReturn::getReturn).toArray())
                )
            ));
        
        System.out.println("  Volatility by stock: " + volatility);
        System.out.println();
    }
    
    /**
     * Example 2: Portfolio Management
     * 
     * Managing investment portfolios using functional programming.
     */
    private static void demonstratePortfolioManagement() {
        System.out.println("2. Portfolio Management:");
        
        List<Portfolio> portfolios = Arrays.asList(
            new Portfolio("Conservative", Arrays.asList(
                new Holding("BOND", 1000, 100.0),
                new Holding("GOLD", 50, 2000.0),
                new Holding("CASH", 10000, 1.0)
            )),
            new Portfolio("Aggressive", Arrays.asList(
                new Holding("AAPL", 100, 150.0),
                new Holding("GOOGL", 10, 2800.0),
                new Holding("TSLA", 50, 200.0)
            )),
            new Portfolio("Balanced", Arrays.asList(
                new Holding("AAPL", 50, 150.0),
                new Holding("BOND", 500, 100.0),
                new Holding("GOLD", 25, 2000.0)
            ))
        );
        
        // Calculate portfolio values
        Map<String, Double> portfolioValues = portfolios.stream()
            .collect(Collectors.toMap(
                Portfolio::getName,
                portfolio -> portfolio.getHoldings().stream()
                    .mapToDouble(holding -> holding.getQuantity() * holding.getPrice())
                    .sum()
            ));
        
        System.out.println("  Portfolio values: " + portfolioValues);
        
        // Find most valuable portfolio
        Optional<Portfolio> mostValuable = portfolios.stream()
            .max(Comparator.comparing(portfolio -> 
                portfolio.getHoldings().stream()
                    .mapToDouble(holding -> holding.getQuantity() * holding.getPrice())
                    .sum()
            ));
        
        System.out.println("  Most valuable portfolio: " + mostValuable.map(Portfolio::getName).orElse("None"));
        
        // Calculate diversification (number of different holdings)
        Map<String, Long> diversification = portfolios.stream()
            .collect(Collectors.toMap(
                Portfolio::getName,
                portfolio -> (long) portfolio.getHoldings().size()
            ));
        
        System.out.println("  Diversification (number of holdings): " + diversification);
        System.out.println();
    }
    
    /**
     * Example 3: Risk Assessment
     * 
     * Assessing investment risk using various metrics.
     */
    private static void demonstrateRiskAssessment() {
        System.out.println("3. Risk Assessment:");
        
        List<RiskMetric> riskMetrics = Arrays.asList(
            new RiskMetric("AAPL", 0.15, 0.25, 0.05),
            new RiskMetric("GOOGL", 0.18, 0.30, 0.08),
            new RiskMetric("TSLA", 0.25, 0.45, 0.12),
            new RiskMetric("BOND", 0.05, 0.08, 0.02),
            new RiskMetric("GOLD", 0.08, 0.12, 0.03)
        );
        
        // Calculate risk-adjusted returns (Sharpe ratio)
        Map<String, Double> sharpeRatios = riskMetrics.stream()
            .collect(Collectors.toMap(
                RiskMetric::getSymbol,
                metric -> metric.getExpectedReturn() / metric.getVolatility()
            ));
        
        System.out.println("  Sharpe ratios: " + sharpeRatios);
        
        // Find high-risk investments
        List<String> highRiskInvestments = riskMetrics.stream()
            .filter(metric -> metric.getVolatility() > 0.20)
            .map(RiskMetric::getSymbol)
            .collect(Collectors.toList());
        
        System.out.println("  High-risk investments: " + highRiskInvestments);
        
        // Calculate portfolio risk (simplified)
        double portfolioRisk = riskMetrics.stream()
            .mapToDouble(metric -> metric.getVolatility() * metric.getWeight())
            .sum();
        
        System.out.println("  Portfolio risk: " + String.format("%.3f", portfolioRisk));
        
        // Find investments with good risk-return profile
        List<String> goodInvestments = riskMetrics.stream()
            .filter(metric -> metric.getExpectedReturn() > 0.10 && metric.getVolatility() < 0.20)
            .map(RiskMetric::getSymbol)
            .collect(Collectors.toList());
        
        System.out.println("  Good risk-return investments: " + goodInvestments);
        System.out.println();
    }
    
    /**
     * Example 4: Trading Strategies
     * 
     * Implementing trading strategies using functional programming.
     */
    private static void demonstrateTradingStrategies() {
        System.out.println("4. Trading Strategies:");
        
        List<StockPrice> prices = Arrays.asList(
            new StockPrice("AAPL", "2024-01-01", 150.0, 1000000),
            new StockPrice("AAPL", "2024-01-02", 152.5, 1200000),
            new StockPrice("AAPL", "2024-01-03", 148.0, 900000),
            new StockPrice("AAPL", "2024-01-04", 155.0, 1500000),
            new StockPrice("AAPL", "2024-01-05", 153.5, 1100000)
        );
        
        // Moving average strategy
        List<TradingSignal> movingAverageSignals = calculateMovingAverageSignals(prices, 3);
        System.out.println("  Moving average signals: " + movingAverageSignals);
        
        // Momentum strategy
        List<TradingSignal> momentumSignals = calculateMomentumSignals(prices, 2);
        System.out.println("  Momentum signals: " + momentumSignals);
        
        // Volume-based strategy
        List<TradingSignal> volumeSignals = calculateVolumeSignals(prices);
        System.out.println("  Volume signals: " + volumeSignals);
        
        // Combine strategies
        List<TradingSignal> combinedSignals = combineSignals(movingAverageSignals, momentumSignals, volumeSignals);
        System.out.println("  Combined signals: " + combinedSignals);
        
        // Calculate strategy performance
        double strategyReturn = calculateStrategyReturn(prices, combinedSignals);
        System.out.println("  Strategy return: " + String.format("%.2f%%", strategyReturn));
        System.out.println();
    }
    
    /**
     * Example 5: Performance Metrics
     * 
     * Calculating various performance metrics for investments.
     */
    private static void demonstratePerformanceMetrics() {
        System.out.println("5. Performance Metrics:");
        
        List<Double> returns = Arrays.asList(0.02, 0.01, -0.01, 0.03, 0.02, -0.02, 0.01, 0.04, 0.01, 0.02);
        
        // Calculate cumulative returns
        List<Double> cumulativeReturns = calculateCumulativeReturns(returns);
        System.out.println("  Cumulative returns: " + cumulativeReturns);
        
        // Calculate maximum drawdown
        double maxDrawdown = calculateMaxDrawdown(cumulativeReturns);
        System.out.println("  Maximum drawdown: " + String.format("%.2f%%", maxDrawdown * 100));
        
        // Calculate win rate
        double winRate = returns.stream()
            .mapToDouble(r -> r > 0 ? 1.0 : 0.0)
            .average()
            .orElse(0.0);
        
        System.out.println("  Win rate: " + String.format("%.2f%%", winRate * 100));
        
        // Calculate average win and loss
        double avgWin = returns.stream()
            .filter(r -> r > 0)
            .mapToDouble(Double::doubleValue)
            .average()
            .orElse(0.0);
        
        double avgLoss = returns.stream()
            .filter(r -> r < 0)
            .mapToDouble(Double::doubleValue)
            .average()
            .orElse(0.0);
        
        System.out.println("  Average win: " + String.format("%.2f%%", avgWin * 100));
        System.out.println("  Average loss: " + String.format("%.2f%%", avgLoss * 100));
        
        // Calculate profit factor
        double profitFactor = Math.abs(avgWin / avgLoss);
        System.out.println("  Profit factor: " + String.format("%.2f", profitFactor));
        System.out.println();
    }
    
    // Helper methods
    
    private static List<DailyReturn> calculateDailyReturns(List<StockPrice> prices) {
        return IntStream.range(1, prices.size())
            .filter(i -> prices.get(i).getSymbol().equals(prices.get(i-1).getSymbol()))
            .mapToObj(i -> {
                StockPrice current = prices.get(i);
                StockPrice previous = prices.get(i-1);
                double returnValue = (current.getPrice() - previous.getPrice()) / previous.getPrice();
                return new DailyReturn(current.getSymbol(), current.getDate(), returnValue);
            })
            .collect(Collectors.toList());
    }
    
    private static double calculateVolatility(double[] returns) {
        double mean = Arrays.stream(returns).average().orElse(0.0);
        double variance = Arrays.stream(returns)
            .map(r -> Math.pow(r - mean, 2))
            .average()
            .orElse(0.0);
        return Math.sqrt(variance);
    }
    
    private static List<TradingSignal> calculateMovingAverageSignals(List<StockPrice> prices, int period) {
        return IntStream.range(period, prices.size())
            .mapToObj(i -> {
                double currentPrice = prices.get(i).getPrice();
                double movingAverage = prices.subList(i - period, i).stream()
                    .mapToDouble(StockPrice::getPrice)
                    .average()
                    .orElse(0.0);
                
                String signal = currentPrice > movingAverage ? "BUY" : "SELL";
                return new TradingSignal(prices.get(i).getSymbol(), prices.get(i).getDate(), signal);
            })
            .collect(Collectors.toList());
    }
    
    private static List<TradingSignal> calculateMomentumSignals(List<StockPrice> prices, int period) {
        return IntStream.range(period, prices.size())
            .mapToObj(i -> {
                double currentPrice = prices.get(i).getPrice();
                double pastPrice = prices.get(i - period).getPrice();
                double momentum = (currentPrice - pastPrice) / pastPrice;
                
                String signal = momentum > 0.02 ? "BUY" : momentum < -0.02 ? "SELL" : "HOLD";
                return new TradingSignal(prices.get(i).getSymbol(), prices.get(i).getDate(), signal);
            })
            .collect(Collectors.toList());
    }
    
    private static List<TradingSignal> calculateVolumeSignals(List<StockPrice> prices) {
        double avgVolume = prices.stream()
            .mapToDouble(StockPrice::getVolume)
            .average()
            .orElse(0.0);
        
        return prices.stream()
            .map(price -> {
                String signal = price.getVolume() > avgVolume * 1.5 ? "BUY" : "HOLD";
                return new TradingSignal(price.getSymbol(), price.getDate(), signal);
            })
            .collect(Collectors.toList());
    }
    
    private static List<TradingSignal> combineSignals(List<TradingSignal>... signalLists) {
        return IntStream.range(0, signalLists[0].size())
            .mapToObj(i -> {
                long buyCount = Arrays.stream(signalLists)
                    .mapToLong(list -> list.get(i).getSignal().equals("BUY") ? 1 : 0)
                    .sum();
                
                long sellCount = Arrays.stream(signalLists)
                    .mapToLong(list -> list.get(i).getSignal().equals("SELL") ? 1 : 0)
                    .sum();
                
                String combinedSignal = buyCount > sellCount ? "BUY" : sellCount > buyCount ? "SELL" : "HOLD";
                return new TradingSignal(signalLists[0].get(i).getSymbol(), signalLists[0].get(i).getDate(), combinedSignal);
            })
            .collect(Collectors.toList());
    }
    
    private static double calculateStrategyReturn(List<StockPrice> prices, List<TradingSignal> signals) {
        // Simplified calculation - in reality, this would be more complex
        return signals.stream()
            .mapToDouble(signal -> signal.getSignal().equals("BUY") ? 0.01 : -0.01)
            .sum() * 100;
    }
    
    private static List<Double> calculateCumulativeReturns(List<Double> returns) {
        List<Double> cumulative = new ArrayList<>();
        double cumulativeValue = 1.0;
        
        for (double returnValue : returns) {
            cumulativeValue *= (1 + returnValue);
            cumulative.add(cumulativeValue);
        }
        
        return cumulative;
    }
    
    private static double calculateMaxDrawdown(List<Double> cumulativeReturns) {
        double maxDrawdown = 0.0;
        double peak = cumulativeReturns.get(0);
        
        for (double value : cumulativeReturns) {
            if (value > peak) {
                peak = value;
            }
            double drawdown = (peak - value) / peak;
            if (drawdown > maxDrawdown) {
                maxDrawdown = drawdown;
            }
        }
        
        return maxDrawdown;
    }
    
    // Data classes
    
    public static class StockPrice {
        private final String symbol;
        private final String date;
        private final double price;
        private final long volume;
        
        public StockPrice(String symbol, String date, double price, long volume) {
            this.symbol = symbol;
            this.date = date;
            this.price = price;
            this.volume = volume;
        }
        
        public String getSymbol() { return symbol; }
        public String getDate() { return date; }
        public double getPrice() { return price; }
        public long getVolume() { return volume; }
        
        @Override
        public String toString() {
            return String.format("%s: $%.2f (Vol: %,d)", symbol, price, volume);
        }
    }
    
    public static class DailyReturn {
        private final String symbol;
        private final String date;
        private final double returnValue;
        
        public DailyReturn(String symbol, String date, double returnValue) {
            this.symbol = symbol;
            this.date = date;
            this.returnValue = returnValue;
        }
        
        public String getSymbol() { return symbol; }
        public String getDate() { return date; }
        public double getReturn() { return returnValue; }
        
        @Override
        public String toString() {
            return String.format("%s: %.2f%%", symbol, returnValue * 100);
        }
    }
    
    public static class Portfolio {
        private final String name;
        private final List<Holding> holdings;
        
        public Portfolio(String name, List<Holding> holdings) {
            this.name = name;
            this.holdings = holdings;
        }
        
        public String getName() { return name; }
        public List<Holding> getHoldings() { return holdings; }
        
        @Override
        public String toString() {
            return String.format("Portfolio{name='%s', holdings=%s}", name, holdings);
        }
    }
    
    public static class Holding {
        private final String symbol;
        private final int quantity;
        private final double price;
        
        public Holding(String symbol, int quantity, double price) {
            this.symbol = symbol;
            this.quantity = quantity;
            this.price = price;
        }
        
        public String getSymbol() { return symbol; }
        public int getQuantity() { return quantity; }
        public double getPrice() { return price; }
        
        @Override
        public String toString() {
            return String.format("%s: %d @ $%.2f", symbol, quantity, price);
        }
    }
    
    public static class RiskMetric {
        private final String symbol;
        private final double expectedReturn;
        private final double volatility;
        private final double weight;
        
        public RiskMetric(String symbol, double expectedReturn, double volatility, double weight) {
            this.symbol = symbol;
            this.expectedReturn = expectedReturn;
            this.volatility = volatility;
            this.weight = weight;
        }
        
        public String getSymbol() { return symbol; }
        public double getExpectedReturn() { return expectedReturn; }
        public double getVolatility() { return volatility; }
        public double getWeight() { return weight; }
        
        @Override
        public String toString() {
            return String.format("%s: E[R]=%.2f%%, Vol=%.2f%%, W=%.2f%%", 
                symbol, expectedReturn * 100, volatility * 100, weight * 100);
        }
    }
    
    public static class TradingSignal {
        private final String symbol;
        private final String date;
        private final String signal;
        
        public TradingSignal(String symbol, String date, String signal) {
            this.symbol = symbol;
            this.date = date;
            this.signal = signal;
        }
        
        public String getSymbol() { return symbol; }
        public String getDate() { return date; }
        public String getSignal() { return signal; }
        
        @Override
        public String toString() {
            return String.format("%s %s: %s", symbol, date, signal);
        }
    }
    
    /**
     * Key Takeaways:
     * 
     * 1. Functional programming excels at financial data analysis
     * 2. Streams provide powerful tools for calculating metrics and ratios
     * 3. Collectors enable complex aggregations and groupings
     * 4. Optional helps handle missing or invalid financial data
     * 5. Pipeline patterns make financial calculations more maintainable
     * 6. Error handling is crucial in financial applications
     * 7. Real-world financial analysis requires robust data processing
     */
}
