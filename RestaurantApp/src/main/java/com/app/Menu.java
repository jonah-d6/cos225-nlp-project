package com.app;

import com.app.database.Database;
import com.app.review.Review;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * The Menu class is the main frontend functionality for the app
 * It displays the menu to the user and allows for the navigation of it
 * It calls functions from the other files as needed per the menu selection
 */

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
    /**
     * showMenu() is a simple function to display the menu options to the user
     */
    private static void showMenu() {
        System.out.println("\n=== Menu ===");
        System.out.println("1. View and manage reviews");
        System.out.println("2. Upload reviews to a destination (CSV or database)");
        System.out.println("3. Sort and filter reviews based on rating or keywords");
        System.out.println("4. Initialize database connection");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    /**
     * getUserInput() gets the user input from terminal
     * 
     * @return int - user selection
     * 
     * Called in main for user menu navigation
     */

    private static int getUserInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    /**
     * viewAndManageReviews() runs after one selection of the menu and displays a menu of its own
     * 
     * Has functionality of calling functions in the Review class to get and display the attributes of a review
     * Also has functionality to add a new Review object via user input
     */

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

        System.out.println("\n1. Add new review with rating");
        System.out.println("2. Add new review with rating prediction");
        System.out.println("3. Go back to the main menu");
        System.out.print("Enter your choice: ");
        int choice = getUserInput();
        if (choice == 1) {
            addNewReview();
        }else if (choice == 2){
            assert true;
            //TODO:call function from Classifier class (does not exist yet)
        }
    }

    /**
     * addNewReview() adds a new review via user input to the reviews array list attribute of Menu
     */

    private static void addNewReview() {
        Review newReview = Review.createReview();
        reviews.add(newReview);
        System.out.println("Review added successfully!");
    }

    /**
     * uploadReviews() acts as a "sub-menu" for one of the main menu selections
     * 
     * inlcudes options to upload reviews to the csv file, or MongoDB
     */

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

    /**
     * uploadToDatabase() calls the functions initialize connection and upload to MongoDB
     * It will upload the current Reviews in the arraylist reviews attribute of Menu to MongoDB
     */

    private static void uploadToDatabase() {
        System.out.println("Uploading reviews to MongoDB...");
        Database.initializeConnection();
        for (Review review : reviews) {
            review.uploadToMongo("reviews");
        }
        System.out.println("Reviews uploaded to MongoDB successfully!");
    }

    /**
     * sortAndFilterReviews() is also a "sub-menu" that the user can navigate to
     * Similarly to the other menus and sub-menus, the user is given options and can choose them
     */

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

    /**
     * sortReviewsByRating() will sort the reviews based on the 1-5 rating in descending order.
     * It will then display the reviews in the sorted order to the user.
     */

    private static void sortReviewsByRating() {
        System.out.println("\nSorting reviews by rating...");
        reviews.sort(Comparator.comparing(Review::getIsPositive).reversed()); // Sort by rating, positive reviews first
        System.out.println("Reviews sorted successfully!");
        viewAndManageReviews(); // View reviews after sorting
    }

    /**
     * filterReviewsByKeyword() will allow the user to input a word to search for reviews containing it
     * If no reviews are found with the chosen word, it will inform the user of that
     * If reviews are found with the keyword, they will be displayed to the user
     */

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

    /**
     * initializeDataBaseConnection() connects the program to MongoDB
     * This is done by calling the initializeConnection() function in the Database object
     * This should be used before attempting to make any uploads to MongoDB
     */

    private static void initializeDatabaseConnection() {
        System.out.println("\nInitializing database connection...");
        Database.initializeConnection();
        System.out.println("Database connection initialized successfully!");
    }
}
