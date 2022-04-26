package com.home;

import com.home.model.User;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    public static void main(String[] args) throws IOException {

        String username, password;
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the Username: ");
        username = sc.nextLine();
        AtomicBoolean ab;
        int loginAttempt = 1;
        do {
            System.out.print("Enter the Password: ");
            password =  sc.nextLine();
            ab = login(username,password,fileReader());
            loginAttempt ++;
            if (loginAttempt==4){
                System.out.println("3rt attempt also failed.");
                System.exit(1);
            }

        }
        while (!ab.get());
    }
    public static AtomicBoolean login(String username,String password,List<User> userList) throws IOException {
        AtomicBoolean isAuthenticated = new AtomicBoolean(false);
        userList.forEach(user1 -> {
                if(user1.getUsername().contains(username)){
                    if (user1.getPassword().equals(password)){
                        try {

                            if (user1.getRole().equals("Student")){
                                initQuiz(userList);
                            }else {
                                System.out.println("You are a Teacher");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        isAuthenticated.set(true);
                    }
                }

        });
        return isAuthenticated;
    }
    public static List<User> fileReader() throws IOException {
        ArrayList<User> userArrayList = new ArrayList<>();
        BufferedReader csvReader = new BufferedReader(new FileReader("src/com/home/resource/User.csv"));
        String line = "";
        while ((line = csvReader.readLine()) != null) {
            String[] tokens = line.split(",");
            if (Objects.equals(tokens[0], "name")) {
                continue;
            }
            userArrayList.add(new User(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], tokens[5]));

        }
        return userArrayList;
    }
    public static void initQuiz(List<User> userList) throws IOException {
        System.out.println("Quiz has Started...");

        ArrayList<String> questionList = new ArrayList<>();
        BufferedReader csvReader = new BufferedReader(new FileReader("src/com/home/resource/Questions.txt"));
        String line = "";
        while ((line = csvReader.readLine()) != null) {
            questionList.add(line);
        }
        Collections.shuffle(questionList);
        int randomSeriesLength = 10;
        List<String> randomSeries = questionList.subList(0, randomSeriesLength);
        int counter = 1;
        for (String ques:randomSeries) {
            System.out.println("Question no. "+counter+" "+ques);
            counter++;
        }
    }

}
