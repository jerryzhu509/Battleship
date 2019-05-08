/*
Programmer Name: Jerry Zhu
Program name: ShipAnimation
Teacher name: Ms.Krasteva
Date: December 17, 2018
This program is the class for my ship animation as a thread for my ISP that will
be displayed in splashScreen().

Variable Dictionary
Name:                   Type:               Purpose:
c                       Console             Creates an instance of Console class.
*/

// The "ShipAnimation" class.
import java.awt.*;
import hsa.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.ImageIO;

public class ShipAnimation extends Thread
{
    private Console c;           // The output console


    /*
    The purpose of this method is to display a graphic.

    Variable Dictionary
    Name:               Type:               Purpose:
    backgroundColor    Color               stores the color to draw the background.
    calibriBold         Font                stores the font in which the text is output.

    The for loop is to draw the ship and move it.
    The first try block is to output the image.
    The second try block is to animate the ship.
    */
    public void drawShip ()
    {
	Color backgroundColor = new Color (0, 179, 0);
	Font calibriBold = new Font ("calibri", Font.BOLD, 30);

	//for loop to animate the ship
	for (int x = 0 ; x < 630 ; x++)
	{
	    c.setColor (backgroundColor);
	    c.fillRect (x - 601, 74, 541, 357); //erase

	    c.setColor (Color.black);
	    c.fillRect (x - 310, 160, 140, 70); //upmost rect
	    c.fillRect (x - 350, 230, 220, 70); //2nd upmost rect

	    c.setColor (backgroundColor);
	    c.fillRect (x - 308, 162, 136, 68); //erases the inside
	    c.fillRect (x - 348, 232, 216, 68); //erases the inside

	    c.setColor (Color.black);
	    c.drawLine (x - 280, 110, x - 200, 110); //cross
	    c.drawLine (x - 240, 75, x - 240, 160); //cross
	    c.drawLine (x - 280, 111, x - 200, 111); //cross
	    c.drawLine (x - 239, 75, x - 239, 160); //cross

	    c.drawLine (x - 570, 250, x - 490, 250); //cross
	    c.drawLine (x - 530, 215, x - 530, 300); //cross
	    c.drawLine (x - 570, 251, x - 490, 251); //cross
	    c.drawLine (x - 529, 215, x - 529, 300); //cross

	    c.drawLine (x - 600, 300, x - 510, 300); //left lines
	    c.drawLine (x - 600, 301, x - 510, 301); //left lines
	    c.drawLine (x - 600, 300, x - 510, 430); //left lines
	    c.drawLine (x - 600, 301, x - 510, 429); //left lines

	    c.drawLine (x - 130, 300, x - 60, 300); //right lines
	    c.drawLine (x - 130, 301, x - 60, 301); //right lines
	    c.drawLine (x - 60, 301, x - 130, 430); //right lines
	    c.drawLine (x - 60, 300, x - 130, 429); //right lines

	    c.fillRect (x - 510, 300, 380, 130); //ship body rect
	    c.setColor (backgroundColor);
	    c.fillRect (x - 510, 302, 380, 126); //erases the inside

	    c.setColor (Color.black);
	    c.fillOval (x - 500, 310, 40, 30); //left window
	    c.fillOval (x - 350, 310, 40, 30); //middle window
	    c.fillOval (x - 200, 310, 40, 30); //right window
	    c.drawLine (x - 565, 350, x - 88, 350); //middle lines
	    c.drawLine (x - 565, 351, x - 88, 351); //middle lines

	    c.setColor (Color.white);
	    c.fillOval (x - 496, 314, 32, 22); //left window
	    c.fillOval (x - 346, 314, 32, 22); //middle window
	    c.fillOval (x - 196, 314, 32, 22); //right window

	    c.setFont (calibriBold);
	    c.setColor (Color.black);
	    c.drawString ("BATTLESHIP", x - 380, 400);

	    //gets image
	    try
	    {
		BufferedImage boardPic = ImageIO.read (new File ("lol.jpg"));
		c.drawImage (boardPic, x - 500, 360, null);
	    }
	    catch (IOException e)
	    {
		JOptionPane.showMessageDialog (null, "File not found!");
	    }


	    //delay
	    try
	    {
		Thread.sleep (1);
	    }
	    catch (Exception e)
	    {
	    }
	}
    }


    /*
    The purpose of this constructor is to create the class object.
    */
    public ShipAnimation (Console con)
    {
	c = con;
    }

    /*
    The purpose of this method is to call the method that draws and animate the ship.
    */
    public void run ()
    {
	drawShip ();
    } // main method
} // ShipAnimation class
