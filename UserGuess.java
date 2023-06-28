import java.util.ArrayList;
import java.util.Scanner;
class UserGuess{
  public static int UserInput(String[][][]userBoard,String[][][]compBoard,ArrayList<String> userShips,ArrayList<String> aiShips,ArrayList<Move> moveHistAI,int hit){
    boolean gotXCoord=false;
    boolean gotYCoord=false;
    boolean cordsSet=false;
    boolean done=false;
    int xCoord=0;
    int yCoord=0;
    Main.space();
    while(!cordsSet){
      gotXCoord=false;
      gotYCoord=false;
      if(hit==0){
        System.out.print("\tYou missed\n");
      }
      else if(hit==1){
        System.out.print("\tYou hit a ship\n");
      }
      else if(hit==2){
        System.out.print("\tYou sunk a ship\n");
      }
      hit=-1;
      while(!gotXCoord){
        selectScreen(userBoard,compBoard);
        System.out.print("\n(");
        xCoord= getCoord();
        if(xCoord!=-1){
          gotXCoord=true;
        }
      }
      
      Main.space();
      while(!gotYCoord){
        selectScreen(userBoard,compBoard);
        System.out.print("If you would like to start your point selection over enter \"CANCEL\" ");
        System.out.print("\n("+xCoord+",");
        yCoord= getCoord();
        
        if(yCoord>0){
          gotYCoord=true;
          if((compBoard[0][Main.gTOaY(yCoord)][Main.gTOaX(xCoord)]==null)||!compBoard[0][Main.gTOaY(yCoord)][Main.gTOaX(xCoord)].equals("ged")){
            cordsSet=true;
            done=true;
            gotYCoord=true;
          }
          else{
            yCoord=-3;
          }
        }
        Main.space();
        if(yCoord==-3){
          System.out.print("\tYou already guessed that point\n");
        }
        else if(yCoord<0){
          System.out.print("\tThat was not a valid coordinate.\n");
        }
        if(yCoord<=-3){
          done=false;
          gotYCoord=true;
        }
      }
      if(!done){
        gotXCoord=false;
        gotYCoord=false;
        cordsSet=false;
        xCoord=0;
        yCoord=0;
      }
    }
    return guessResults(compBoard,aiShips,moveHistAI,xCoord,yCoord);
  }
  /*
    final String ANSI_RED = "\u001B[31m";
    final String ANSI_GREEN = "\u001B[32m";
    final String ANSI_YELLOW = "\u001B[33m";
    final String ANSI_BLUE = "\u001B[34m";
    final String ANSI_PURPLE = "\u001B[35m";
    */
  public static int guessResults(String[][][]adjBoard,ArrayList<String> shipList,ArrayList<Move> moveHist,int x,int y){
    x=Main.gTOaX(x);
    y=Main.gTOaY(y);
    String boardPlace=adjBoard[0][y][x];
    int hit;
    adjBoard[0][y][x]="ged";
    if(boardPlace==null){
      hit=0;
      adjBoard[1][y][x]="O";
    }
    else{
      if(shipContain(adjBoard,boardPlace)){
        hit=1;//hit ship not sink
        adjBoard[1][y][x]="\u001B[31mX\u001B[0m";
      }
      else{
        String colorShip="";
        hit=2;
        int length=0;
        if (boardPlace.equals("a")){
          colorShip="\u001B[31m"; //red
        }
        else if(boardPlace.equals("b")){
          colorShip="\u001B[32m"; //green
        }
        else if(boardPlace.equals("c")){
          colorShip="\u001B[33m"; //yellow
        }
        else if(boardPlace.equals("s")){
          colorShip="\u001B[36m"; //cyan
        }
        else if(boardPlace.equals("d")){
          colorShip="\u001B[35m"; //purple
        }
        for(int r = 0;r<10;r++){
          for(int c = 0; c<10;c++){
            try{
              if(adjBoard[2][r][c].equals(boardPlace)){
                adjBoard[1][r][c] = (colorShip+"o\u001B[0m");
              }
            }
            catch(Exception e){}
          }
        }
        shipList.remove(shipList.indexOf(boardPlace));
      }
    }
    return hit;
  }
  
  
  public static int getCoord(){
    Scanner coordInput = new Scanner(System.in);
    String stringCoord = coordInput.nextLine();
    try{
      int newCoord = Integer.parseInt(stringCoord);
      if(newCoord>10||newCoord<1){
        throw new Exception();
      }
      return newCoord;
    }
    catch(Exception e){
      Main.space();
      System.out.print("\tThat was not a valid number.\n");
      stringCoord=stringCoord.toUpperCase();
      if(stringCoord.equals("CANCEL")){
        return -2;
      }
    }
    return -1;
  }
  
  public static boolean shipContain(String[][][]board,String ship){
    for(int r = 0;r<10;r++){
      for(int c = 0; c<10;c++){
        try{
          if(board[0][r][c].equals(ship)){
            return true;
          }
        }
        catch(Exception e){}
      }
    }
    return false;
  }
  
  public static void selectScreen(String[][][]userBoard,String[][][]compBoard){
    System.out.print("\nTime to make your move.....\n\nYour board\n");
    Main.printBoard(userBoard,0);
    System.out.print("\n\nComputers Board\n");
    Main.printBoard(compBoard,1);
    System.out.print("\n\n\tWhat position would you like to guess?\n");
  }

}
