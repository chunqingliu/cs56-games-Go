//package edu.ucsb.cs56.W14.kwwham.cp1;

import java.util.ArrayList;
/**
 * A grid for playing Go, with numbers according to row, 1-19 being the first row
   and 20-38 being the next, etc.


   @author David Winkenwerder and Dustin Henderson
   @author Keith Waldron and Nick Abrahan
   @author Jeffrey Liu and Lauren Dumapias
   @author Chunqing Liu and Xingyuan Zhang
   @version CS56 7/21/16
   @see GoGridTest
*/
public class GoGrid implements GoGame
{

    private ArrayList<Character> grid = new ArrayList<Character>();
    private int numMoves=0;
    private char turn = 'B';
    private int scoreW = 0;//amount of white tiles
    private int scoreB = 0;//amount of black tiles
    private char winner = ' ';
    private boolean gameOver = false;
    private boolean surrender1 = false; boolean surrender2=false; boolean surrendering = false;
    //add spaces to the game state equal to the number of spaces on the board: 362.
    
    /**
     * Default constructor for objects of the class GoGrid
     */
    public GoGrid() {	
	grid.add('_');
	for(int i=1;i<362; i++)
	{
	    grid.add(' ');
	}

    }


  /*  public void setGameStatus(boolean boo){
        this.gameOver = boo;
    }*/
 /**
       the restart function for the game
          */
    public void restart(){
        for(int i=1;i<362; i++)
        {
            setGrid(i, ' ');
        }
        numMoves = 0;
        turn = 'B';
        scoreW = 0;
        scoreB = 0;
        winner = ' ';
        gameOver = false;
        surrender1 = false;
        surrender2 = false;
        surrendering = false;
    }

 /**
       the function to set the turn of player
          */
    public void setTurn(char turn){
	this.turn = turn;
    }
 /**
       the function to see the player is surrendering 
          */
    public void setSurrendering() {
        this.surrendering = true;
    }
	 /**
     it will call the grit.set function to set up the grid as shown on the right of the game
          */
    public void setGrid(int i, char c){
	grid.set(i,c);
    }
    //NOT DONE, reason a large enough change to user functionality that it could go along with the GUI to be created in CP3.
    /**
     Constructor for objects of class GoGrid that allows
     creation of games in middle of play. This is mostly for testing.

     There should be an equal number of B's and Ws
     (in which case it is the Ws turn) or else
     1 more W that there are Bs (in which case it is Bs turn),
     otherwise IllegalArgumentException("illegal game state") is thrown.

     @param gameState 362 character string with Ws and Bs for positions 1-362
     @return turn 'B' or 'W' for turn
    */
    //DONE
    public char GoGrid(ArrayList gameState) {
	this.grid = gameState;
	    return turn;
    }

    /**
       @param i is number of square (like telephone pad)
       @return 'B','W', or ' '--i.e. contents of square i
     */ 
    //DONE
 /**
       return the location of the stone player had played
          */
    public char charAt(int i)
    {
        return grid.get(i);
    }

    /**
       checkSurrounded checks to see if the piece selected is surrounded
       the other players pieces. it returns true if surrounded, 
       and false, if it is not.
    */
    
    public boolean checkSurrounded(int i, char color)
    {
        //char color = grid.get(i);
        ArrayList<Integer> visited = new ArrayList<Integer>();
	ArrayList<Integer> canVisit = new ArrayList<Integer>();
	canVisit.add(i);     // we start with a single element in canVisit. the helper function may add more elements to or remove elements from canVisit
	while(canVisit.size()>0)       // loop exits when we checked all the important stones in the area or when liberty is found
	    {
		if(!checkSurroundedHelper(canVisit.get(0),color,visited,canVisit))   // check first element of canVisit
		    return false;          // there is liberty, function returns
	    }
	for(int in: visited)
	    {
		grid.set(in,' ');          // no liberty, surrounded stones get removed.
	    }
	return true;
    }

    /**
       checkSurrounded2 is like checkSurrounded but it doesn't modify the grid. It is useful for checking whether a move is legal.
    */

    public boolean checkSurrounded2(int i)
    {
	char color = grid.get(i);              // the actionPerformed method in GoComponent.java already put the stone in without knowing the legality of the move. Depending on what checksurrounded2 returns, it would either keep the stone or throw it away.
        ArrayList<Integer> visited = new ArrayList<Integer>();
	ArrayList<Integer> canVisit = new ArrayList<Integer>();
	canVisit.add(i);
	while(canVisit.size()>0)
	    {
		if(!checkSurroundedHelper(canVisit.get(0),color,visited,canVisit))
		    return false;
	    }
	setGrid(i,' ');                  // undo illegal move
	return true;
    }
    
    //NOT DONE reason The two work fine as seperate, and we may come back and make them recursive for CP3.
 /**
       checkSurroundedHelper is the helper function used in checkSurrounded2 function to check the player's move
          */
    public boolean checkSurroundedHelper(int i,char color,ArrayList visited,ArrayList canVisit) //DONE
    {
	if(!visited.contains(i)) 
	    {
		if(grid.get(i)==' ')
		    return false;                // this means there is liberty
		if(grid.get(i)!=color)           // it's the color of the opponent's stone
		    {
			canVisit.remove(canVisit.indexOf(i));
		    return true;
		    }
		//System.out.println("b");
		visited.add(i);                // stones at risk of removal
		int v = canVisit.indexOf(i);
		if(v!=-1)         // -1 means not in arraylist
		    canVisit.remove(v);
		//find the four neighbors
		//if not in can visit and not over an edge, add it to canvisit
		if((i)%19!=0)
		    {
			if(!canVisit.contains(i+1))
			    if(!visited.contains(i+1))
				canVisit.add(i+1);
		    }
		if((i)%19!=1)
		    {
			if(!canVisit.contains(i-1))
			    if(!visited.contains(i-1))
				canVisit.add(i-1);
		    }
		if((i+19)<362)
		    {
			if(!canVisit.contains(i+19))
			    if(!visited.contains(i+19))
				canVisit.add(i+19);
		    }
	       	if((i-19)>0)
		    {
			if(!canVisit.contains(i-19))
			    if(!visited.contains(i-19))
				canVisit.add(i-19);
		    }
	    }
	/*int v;
	if((v=canVisit.indexOf(i))!=-1)
	canVisit.remove(v);*/
	return true;
	


    }

  /**
      return the score of the white player 
    */

   public int getWScore(){
	int wcount=0;
	for(char c : grid){
   	   if(c=='W')
		wcount++;
	   }
	return wcount;
}
 /**
      return of the score of the black player 
    */
   public int getBScore(){
	int bcount=0;
	for(char c : grid){
   	   if(c=='B')
		bcount++;
	   }
	return bcount;
}





    /** This private convenience method sets the gameOver and winner variables,
	and then returns the winner. It is simply a way to factor out
	common code from all the cases in getWinner */

    public char setWinner(char c) 
    {
	this.gameOver = true;
	this.winner = c;
	this.turn = ' ';
        return c;
    }
    
    /**
       @return 'W' or 'B' if there is a winner, ' ' if there is not
     */

    public char getWinner() 
    {
	char result;
	
	if(gameOver)
	    return winner;
	int blackCount=0;
	int whiteCount=0;
	if(surrendering){
	    for(char c : grid)
		{
		    if(c=='B')
			blackCount++;
		    if(c=='W')
			whiteCount++;
		}
	    if(blackCount>whiteCount)
		setWinner('B');
	    else if(whiteCount>blackCount)
		setWinner('W');
	    else
		setWinner(' ');
	}
	return winner;
    }

    /** is this square blank. Should be called before move() to see
	whether the move is legal.


	
	@param i number between 1 and 361 indicating square
	@return whether square is blank or not.
	@throws IllegalArguementException if i is not between 1 and 361 (inclusive)
    */
    public boolean isBlank(int i)
    {
	return(grid.get(i)==' ');
    }

    /**
       play the next move. if isBlank(i) is not true, will throw
       GoIllegalMoveException

       @param i number between 1 and 361 indicating square,
       organized like a telephone grid. If i is already occupied,
       a GoIllegalMoveException is thrown.
       @return winner 'B', 'W', or 'D' for draw, or ' ' for none yet.
    */
   /* public char move(int i) throws GoIllegalMoveException{
//@@@    public void move(int i) throws GoIllegalMoveException{
	/* indexes go from 0 to 361.
	   Second substring has 362 as last param because second param
	   to substring is first index NOT included in subsequence.
	*/
        /*if(!this.isBlank(i))
	    throw new GoIllegalMoveException("Square "+i+" occupied\n");
	//if(this.checkSurrounded(i))
	//  throw new GoIllegalMoveException("Cannot place piece here, it would be surrounded.\n");
	grid.set(i,turn);
	if(i+1<362)
    checkSurrounded(i+1);
	if(i-19>0)
	    checkSurrounded(i-19);
	if(i-1>0)
	    checkSurrounded(i-1);
	if(i+19<362)
	    checkSurrounded(i+19);
	turn = (turn=='W')?'B':'W'; //change turn
	numMoves++;

	return this.getWinner(); //may change gameOver and winner as side effect
	//	return;
    }*/

    public boolean makeMove(int i) throws GoIllegalMoveException{
           /* indexes go from 0 to 361.
              Second substring has 362 as last param because second param
              to substring is first index NOT included in subsequence.
           */
           if(!this.isBlank(i))
               throw new GoIllegalMoveException("Square "+i+" occupied\n");
           //if(this.checkSurrounded(i))
           //  throw new GoIllegalMoveException("Cannot place piece here, it would be surrounded.\n");
           char e = ' ', w = ' ', s = ' ', n = ' ';
           if (i%19!=0)
               e = grid.get(i+1);
           if (i%19!=1)
               w = grid.get(i-1);
           if (i+19<362)
               s = grid.get(i+19);
           if (i-19>0)
               n = grid.get(i-19);
           grid.set(i,turn);
           char turn2 = (turn=='W')?'B':'W';
           if(i+1<362)
               checkSurrounded(i+1,turn2);
           if(i-19>0)
               checkSurrounded(i-19, turn2);
           if(i-1>0)
               checkSurrounded(i-1, turn2);
           if(i+19<362)
               checkSurrounded(i+19, turn2);
           //turn = (turn=='W')?'B':'W'; //change turn
           //numMoves++;

   //	return this.getWinner(); //may change gameOver and winner as side effect
           if (i%19!=0) {
               if (e!=grid.get(i+1))
                   return true;
           }
           if (i%19!=1) {
               if (w!=grid.get(i-1))
                   return true;
           }
           if (i+19<362) {
               if (s!=grid.get(i+19))
                   return true;
           }
           if (i-19>0) {
               if (n!=grid.get(i-19))
                   return true;
           }
           return !checkSurrounded2(i);
   }
      /**
      changing the turn of each player
    */
       public void changeTurn() {
           turn = (turn=='W')?'B':'W'; //change turn
           numMoves++;
         }


    /** Return game state as a 361 character string.
	This is mostly for testing purposes.
	
	@param game state string
    */

    public String getGameState()
    {
	ArrayList retval = new ArrayList();
	retval = grid;
	retval.remove(0);
	return retval.toString();
    }


    /**
       return the Go grid with the contents
    */

/*    public String toString()
    {
	final String gridLine = "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-";
	String result = "";
	int i,j;
	result +=gridLine + "\n";
	for(i=0;i<19;i++)
	    {
		for(j=1;j<20;j++)
		    {
			if(i!=19)
			    result = result + grid.get(j+i*19)+ "|";
			else
			    result = result + grid.get(j+i*19);
                    }
                    result+= "\n";

                }
                result +="\n" + gridLine;

        }*/


public char getTurn()
{
    return turn;

}

    /**
      return whether the surrender 1 is surrending
    */
public boolean getSurrender1(){
    return surrender1;
}
    
    /**
      surrender 1 is surrending, set surrending condition
    */
public void setSurrender1(boolean boo){

    this.surrender1=boo;
    }

    /**
      return whether the surrender 2 is surrending
    */
public boolean getSurrender2(){
    return surrender2;
}
      /**
      surrender 2 is surrending, set surrending condition
    */
public void setSurrender2(boolean boo){

    this.surrender2=boo;
    }


}
