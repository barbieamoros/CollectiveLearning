package game;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.lang.*;

public class ChopsticksView extends JFrame {

JFrame theInstructions = new JFrame();
JFrame theG = new JFrame();
JFrame gameStats = new JFrame();

String playerTurn = "Player 1";

JPanel pan1 = new JPanel();
JPanel instructionsFrame = new JPanel();
JPanel southArea = new JPanel();
JPanel instructions = new JPanel();
JPanel play1 = new JPanel();
JPanel play2 = new JPanel();
JPanel todo = new JPanel();
JPanel panStat = new JPanel();

ImageIcon symbol = new ImageIcon("images/1finger.png");
ImageIcon symbol1 = new ImageIcon("images/1fingerBack.png");
ImageIcon fing2Pic = new ImageIcon("images/2fingerU.png");
ImageIcon fing2PicD = new ImageIcon("images/2fingerD.png");
ImageIcon fing3PicU = new ImageIcon("images/3fingers.png");
ImageIcon fing3PicD = new ImageIcon("images/3fingersU.png");
ImageIcon fing4PicU = new ImageIcon("images/4fingers.png");
ImageIcon fing4PicD = new ImageIcon("images/4fingersBack.png");


JButton continueButton = new JButton("Let's Play!");
JButton finger1 = new JButton(symbol);
JButton finger2 = new JButton(symbol);
JButton finger3 = new JButton(symbol1);
JButton finger4 = new JButton(symbol1);
JButton openFile = new JButton("Open File");
JButton save = new JButton("Save");


JLabel title = new JLabel("The Chopsticks Game");
JLabel principles = new JLabel("Using the principles of Collective Learning in Artificial Intelligence", JLabel.CENTER);
JLabel developers = new JLabel("Developed by Barbara & Maria Amoros", JLabel.CENTER);
JLabel howTo = new JLabel("  How To Play:");
JLabel turn = new JLabel(playerTurn);

JCheckBox learning = new JCheckBox("Learning");
JCheckBox fin1 = new JCheckBox();
JCheckBox fin2 = new JCheckBox();
JCheckBox fin3 = new JCheckBox();
JCheckBox fin4 = new JCheckBox();

JComboBox learningComboB =  new JComboBox();

ImageIcon tcuImage = new ImageIcon("images/TCU.png");
JLabel tcuLabel = new JLabel (tcuImage);

Color myTCUpurple = Color.decode("#502E84");
Color violeta = Color.decode("#bbbddf");
Color cremita = Color.decode("#fffdd6");

Font fontTitle = new Font("Georgia", Font.BOLD, 30);
JTextArea howTo1 = new JTextArea("Each time you start a round of the game you both hold your hands out with one finger extended. Player 1 will start the game, and then will take turns to go back \n"
+ "and forth with player two. one player will use one hand to tap one of their opponent's hand. If you tap with one finger then your opponent will add your one finger + their extended \n"
+ "fingers and extend the sum of the two. For example, you tap your opponent's hand. You have one finger and they have two. They then add the fingers and on their tapped hand, they put \n"
+ "out three fingers. On the next turn, your opponent uses their hand of three fingers to tap your hand of one. You now have to hold out four fingers because your one finger plus their \n"
+ "three equals four fingers. Only the tapping hand has the power to change your opponent's hand. Take turns between players to tap each others' hands. The goal is to keep going and adding \n"
+ "fingers to your opponent's hand by tapping. When someone's hand reaches five fingers that are extended, that hand is considered dead and is no longer in play. Hide dead hands behind your \n"
+ "back. Continue playing until one player has lost both of their hands. The goal is to be the last one standing with at least one hand left still alive." 
); 
JScrollPane scroller = new JScrollPane(howTo1);


public ChopsticksView() {
	initialInstructions();
}


public void initialInstructions() {

	theInstructions.setVisible(true);
	
	title.setBackground(myTCUpurple);
	title.setFont(fontTitle);
	title.setForeground(Color.WHITE);
	title.setHorizontalAlignment(JLabel.CENTER);
	
	continueButton.setOpaque(true);
	continueButton.setBackground(myTCUpurple);
	
	learning.setForeground(Color.WHITE);
	learning.setFont(new Font("Georgia", Font.ITALIC, 14));
	
	learningComboB.addItem("SELECT Algedonic"); //0
	learningComboB.addItem("Reward/Punish");  //1
	learningComboB.addItem("Linear"); //2
	learningComboB.addItem("Quadratic"); //3
	learningComboB.addItem("Reward/Inaction");  //4
	learningComboB.addItem("Linear RI");  //5
	learningComboB.addItem("Quadratic RI");  //6
	learningComboB.setSelectedIndex(3); 
	
	southArea.setLayout(new GridLayout(1,3));
	southArea.setBackground(myTCUpurple);
	southArea.add(learningComboB);
	//southArea.add(openFile);
	southArea.add(save);
	southArea.add(learning);
	southArea.add(continueButton);
	
	instructionsFrame.add(tcuLabel, BorderLayout.NORTH);
	instructionsFrame.setBackground(myTCUpurple);
	
	principles.setForeground(violeta);
	principles.setFont(new Font("Georgia", Font.ITALIC, 14));
	
	developers.setForeground(violeta);
	developers.setFont(new Font("Georgia", Font.ITALIC, 14));
	
	howTo1.setFont(new Font("Georgia", Font.PLAIN, 12));
	howTo1.setForeground(Color.WHITE);
	howTo1.setBackground(myTCUpurple);
	
	instructions.setLayout(new GridLayout(5,1));
	instructions.add(title, BorderLayout.NORTH);
	instructions.add(principles);
	instructions.add(developers);
	instructions.add(howTo1);
	theInstructions.setBounds(200,200, 650, 450);
	instructions.setBackground(myTCUpurple);
	
	theInstructions.setVisible(true);
	theInstructions.setBounds(200,200, 550, 350);
	theInstructions.setLayout(new BorderLayout());
	theInstructions.setTitle("Welcome to Chopsticks!");
	theInstructions.add(instructionsFrame, BorderLayout.NORTH);
	theInstructions.add(instructions, BorderLayout.CENTER);
	theInstructions.add(southArea, BorderLayout.SOUTH);	
}

public void theGame() {
	players();
	palitos();
	pan1.setBackground(myTCUpurple);
	theG.add(pan1);
	theG.setVisible(true);
	theG.setTitle("Let's Play!");
	theG.setBounds(200,200, 400, 350);	
}


public void players() {
	JLabel player1 = new JLabel("Player 1", JLabel.CENTER);
	JLabel player2 = new JLabel("Player 2", JLabel.CENTER);
	JPanel pl1 = new JPanel();
	JPanel pl2 = new JPanel();
	pl1.setBackground(Color.WHITE);
	pl2.setBackground(myTCUpurple);
	player1.setFont(new Font("Georgia", Font.ITALIC, 25));
	player2.setFont(new Font("Georgia", Font.ITALIC, 25));
	player1.setForeground(myTCUpurple);
	player2.setForeground(Color.WHITE);
	pl1.add(player1);
	pl2.add(player2);
	pan1.setLayout(new BorderLayout());
	pan1.add(pl2, BorderLayout.NORTH);
	pan1.add(pl1, BorderLayout.SOUTH);
}

public void gameStats() {
	panStat.setBackground(myTCUpurple);
	JLabel whose = new JLabel("Turn: ");
	whose.setFont(new Font("Georgia", Font.BOLD, 20));
	whose.setForeground(violeta);
	turn.setFont(new Font("Georgia", Font.PLAIN, 15));
	turn.setForeground(Color.WHITE);
	
	panStat.add(whose);
	gameStats.setVisible(true);
	gameStats.setTitle("Let's Play!");
	gameStats.setBounds(600,200, 300, 350);	
	gameStats.add(panStat);
	
}

public void palitos() {
	finger1.setBackground(myTCUpurple);
	finger1.setOpaque(true);
	finger1.setBorderPainted(false);
	finger2.setBackground(myTCUpurple);
	finger2.setOpaque(true);
	finger2.setBorderPainted(false);	
	finger3.setBackground(Color.WHITE);
	finger3.setOpaque(true);
	finger3.setBorderPainted(false);
	finger4.setBackground(Color.WHITE);
	finger4.setOpaque(true);
	finger4.setBorderPainted(false);
	
	fin1.setOpaque(true);
	fin1.setBorderPainted(false);
	fin2.setOpaque(true);
	fin2.setBorderPainted(false);
	fin3.setOpaque(true);
	fin3.setBorderPainted(false);
	fin4.setOpaque(true);
	fin4.setBorderPainted(false);

	
	todo.setLayout(new GridLayout(2,1));
	play2.setLayout(new GridLayout(1,2));
	play1.setLayout(new GridLayout(1,2));
	play1.add(finger1);
	play1.add(fin1);
	play1.add(finger2);
	play1.add(fin2);
	play2.add(finger3);
	play2.add(finger4);
	play1.setBackground(myTCUpurple);
	play2.setBackground(Color.WHITE);
	todo.add(play2);
	todo.add(play1);
	pan1.add(todo, BorderLayout.CENTER);
}


public static void main(String[] args) { 
	new ChopsticksView();

	} 
}