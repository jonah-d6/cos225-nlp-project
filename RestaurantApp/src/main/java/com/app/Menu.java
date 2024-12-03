package com.app;

import com.app.database.Database;
import com.app.review.Review;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Menu {
    private static List<Review> reviews = new ArrayList<>();
    public final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            showMenu();
            int choice = getUserInput();
            switch (choice) {
                case 1:
                    viewAndManageReviews();
                    break;
                case 2:
                    uploadReviews();
                    break;
                case 3:
                    sortAndFilterReviews();
                    break;
                case 4:
                    initializeDatabaseConnection();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n=== Menu ===");
        System.out.println("1. View and manage reviews");
        System.out.println("2. Upload reviews to a destination (CSV or database)");
        System.out.println("3. Sort and filter reviews based on rating or keywords");
        System.out.println("4. Initialize database connection");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getUserInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static void viewAndManageReviews() {
        System.out.println("\n=== View and Manage Reviews ===");
        if (reviews.isEmpty()) {
            System.out.println("No reviews available.");
        } else {
            for (int i = 0; i < reviews.size(); i++) {
                Review review = reviews.get(i);
                System.out.println(i + 1 + ". Rating: " + (review.getIsPositive() ? "Positive" : "Negative") +
                        ", Review: " + String.join(" ", review.getContents()));
            }
        }

        System.out.println("\n1. Add new review");
        System.out.println("2. Go back to the main menu");
        System.out.print("Enter your choice: ");
        int choice = getUserInput();
        if (choice == 1) {
            addNewReview();
        }
    }

    private static void addNewReview() {
        Review newReview = Review.createReview();
        reviews.add(newReview);
        System.out.println("Review added successfully!");
    }

    private static void uploadReviews() {
        System.out.println("\n=== Upload Reviews ===");
        System.out.println("1. Upload to CSV");
        System.out.println("2. Upload to MongoDB");
        System.out.println("3. Go back to the main menu");
        System.out.print("Enter your choice: ");
        int choice = getUserInput();
        if (choice == 1) {
            uploadToCSV();
        } else if (choice == 2) {
            uploadToDatabase();
        }
    }

    private static void uploadToCSV() {
        // Implement CSV upload functionality here
        System.out.println("Uploading reviews to CSV...");
        // Example: Use a CSV library like OpenCSV or write a custom method to write reviews to a CSV file.
    }

    private static void uploadToDatabase() {
        System.out.println("Uploading reviews to MongoDB...");
        Database.initializeConnection();
        for (Review review : reviews) {
            review.uploadToMongo("reviews");
        }
        System.out.println("Reviews uploaded to MongoDB successfully!");
    }

    private static void sortAndFilterReviews() {
        System.out.println("\n=== Sort and Filter Reviews ===");
        System.out.println("1. Sort reviews by rating");
        System.out.println("2. Filter reviews by keyword");
        System.out.println("3. Go back to the main menu");
        System.out.print("Enter your choice: ");
        int choice = getUserInput();

        if (choice == 1) {
            sortReviewsByRating();
        } else if (choice == 2) {
            filterReviewsByKeyword();
        }
    }

    private static void sortReviewsByRating() {
        System.out.println("\nSorting reviews by rating...");
        reviews.sort(Comparator.comparing(Review::getIsPositive).reversed()); // Sort by rating, positive reviews first
        System.out.println("Reviews sorted successfully!");
        viewAndManageReviews(); // View reviews after sorting
    }

    private static void filterReviewsByKeyword() {
        System.out.print("\nEnter the keyword to filter reviews: ");
        String keyword = scanner.nextLine().toLowerCase();

        List<Review> filteredReviews = reviews.stream()
                .filter(review -> review.getContents().stream().anyMatch(content -> content.toLowerCase().contains(keyword)))
                .collect(Collectors.toList());

        if (filteredReviews.isEmpty()) {
            System.out.println("No reviews found with the keyword '" + keyword + "'.");
        } else {
            System.out.println("\nFiltered reviews:");
            for (Review review : filteredReviews) {
                System.out.println("Rating: " + (review.getIsPositive() ? "Positive" : "Negative") +
                        ", Review: " + String.join(" ", review.getContents()));
            }
        }
    }

    private static void initializeDatabaseConnection() {
        System.out.println("\nInitializing database connection...");
        Database.initializeConnection();
        System.out.println("Database connection initialized successfully!");
    }
}
