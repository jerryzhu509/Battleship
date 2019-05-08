/*
Programmer Name: Jerry Zhu
Program name: BattleshipGame
Teacher name: Ms.Krasteva
Date: December 17, 2018
This program is my battleship game for the ISP.
All of the text, input, and output takes place on 1 screen.
The program will start at splashScreen where there'll be an animation. After a short delay, the user will be taken to the mainMenu where
the user will be given 4 choices.
If the user clicks 1, the program will ask for the inputs in askData(). Then, the program goes to newGame() where the user will play the game.
The result of the game is displayed in displayGameResult(). After displayGameResult() where the user is displayed the outcome of the game,
he/she is taken back to mainMenu().
If the user clicks 2, the program goes to displayHighScores() where the top 10 scores are displayed. The user clicks a key to go back to mainMenu().
If the user clicks 3, the program will go to displayInstructions() where the program is explained. The user clicks a key to go back to mainMenu().
If the user clicks 4, the program will exit to goodBye where there is a goodbye message. The window closes after a short delay.

Variable Dictionary
Name:                   Type:               Purpose:
c                       Console             Creates an instance of Console class.
userChoice              static char         Takes an user input in mainMenu and determines what the program will do.
difficulty              char                stores the difficulty of the game the user wants.
name                    String              Stores user's input of their name.
firstShipPosition       String              Stores the position of the first ship.
secondShipPosition      String Array        Stores the 2 coordinates of the second ship.
thirdShipPosition       String Array        Stores the 3 coordinates of the third ship.
fourthShipPosition      String Array        Stores the 4 coordinates of the fourth ship.
fifthShipPosition       String Array        Stores the 5 coordinates of the fifth ship.
userHits                int                 Stores the number of times the user has hit the computer's ships.
computerHits            int                 Stores the number of times the computer has hit the user's ships.
score                   int                 Stores the user's game score.

*****Please Download the Font Included in the Folder*****
*/


// The "BattleshipGame" class.
import java.awt.*;
import hsa.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*; //https://www.geeksforgeeks.org/arrays-aslist-method-in-java-with-examples/

public class BattleshipGame
{
    Console c;           // The output console
    static char userChoice;
    char difficulty;
    String firstShipPosition = "", name = "";
    String[] secondShipPosition = new String [2], thirdShipPosition = new String [3], fourthShipPosition = new String [4], fifthShipPosition = new String [5];
    int userHits = 0, computerHits = 0, score = 0;

    /*The purpose of this class constructor is to create an output console
    VariableDictionary
    Name:           Type:               Purpose:
    c               Console             Creates a console output screen
    */
    public BattleshipGame ()
    {
	c = new Console ("Battleship Game");
    }


    /////////////////////////////////////////////////////Public methods////////////////////////////////////////////////////////////

    /*
    The purpose of this method is to display a graphic.

    Variable Dictionary
    Name:               Type:               Purpose:
    b                   ShipAnimation       Creates an instance of ShipAnimation so I can run it here.

    The try block is to wait for the animation to finish before goign to mainMenu.
    */
    public void splashScreen ()
    {
	drawTitle ();
	//creates the thread
	ShipAnimation b = new ShipAnimation (c);

	//starts the thread
	b.start ();

	//delay
	try
	{
	    Thread.sleep (9000);
	}
	catch (Exception e)
	{
	}
    }


    /*
    The purpose of the mainMenu method is to take an input from the user and let the user decide what he/she wants the program to do by pressing
    1, 2, 3, or 4 .

    Variable Dictionary
    Name:           Type:               Purpose:
    calibri         Font                stores the font in which the text is output.
    boardPic        BufferedImage       stores the image I've put in the same folder.

    The try block is to output the image.

    The while loop and the embedded if statement is to ask for input and error trap it to make sure that the user is inputting 1, 2, 3, or 4 and
    that if the user clicks 4, the program exits. If the user clicks 1, 2, or 3, the loop will break and continue to either askData(),
    displayInstructions() or displayHighScores().
    */
    public void mainMenu ()
    {
	Font calibri = new Font ("calibri", Font.PLAIN, 30);
	BufferedImage boardPic;
	userHits = 0;
	computerHits = 0;
	score = 0;

	drawTitle ();

	//gets image
	try
	{
	    boardPic = ImageIO.read (new File ("download.jpg"));
	    c.drawImage (boardPic, 225, 350, null);
	}
	catch (IOException e)
	{
	    JOptionPane.showMessageDialog (null, "File not found!");
	}

	c.setFont (calibri);
	c.drawString ("1. New Game Against the Computer", 110, 130);
	c.drawString ("2. High Score", 245, 170);
	c.drawString ("3. Instructions", 240, 210);
	c.drawString ("4. Exit Game", 248, 250);
	c.drawString ("Please enter what you would like to do: ", 0, 315);

	//asking for input
	while (true)
	{
	    userChoice = c.getChar ();

	    //if statement for error trapping the input
	    if (userChoice != '1' && userChoice != '2' && userChoice != '3' && userChoice != '4')
		JOptionPane.showMessageDialog (null, "Please press either 1, 2, 3, or 4!");
	    else
		break;
	}
    }


    /*
    The displayHighScores method serves the purpose of displaying the top 10 scores along with their difficulty
    and name to the user.

    Variable Dictionary
    Name:           Type:               Purpose:
    numberOfLine    int                 runs through the highscores file to determine how many lines there are to create
					appropriately sized arrays to store data.
    temp1           int                 stores the lower number during the process of sorting data.
    line            String              reads the file to determine 'numberOfLines' until this is null.
    temp2           String              stores names being replaced during the process of sorting data.
    temp3           String              stores difficulty being replaced during the process of sorting data.
    calibri         Font                stores the font in which the text is output.
    userInput       char                pauses the program and gives the user the option to clear all of the scores.
    linesFile       String[]            stores all of the contents of highscores.txt
    names           String[]            stores all of the names of highscores.txt
    level           String[]            stores all of the levels of highscores.txt
    scoresArray     int[]               stores all of the scoresArray of highscores.txt

    The first combination of while + try + while is to read through the highscores file and determine how many lines are in the file
    to create the appropriate array sizes.

    The second try block is to read the file into arrays in the program. The embedded while loop is to read all of the data
    into a big array called linesFile. The embedded for loop is to transfer the data in linesFile into specific typed-data arrays
    called names, level, and scoresArray to store data to be output in this method.

    The third combination of for + for + if serves the purpose of sorting all of the data in greatest to least that's appropriate
    for high scores. The for loops run through the scoresArray and sorts accordingly if the next element is greater than the current
    element. The names and level arrays change to the same position as the scoresArray.

    The fourth for loop is to output the data.

    The if + try is to erase all of the contents if the user enters 1.
    */
    public void displayHighScores ()
    {
	int numberOfLine = 0, temp1;
	BufferedReader input;
	String line = "", temp2, temp3;
	Font calibri = new Font ("calibri", Font.PLAIN, 20);
	char userInput;

	drawTitle ();

	c.setFont (calibri);
	c.drawString ("#1", 50, 120);
	c.drawString ("#2", 50, 150);
	c.drawString ("#3", 50, 180);
	c.drawString ("#4", 50, 210);
	c.drawString ("#5", 50, 240);
	c.drawString ("#6", 50, 270);
	c.drawString ("#7", 50, 300);
	c.drawString ("#8", 50, 330);
	c.drawString ("#9", 50, 360);
	c.drawString ("#10", 50, 390);
	c.drawString ("Name", 170, 70);
	c.drawString ("Difficulty", 315, 70);
	c.drawString ("Score", 500, 70);
	c.drawString ("Press: ", 50, 435);
	c.drawString ("1 - Clear File", 50, 465);
	c.drawString ("Any Other key to Return to Main Menu", 50, 495);

	//counts number of scores
	while (true)
	{
	    try
	    {
		input = new BufferedReader (new FileReader ("highscores.txt"));

		//loop for as long as there is data in the file
		while (line != null)
		{
		    line = input.readLine (); //reads each line in the file
		    numberOfLine++;
		}
		input.close (); //closes the stream
		break;
	    }
	    catch (IOException e)
	    {
		JOptionPane.showMessageDialog (null, "Sorry, this file cannot be found. Please enter a different file name.");   //error message
	    }
	}

	String[] linesFile = new String [numberOfLine - 1]; //create array with size to match number of lines in file
	String[] names = new String [(numberOfLine - 1) / 3];
	String[] level = new String [(numberOfLine - 1) / 3];
	int[] scoresArray = new int [(numberOfLine - 1) / 3]; //creates an array to store the converted string array

	try
	{
	    //open the same file again
	    BufferedReader r = new BufferedReader (new FileReader ("highscores.txt")); // reset the buffer
	    int x = 0;

	    while (x < linesFile.length) //loop until end of file is reached
	    {
		linesFile [x] = r.readLine (); //feed each data line into an array
		x++;
	    }

	    //stores the names into the string array
	    for (int i = 0 ; i < linesFile.length ; i = i + 3)
	    {
		names [i / 3] = linesFile [i];
		level [i / 3] = linesFile [i + 1];
		scoresArray [i / 3] = Integer.parseInt (linesFile [i + 2]);
	    }
	    r.close (); //close data file
	}
	catch (IOException e)  //handle file related errors
	{
	    System.out.println (e); //error msg
	}

	//loops through array
	for (int x = 0 ; x < scoresArray.length - 1 ; x++)
	{
	    for (int i = 0 ; i < scoresArray.length - 1 ; i++)
		//if the number after is bigger than previous
		if (scoresArray [i] < scoresArray [i + 1])
		{
		    temp1 = scoresArray [i]; //stores the bigger
		    scoresArray [i] = scoresArray [i + 1]; //makes the current the next number
		    scoresArray [i + 1] = temp1; //makes the next number the current

		    temp2 = names [i];
		    names [i] = names [i + 1];
		    names [i + 1] = temp2;

		    temp3 = level [i];
		    level [i] = level [i + 1];
		    level [i + 1] = temp3;
		}
	}

	for (int x = 0 ; x < linesFile.length / 3 ; x++)
	{
	    c.drawString (names [x], 170, 120 + x * 30);
	    c.drawString (level [x], 315, 120 + x * 30);
	    c.drawString (String.valueOf (scoresArray [x]), 500, 120 + x * 30);
	}

	userInput = c.getChar ();

	//clears the file
	if (userInput == '1')
	{
	    try
	    {
		//open the same file again
		PrintWriter output = new PrintWriter (new BufferedWriter (new FileWriter ("highscores.txt", false)));
		output.print ("");
		output.close ();

	    }
	    catch (IOException e)  //handle file related errors
	    {
		System.out.println (e); //error msg
	    }
	}
    }


    /*
    The displayInstructions method is to inform the user of how to use the program.

    Variable Dictionary
    Name:           Type:               Purpose:
    calibri         Font                stores the font in which the text is output.
    */
    public void displayInstructions ()
    {
	Font calibri = new Font ("calibri", Font.PLAIN, 19);
	drawTitle ();

	c.setFont (calibri);
	c.drawString ("In main menu, you are asked to enter either 1, 2, 3, or 4.", 0, 90);

	c.drawString ("If you enter 1, you will start a new game against the computer...", 0, 130);
	c.drawString ("You will be asked to enter the difficulty. Easy is that the computer has a 8x8 grid;", 0, 155);
	c.drawString ("hard is 9x9 grid. You'll then input the positions for your 5 ships. Then, you will start", 0, 180);
	c.drawString ("playing against the computer who generates random positions for its ships. Each", 0, 205);
	c.drawString ("turn, you will be asked to input a position on the alphanumeric grid in the format", 0, 230);
	c.drawString ("letter + number. E.g. A4. If one of the computer's ships is on A4, you have", 0, 255);
	c.drawString ("punctured one of its ships. If its ships aren 't on A4, you miss and will continue ", 0, 280);
	c.drawString ("trying to hit his ships next turn. You win if you destroy all of the computer's ships.", 0, 305);
	c.drawString ("You lose if the computer destroys all of your ships.", 0, 330);

	c.drawString ("If you enter 2, you will be shown all the high scores of those who played", 0, 365);
	c.drawString ("the game in the past.", 0, 390);

	c.drawString ("If you enter 3, you will be taken here.", 0, 420);
	c.drawString ("If you enter 4, you will exit the game.", 0, 445);
	pauseProgram ();
    }


    /*
    This method lets the user input the string they want to modify, the word they want to replace, and the word they want in place of that word.

    VariableDictionary
    Name:               Type:               Purpose:
    backgroundColor     Color               Stores the colour of the background for the erase.
    calibriBig          Font                Stores the font in which text is output.
    calibriBold         Font                Stores the font in which text is output.
    calibriSmall        Font                Stores the font in which text is output.
    formatExit          boolean             stores whether or not the user enters a correct coordinate.
    adjacentExit        boolean             stores whether or not the user enters a coordinate adjacent to previous coordinates.
    duplicateExit       boolean             stores whether or not the user enters a new coordinate.
    horizontalShip      boolean             stores whether or not the user's ship is horizontal or vertical.
    verticalShip        boolean             stores whether or not the user's ship is horizontal or vertical. ******COULD BE UNNECESSARY******
    letters             String              Stores the letters of the grid.
    numbers             String              stores the numbers of the grid.
    usedCoordinates     String Array        stores the coordinates the user has entered.
    asciiValues         int Array           stores the ascii values of the 2 chars the user enters to compare whether the coordinates are adjacent.

    The first set of embedded for loops is to output the grid for battleship for the user's reference.

    The first while loop is to get input for the level of difficulty the user wants. The if statement error traps the choices and limits the input
    to 1 or 2 and outputs an error trap otherwise.

    The second while loop is to get the input for the user's first ship. The first embedded if statement is to check whether the length is right.
    The next two for loops + if statment check whether the two characters are characters that are in accord with the [letter, number] format. If that is true,
    formatExit will be true and the loop exits. Otherwise, it outputs an error messsage and prompts the user to enter again.

    All of the other for + while loops have the almost completely the same structure and all serve to get input for the user's 2nd, 3rd, 4th, 5th ships respectively.
    The three conditions to exit the while loop and continue on with the for loop are if formatExit, duplicateExit, and adjacentExit are true, and the embedded ifs and
    for loops serve to check those. After the while loop, the if + for + for + if check whether the user  has entered, correctly, a coordinate in the format [letter, number]
    and thereby determining if formatExit is true or not. If formatExit is false, an error msg is output right away without checking for the other condition. If formatExit
    is true, a for loop will run through the array that stores all of the usedCoordinates and checks if the cooridnate the user entered has already been input.
    If it has, duplicateExit will be false and an error msg will be output. if duplicateExit is true, however, the program will go on to check for adjacentExit by
    comparing the asciiValues of the 2 chars of the user's coordinates using a for loop and check if the difference is greater than 1. If it is, adjacentExit is false
    and an error message will be output. If one of the conditions are false, an erase box will be drawn to erase the wrong input. If all three are true, the while loop exits
    and the for loop continues to get the next coordinate. Once the for loop concludes, the program will go to the next for loop of the next ship and this repeats. After the 5th
    ship is done, the method exits.
    */
    public void askData ()
    {
	Color backgroundColor = new Color (0, 179, 0);
	Font calibriBig = new Font ("calibri", Font.PLAIN, 20);
	Font calibriBold = new Font ("calibri", Font.BOLD, 16);
	Font calibriSmall = new Font ("calibri", Font.BOLD, 14);
	boolean formatExit = false, adjacentExit = false, duplicateExit = true, horizontalShip = false, verticalShip = false;
	String letters = "ABCDEFGH", numbers = "12345678", strInt;
	String[] usedCoordinates = new String [15];
	int[] asciiValues = new int [10];

	drawTitle ();

	//draws the grid
	c.setFont (calibriBold);
	for (int x = 0 ; x < 8 ; x++)
	{
	    c.drawString (numbers.substring (x, x + 1), 210, 87 + x * 25);
	    c.drawString (letters.substring (x, x + 1), 237 + x * 25, 65);
	    for (int y = 0 ; y < 8 ; y++)
		c.drawRect (x * 25 + 230, y * 25 + 70, 25, 25);
	}

	c.setFont (calibriBig);
	c.drawString ("Please Select Your Level ", 230, 315);
	c.drawString ("1 - Easy ", 293, 345);
	c.drawString ("2 - Hard ", 293, 375);
	while (true)
	{
	    difficulty = c.getChar ();
	    if (difficulty != '1' && difficulty != '2')
		JOptionPane.showMessageDialog (null, "You must enter 1 or 2! Please try again!");
	    else
		break;
	}
	c.setColor (backgroundColor);
	c.fillRect (0, 295, 640, 205);

	//gets input for the first ship
	c.setFont (calibriSmall);
	while (true)
	{
	    c.setColor (Color.black);
	    c.drawString ("Please enter a coordinate(letter, number) for the position of your 1x1 ship: ", 1, 295);
	    c.setCursor (15, 57);
	    c.setTextBackgroundColor (backgroundColor);
	    firstShipPosition = c.readLine ();
	    firstShipPosition = firstShipPosition.toUpperCase ();

	    if (firstShipPosition.length () == 2)
	    {
		for (char a = 65 ; a < 73 ; a++)
		{
		    for (int b = 49 ; b < 57 ; b++)
			if (firstShipPosition.charAt (0) == a && firstShipPosition.charAt (1) == b)
			    formatExit = true;
		}
	    }

	    if (formatExit == true)
		break;
	    else
	    {
		JOptionPane.showMessageDialog (null, "You must enter a position in the form [letter(A-H), number(1-8)] ! E.g. A1. Please try again!");
		c.setColor (backgroundColor);
		c.fillRect (445, 280, 195, 20);
		c.fillRect (0, 300, 640, 220);
	    }
	}

	//outputs 1st ship
	c.drawLine (3, 35, 175, 35);
	c.drawString ("1st Ship", 5, 50);
	c.drawString (firstShipPosition, 23, 70);
	c.setColor (backgroundColor);
	c.fillRect (0, 280, 640, 220); //erases input
	usedCoordinates [0] = firstShipPosition;

	//gets input for second ship
	for (int x = 0 ; x < secondShipPosition.length ; x++)
	{
	    while (true)
	    {
		//reset exit loop condition
		formatExit = false;
		adjacentExit = true;
		duplicateExit = true;
		c.setColor (Color.black);
		c.drawString ("Please enter a coordinate(letter, number) for the position of your 1x2 ship: ", 1, 295 + x * 20);
		c.setCursor (15 + x, 57);
		c.setTextBackgroundColor (backgroundColor);
		secondShipPosition [x] = c.readLine ();
		secondShipPosition [x] = secondShipPosition [x].toUpperCase ();

		//if length is 2
		if (secondShipPosition [x].length () == 2)
		{
		    for (char a = 65 ; a < 73 ; a++)
		    {
			for (int b = 49 ; b < 57 ; b++)
			    if (secondShipPosition [x].charAt (0) == a && secondShipPosition [x].charAt (1) == b) //if format is not right
				formatExit = true;
		    }
		}

		//checks if duplicate
		if (formatExit == true)
		{
		    //loops through usedCoordinates array
		    for (int i = 0 ; i < 3 ; i++)
		    {
			//checks for duplicate
			if (secondShipPosition [x].equals (usedCoordinates [i]))
			    duplicateExit = false;
		    }
		}

		//checks if adjacent
		if (formatExit == true && duplicateExit == true && x == 1)
		{
		    asciiValues [2] = secondShipPosition [1].charAt (0);
		    asciiValues [3] = secondShipPosition [1].charAt (1);

		    //checks if coordinates are adjacent horizontally or vertically
		    if (Math.abs (asciiValues [1] - asciiValues [3]) > 1 || Math.abs (asciiValues [0] - asciiValues [2]) > 1
			    || Math.abs (asciiValues [1] - asciiValues [3]) - Math.abs (asciiValues [0] - asciiValues [2]) == 0)
			adjacentExit = false;
		}

		//if format is wrong
		if (formatExit == false)
		    JOptionPane.showMessageDialog (null, "You must enter a position in the form [letter(A-H), number(1-8)] ! E.g. A1. Please try again!");
		else if (formatExit == true && duplicateExit == false)
		    JOptionPane.showMessageDialog (null, "You may not enter a grid you've already chose to put one of your ships on! Please try again!");
		else if (formatExit == true && duplicateExit == true && adjacentExit == false)
		    JOptionPane.showMessageDialog (null, "For the same ship, you must enter grids next to each other horizontally or vertically only! Please try again!");

		//erases
		if ((formatExit == false || duplicateExit == false || adjacentExit == false))
		{
		    c.setColor (backgroundColor);
		    c.fillRect (0, 280 + x * 20, 640, 220);
		}

		//exit if all booleans are true
		if (formatExit == true && adjacentExit == true && duplicateExit == true)
		{
		    usedCoordinates [x + 1] = secondShipPosition [x];
		    asciiValues [0] = secondShipPosition [0].charAt (0);
		    asciiValues [1] = secondShipPosition [0].charAt (1);
		    break;
		}
	    }
	}


	//outputs 2nd ship
	c.setColor (Color.black);
	c.drawLine (55, 35, 55, 180);
	c.drawString ("2nd Ship", 60, 50);
	for (int x = 0 ; x < secondShipPosition.length ; x++)
	    c.drawString (secondShipPosition [x], 82, 70 + x * 20);
	c.setColor (backgroundColor);
	c.fillRect (0, 280, 640, 220); //erases input

	//gets input for third ship
	for (int x = 0 ; x < thirdShipPosition.length ; x++)
	{
	    while (true)
	    {
		//reset exit loop condition
		formatExit = false;
		adjacentExit = true;
		duplicateExit = true;
		c.setColor (Color.black);
		c.drawString ("Please enter a coordinate(letter, number) for the position of your 1x3 ship: ", 1, 295 + x * 20);
		c.setCursor (15 + x, 57);
		c.setTextBackgroundColor (backgroundColor);
		thirdShipPosition [x] = c.readLine ();
		thirdShipPosition [x] = thirdShipPosition [x].toUpperCase ();

		//if length is 2
		if (thirdShipPosition [x].length () == 2)
		{
		    for (char a = 65 ; a < 73 ; a++)
		    {
			for (int b = 49 ; b < 57 ; b++)
			    if (thirdShipPosition [x].charAt (0) == a && thirdShipPosition [x].charAt (1) == b) //if format is not right
				formatExit = true;
		    }
		}

		//checks if duplicate
		if (formatExit == true)
		{
		    //loops through usedCoordinates array
		    for (int i = 0 ; i < 6 ; i++)
			if (thirdShipPosition [x].equals (usedCoordinates [i])) //checks for duplicate
			    duplicateExit = false;
		}

		//checks if adjacent for other inputs
		if (formatExit == true && duplicateExit == true && x == 1)
		{
		    asciiValues [2] = thirdShipPosition [1].charAt (0);
		    asciiValues [3] = thirdShipPosition [1].charAt (1);

		    //checks if coordinates are adjacent horizontally or vertically
		    if (Math.abs (asciiValues [1] - asciiValues [3]) > 1 || Math.abs (asciiValues [0] - asciiValues [2]) > 1 ||
			    Math.abs (asciiValues [1] - asciiValues [3]) - Math.abs (asciiValues [0] - asciiValues [2]) == 0)
			adjacentExit = false;

		    //determine the direction of the ship
		    if (adjacentExit == true)
		    {
			if (asciiValues [0] == asciiValues [2])
			    verticalShip = true;
			else
			    horizontalShip = true;
		    }
		}

		//checks if adjacent for third input
		if (formatExit == true && duplicateExit == true && x > 1)
		{
		    asciiValues [x * 2] = thirdShipPosition [x].charAt (0);
		    asciiValues [x * 2 + 1] = thirdShipPosition [x].charAt (1);

		    //runs through ascii values
		    for (int y = 0 ; y < 6 ; y += 2)
		    {
			if (verticalShip == true && (asciiValues [x * 2] != asciiValues [y] || Math.abs (asciiValues [x * 2 + 1] - asciiValues [y + 1]) > x))
			    adjacentExit = false;
			else if (horizontalShip == true && (asciiValues [x * 2 + 1] != asciiValues [y + 1] || Math.abs (asciiValues [x * 2] - asciiValues [y]) > x))
			    adjacentExit = false;
		    }
		}

		//if format is wrong
		if (formatExit == false)
		    JOptionPane.showMessageDialog (null, "You must enter a position in the form [letter(A-H), number(1-8)] ! E.g. A1. Please try again!");
		else if (formatExit == true && duplicateExit == false)
		    JOptionPane.showMessageDialog (null, "You may not enter a grid you've already chose to put one of your ships on! Please try again!");
		else if (formatExit == true && duplicateExit == true && adjacentExit == false)
		    JOptionPane.showMessageDialog (null, "For the same ship, you must enter grids next to each other horizontally or vertically in the same direction! Please try again!");

		//erases
		if (formatExit == false || duplicateExit == false || adjacentExit == false)
		{
		    c.setColor (backgroundColor);
		    c.fillRect (0, 280 + x * 20, 640, 220);
		}

		//exit if all booleans are true
		if (formatExit == true && adjacentExit == true && duplicateExit == true)
		{
		    usedCoordinates [x + 3] = thirdShipPosition [x];
		    asciiValues [0] = thirdShipPosition [0].charAt (0);
		    asciiValues [1] = thirdShipPosition [0].charAt (1);
		    break;
		}
	    }
	}


	//outputs 3rd ship
	c.setColor (Color.black);
	c.drawLine (115, 35, 115, 180);
	c.drawString ("3rd Ship", 120, 50);
	for (int x = 0 ; x < thirdShipPosition.length ; x++)
	    c.drawString (thirdShipPosition [x], 142, 70 + x * 20);
	c.setColor (backgroundColor);
	c.fillRect (0, 280, 640, 220); //erases input
	verticalShip = false;
	horizontalShip = false;

	//gets input for fourth ship
	for (int x = 0 ; x < fourthShipPosition.length ; x++)
	{
	    while (true)
	    {
		//reset exit loop condition
		formatExit = false;
		adjacentExit = true;
		duplicateExit = true;

		c.setColor (Color.black);
		c.drawString ("Please enter a coordinate(letter, number) for the position of your 1x4 ship: ", 1, 295 + x * 20);
		c.setCursor (15 + x, 57);
		c.setTextBackgroundColor (backgroundColor);
		fourthShipPosition [x] = c.readLine ();
		fourthShipPosition [x] = fourthShipPosition [x].toUpperCase ();

		//if length is 2
		if (fourthShipPosition [x].length () == 2)
		{
		    for (char a = 65 ; a < 73 ; a++)
		    {
			for (int b = 49 ; b < 57 ; b++)
			    if (fourthShipPosition [x].charAt (0) == a & fourthShipPosition [x].charAt (1) == b) //if format is not right
				formatExit = true;
		    }
		}

		//checks if duplicate
		if (formatExit == true)
		{
		    //loops through usedCoordinates array
		    for (int i = 0 ; i < 10 ; i++)
			if (fourthShipPosition [x].equals (usedCoordinates [i])) //checks for duplicate
			    duplicateExit = false;
		}

		//checks if adjacent for other inputs
		if (formatExit == true && duplicateExit == true && x == 1)
		{
		    asciiValues [2] = fourthShipPosition [1].charAt (0);
		    asciiValues [3] = fourthShipPosition [1].charAt (1);

		    //checks if coordinates are adjacent horizontally or vertically
		    if (Math.abs (asciiValues [1] - asciiValues [3]) > 1 || Math.abs (asciiValues [0] - asciiValues [2]) > 1 ||
			    Math.abs (asciiValues [1] - asciiValues [3]) - Math.abs (asciiValues [0] - asciiValues [2]) == 0)
			adjacentExit = false;

		    //determine the direction of the ship
		    if (adjacentExit == true)
		    {
			if (asciiValues [0] == asciiValues [2])
			    verticalShip = true;
			else
			    horizontalShip = true;
		    }
		}

		//checks if adjacent for other inputs
		if (formatExit == true && duplicateExit == true && x > 1)
		{
		    asciiValues [x * 2] = fourthShipPosition [x].charAt (0);
		    asciiValues [x * 2 + 1] = fourthShipPosition [x].charAt (1);

		    //runs through ascii values
		    for (int y = 0 ; y < 8 - (6 - x * 2) ; y += 2)
		    {
			if (verticalShip == true && (asciiValues [x * 2] != asciiValues [y] || Math.abs (asciiValues [x * 2 + 1] - asciiValues [y + 1]) > x))
			    adjacentExit = false;
			else if (horizontalShip == true && (asciiValues [x * 2 + 1] != asciiValues [y + 1]) || Math.abs (asciiValues [x * 2] - asciiValues [y]) > x)
			    adjacentExit = false;
		    }
		}

		//if format is wrong
		if (formatExit == false)
		    JOptionPane.showMessageDialog (null, "You must enter a position in the form [letter(A-H), number(1-8)] ! E.g. A1. Please try again!");
		else if (formatExit == true && duplicateExit == false)
		    JOptionPane.showMessageDialog (null, "You may not enter a grid you've already chose to put one of your ships on! Please try again!");
		else if (formatExit == true && duplicateExit == true && adjacentExit == false)
		    JOptionPane.showMessageDialog (null, "For the same ship, you must enter grids next to each other horizontally or vertically in the same direction! Please try again!");

		//erases
		if (formatExit == false || duplicateExit == false || adjacentExit == false)
		{
		    c.setColor (backgroundColor);
		    c.fillRect (0, 280 + x * 20, 640, 220);
		}

		//exit if all booleans are true
		if (formatExit == true && adjacentExit == true && duplicateExit == true)
		{
		    usedCoordinates [x + 6] = fourthShipPosition [x];
		    asciiValues [0] = fourthShipPosition [0].charAt (0);
		    asciiValues [1] = fourthShipPosition [0].charAt (1);
		    break;
		}
	    }
	}

	//outputs 4th ship
	c.setColor (Color.black);
	c.drawLine (455, 35, 575, 35);
	c.drawString ("4th Ship", 460, 50);
	for (int x = 0 ; x < fourthShipPosition.length ; x++)
	    c.drawString (fourthShipPosition [x], 482, 70 + x * 20);
	c.setColor (backgroundColor);
	c.fillRect (0, 280, 640, 220); //erases input

	//reset stuff
	verticalShip = false;
	horizontalShip = false;

	//gets input for fifth ship
	for (int x = 0 ; x < fifthShipPosition.length ; x++)
	{
	    while (true)
	    {
		//reset exit loop condition
		formatExit = false;
		adjacentExit = true;
		duplicateExit = true;

		c.setColor (Color.black);
		c.drawString ("Please enter a coordinate(letter, number) for the position of your 1x5 ship: ", 1, 295 + x * 20);
		c.setCursor (15 + x, 57);
		c.setTextBackgroundColor (backgroundColor);
		fifthShipPosition [x] = c.readLine ();
		fifthShipPosition [x] = fifthShipPosition [x].toUpperCase ();

		//if length is 2
		if (fifthShipPosition [x].length () == 2)
		{
		    for (char a = 65 ; a < 73 ; a++)
		    {
			for (int b = 49 ; b < 57 ; b++)
			    if (fifthShipPosition [x].charAt (0) == a && fifthShipPosition [x].charAt (1) == b) //if format is not right
				formatExit = true;
		    }
		}

		//checks if duplicate
		if (formatExit == true)
		{
		    //loops through usedCoordinates array
		    for (int i = 0 ; i < 15 ; i++)
			if (fifthShipPosition [x].equals (usedCoordinates [i])) //checks for duplicate
			    duplicateExit = false;
		}

		//checks if adjacent for other inputs
		if (formatExit == true && duplicateExit == true && x == 1)
		{
		    asciiValues [2] = fifthShipPosition [1].charAt (0);
		    asciiValues [3] = fifthShipPosition [1].charAt (1);

		    //checks if coordinates are adjacent horizontally or vertically
		    if (Math.abs (asciiValues [1] - asciiValues [3]) > 1 || Math.abs (asciiValues [0] - asciiValues [2]) > 1 ||
			    Math.abs (asciiValues [1] - asciiValues [3]) - Math.abs (asciiValues [0] - asciiValues [2]) == 0)
			adjacentExit = false;

		    //determine the direction of the ship
		    if (adjacentExit == true)
		    {
			if (asciiValues [0] == asciiValues [2])
			    verticalShip = true;
			else
			    horizontalShip = true;
		    }
		}

		//checks if adjacent for other inputs
		if (formatExit == true && duplicateExit == true && x > 1)
		{
		    asciiValues [x * 2] = fifthShipPosition [x].charAt (0);
		    asciiValues [x * 2 + 1] = fifthShipPosition [x].charAt (1);

		    //runs through ascii values
		    for (int y = 0 ; y < 10 - (8 - x * 2) ; y += 2)
		    {
			if (verticalShip == true && (asciiValues [x * 2] != asciiValues [y] || Math.abs (asciiValues [x * 2 + 1] - asciiValues [y + 1]) > x))
			    adjacentExit = false;
			else if (horizontalShip == true && (asciiValues [x * 2 + 1] != asciiValues [y + 1]) || Math.abs (asciiValues [x * 2] - asciiValues [y]) > x)
			    adjacentExit = false;
		    }
		}

		//if format is wrong
		if (formatExit == false)
		    JOptionPane.showMessageDialog (null, "You must enter a position in the form [letter(A-H), number(1-8)] ! E.g. A1. Please try again!");
		else if (formatExit == true && duplicateExit == false)
		    JOptionPane.showMessageDialog (null, "You may not enter a grid you've already chose to put one of your ships on! Please try again!");
		else if (formatExit == true && duplicateExit == true && adjacentExit == false)
		    JOptionPane.showMessageDialog (null, "For the same ship, you must enter grids next to each other horizontally or vertically in the same direction! Please try again!");

		//erases
		if (formatExit == false || duplicateExit == false || adjacentExit == false)
		{
		    c.setColor (backgroundColor);
		    c.fillRect (0, 280 + x * 20, 640, 220);
		}

		//exit if all booleans are true
		if (formatExit == true && adjacentExit == true && duplicateExit == true)
		{
		    usedCoordinates [x + 10] = fifthShipPosition [x];
		    asciiValues [0] = fifthShipPosition [0].charAt (0);
		    asciiValues [1] = fifthShipPosition [0].charAt (1);
		    break;
		}
	    }
	}
	//outputs 5th ship
	c.drawLine (515, 35, 515, 180);
	c.drawString ("5th Ship", 520, 50);
	for (int x = 0 ; x < fifthShipPosition.length ; x++)
	    c.drawString (fifthShipPosition [x], 542, 70 + x * 20);

	//pauses
	c.setFont (calibriBig);
	c.drawString ("Ready?", 0, 455);
	pauseProgram ();
    }


    /*
    The newGame() method displays all of the user's inputs and the new string.

    Variable Dictionary
    Name:               Type:               Purpose:
    gridCoordiantes     final String[]      Stores the all of the coordinates of the 8x8 game board.
    backgroundColor     Color               Stores the colour of the background for the erase.
    calibriBold         Font                Stores the font in which text is output.
    calibriSmall        Font                Stores the font in which text is output.
    letters             String              Stores the letters of the grid.
    numbers             String              stores the numbers of the grid.
    attackedGrid        String              stores the user's input.
    usedCoordinates     String[]            stores the coordinates the user has entered.
    formatExit          boolean             stores whether or not the user enters a correct coordinate.
    duplicateExit       boolean             stores whether or not the user enters a new coordinate.
    loopCounter         int                 stores the numnber of times the while loop has ran.
    shownGridCounter    int                 stores the number of times the user has been shown the grid.
    computerGrids       int[]               stores the randomly generated computer grids.

    The first two embedded for loops are to draw the grids for the computer.

    The second if + embedded for loops are to draw the grids for the user depending on the difficulty the user chose.

    The outer while loop is to repeat the process of the user and the computer attacking until one side wins
    or if the user decides to return to mainMenu().

    The embedded while loop is to ask the user for input and the associated error traps like that the user must enter
    in the correct formatting and that the coordinate must not be duplicate. The first embedded combination of
    if + for + for + if checks whether the user has entered an input with the correct formatting by looping through appropriate
    numbers and chars and comparing them to the user's input. The second embedded combination is an if statment that sets
    formatExit to true if he/she enters 1 or 2 regardless of whether the user has input [letter, number] or not. The third
    embedded combination of if + for + if is to check whether the user has input a non-duplicate coordinate or not by looping
    through the 'usedCoordinates' array and checking if the input is equal to any of them. The next 3 if statements are to
    output the error message if applicable, erase, and break the loop respectively.

    Outside the embedded while loop, the first 3 if statements are to randomize the computer's ships, store usedCoordinates and
    count up 'loopCounter', and output computer ships respectively. The next if statement is to check if the user's attack hit
    one of the computer's ships or not and outputs a message accordingly. An if statement follows it to pauseProgram()
    to show the user whether the computer hit. The method finishes after this and moves on to displayGameResult().
    */
    public void newGame ()
    {
	final String[] gridCoordinates = {"A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "D1", "D2", "D3", "D4", "D5", "D6",
	    "D7", "D8", "D9", "E1", "E2", "E3", "E4", "E5", "E6", "E7", "E8", "E9", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "G1", "G2", "G3", "G4", "G5", "G6", "G7", "G8", "G9", "H1", "H2", "H3", "H4", "H5", "H6", "H7", "H8", "H9",
	    "I1", "I2", "I3", "I4", "I5", "I6", "I7", "I8", "I9"};
	Color backgroundColor = new Color (0, 179, 0);
	Font calibriBold = new Font ("calibri", Font.BOLD, 16);
	Font calibriSmall = new Font ("calibri", Font.BOLD, 14);
	String letters = "ABCDEFGHI", numbers = "123456789", attackedGrid = "";
	String[] usedCoordinates = new String [64];
	boolean formatExit = false, duplicateExit = true;
	int loopCounter = 0, shownGridCounter = 0;
	int[] computerGrids = new int [15];

	drawTitle ();

	//prints the score
	c.setFont (calibriBold);
	c.drawString ("Score", 585, 17);

	//draws the user grid
	c.drawString ("You", 162, 65);
	for (int x = 0 ; x < 8 ; x++)
	{
	    c.drawString (numbers.substring (x, x + 1), 60, 117 + x * 25);
	    c.drawString (letters.substring (x, x + 1), 87 + x * 25, 95);
	    for (int y = 0 ; y < 8 ; y++)
		c.drawRect (x * 25 + 80, y * 25 + 100, 25, 25);
	}

	//separates the 2 grids
	c.drawLine (320, 50, 320, 300);

	//draws the computer grid
	c.drawString ("Computer", 452, 65);

	//easy mode
	if (difficulty == '1')
	{
	    for (int x = 0 ; x < 8 ; x++)
	    {
		c.drawString (numbers.substring (x, x + 1), 360, 117 + x * 25);
		c.drawString (letters.substring (x, x + 1), 387 + x * 25, 95);
		for (int y = 0 ; y < 8 ; y++)
		    c.drawRect (x * 25 + 380, y * 25 + 100, 25, 25);
	    }
	}
	else
	{
	    for (int x = 0 ; x < 9 ; x++)
	    {
		c.drawString (numbers.substring (x, x + 1), 360, 117 + x * 25);
		c.drawString (letters.substring (x, x + 1), 387 + x * 25, 95);
		for (int y = 0 ; y < 9 ; y++)
		    c.drawRect (x * 25 + 380, y * 25 + 100, 25, 25);
	    }
	}

	//while neither wins
	while (computerHits < 15 && userHits < 15 && !attackedGrid.equals ("2"))
	{
	    //gets input
	    c.setFont (calibriSmall);
	    while (true)
	    {
		formatExit = false;
		duplicateExit = true;

		c.setColor (Color.black);
		c.drawString ("Enter which of the computer's grids you would like to hit. ", 1, 375);
		c.drawString ("You can also enter 1 to show computer ships, 2 to go back to main menu: ", 1, 395);
		c.setCursor (20, 56);
		c.setTextBackgroundColor (backgroundColor);
		attackedGrid = c.readLine ();
		attackedGrid = attackedGrid.toUpperCase ();

		//if length is 2
		if (attackedGrid.length () == 2)
		{
		    if (difficulty == '1')
			for (char a = 65 ; a < 73 ; a++)
			{
			    for (int b = 49 ; b < 57 ; b++)
				if (attackedGrid.charAt (0) == a && attackedGrid.charAt (1) == b) //if format is not right
				    formatExit = true;
			}
		    else
			for (char a = 65 ; a < 74 ; a++)
			{
			    for (int b = 49 ; b < 58 ; b++)
				if (attackedGrid.charAt (0) == a && attackedGrid.charAt (1) == b) //if format is not right
				    formatExit = true;
			}
		}
		//if it is 1 or 2
		if (attackedGrid.equals ("1") || attackedGrid.equals ("2"))  //if format is not right
		    formatExit = true;

		//checks if duplicate
		if (formatExit == true)
		{
		    //loops through usedCoordinates array
		    for (int i = 0 ; i < loopCounter ; i++)
		    {
			//checks for duplicate
			if (attackedGrid.equals (usedCoordinates [i]))
			    duplicateExit = false;
		    }
		}

		//if format is wrong
		if (formatExit == false && difficulty == '1')
		    JOptionPane.showMessageDialog (null, "You must enter a position in the form [letter(A-H), number(1-8)] ! E.g. A1. Please try again!");
		else if (formatExit == false && difficulty == '2')
		    JOptionPane.showMessageDialog (null, "You must enter a position in the form [letter(A-I), number(1-9)] ! E.g. A1. Please try again!");
		else if (formatExit == true && duplicateExit == false)
		    JOptionPane.showMessageDialog (null, "You may not enter a grid you've already chosen to attack! Please try again!");

		//erases
		if ((formatExit == false || duplicateExit == false) && (!attackedGrid.equals ("2") && !attackedGrid.equals ("1")))
		{
		    c.setColor (backgroundColor);
		    c.fillRect (0, 360, 640, 140);
		}

		//if someone wins or if user exits
		if (formatExit == true && duplicateExit == true || userHits == 15 || computerHits == 15 || attackedGrid.equals ("2"))
		    break;
	    }
	    //generate grid when it's a new game
	    if (loopCounter == 0 && shownGridCounter == 0 && difficulty == '1')
		computerGrids = generateGrid (true, '1');
	    else if (loopCounter == 0 && shownGridCounter == 0 && difficulty == '2')
		computerGrids = generateGrid (true, '2');

	    //if the user does not enter 1 or 2
	    if (!attackedGrid.equals ("1"))
	    {
		usedCoordinates [loopCounter] = attackedGrid;
		loopCounter++;
	    }

	    //shows computer ships
	    if (attackedGrid.equals ("1"))
	    {
		shownGridCounter = 1;
		c.setFont (calibriSmall);
		for (int x = 0 ; x < computerGrids.length ; x++)
		    c.drawString (gridCoordinates [computerGrids [x]] + "", 620, 55 + x * 20);
	    }

	    //erases
	    c.setFont (calibriBold);
	    c.setColor (Color.black);

	    //see if it was a hit
	    if (isOrIsNotHit (computerGrids, attackedGrid, true) == true && (!attackedGrid.equals ("2") && !attackedGrid.equals ("1")))
	    {
		userHits++;
		c.drawString ("You hit!", 295, 420);

		if (difficulty == '1')
		    score += 300;
		else
		    score += 600;

		int temp = Arrays.asList (gridCoordinates).indexOf (attackedGrid);  //https://stackoverflow.com/questions/3384203/finding-an-element-in-an-array-in-java
		c.drawLine (385 + temp / 9 * 25, 115 + (temp % 9) * 25, 390 + temp / 9 * 25, 120 + (temp % 9) * 25);
		c.drawLine (390 + temp / 9 * 25, 120 + (temp % 9) * 25, 400 + temp / 9 * 25, 105 + (temp % 9) * 25);

		c.setFont (calibriBold);
		c.drawString ("Waiting for the computer to move...", 0, 460);
	    }
	    else if (isOrIsNotHit (computerGrids, attackedGrid, true) == false && (!attackedGrid.equals ("2") && !attackedGrid.equals ("1")))
	    {
		c.drawString ("You missed!", 290, 420);
		int temp = Arrays.asList (gridCoordinates).indexOf (attackedGrid);  //https://stackoverflow.com/questions/3384203/finding-an-element-in-an-array-in-java
		c.drawLine (383 + temp / 9 * 25, 103 + (temp % 9) * 25, 402 + temp / 9 * 25, 122 + (temp % 9) * 25);
		c.drawLine (383 + temp / 9 * 25, 122 + (temp % 9) * 25, 402 + temp / 9 * 25, 103 + (temp % 9) * 25);

		if (difficulty == '1' && score > 0)
		    score -= 100;
		else if (difficulty == '2' && score > 0)
		    score -= 200;
		c.drawString ("Waiting for the computer to move...", 0, 460);
	    }

	    //print score and delay
	    if (!attackedGrid.equals ("1") && !attackedGrid.equals ("2"))
	    {
		//prints the score
		c.setColor (backgroundColor);
		c.fillRect (590, 20, 50, 16);
		c.setColor (Color.black);
		c.setFont (calibriBold);
		c.drawString (String.valueOf (score), 600, 35);
		//delay
		try
		{
		    Thread.sleep (1500);
		}
		catch (Exception e)
		{
		}
	    }

	    //erases
	    c.setColor (backgroundColor);
	    c.fillRect (0, 365, 580, 195);

	    if (!attackedGrid.equals ("2") && !attackedGrid.equals ("1"))
	    {
		boolean storeHit = isOrIsNotHit (generateGrid (false, '1'), attackedGrid, false);
		c.setFont (calibriBold);
		c.setColor (Color.black);

		//computer attacks
		if (storeHit == false)
		    c.drawString ("The computer missed!", 250, 420);
		else
		{
		    computerHits++;
		    c.drawString ("The computer hit!", 255, 420);
		}
		pauseProgram ();
	    }

	    //erases
	    c.setColor (backgroundColor);
	    c.fillRect (100, 335, 480, 40);
	    c.fillRect (0, 365, 580, 135);
	    c.setColor (Color.black);
	    //outputs number of remaining ships
	    c.drawString (15 - computerHits + " ships remaining", 115, 348);
	    c.drawString (15 - userHits + " ships remaining", 420, 348);
	}
    }


    /*
    The displayGameResult method displays all of the user's inputs and the new string.

    Variable Dictionary
    Name:               Type:               Purpose:
    calibriBig          Font                Stores the font in which text is output.
    calibri             Font                Stores the font in which text is output.
    backgroundColor     Color               Stores the colour of the background for the erase.
    lvl                 String              Stores the difficulty to output in a word.

    The if statement is to check if the computer or the user has won. If not, the method will just exit to go to mainMenu.
    The embedded if is to output the result of the game.
    The embedded while loop is to get the input for the user's name.
    The second embedded if is to store the difficulty of the game in a word, not just a char.
    The try block is to write the data to highscores.txt.
    */
    public void displayGameResult ()
    {
	Font calibriBig = new Font ("calibri", Font.BOLD, 40);
	Font calibri = new Font ("calibri", Font.PLAIN, 25);
	Color backgroundColor = new Color (0, 179, 0);
	String lvl = "";
	drawTitle ();

	//if someone wins
	c.setFont (calibriBig);
	if (userHits == 15 || computerHits == 15)
	{
	    if (userHits == 15)
		c.drawString ("You Win!", 257, 250);
	    else
		c.drawString ("You Lose!", 253, 250);

	    c.setFont (calibri);
	    c.drawString ("Your final score is: " + score, 1, 360);

	    //gets user name
	    while (true)
	    {
		c.drawString ("Please enter your name: ", 1, 397);
		c.setCursor (20, 33);
		c.setTextBackgroundColor (backgroundColor);
		name = c.readLine ();
		break;
	    }
	    if (difficulty == '1')
		lvl = "Easy";
	    else
		lvl = "Hard";
	    //writes to notepad
	    try
	    {
		PrintWriter output = new PrintWriter (new BufferedWriter (new FileWriter ("highscores.txt", true)));
		output.println (name);
		output.println (lvl);
		output.println (score);
		output.close ();
	    }
	    catch (IOException e)
	    {
		JOptionPane.showMessageDialog (null, "Sorry, this file cannot be found. Please enter a different file name.");   //error message
	    }
	}
    }


    /*
    The purpose of the goodbye method is to print goodbye messages and my name when the user exits or
    the program finishes. If the user clicks a key in this method, the window closes.

    Variable Dictionary
    Name:           Type:               Purpose:
    calibri         Font                stores the font in which the text is output.

    The try block is to delay closing the window by 3 seconds for the reader to read the goodbye msg.
    */
    public void goodbye ()
    {
	Font calibri = new Font ("calibri", Font.PLAIN, 25);
	drawTitle ();
	c.setFont (calibri);
	c.drawString ("Thanks for using this program!", 180, 235);
	c.drawString ("Program by: Jerry Zhu", 220, 265);

	//delay
	try
	{
	    Thread.sleep (3000);
	}
	catch (Exception e)
	{
	}
	c.close ();
    }


    /////////////////////////********PRIVATE METHODS*****************///////////////


    /*
    The purpose of this method is to clear the screen and print the title.

    Variable Dictionary
    Name:              Type:               Purpose:
    titleFont          Font                stores the font in which the title is output.
    backgroundColor    Color               stores the color to draw the background.
    */
    private void drawTitle ()  //drawTitle method
    {
	Color backgroundColor = new Color (0, 179, 0);
	Font titleFont = new Font ("UnifrakturMaguntia", Font.BOLD, 35); //Initializes the font
	c.clear ();
	c.println ("\n");
	c.setColor (backgroundColor);
	c.fillRect (0, 0, 640, 500);
	c.setFont (titleFont);
	c.setColor (Color.black);
	c.drawString ("Battleship", 240, 30);
    }


    /*
    The purpose of this method is to pause the program and gives the user time to understand the program.
    */
    private void pauseProgram ()  //pauseProgram method
    {
	c.setColor (Color.black);
	c.drawString ("Press any key to continue...", 0, 480);
	c.getChar ();
    }


    /*
    The purpose of this method is to check whether the computer or the user has hit the other's ships and returns
    a boolean value based on it.

    VariableDictionary
    Name:                       Type:               Purpose:
    computergridCoordinates     final String[]      stores all of the coordinates of the 9x9 game board.
    gridCoordinates             final String[]      stores all of the coordinates of the 8x8 game board.
    computerAttack              int                 stores the computer's randomly generated attack value.
    hit                         boolean             stores whether the computer has hit one of the user's ships.

    The outmost if statement is to determine which action to do based on the userAttack parameter.

    If userAttack is true, this method returns a boolean based on whether the user's attack has hit using a for loop to run through the 'grids' parameter,
    which stores the computer ships, and an if statement to compare if the user's attack equals to any of the values of grids. If it's true, this
    method returns true and returns false otherwise.

    If userAttack is false, this method randomly generates an computer attack and uses a for loop to run through the 'grids', and if the value is equal(compared
    using an if), the hit becomes true and otherwise false. Based on 'hit', this method will mark the grid and return 'hit'.
    */
    private boolean isOrIsNotHit (int[] grids, String attack, boolean userAttack)
    {
	final String[] computerGridCoordinates = {"A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "D1", "D2", "D3", "D4", "D5", "D6",
	    "D7", "D8", "D9", "E1", "E2", "E3", "E4", "E5", "E6", "E7", "E8", "E9", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "G1", "G2", "G3", "G4", "G5", "G6", "G7", "G8", "G9", "H1", "H2", "H3", "H4", "H5", "H6", "H7", "H8", "H9",
	    "I1", "I2", "I3", "I4", "I5", "I6", "I7", "I8", "I9"};
	final String[] gridCoordinates = {"A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "D1", "D2", "D3", "D4", "D5", "D6",
	    "D7", "D8", "E1", "E2", "E3", "E4", "E5", "E6", "E7", "E8", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "G1", "G2", "G3", "G4", "G5", "G6", "G7", "G8", "H1", "H2", "H3", "H4", "H5", "H6", "H7", "H8", };
	int computerAttack = 0;
	boolean hit;

	//user attack
	if (userAttack == true)
	{
	    for (int x = 0 ; x < grids.length ; x++)
		if (Arrays.asList (computerGridCoordinates).indexOf (attack) == grids [x]) //https://stackoverflow.com/questions/3384203/finding-an-element-in-an-array-in-java
		    return true;
	}
	else
	{
	    hit = false;
	    computerAttack = (int) (0 + Math.random () * 64);

	    //checks if the AI hits
	    for (int x = 0 ; x < grids.length ; x++)
		if (computerAttack == grids [x])
		    hit = true;

	    c.setColor (Color.black);
	    if (hit == true)
	    {
		c.drawLine (85 + computerAttack / 8 * 25, 115 + (computerAttack % 8) * 25, 90 + computerAttack / 8 * 25, 120 + (computerAttack % 8) * 25);
		c.drawLine (90 + computerAttack / 8 * 25, 120 + (computerAttack % 8) * 25, 100 + computerAttack / 8 * 25, 105 + (computerAttack % 8) * 25);
	    }
	    else
	    {
		c.drawLine (83 + computerAttack / 8 * 25, 103 + (computerAttack % 8) * 25, 102 + computerAttack / 8 * 25, 122 + (computerAttack % 8) * 25);
		c.drawLine (83 + computerAttack / 8 * 25, 122 + (computerAttack % 8) * 25, 102 + computerAttack / 8 * 25, 103 + (computerAttack % 8) * 25);
	    }
	    return hit;
	}
	return false;
    }


    /*
    The purpose of this method is to return the randomly generated computer grids or sort the userGrids into a single array.

    Variable Dictionary
    Name:                     Type:               Purpose:
    gridCoordinates           final String[]      stores all of the coordinates of the game board.
    first                     String              stores the first computer ship.
    second                    String[]            stores the second computer ship.
    third                     String[]            stores the third computer ship.
    fourth                    String[]            stores the fourth computer ship.
    fifth                     String[]            stores the fifth computer ship.
    firstAvailableGrids       String[]            sets the grids in which the computer can randomize for the first ship.
    secondAvailableGrids      String[]            sets the grids in which the computer can randomize for second the ship.
    thirdAvailableGrids       String[]            sets the grids in which the computer can randomize for the third ship.
    fourthAvailableGrids      String[]            sets the grids in which the computer can randomize for the fourth ship.
    fifthAvailableGrids       String[]            sets the grids in which the computer can randomize for the fifth ship.
    asciiValues               int[]               stores the ascii values of the computer ships for
						   making the computer ships adjacent.
    computerGrids             int[]               stores the computer's ships.
    userGrids                 int[]               stores the user's ships.
    usedGrids                 int[]               stores the computer's used grids to prevent duplicates.
    verticalShip              boolean             stores whether the computer is vertical or not.
    horizontalShip            boolean             stores whether the computer is horizontal or not.
    duplicateExit             boolean             stores whether the computer's generated grids are unique.
    adjacentExit              boolean             stores whether a computer's generated grids are adjacent.

    The outmost if statement is to determine which action to do based on the computerGrid parameter.

    If computerGrid is true, this method serves the general purpose of randomly generating computer's ships.

    The first if statement is to set the available grids in which the computer can randomize grids based on
    the difficulty. Hard has more grids in which the computer randomizes than easy.
    The second if statement is to randomize the computer's first ship based on the difficulty.
    The next four combinations of if, for, and while statements are all very similarly structured
    that randomize the computer's second, third, fourth, and fifth ships respectively.
    The if statement tells it what to randomize based on the difficulty. The while loop keeps randomizing the grids
    lest the two exit conditions: duplicateExit and adjacentExit are fulfilled.
    duplicateExit is fulfilled by comparing the genertated grid with 'usedGrids' that stores all of the grids the
    computer has used using for loops and if statements.
    adjacentExit is fulfilled also using for loops and if staments that run through the ascii values of previously
    generated grids and determines if they are adjacent.
    The loop exits when those conditions are fulfilled and it goes on to the next ship. When all of the ships are done,
    the method returns computerGrids that stores all of the computer's ships.

    If computerGrid is false, this method sorts all of the user ships into a single int[] using for loops to run through
    all of the strings and returns it.
    */
    private int[] generateGrid (boolean computerGrid, char level)
    {
	final String[] gridCoordinates = {"A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "D1", "D2", "D3", "D4", "D5", "D6",
	    "D7", "D8", "D9", "E1", "E2", "E3", "E4", "E5", "E6", "E7", "E8", "E9", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "G1", "G2", "G3", "G4", "G5", "G6", "G7", "G8", "G9", "H1", "H2", "H3", "H4", "H5", "H6", "H7", "H8", "H9",
	    "I1", "I2", "I3", "I4", "I5", "I6", "I7", "I8", "I9"};
	String first;
	String[] second = new String [2], third = new String [3], fourth = new String [4], fifth = new String [5], firstAvailableGrids, secondAvailableGrids, thirdAvailableGrids, fourthAvailableGrids;
	int[] asciiValues = new int [10], computerGrids = new int [15], userGrids = new int [15], usedGrids = new int [5];
	boolean verticalShip = false, horizontalShip = false, duplicateExit = false, adjacentExit = false;

	//if generate computerGrid
	if (computerGrid == true)
	{
	    //determines available grids
	    if (level == '1')
	    {
		firstAvailableGrids = new String[]
		{
		    "E1", "E2", "E3", "E4", "F1", "F2", "F3", "F4", "G1", "G2", "G3", "G4", "H1", "H2", "H3", "H4"
		}
		;
		secondAvailableGrids = new String[]
		{
		    "E1", "E2", "E3", "E4", "F1", "F2", "F3", "F4", "G1", "G2", "G3", "G4", "H1", "H2", "H3", "H4"
		}
		;
		thirdAvailableGrids = new String[]
		{
		    "A6", "A7", "A8", "B6", "B7", "B8", "C6", "C7", "C8", "D6", "D7", "D8"
		}
		;
		fourthAvailableGrids = new String[]
		{
		    "E5", "E6", "E7", "E8", "F5", "F6", "F7", "F8", "G5", "G6", "G7", "G8", "H5", "H6", "H7", "H8"
		}
		;
	    }
	    else
	    {
		firstAvailableGrids = new String[]
		{
		    "E1", "E2", "E3", "E4", "F1", "F2", "F3", "F4", "G1", "G2", "G3", "G4", "H1", "H2", "H3", "H4", "I1", "I2", "I3", "I4"
		}
		;
		secondAvailableGrids = new String[]
		{
		    "E1", "E2", "E3", "E4", "F1", "F2", "F3", "F4", "G1", "G2", "G3", "G4", "H1", "H2", "H3", "H4", "I1", "I2", "I3", "I4"
		}
		;
		thirdAvailableGrids = new String[]
		{
		    "A6", "A7", "A8", "A9", "B6", "B7", "B8", "B9", "C6", "C7", "C8", "C9", "D6", "D7", "D8", "D9"
		}
		;
		fourthAvailableGrids = new String[]
		{
		    "E5", "E6", "E7", "E8", "E9", "F5", "F6", "F7", "F8", "F9", "G5", "G6", "G7", "G8", "G9", "H5", "H6", "H7", "H8", "H9", "I5", "I6", "I7", "I8", "I9"
		}
		;
	    }
	    String[] fifthAvailableGrids = {"A1", "A2", "A3", "A4", "A5", "B1", "B2", "B3", "B4", "B5", "C1", "C2", "C3", "C4", "C5", "D1", "D2", "D3", "D4", "D5"};

	    if (level == '1')
		first = firstAvailableGrids [(int) (0 + Math.random () * 16)];
	    else
		first = firstAvailableGrids [(int) (0 + Math.random () * 20)];
	    computerGrids [0] = Arrays.asList (gridCoordinates).indexOf (first); //https://stackoverflow.com/questions/3384203/finding-an-element-in-an-array-in-java
	    usedGrids [0] = Arrays.asList (firstAvailableGrids).indexOf (first); //https://stackoverflow.com/questions/3384203/finding-an-element-in-an-array-in-java


	    //fills the secondShipPosition ship
	    for (int x = 0 ; x < second.length ; x++)
	    {

		int a = 0;
		while (true)
		{
		    duplicateExit = true;
		    adjacentExit = true;

		    if (level == '1')
			a = (int) (0 + Math.random () * 16);
		    else
			a = (int) (0 + Math.random () * 20);

		    second [x] = secondAvailableGrids [a];

		    for (int y = 0 ; y < 3 ; y++)
		    {
			if (a == usedGrids [y])
			    duplicateExit = false;
		    }

		    if (duplicateExit == true && x == 1)
		    {
			asciiValues [x * 2] = second [1].charAt (0);
			asciiValues [x * 2 + 1] = second [1].charAt (1);

			//checks if coordinates are adjacent horizontally or vertically
			if (Math.abs (asciiValues [1] - asciiValues [3]) > 1 || Math.abs (asciiValues [0] - asciiValues [2]) > 1 ||
				Math.abs (asciiValues [1] - asciiValues [3]) - Math.abs (asciiValues [0] - asciiValues [2]) == 0)
			    adjacentExit = false;
		    }

		    if (duplicateExit == true && adjacentExit == true)
		    {
			asciiValues [0] = second [0].charAt (0);
			asciiValues [1] = second [0].charAt (1);
			computerGrids [x + 1] = Arrays.asList (gridCoordinates).indexOf (second [x]); //https://stackoverflow.com/questions/3384203/finding-an-element-in-an-array-in-java
			usedGrids [x + 1] = Arrays.asList (secondAvailableGrids).indexOf (second [x]); //https://stackoverflow.com/questions/3384203/finding-an-element-in-an-array-in-java
			break;
		    }
		}
	    }


	    //fills thirdShipPosition ship
	    for (int x = 0 ; x < third.length ; x++)
	    {
		int a = 0;
		while (true)
		{
		    duplicateExit = true;
		    adjacentExit = true;
		    if (level == '1')
			a = (int) (0 + Math.random () * 12);
		    else
			a = (int) (0 + Math.random () * 16);
		    third [x] = thirdAvailableGrids [a];

		    for (int y = 0 ; y < x ; y++)
		    {
			if (a == usedGrids [y])
			    duplicateExit = false;
		    }

		    if (duplicateExit == true && x == 1)
		    {
			asciiValues [x * 2] = third [x].charAt (0);
			asciiValues [x * 2 + 1] = third [x].charAt (1);

			//checks if coordinates are adjacent horizontally or vertically
			if (Math.abs (asciiValues [1] - asciiValues [3]) > 1 || Math.abs (asciiValues [0] - asciiValues [2]) > 1 ||
				Math.abs (asciiValues [1] - asciiValues [3]) - Math.abs (asciiValues [0] - asciiValues [2]) == 0)
			    adjacentExit = false;

			//determine the direction of the ship
			if (adjacentExit == true)
			{
			    if (asciiValues [0] == asciiValues [2])
				verticalShip = true;
			    else
				horizontalShip = true;
			}
		    }

		    //checks if adjacent for thirdShipPosition input
		    if (duplicateExit == true && x > 1)
		    {
			asciiValues [x * 2] = third [x].charAt (0);
			asciiValues [x * 2 + 1] = third [x].charAt (1);

			//runs through ascii values
			for (int y = 0 ; y < 6 ; y += 2)
			{
			    if (verticalShip == true && (asciiValues [x * 2] != asciiValues [y] || Math.abs (asciiValues [x * 2 + 1] - asciiValues [y + 1]) > x))
				adjacentExit = false;
			    else if (horizontalShip == true && (asciiValues [x * 2 + 1] != asciiValues [y + 1] || Math.abs (asciiValues [x * 2] - asciiValues [y]) > x))
				adjacentExit = false;
			}
		    }

		    if (duplicateExit == true && adjacentExit == true)
		    {
			asciiValues [0] = third [0].charAt (0);
			asciiValues [1] = third [0].charAt (1);
			computerGrids [x + 3] = Arrays.asList (gridCoordinates).indexOf (third [x]); //https://stackoverflow.com/questions/3384203/finding-an-element-in-an-array-in-java
			usedGrids [x] = Arrays.asList (thirdAvailableGrids).indexOf (third [x]); //https://stackoverflow.com/questions/3384203/finding-an-element-in-an-array-in-java
			break;
		    }
		}
	    }


	    //fills the fourthShipPosition ship
	    for (int x = 0 ; x < fourth.length ; x++)
	    {

		int a = 0;
		while (true)
		{
		    duplicateExit = true;
		    adjacentExit = true;

		    if (level == '1')
			a = (int) (0 + Math.random () * 16);
		    else
			a = (int) (0 + Math.random () * 25);

		    fourth [x] = fourthAvailableGrids [a];

		    for (int y = 0 ; y < x ; y++)
		    {
			if (a == usedGrids [y])
			    duplicateExit = false;
		    }

		    if (duplicateExit == true && x == 1)
		    {
			asciiValues [2] = fourth [1].charAt (0);
			asciiValues [3] = fourth [1].charAt (1);

			//checks if coordinates are adjacent horizontally or vertically
			if (Math.abs (asciiValues [1] - asciiValues [3]) > 1 || Math.abs (asciiValues [0] - asciiValues [2]) > 1 ||
				Math.abs (asciiValues [1] - asciiValues [3]) - Math.abs (asciiValues [0] - asciiValues [2]) == 0)
			    adjacentExit = false;

			//determine the direction of the ship
			if (adjacentExit == true)
			{
			    if (asciiValues [0] == asciiValues [2])
			    {
				verticalShip = true;
				horizontalShip = false;
			    }
			    else
			    {
				horizontalShip = true;
				verticalShip = false;
			    }
			}
		    }

		    //checks if adjacent for thirdShipPosition input
		    if (duplicateExit == true && x > 1)
		    {
			asciiValues [x * 2] = fourth [x].charAt (0);
			asciiValues [x * 2 + 1] = fourth [x].charAt (1);

			//runs through ascii values
			for (int y = 0 ; y < 8 - (6 - x * 2) ; y += 2)
			{
			    if (verticalShip == true && (asciiValues [x * 2] != asciiValues [y] || Math.abs (asciiValues [x * 2 + 1] - asciiValues [y + 1]) > x))
				adjacentExit = false;
			    else if (horizontalShip == true && (asciiValues [x * 2 + 1] != asciiValues [y + 1] || Math.abs (asciiValues [x * 2] - asciiValues [y]) > x))
				adjacentExit = false;
			}
		    }


		    if (duplicateExit == true && adjacentExit == true)
		    {
			asciiValues [0] = fourth [0].charAt (0);
			asciiValues [1] = fourth [0].charAt (1);
			computerGrids [x + 6] = Arrays.asList (gridCoordinates).indexOf (fourth [x]); //https://stackoverflow.com/questions/3384203/finding-an-element-in-an-array-in-java
			usedGrids [x] = Arrays.asList (fourthAvailableGrids).indexOf (fourth [x]); //https://stackoverflow.com/questions/3384203/finding-an-element-in-an-array-in-java
			break;
		    }
		}
	    }

	    //fills the fifthShipPosition ship
	    for (int x = 0 ; x < fifth.length ; x++)
	    {
		int a = 0;
		while (true)
		{
		    duplicateExit = true;
		    adjacentExit = true;
		    verticalShip = true;

		    a = (int) (0 + Math.random () * 20);
		    fifth [x] = fifthAvailableGrids [a];

		    for (int y = 0 ; y < x ; y++)
		    {
			if (a == usedGrids [y])
			    duplicateExit = false;
		    }

		    //checks if adjacent for thirdShipPosition input
		    if (duplicateExit == true)
		    {
			asciiValues [x * 2] = fifth [x].charAt (0);
			asciiValues [x * 2 + 1] = fifth [x].charAt (1);

			//runs through ascii values
			for (int y = 0 ; y < 10 - (8 - x * 2) ; y += 2)
			{
			    if ((asciiValues [x * 2] != asciiValues [y] || Math.abs (asciiValues [x * 2 + 1] - asciiValues [y + 1]) > x))
				adjacentExit = false;
			}
		    }

		    if (duplicateExit == true && adjacentExit == true)
		    {
			asciiValues [0] = fifth [0].charAt (0);
			asciiValues [1] = fifth [0].charAt (1);
			computerGrids [x + 10] = Arrays.asList (gridCoordinates).indexOf (fifth [x]); //https://stackoverflow.com/questions/3384203/finding-an-element-in-an-array-in-java
			usedGrids [x] = Arrays.asList (fifthAvailableGrids).indexOf (fifth [x]); //https://stackoverflow.com/questions/3384203/finding-an-element-in-an-array-in-java
			break;
		    }
		}
	    }
	    return computerGrids;
	}
	else
	{
	    //stores all of the ships into userGrids
	    userGrids [0] = Arrays.asList (gridCoordinates).indexOf (firstShipPosition); //https://stackoverflow.com/questions/3384203/finding-an-element-in-an-array-in-java
	    for (int x = 0 ; x < secondShipPosition.length ; x++)
		userGrids [x + 1] = Arrays.asList (gridCoordinates).indexOf (secondShipPosition [x]); //https://stackoverflow.com/questions/3384203/finding-an-element-in-an-array-in-java
	    for (int x = 0 ; x < thirdShipPosition.length ; x++)
		userGrids [x + 3] = Arrays.asList (gridCoordinates).indexOf (thirdShipPosition [x]); //https://stackoverflow.com/questions/3384203/finding-an-element-in-an-array-in-java
	    for (int x = 0 ; x < fourthShipPosition.length ; x++)
		userGrids [x + 6] = Arrays.asList (gridCoordinates).indexOf (fourthShipPosition [x]); //https://stackoverflow.com/questions/3384203/finding-an-element-in-an-array-in-java
	    for (int x = 0 ; x < fifthShipPosition.length ; x++)
		userGrids [x + 10] = Arrays.asList (gridCoordinates).indexOf (fifthShipPosition [x]); //https://stackoverflow.com/questions/3384203/finding-an-element-in-an-array-in-java

	    return userGrids;
	}
    }


    /*
    The purpose of main method is to control the flow of the program and creating the output console.

    Variable Dictionary
    Name:               Type:                          Purpose:
    d                   BattleshipGame                 Declares an instance variable of the class and creates the output screen.

    The while loop is for the purpose of looping the program back to mainMenu if the user does not exit.
    The if statement is to control the method calling. If the user enters 3, the program will exit. Otherwise, it will continue to the other methods.
    */
    public static void main (String[] args)
    {
	BattleshipGame d = new BattleshipGame ();
	d.splashScreen ();
	while (true)
	{
	    d.mainMenu ();
	    if (userChoice == '2')
		d.displayHighScores ();
	    else if (userChoice == '3')
		d.displayInstructions ();
	    else if (userChoice == '1')
	    {
		d.askData ();
		d.newGame ();
		d.displayGameResult ();
	    }
	    else
		break;
	}
	d.goodbye ();
    } //main method
} //BattleshipGame class
