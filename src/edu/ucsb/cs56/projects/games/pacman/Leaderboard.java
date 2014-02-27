package edu.ucsb.cs56.projects.games.pacman;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

import java.util.Date;

/**
* Stores instances of GamePlayed objects and sorts them by highest score--keeps track of Game History
* Loads and Stores the GamePlayed Objects in a file
* @author Kateryna Fomenko
* @author Deanna Hartsook
* @version CS56, Winter 2014
 */

public class Leaderboard extends ArrayList<GamePlayed> implements Serializable {
	private static String filename="pacmanLeaderboard.ser";
	
	/**Setter for filename 
	 * @param fileName - represents the new filename of the .ser file
	 */
	public void setFileName(String fileName){
		this.filename = fileName;
	}
	
	/**Add function that adds a GamePlayed object to the Leaderboard in the proper spot 
	* @param g - represents a GamePlayed object that is to be added to the leaderboard
	* @return boolean - true if added succesfully, false otherwise
	*/
	@Override
	public boolean add(GamePlayed g){
		 int i=0;
         //~ if (this.isEmpty()) {
             //~ this.add(0,g);
             //~ return true;
         //~ }
		 for(GamePlayed game: this){
			 if(game.getScore() <= g.getScore()){
				this.add(i, g);
                 return true;
			 }
			i++;
		 }
		 if(i!=0){
			add(i-1, g);
		}else{
			add(0,g);
		}
		return true;
	 }

    /**Add function that creates a GamePlayed object and adds it to the Leaderboard in the proper spot
     * @param name - represents the name of the player
     * @param d - represents the date of the game
     * @param score - represents the player's score
     * @return boolean - true if added succesfully, false otherwise
     */
	public boolean add(String name, Date d, int score){
		GamePlayed g = new GamePlayed(name, d, score);
		return this.add(g);
		
	}
    
    /** Saves the Leaderboard ArrayList in .ser file
    */
    public void save(){
        try {
            FileOutputStream fileOut = new FileOutputStream(this.filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
        }catch(IOException io) {
            //~ io.printStackTrace();
            System.out.println("COULD NOT SAVE LEADERBOARD");
        }
    }
    
    /** Loads the Leaderboard ArrayList from the .ser file
     */
    public void load(){
        Leaderboard temp = null;
        try{
            FileInputStream fileIn = new FileInputStream(this.filename);
            ObjectInputStream in  = new ObjectInputStream(fileIn);
            temp = (Leaderboard) in.readObject();
            in.close();
            fileIn.close();
        }catch (Exception e){
            //~ e.printStackTrace();
            System.out.println("NO LEADERBOARD FOUND");
            return;
        }
        this.clear();
        this.addAll(temp);
    }
	
    /** returns a formatted string of the top three all time highest scores
    * @return formatted string of scores
    * e.g. 
    * "        Barbara   50 09/18/2013
    *             Nick   30 12/01/2013
    *            Katie   29 10/15/2013"
    */
	public String getTopThree(){
		int length = this.size();
		String result ="";
		if(length < 3){
			for(int i = 0; i < length; i++){
				result += this.get(i) + "<br>";
			}
		}else{
			for(int i=0; i< 3; i++){
				result += this.get(i) + "<br>";
			}
		}
		return result;
		
	}
	
    /** returns a formatted string of the top three scores for a player
     * @param name of the player
     * @return formatted string of scores
     * e.g. "Barbara 50 09/18/2013
     *       Barbara 30 12/1/2013
     *       Barbara 29 10/15/2013"
     */
	public String getPlayerTopThree(String playerName){
		int counter = 0;
		String result ="";
		for(GamePlayed game: this){
			if(game.getName().equals(playerName)){
				//the user played this game
				result += game + "<br>";
				counter ++;
				if(counter == 3){
					return result;
				}
			}
		}
		return result;
	}
	
	
    public static void main(String [] args){
		//~ if(args.length == 1){
			//~ this.filename= args[0];
		//~ }else if(args.length > 1){
			//~ System.out.println("Invalid Command Line Arguments--Please enter a single file name, or leave blank for default.");
		//~ }
    }
	
	
}
