/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quiz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Grzes
 */
public class Quiz extends JFrame {

    
    //File with Q&A
    private BufferedReader fileWithQuestions;
    private BufferedReader fileWithAnswers;
    private BufferedReader correctAnswers;
    
    private final int sizeOfTabs = 8;
    private String[] questions = new String[sizeOfTabs];
    private String[] answers = new String[sizeOfTabs];
    private String[] correct= new String[3];
    List <Integer>indexs = new ArrayList<Integer>();
    
    //Panels
    private JPanel container = new JPanel();
    
    //Lables
    private JLabel background = new JLabel(new ImageIcon("photos/background.jpg"));
    private JLabel questionPrint;
    private JLabel score;
    private JLabel scoreOfFalse;
    
    
    
    //Buttons
    private JButton A;
    private JButton B;
    private JButton C;
    private JButton D;
    
    //Static
    private static int randomQuestion;
    private static int scoreTrue;
    private static int scoreFalse;
    
    public Quiz() throws IOException
    {
        BODY();
    }
    
    private void BODY() throws IOException
    {
        this.setBounds(1200,350,500,500);
//        this.setLayout(new BorderLayout());
        
        //open file with Q&A
        fileWithQuestions = new BufferedReader(new FileReader("questions/questions.txt"));
        fileWithAnswers = new BufferedReader(new FileReader("answers/answers.txt"));
        correctAnswers  = new BufferedReader(new FileReader("answers/correct.txt"));

        // Reading questions,answers and correct answers from files to tabs
        questions = fileReader(fileWithQuestions);
        answers = fileReader(fileWithAnswers);
        correct = fileReader(correctAnswers);
        
        //rand question
        randomQuestion = randomIndex();
        //add question to list (to get rid of the repetition of the question)
        indexs.add(randomQuestion);
        
        //Adding anserws on buttons first time
        String linia = answers[randomQuestion];
        String tab[] = new String[4];
        tab=linia.split("\\*");

        
        //Label with question
        questionPrint = new JLabel(questions[randomQuestion].substring(2,questions[randomQuestion].length() ));
            questionPrint.setFont(new Font("TimesRoman", Font.TYPE1_FONT, 60));
            questionPrint.setForeground(Color.white);
            questionPrint.setLocation(0,400);
            questionPrint.setSize(new Dimension(2300,100));
        
        //scores
        score = new JLabel("Poprawne odpowiedzi: "+ scoreTrue);
            score.setFont(new Font("TimesRoman", Font.TYPE1_FONT, 30));
            score.setForeground(Color.white);
            score.setLocation(1500,0);
            score.setSize(new Dimension(380,50));
                
        scoreOfFalse = new JLabel("Niepoprawne odpowiedzi: "+ scoreFalse);
            scoreOfFalse.setFont(new Font("TimesRoman", Font.TYPE1_FONT, 30));
            scoreOfFalse.setForeground(Color.white);
            scoreOfFalse.setLocation(1500,40);
            scoreOfFalse.setSize(new Dimension(400,50));
       
        
        
        
        //creating buttons
        A = new JButton(tab[0]);
            A.setLocation(300,900);
            A.setSize(new Dimension(250,90));
        B = new JButton(tab[1]);
            B.setLocation(700,900);
            B.setSize(new Dimension(250,90));
        C = new JButton(tab[2]);
            C.setLocation(1100,900);
            C.setSize(new Dimension(250,90));
        D = new JButton(tab[3]);
            D.setLocation(1500,900);
            D.setSize(new Dimension(250,90));
        
        A.setFont(new Font("TimesRoman", Font.TYPE1_FONT, 20));
        B.setFont(new Font("TimesRoman", Font.TYPE1_FONT, 20));
        C.setFont(new Font("TimesRoman", Font.TYPE1_FONT, 20));
        D.setFont(new Font("TimesRoman", Font.TYPE1_FONT, 20));   
       
        
        
        background.add(A);
        background.add(B);
        background.add(C);
        background.add(D);
        
        //answers listener
        A.addActionListener(new buttonListener("A"));
        B.addActionListener(new buttonListener("B"));
        C.addActionListener(new buttonListener("C"));
        D.addActionListener(new buttonListener("D"));
        
        
        background.add(score);
        background.add(scoreOfFalse);
        background.add(questionPrint);
        background.setLayout(null);
        this.add(background);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    
    //class with answers listener
    private class buttonListener implements ActionListener
    {

        private String answ;
        
        
        public buttonListener(String answ)
        {
            this.answ = answ;
        }
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            if(answ.equals(correct[randomQuestion]))
                {
                    JOptionPane.showMessageDialog(null, "Correct!");
                    scoreTrueChanger();          
                    anotherQuestion();
                    questionPrint.setText(questions[randomQuestion].substring(2,questions[randomQuestion].length()));
                    buttonsAnswers();
                    
                    }
                
                else
                {
                    JOptionPane.showMessageDialog(null, "In correct, try again!");
                    scoreFalseChanger();
                }
            
        }
        
    }
    /////////////////////////////////////////////////////////////MAIN////////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) throws IOException {
        
        new Quiz().setVisible(true);
        
    }
    /////////////////////////////////////////////////////////////MAIN/////////////////////////////////////////////////////////////////////////////

//function for reading Q&A from files
private String[] fileReader(BufferedReader file ) throws IOException
    {
      String tab[] = new String[sizeOfTabs];
      String line = file.readLine();
        for (int i = 0; line != null;++i)
        {
            tab[i] = line;
            line = file.readLine();

        }   
        return tab;
        
    
    }

//function for random question
    private int randomIndex()
        {
        
            return new Random().nextInt(sizeOfTabs);
        }

//checking if the question is repeated
    private boolean isInListIndexs(int random)
    {
        for(int i: indexs)
        {
            if(i == random)
                return true;
            else
                continue;
        }
        return false;
        
    }

//drawing the next question
   private void anotherQuestion()
    {
        randomQuestion = randomIndex();
        
        if(questions.length == indexs.size())
        {
            JOptionPane.showMessageDialog(null, "No more questions, your result: "+ ((scoreTrue-scoreFalse)*100)/8 + "%.");
            
            System.exit(1);
        }
        
        if(isInListIndexs(randomQuestion))//calling a function that checks whether the question was repeated
        {
            anotherQuestion();
        }
        else
        {
            indexs.add(randomQuestion);
        }
              
    }
   
   //adding answers on buttons
   private void buttonsAnswers()
   {
       String linia = answers[randomQuestion];
       String tab[] = new String[4];
       tab=linia.split("\\*");
       System.out.println(tab[2]);
       A.setText(tab[0]);
       B.setText(tab[1]);
       C.setText(tab[2]);
       D.setText(tab[3]);
   }
   
   //True answers counter
   private void scoreTrueChanger()
   {
       scoreTrue++;
       score.setText("Poprawna odpowiedz: "+ scoreTrue);   
   }
   
   //False answers counter
    private void scoreFalseChanger()
   {
       scoreFalse++;
       scoreOfFalse.setText("Niepoprawna odpowiedz: "+ scoreFalse);
   }

}