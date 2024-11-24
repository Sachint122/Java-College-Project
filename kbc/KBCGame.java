import java.util.Random;
import java.util.Scanner;

public class KBCGame {
    private static Random random = new Random();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String[] questions = {
            "Who is the current Prime Minister of India?", 
            "Which city is known as the Pink City of India?",
            "What is the national animal of India?",
            "Who wrote the Indian national anthem, Jana Gana Mana ?",
            "Which Indian state is famous for the backwaters of Alleppey?",
            "Which city is home to the iconic Taj Mahal?",
            "Who is known as the Father of the Indian Constitution?",
            "Which Indian city is famous for its annual Kumbh Mela festival?",
            "Which Indian cricketer holds the record for the highest individual score in Test cricket?",
            "What is the currency of India?",
            "Who was the first President of India?",
            "Which Indian state is known as the Land of the Rising Sun?",
            "Which Indian city is known as the Silicon Valley of India?",
            "Who is the Missile Man of India?",
            "Which Indian state is home to the Kaziranga National Park, known for its one-horned rhinoceros?"
        };
        String[][] options = {
            {"Lalu Singh", "Narendra Modi", "Bengal Tiger", "Dr. B.R. Ambedkar"},
            {"Jaipur", "Delhi", "Kolkata", "Indore"},
            {"Bengal Tiger", "Elephant", "Lion", "Panther"},
            {"Chetan Bhagat", "Arundhati Roy", "Rabindranath Tagore", "Salman Rushdie"},
            {"Gujarat", "Madhya Pradesh", "Uttar Pradesh", "Kerala"},
            {"Agra", "Mumbai", "New Delhi", "Kolkata"},
            {"Rajendra Prasad", "Jawaharlal Nehru", "Dr. B.R. Ambedkar", "G.V. Mavlankar"},
            {"Prayagraj", "Jaipur", "Varanasi", "Kolkata"},
            {"Sachin T.", "Dhoni", "Rohit", "Brian Lara"},
            {"INR", "USD", "JPY", "GBP"},
            {"Velikkakathu Sankaran", "Pawar", "Dr. Rajendra Prasad", "Zakir Husain Khan"},
            {"Gujarat", "Arunachal Pradesh", "Bihar", "Sikkim"},
            {"Bengaluru", "Chennai", "New Delhi", "Mayamar"},
            {"Rabindranath Tagore", "Dr. A.P.J. Abdul Kalam", "Narendra Modi", "Lalu Singh"},
            {"Assam", "Sikkim", "Kashmir", "Karnataka"}
        };
        int[] answers = {1, 0, 0, 2, 3, 0, 2, 0, 3, 0, 2, 1, 0, 1, 0};

        int[] askedQuestions = new int[10];
        int questionCount = 0;
        int score = 0;

        while (questionCount < 10) {
            int temp = random.nextInt(questions.length);
            if (!isAsked(askedQuestions, temp)) {
                askedQuestions[questionCount] = temp;
                questionCount++;
                System.out.println("Question number (" + questionCount + ")");
                System.out.println(questions[temp]);
                for (int i = 0; i < 4; i++) {
                    System.out.println("(" + (i + 1) + ") = " + options[temp][i]);
                }
                System.out.print("Please choose the correct answer: ");
                int userAnswer = sc.nextInt() - 1;

                if (answers[temp] == userAnswer) {
                    System.out.println("Correct answer!");
                    score += 1000;
                    System.out.println("You won " + score + " Rupees");
                } else {
                    System.out.println("Wrong answer. Game Over!");
                    break;
                }
                if (questionCount == 10) {
                    System.out.println("Great! You reached the highest level of the game!");
                    break;
                }
            }
        }
        sc.close();
    }

    private static boolean isAsked(int[] askedQuestions, int question) {
        for (int i : askedQuestions) {
            if (i == question) {
                return true;
            }
        }
        return false;
    }
}
