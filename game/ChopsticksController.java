package game;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;

import javax.swing.*;


public class ChopsticksController extends ChopsticksView implements ActionListener, ItemListener {

	ChopsticksModel m = new ChopsticksModel(this);
	
	int play1Fing1 = 1;
	int play1Fing2 = 1;	
	int play2Fing3 = 1;
	int play2Fing4 = 1;
	int numOfPlays = 0;
	
	boolean turn1;
	boolean turn2;
	boolean noHand1 = false;
	boolean noHand2 = false;
	boolean noHand3 = false;
	boolean noHand4 = false;
	
	String winner = "";
	String curState;
	

	public ChopsticksModel model = new ChopsticksModel();
	
	public static void main(String args[])
	  { new ChopsticksController();}
	
	public ChopsticksController() {
	afterMove(); 
	continueButton.addActionListener(this);
	openFile.addActionListener(this);
	//	save.addActionListener(this);
	
	finger1.addActionListener(this);
	finger2.addActionListener(this);
	finger3.addActionListener(this);
	finger4.addActionListener(this);
	
	fin1.addItemListener(this);
	fin2.addItemListener(this);
	fin3.addItemListener(this);
	fin4.addItemListener(this);
	}

	public void actionPerformed(ActionEvent e) {
	
	//************************************//
	if (e.getSource() == continueButton) {
	System.out.println("Action Performed: Continue Button");
	theGame();	
	m.newGame();
	//gameStats();
	plays();
	enable();
	noWrongs();
	theInstructions.dispose();
	}
	if (e.getSource() == finger1) player2Moves(finger1);	
	if (e.getSource() == finger2) player2Moves(finger2);	
	if (e.getSource() == finger3) player1Moves(finger3);	
	if (e.getSource() == finger4) player1Moves(finger4);
	//	if (e.getSource() == save) 
	
} 
//**********************************************************************	
	
	public void plays() {
	if(numOfPlays % 2 == 0) {
	playerTurn = "Player 1";
	System.out.println("Turn: " + playerTurn);
	}
	if(numOfPlays % 2 != 0) {
	playerTurn = "Player 2";
	System.out.println("Turn: " + playerTurn);
	}
	numOfPlays++;
	} 
	
//**********************************************************************	
	
	public int math(int finger) {
	int result = 0;
	int toAddFing = whichAdding();
	int addWith = 0;
	int with = 0;
	
	if(finger == 1) with = play1Fing1;
	else if(finger == 2) with = play1Fing2;
	else if(finger == 3) with = play2Fing3;
	else if(finger == 4) with = play2Fing4;
	
	if(toAddFing == 1) addWith = play1Fing1;
	if(toAddFing == 2) addWith = play1Fing2;
	if(toAddFing == 3) addWith = play2Fing3;
	if(toAddFing == 4) addWith = play2Fing4;
	
	result = with + addWith;
	
	return result;	
	}
	
//**********************************************************************	
	
	public void unselect() {
	fin1.setSelected(false);
	fin2.setSelected(false);
	fin3.setSelected(false);
	fin4.setSelected(false);
	}
	
//**********************************************************************
	
	public void itemStateChanged(ItemEvent e) {
	noUnused();
	if (e.getSource() == fin1) {
	fin2.setSelected(false); 
	play1.remove(fin2);
	play1.revalidate();
	play1.repaint();
	finger1.setBackground(Color.YELLOW);
	fin1.setBackground(Color.YELLOW);
	}
	else if (e.getSource() == fin2) {
	fin1.setSelected(false); 
	play1.remove(fin1);
	play1.revalidate();
	play1.repaint();
	finger2.setBackground(Color.YELLOW);
	fin2.setBackground(Color.YELLOW);
	}
	else if (e.getSource() == fin3) {
	fin4.setSelected(false); 
	play2.remove(fin4);
	play2.revalidate();
	play2.repaint();
	finger3.setBackground(Color.RED);
	fin3.setBackground(Color.RED);
	}
	else if (e.getSource() == fin4) {
	fin3.setSelected(false);
	play2.remove(fin3);
	play2.revalidate();
	play2.repaint();
	finger4.setBackground(Color.RED);
	fin4.setBackground(Color.RED);
	}	
	whichAdding();
	}
	
//**********************************************************************	
	
	public void enable() {
	if(playerTurn.contentEquals("Player 1")) {
	fin3.setEnabled(false);
	fin4.setEnabled(false);
	fin1.setEnabled(true);
	fin2.setEnabled(true);
	play2.remove(fin3);
	play2.remove(fin4);
	play2.revalidate();
	play2.repaint();
	}
	else if(playerTurn.contentEquals("Player 2")) {
	fin3.setEnabled(true);
	fin4.setEnabled(true);	
	fin1.setEnabled(false);
	fin2.setEnabled(false);
	play1.remove(fin1);
	play1.remove(fin2);
	play1.revalidate();
	play1.repaint();
	}	
	}
	
//**********************************************************************	
	
	public void noWrongs() {
	if(playerTurn.contentEquals("Player 1")) {
	turn1 = true;
	turn2 = false;
	}
	else if(playerTurn.contentEquals("Player 2")) {
	turn1 = false;
	turn2 = true;
	}
	}
	
//**********************************************************************
	
  public int whichAdding() {
	    int add = 0;	
	if(fin1.isSelected()) add = 1;
	else if(fin2.isSelected()) add = 2;
	else if(fin3.isSelected()) add = 3;
	else if(fin4.isSelected()) add = 4;
	return add;
  }
  
//**********************************************************************  
  public void adjustBackground() {
	    finger1.setBackground(myTCUpurple);
	    fin1.setBackground(myTCUpurple);
	    finger2.setBackground(myTCUpurple);
	    fin2.setBackground(myTCUpurple);
	    finger3.setBackground(Color.white);
	    fin3.setBackground(Color.white);
	    finger4.setBackground(Color.white);
	    fin4.setBackground(Color.white);
}
//**********************************************************************  
  public void noUnused() {
	if(playerTurn.contentEquals("Player 1")) {
	play2.remove(fin3);
	play2.remove(fin4);
	play2.revalidate();
	play2.repaint();
	}
	else if(playerTurn.contentEquals("Player 2")) {
	play1.remove(fin1);
	play1.remove(fin2);
	play1.revalidate();
	play1.repaint();
	}
}
//********************************************************************** 
  public void addMissing() {
	if(playerTurn.contentEquals("Player 1")) {
	if(noHand1 == false && noHand2 ==false) {
	play1.remove(finger1);
	play1.remove(finger2);
	play1.add(finger1);
	play1.add(fin1);
	play1.add(finger2);
	play1.add(fin2);
	play1.revalidate();
	play1.repaint();
	}
	else if(noHand1 == true && noHand2 == false) {
	play1.remove(finger1);
	play1.remove(finger2);
	play1.add(finger2);
	play1.add(fin2);
	play1.revalidate();
	play1.repaint();	
	}
	else if(noHand1 == false && noHand2 == true) {
	play1.remove(finger1);
	play1.remove(finger2);
	play1.add(finger1);
	play1.add(fin1);
	play1.revalidate();
	play1.repaint();	
	}
	else if(noHand1 == true && noHand2 == true) {
	play1.remove(finger1);
	play1.remove(finger2);
	play1.revalidate();
	play1.repaint();	
	}
	}
	else if(playerTurn.contentEquals("Player 2")) {
	if(noHand3 == false && noHand4 == false) {
	play2.remove(finger3);
	play2.remove(finger4);
	play2.add(finger3);
	play2.add(fin3);
	play2.add(finger4);
	play2.add(fin4);
	play2.revalidate();
	play2.repaint();
	}
	else if(noHand3 == true && noHand4 == false) {
	play2.remove(finger3);
	play2.remove(finger4);
	play2.add(finger4);
	play2.add(fin4);
	play2.revalidate();
	play2.repaint();
	}
	else if(noHand3 == false && noHand4 == true) {
	play2.remove(finger3);
	play2.remove(finger4);
	play2.add(finger3);
	play2.add(fin3);
	play2.revalidate();
	play2.repaint();
	}
	else if(noHand3 == true && noHand4 == true) {
	play2.add(finger3);
	play2.add(fin3);
	play2.revalidate();
	play2.repaint();
	}
	}
}
//********************************************************************** 
  public void whoWon() {
	    if(noHand1 == true && noHand2 == true) {
	    	winner = "The Winner is Player 2";
	    	play1.remove(finger1);
	play1.remove(finger2);
	play1.remove(fin1);
	play1.remove(fin2);
	play1.revalidate();
	play1.repaint();
	    	JLabel win = new JLabel(winner, JLabel.CENTER);
	    	win.setFont(new Font("Georgia", Font.ITALIC, 25));
	    	win.setForeground(Color.YELLOW);
	    	play1.add(win);
	    	
	    }
	    else if(noHand3 == true && noHand4 == true) {
	    	play2.remove(finger3);
	play2.remove(finger4);
	play2.remove(fin3);
	play2.remove(fin4);
	    	winner = "The Winner is Player 1";
	    	play2.revalidate();
	play2.repaint();
	    	JLabel win = new JLabel(winner, JLabel.CENTER);
	    	win.setFont(new Font("Georgia", Font.ITALIC, 25));
	    	win.setForeground(Color.RED);
	    	play2.add(win);
	    }
}

  //**********************************************************************    
  public void player1Moves(JButton source) {

	  //****** FINGER 3 ********************
	  if(source == finger3) {
	  plays();
	  enable();
	  noWrongs();   
	  play2Fing3 = math(3);
	  
	  switch(play2Fing3-1) {
	  	case 1: finger3.setIcon(fing2PicD);
	break;
	case 2: finger3.setIcon(fing3PicD);
	break;
	case 3: finger3.setIcon(fing4PicD);
	break;
	case 4: 	
	noHand3 = true;
	addMissing();
	break;
	default: 
	    noHand3 = true;
	    addMissing();	
	break;
	}
	}
  //*********FINGER 4 ******************
	  if(source == finger4) {
	  plays();
	  enable();
	  noWrongs();  
	  
	  play2Fing4 = math(4);
	  
	  switch(play2Fing4-1){
	case 1: finger4.setIcon(fing2PicD);
	break;	
	case 2: finger4.setIcon(fing3PicD);
	break;
	case 3: finger4.setIcon(fing4PicD);
	break;
	case 4: 
	    noHand4 = true;
	addMissing();
	break;
	default: 
	    noHand4 = true;
	    addMissing();
	    break;
	}
	 }
	  afterMove();
	  curState = m.detPlayer1State() + m.detPlayer2State();
	  System.out.println("HERE IS THE STATE: CurState " + curState);
	  doMagic();
  }
 //**********************************************************************
  public void player2Moves(JButton source) {
	  if(source == finger1) {
	   plays();
	   enable();
	   noWrongs();
	 
	   switch(play1Fing1-1) {
	case 1: finger1.setIcon(fing2Pic); 
	break;
	case 2: finger1.setIcon(fing3PicU);
	break;
	case 3: finger1.setIcon(fing4PicU);
	break;
	case 4: 
	noHand1 = true;
	addMissing();
	break;
	default: 
	noHand1 = true;
	addMissing();
	break;
	}   
	 }
	  if(source == finger2) {
	plays();
	enable();
	noWrongs();	

	switch(play1Fing2-1){
	case 1: finger2.setIcon(fing2Pic); 
	break;
	case 2: finger2.setIcon(fing3PicU);
	break;
	case 3: finger2.setIcon(fing4PicU);
	break;
	case 4: 
	                noHand2 = true;
	                addMissing();
	    break;	
	default: 	
	noHand2 = true;
	addMissing();
	break;
	 }
	}
	  afterMove();
	  curState = m.detPlayer1State() + m.detPlayer2State();
  }
//**********************************************************************
public void doMagic() {
	String nextSt = m.searchSTM();
    if (nextSt.contains("GAME OVER")) {
    	 m.saveFileSTM("data/prescriptive.txt", m.stm);
   	     m.learning();
         m.newGame();
         }
    else {
    	System.out.println("next state  "+ nextSt + " description " + m.getDes());	
    }
}
//********************************************************************** 
  public void playerTwoMovesA(char change) {
	if(change == 'a') {
	play1Fing1 = 1;
	player2Moves(finger1);
	}
	if(change == 'c') {
	play1Fing1 = 2;
	player2Moves(finger1);
	}
	if(change == 'e') {
	play1Fing1 = 3;
	player2Moves(finger1);
	}
	if(change == 'g') {
	play1Fing1 = 4;
	player2Moves(finger1);
	}
	if(change == 'i') {
	play1Fing1 = 5;
	player2Moves(finger1);
	}	
	if(change == 'b') {
	play1Fing2 = 1;
	player2Moves(finger2);
	}
	if(change == 'd') {
	play1Fing2 = 2;
	player2Moves(finger2);
	}
	if(change == 'f') {
	play1Fing2 = 3;
	player2Moves(finger2);
	}
	if(change == 'h') {
	play1Fing2 = 4;
	player2Moves(finger2);
	}
	if(change == 'j') {
	play1Fing2 = 5;
	player2Moves(finger2);	
	}	
	}
  //**********************************************************************
 public void afterMove() {
	 unselect();
	 adjustBackground();
	 addMissing();
	 whoWon();
	 m.detStates();
 }
//**********************************************************************
	public void windowClosing(WindowEvent e) { 
	dispose(); 
	System.exit(0); 
	} 
	public void windowOpened(WindowEvent e) {  } 
	public void windowIconified(WindowEvent e) {  } 
	public void windowClosed(WindowEvent e) {  } 
	public void windowDeiconified(WindowEvent e) {  } 
	public void windowActivated(WindowEvent e) {  } 
	public void windowDeactivated(WindowEvent e) {  } 
}