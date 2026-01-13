import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Question {
    String question;
    String[] options;
    int correctAnswer;
    
    public Question(String question, String[] options, int correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }
}

public class OnlineExaminationSystem extends JFrame {
    private String username;
    private String password;
    private ArrayList<Question> questions;
    private int currentQuestion = 0;
    private int score = 0;
    private int[] userAnswers;
    private Timer examTimer;
    private int timeRemaining;
    private int examDuration = 300; 
    
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JLabel timerLabel;
    private JLabel questionLabel;
    private ButtonGroup optionsGroup;
    private JRadioButton[] optionButtons;
    private JButton nextButton, submitButton, prevButton;
    private JLabel questionNumberLabel;
    
    public OnlineExaminationSystem() {
        setTitle("Online Examination System");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
   
        initializeQuestions();
        userAnswers = new int[questions.size()];
        for (int i = 0; i < userAnswers.length; i++) {
            userAnswers[i] = -1; 
        }
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        mainPanel.add(createLoginScreen(), "LOGIN");
        mainPanel.add(createProfileScreen(), "PROFILE");
        mainPanel.add(createExamScreen(), "EXAM");
        mainPanel.add(createResultScreen(), "RESULT");
        
        add(mainPanel);
        cardLayout.show(mainPanel, "LOGIN");
        setVisible(true);
    }
    
    private void initializeQuestions() {
        questions = new ArrayList<>();
        
        questions.add(new Question(
            "What is the capital of France?",
            new String[]{"A) London", "B) Berlin", "C) Paris", "D) Madrid"},
            2
        ));
        
        questions.add(new Question(
            "Which programming language is known as the 'mother of all languages'?",
            new String[]{"A) C", "B) Python", "C) Java", "D) Assembly"},
            0
        ));
        
        questions.add(new Question(
            "What does HTML stand for?",
            new String[]{"A) Hyper Text Markup Language", "B) High Tech Modern Language", 
                        "C) Home Tool Markup Language", "D) Hyperlinks and Text Markup Language"},
            0
        ));
        
        questions.add(new Question(
            "Who developed Java programming language?",
            new String[]{"A) Microsoft", "B) Sun Microsystems", "C) Oracle", "D) IBM"},
            1
        ));
        
        questions.add(new Question(
            "What is the result of 15 + 25 * 2?",
            new String[]{"A) 80", "B) 65", "C) 55", "D) 70"},
            1
        ));
        
        questions.add(new Question(
            "Which data structure uses LIFO principle?",
            new String[]{"A) Queue", "B) Stack", "C) Array", "D) Tree"},
            1
        ));
        
        questions.add(new Question(
            "What is the binary representation of decimal 10?",
            new String[]{"A) 1010", "B) 1100", "C) 1000", "D) 1110"},
            0
        ));
        
        questions.add(new Question(
            "Which keyword is used to define a constant in Java?",
            new String[]{"A) const", "B) final", "C) static", "D) constant"},
            1
        ));
        
        questions.add(new Question(
            "What does SQL stand for?",
            new String[]{"A) Structured Query Language", "B) Simple Question Language", 
                        "C) System Quality Level", "D) Standard Query List"},
            0
        ));
        
        questions.add(new Question(
            "Which of the following is not an OOP principle?",
            new String[]{"A) Encapsulation", "B) Inheritance", "C) Compilation", "D) Polymorphism"},
            2
        ));
    }
    
    private JPanel createLoginScreen() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(41, 128, 185));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel titleLabel = new JLabel("ONLINE EXAMINATION");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
        
        JLabel subtitleLabel = new JLabel("System Login");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitleLabel.setForeground(Color.LIGHT_GRAY);
        gbc.gridy = 1;
        panel.add(subtitleLabel, gbc);
        
        JPanel loginPanel = new JPanel(new GridLayout(3, 2, 10, 15));
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JTextField userField = new JTextField();
        userField.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JPasswordField passField = new JPasswordField();
        passField.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(46, 204, 113));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        
        JButton clearButton = new JButton("Clear");
        clearButton.setBackground(new Color(231, 76, 60));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        
        loginPanel.add(userLabel);
        loginPanel.add(userField);
        loginPanel.add(passLabel);
        loginPanel.add(passField);
        loginPanel.add(loginButton);
        loginPanel.add(clearButton);
        
        gbc.gridy = 2; gbc.insets = new Insets(30, 10, 10, 10);
        panel.add(loginPanel, gbc);
        
        loginButton.addActionListener(e -> {
            username = userField.getText();
            password = new String(passField.getPassword());
            if (!username.isEmpty() && !password.isEmpty()) {
                cardLayout.show(mainPanel, "PROFILE");
            } else {
                JOptionPane.showMessageDialog(this, "Please enter username and password!");
            }
        });
        
        clearButton.addActionListener(e -> {
            userField.setText("");
            passField.setText("");
        });
        
        return panel;
    }
    
    private JPanel createProfileScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(236, 240, 241));
        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(44, 62, 80));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel headerLabel = new JLabel("UPDATE PROFILE");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel nameLabel = new JLabel("Full Name:");
        JTextField nameField = new JTextField(20);
        
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);
        
        JLabel oldPassLabel = new JLabel("Old Password:");
        JPasswordField oldPassField = new JPasswordField(20);
        
        JLabel newPassLabel = new JLabel("New Password:");
        JPasswordField newPassField = new JPasswordField(20);
        
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(emailLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(oldPassLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(oldPassField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(newPassLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(newPassField, gbc);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);
        
        JButton updateButton = new JButton("Update Profile");
        updateButton.setBackground(new Color(46, 204, 113));
        updateButton.setForeground(Color.WHITE);
        updateButton.setFont(new Font("Arial", Font.BOLD, 14));
        
        JButton startExamButton = new JButton("Start Examination");
        startExamButton.setBackground(new Color(52, 152, 219));
        startExamButton.setForeground(Color.WHITE);
        startExamButton.setFont(new Font("Arial", Font.BOLD, 14));
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(231, 76, 60));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        
        buttonPanel.add(updateButton);
        buttonPanel.add(startExamButton);
        buttonPanel.add(logoutButton);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);
        
        updateButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Profile updated successfully!");
        });
        
        startExamButton.addActionListener(e -> {
            startExam();
            cardLayout.show(mainPanel, "EXAM");
        });
        
        logoutButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "LOGIN");
        });
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createExamScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(44, 62, 80));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("ONLINE EXAMINATION");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        timerLabel = new JLabel("Time: 05:00");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        timerLabel.setForeground(new Color(231, 76, 60));
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(timerLabel, BorderLayout.EAST);
        
        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        questionPanel.setBackground(Color.WHITE);
        questionPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        questionNumberLabel = new JLabel();
        questionNumberLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questionNumberLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        questionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        questionPanel.add(questionNumberLabel);
        questionPanel.add(Box.createVerticalStrut(20));
        questionPanel.add(questionLabel);
        questionPanel.add(Box.createVerticalStrut(30));

        optionsGroup = new ButtonGroup();
        optionButtons = new JRadioButton[4];
        
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionButtons[i].setFont(new Font("Arial", Font.PLAIN, 16));
            optionButtons[i].setBackground(Color.WHITE);
            optionButtons[i].setAlignmentX(Component.LEFT_ALIGNMENT);
            optionsGroup.add(optionButtons[i]);
            questionPanel.add(optionButtons[i]);
            questionPanel.add(Box.createVerticalStrut(15));
        }
        

        JPanel navPanel = new JPanel(new FlowLayout());
        navPanel.setBackground(Color.WHITE);
        
        prevButton = new JButton("Previous");
        prevButton.setFont(new Font("Arial", Font.BOLD, 14));
        prevButton.setBackground(new Color(149, 165, 166));
        prevButton.setForeground(Color.WHITE);
        
        nextButton = new JButton("Next");
        nextButton.setFont(new Font("Arial", Font.BOLD, 14));
        nextButton.setBackground(new Color(52, 152, 219));
        nextButton.setForeground(Color.WHITE);
        
        submitButton = new JButton("Submit Exam");
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setBackground(new Color(46, 204, 113));
        submitButton.setForeground(Color.WHITE);
        
        navPanel.add(prevButton);
        navPanel.add(nextButton);
        navPanel.add(submitButton);
        
        prevButton.addActionListener(e -> {
            saveCurrentAnswer();
            if (currentQuestion > 0) {
                currentQuestion--;
                displayQuestion();
            }
        });
        
        nextButton.addActionListener(e -> {
            saveCurrentAnswer();
            if (currentQuestion < questions.size() - 1) {
                currentQuestion++;
                displayQuestion();
            }
        });
        
        submitButton.addActionListener(e -> {
            saveCurrentAnswer();
            submitExam();
        });
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(questionPanel, BorderLayout.CENTER);
        panel.add(navPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createResultScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(236, 240, 241));
        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(44, 62, 80));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel headerLabel = new JLabel("EXAMINATION RESULTS");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        
        JPanel resultPanel = new JPanel(new GridBagLayout());
        resultPanel.setBackground(Color.WHITE);
        resultPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        
        JButton closeButton = new JButton("Close Session");
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setBackground(new Color(231, 76, 60));
        closeButton.setForeground(Color.WHITE);
        closeButton.addActionListener(e -> {
            resetExam();
            cardLayout.show(mainPanel, "LOGIN");
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(closeButton);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(resultPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void startExam() {
        currentQuestion = 0;
        score = 0;
        timeRemaining = examDuration;
        for (int i = 0; i < userAnswers.length; i++) {
            userAnswers[i] = -1;
        }
        
        displayQuestion();
        startTimer();
    }
    
    private void startTimer() {
        if (examTimer != null) {
            examTimer.stop();
        }
        
        examTimer = new Timer(1000, e -> {
            timeRemaining--;
            int minutes = timeRemaining / 60;
            int seconds = timeRemaining % 60;
            timerLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));
            
            if (timeRemaining <= 0) {
                examTimer.stop();
                JOptionPane.showMessageDialog(this, "Time's up! Exam will be submitted.");
                saveCurrentAnswer();
                submitExam();
            }
        });
        examTimer.start();
    }
    
    private void displayQuestion() {
        Question q = questions.get(currentQuestion);
        questionNumberLabel.setText("Question " + (currentQuestion + 1) + " of " + questions.size());
        questionLabel.setText("<html>" + q.question + "</html>");
        
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(q.options[i]);
            optionButtons[i].setSelected(false);
        }
        
        if (userAnswers[currentQuestion] != -1) {
            optionButtons[userAnswers[currentQuestion]].setSelected(true);
        }
        
        prevButton.setEnabled(currentQuestion > 0);
        nextButton.setEnabled(currentQuestion < questions.size() - 1);
    }
    
    private void saveCurrentAnswer() {
        for (int i = 0; i < 4; i++) {
            if (optionButtons[i].isSelected()) {
                userAnswers[currentQuestion] = i;
                break;
            }
        }
    }
    
    private void submitExam() {
        if (examTimer != null) {
            examTimer.stop();
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to submit the exam?", 
            "Confirm Submission", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            calculateScore();
            showResults();
            cardLayout.show(mainPanel, "RESULT");
        } else if (timeRemaining > 0) {
            examTimer.start();
        }
    }
    
    private void calculateScore() {
        score = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (userAnswers[i] == questions.get(i).correctAnswer) {
                score++;
            }
        }
    }
    
    private void showResults() {
        JPanel resultPanel = (JPanel) ((JPanel) mainPanel.getComponent(3)).getComponent(1);
        resultPanel.removeAll();
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;
        
        JLabel nameLabel = new JLabel("Student: " + username);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridy = 0;
        resultPanel.add(nameLabel, gbc);
        
        JLabel scoreLabel = new JLabel("Score: " + score + " / " + questions.size());
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setForeground(score >= questions.size() * 0.6 ? 
            new Color(46, 204, 113) : new Color(231, 76, 60));
        gbc.gridy = 1;
        resultPanel.add(scoreLabel, gbc);
        
        double percentage = (score * 100.0) / questions.size();
        JLabel percentLabel = new JLabel(String.format("Percentage: %.2f%%", percentage));
        percentLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridy = 2;
        resultPanel.add(percentLabel, gbc);
        
        String grade;
        if (percentage >= 90) grade = "A+";
        else if (percentage >= 80) grade = "A";
        else if (percentage >= 70) grade = "B";
        else if (percentage >= 60) grade = "C";
        else if (percentage >= 50) grade = "D";
        else grade = "F";
        
        JLabel gradeLabel = new JLabel("Grade: " + grade);
        gradeLabel.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridy = 3;
        resultPanel.add(gradeLabel, gbc);
        
        JLabel statusLabel = new JLabel(percentage >= 60 ? "PASSED" : "FAILED");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 26));
        statusLabel.setForeground(percentage >= 60 ? 
            new Color(46, 204, 113) : new Color(231, 76, 60));
        gbc.gridy = 4;
        resultPanel.add(statusLabel, gbc);
        
        resultPanel.revalidate();
        resultPanel.repaint();
    }
    
    private void resetExam() {
        currentQuestion = 0;
        score = 0;
        timeRemaining = examDuration;
        for (int i = 0; i < userAnswers.length; i++) {
            userAnswers[i] = -1;
        }
        if (examTimer != null) {
            examTimer.stop();
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OnlineExaminationSystem());
    }
}
