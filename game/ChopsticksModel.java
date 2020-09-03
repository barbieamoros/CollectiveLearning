package game;
//*************************************************************************
//Artificial Intelligence: Lab #1 - Collective Learning
//Students: Barbara Amoros & Maria Amoros
//Instructor: Dr. Antonio Sanchez
//Model
//*************************************************************************
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;
import javax.swing.*;
import objects.*;


public class ChopsticksModel {
    ChopsticksController c;
    Move[]  moves= new Move[25];
    JCheckBox fromPos = null;   
    String curPlayer1State;
    String curPlayer2State;
    String fileName = "description";
    String curState;
    String des;
    String fing1State = "";
    String fing2State = "";
    String fing3State = "";
    String fing4State = "";   
    boolean learn;   
    int change; 
    ArrayList<String> records = new ArrayList<String>();
    ArrayList<String> firstFour = new ArrayList<String>();
    ArrayList<String> plays2 = new ArrayList<String>();
    ArrayList<String> plays1 = new ArrayList<String>();
    ArrayList<String> statesChanged = new ArrayList<String>();
    ArrayList<String> toPrescription = new ArrayList<String>(); 
    Hashtable<String, State> stm  = new Hashtable<String,State>(); 
    Hashtable<String, String> state1  = new Hashtable<String,String>(); 
    
    public ChopsticksModel(){   
    }
    
    public static void main(String args[]){ 
        new ChopsticksController();
        }   

//*************************************************************************************************
    
    public ChopsticksModel(ChopsticksController fromControl){   
          c = fromControl;
          openTheStmFile("data/prescriptive.txt");
          newStringHash();        
    }
    
    public void becomesEvenMorePrescriptive() {
        String first = "";
        String second = "";
        int counter = 0;
        
        for(int i =0; i<statesChanged.size(); i++) {
            String t4 = statesChanged.get(i);
            String t4Sub = t4.substring(0,4);
                
            if(firstFour.contains(t4Sub)) {
                int index1 = firstFour.indexOf(t4Sub);
                System.out.println("The index is " + index1);
                records.add(index1, statesChanged.get(i));
                counter++;
            }
    
        }
        System.out.println("There were " + counter + " changes");
        System.out.println("There should have been: " + statesChanged.size());
        
                try {
                    FileWriter outFile = new FileWriter("data/prescriptive.txt");
                    BufferedWriter outStream = new BufferedWriter(outFile);
                    for (int k = 0; k < records.size(); k++)
                    outStream.write(records.get(k)+"\n");   
                    outStream.close();
                    System.out.println("Data saved.");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
    }
//**************************************************************************************************    
    public void newGame() { 
        for (int i=0; i<moves.length ; i++){
          moves[i] = new Move();
          moves[i].from = "";
          moves[i].to = "";
          moves[i].prob = 0.0;
        }   
        change = 0;
        learning();
    }
//**************************************************************************************************
    public ArrayList<String> openTheStmFile(String filename){
      try{
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        String sub;
        while ((line = reader.readLine()) != null){
            sub = line.substring(0,4);
            firstFour.add(sub);
          records.add(line);
          if(line.length()>10) {
              String firstFour = line.substring(0,4); 
              String rest = line.substring(9);
              StringTokenizer tok = new StringTokenizer(rest, "-*");
              int i =0;
              State newState = new State();

              while(tok.hasMoreTokens()) {
          
                  String state1 = tok.nextToken();
                  String probab = tok.nextToken();         
                  newState.state[i] = state1;
                  newState.prob[i] = Double.parseDouble(probab);      
                  i++;  
              }
             newState.nStates = i;
             stm.put(firstFour, newState); 
          }         
        }
        reader.close();
        System.out.println("Prescriptive STM opened and read");
        return records;
      }
      catch (Exception e){
        System.err.format("Exception occurred trying to read:" + filename);
        e.printStackTrace();
        return null;
      }
    }
//***************************************************************************************************
    public void newStringHash(){
          for(int i =0; i<records.size(); i++) {
              String temp = records.get(i);
              String stateFour = temp.substring(0,4);
              String won = temp.substring(5,8);
              String addThis = "STM possibles" ;
        
              if(won.contentEquals("ONE")) {
                  addThis = "Player 1 Wins";
                  state1.put(stateFour,addThis);
              }
              else if(won.contentEquals("TWO")) {
                  addThis = "Player 2 Wins";
                  state1.put(stateFour,addThis);
              }  
              else {
                  state1.put(stateFour,"STM possibles");
              }           
          }   
    }
//****************************************************************************************************
     public void detStates() {
         String pl1 = detPlayer1State();
         String pl2 = detPlayer2State();
         curState = pl1 + pl2;
         System.out.println("The Current State is: " + curState);
         
     }
//***************************************************************************************************
    public String selectCumulative() {
        if(c.turn2 && (!c.noHand1 || !c.noHand2) && (!c.noHand3 || !c.noHand4) ) {
        //TWO//
         int pos=-1;
         String nextSt = "";
         double random = Math.random();
         double sum=0;
        
         System.out.println(stm.get(curState).state.length);
         for (int i=0; i<stm.get(curState).state.length; i++){ 
             sum = sum + stm.get(curState).prob[i];
             if (sum >= random ){
                 nextSt = stm.get(curState).state[i]; pos = i;
                 break;
                 }   
             }
          moves[change].from = curState;
          moves[change].to = nextSt;
          moves[change].prob = stm.get(curState).prob[pos];
          System.out.println("Pos States: " + moves[change].prob);
          moves[change].posStates = stm.get(curState).nStates ;
          System.out.println(random + 
                  " from " + moves[change].from  + " next State is " + moves[change].to  + " with p = " + moves[change].prob
                ); 
          change++;
          System.out.println("NextST is: " + nextSt);
         return nextSt;
        }
        return curState;
    }
//***************************************************************************************************   
    public boolean findState(String nextSt) {
        if(c.turn2) {
        System.out.println("state "+ nextSt);
        des = state1.get(nextSt);
        System.out.println("DES: "+ des);
        boolean cont = false;
        if(des.equals("STM possibles")){
            cont  =  true; 
        }
        else learning();
        return cont;
     }
        else learning();
        return true;
    }
//***************************************************************************************************
    
public void learning(){ 

     boolean won = false;
     double pun = 0.2;
     double reward = 0.6;
   
     String winner = state1.get(curState);
     if(winner != null) {
     System.out.println("Winner is " + winner);
     if(winner.contains("Player 1 Wins") && plays2.size() > 2){
         System.out.println("WON WITH PLAYER 1");
         System.out.println("It won with the state: " + curState);
         System.out.println("Length of arraylist: " + plays2.size());
         System.out.println("Should be equal to: " + c.numOfPlays);
         
        for(int i = 0; i < plays1.size() ; i++ ){   
           System.out.println("The states to be punished are: "+ plays2.get(i));
           System.out.println("Which is related to: " + plays1.get(i));
           System.out.println("The number of states in this are: " + stm.get(plays2.get(i)).nStates);
           int sizeOf = stm.get(plays2.get(i)).nStates;
           
           String res = "";
           
           for (int j = 0; j < sizeOf; j++) {   
               double addToOthers = pun/(double)sizeOf;
               if(stm.get(plays2.get(i)).state[j].contentEquals(plays1.get(i))) {     
                   stm.get(plays2.get(i)).prob[j] = stm.get(plays2.get(i)).prob[j]-pun;        
               }       
               stm.get(plays2.get(i)).prob[j] = stm.get(plays2.get(i)).prob[j]+addToOthers;
               res = res + stm.get(plays2.get(i)).state[j]+ "-"+ stm.get(plays2.get(i)).prob[j]+"*";
           }
            statesChanged.add(plays2.get(i)+"*STM*" + res);
            System.out.println("NEW STATE: " + plays2.get(i)+"*STM*" + res);
        }//Outer Loop
        
        becomesEvenMorePrescriptive();
    } 
     if(winner.contains("Player 2 Wins") && plays2.size() > 2) {
         System.out.println("WON WITH PLAYER 2 ");
         System.out.println("It won with the state: " + curState);
         System.out.println("Length of arraylist: " + plays2.size());
         System.out.println("Should be equal to: " + c.numOfPlays);
         
        for(int i = 0; i < plays1.size() ; i++ ){   
           System.out.println("The states to be punished are: "+ plays2.get(i));
           System.out.println("Which is related to: " + plays1.get(i));
           System.out.println("The number of states in this are: " + stm.get(plays2.get(i)).nStates);
           int sizeOf = stm.get(plays2.get(i)).nStates;
           
        
           String res = "";
           
           for (int j = 0; j < sizeOf; j++) {   
               double subFromOthers = reward/(double)sizeOf;
               if(stm.get(plays2.get(i)).state[j].contentEquals(plays1.get(i))) {     
                   stm.get(plays2.get(i)).prob[j] = stm.get(plays2.get(i)).prob[j]+reward;         
               }
               
               stm.get(plays2.get(i)).prob[j] = stm.get(plays2.get(i)).prob[j]-subFromOthers;
               res = res + stm.get(plays2.get(i)).state[j]+ "-"+ stm.get(plays2.get(i)).prob[j]+"*";
           }
            statesChanged.add(plays2.get(i)+"*STM*" + res);
            System.out.println("NEW STATE: " + plays2.get(i)+"*STM*" + res);
        }//Outer Loop
        
        becomesEvenMorePrescriptive();
     }
   }
 }
//***************************************************************************************************
public void searchSTM() {
    //ONE//
     learn = false;
     
     if(state1.get(curState) != null) {
          if(state1.get(curState).equals("Player 1 Won") || state1.get(curState).equals("Player 2 Won")) {
          System.out.println("We have a winner: " + state1.get(curState));
          learn = true;
      }
      else { 
         
      String nextR = selectCumulative();

      char fing1N = nextR.charAt(0);
      char fing2N = nextR.charAt(1);
      char change = '0';
      
      if(fing1N != curState.charAt(0)) change = fing1N;
      if(fing2N != curState.charAt(1)) change = fing2N;
      
      c.playerTwoMovesA(change);
      boolean cont = findState(nextR);
     
      }
     }    
}
//***************************************************************************************************
public void saveFileSTM(String file, Hashtable<String, State> stm ) {
     System.out.println("Save file STM");
     try {     
         FileOutputStream f = new FileOutputStream(new File("data/.stm"));
         ObjectOutputStream o = new ObjectOutputStream(f);
         o.writeObject(stm);
         o.close();
         f.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }    
}   
//***************************************************************************************************
    public String detPlayer1State() {
           
        curPlayer1State = "";
        if(c.play1Fing1 == 1) fing1State = "a";
        if(c.play1Fing1 == 2) fing1State = "c";
        if(c.play1Fing1 == 3) fing1State = "e";
        if(c.play1Fing1 == 4) fing1State = "g";
        if(c.play1Fing1 >= 5) fing1State = "i";
        
        if(c.play1Fing2 == 1) fing2State = "b";
        if(c.play1Fing2 == 2) fing2State = "d";
        if(c.play1Fing2 == 3) fing2State = "f";
        if(c.play1Fing2 == 4) fing2State = "h";
        if(c.play1Fing2 >= 5) fing2State = "j";
        
        curPlayer1State = fing1State + fing2State;
        return curPlayer1State;
                
    }
//******************************************************************************************************
    
        public String detPlayer2State() {

        curPlayer2State = "";   
        if(c.play2Fing3 == 1) fing3State = "z";
        if(c.play2Fing3 == 2) fing3State = "x";
        if(c.play2Fing3 == 3) fing3State = "v";
        if(c.play2Fing3 == 4) fing3State = "t";
        if(c.play2Fing3 >= 5) fing3State = "r";
        
        if(c.play2Fing4 == 1) fing4State = "y";
        if(c.play2Fing4 == 2) fing4State = "w";
        if(c.play2Fing4 == 3) fing4State = "u";
        if(c.play2Fing4 == 4) fing4State = "s";
        if(c.play2Fing4 >= 5) fing4State = "q";
        
        curPlayer2State = fing3State + fing4State;
        return curPlayer2State;
    }   
//*******************************************************************************************
}

