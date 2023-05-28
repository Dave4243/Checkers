import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

/**
* @author David Huang
* @date   5/28/2023 (commit to github)
* The Checkers.java class is a checkers game using Javax.swing.
* Initially written in Summer 2022.
*/
public class Checkers extends JFrame implements ActionListener
{
	private static JFrame f;
	private JPanel        p, boardPanel, sidePanel;

	private JButton[][]   board;
	private JLabel        timeLabel1, timeLabel2;

	private JTextField    text1, text2, winnerText;

	private Checker[][]   pieces;
	private Color         selected, dark, light;

	private Timer  	timer1, timer2;
	private boolean multipleCapture;

	private int x, y, second, minute, second2, minute2, turn, movesWithoutCapture;

	private String ddSecond, ddMinute, ddSecond2, ddMinute2;	
	private DecimalFormat dFormat = new DecimalFormat("00");

	private Font checkerFont;
	/**
	 * Constructor
	 */
	public Checkers()
	{
		f = new JFrame("Checkers");
		p = new JPanel(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;

		boardPanel = new JPanel(new GridLayout(8, 8));

		board = new JButton[8][8];

		pieces = new Checker[8][8];

		checkerFont = new Font("Neue Helvetica", Font.BOLD, 26);
		movesWithoutCapture = 0;
		
		text1 = new JTextField("Player 1");
		text1.setEditable(true);
		text1.setForeground(Color.WHITE);
		text1.setBackground(new Color(37,36,32));
		text1.setFont(checkerFont);
		text1.setBorder(BorderFactory.createEmptyBorder());
		text1.setHorizontalAlignment(JTextField.CENTER);

		text2 = new JTextField("Player 2");
		text2.setEditable(true);
		text2.setForeground(Color.WHITE);
		text2.setBackground(new Color(37,36,32));
		text2.setFont(checkerFont);
		text2.setBorder(BorderFactory.createEmptyBorder());
		text2.setHorizontalAlignment(JTextField.CENTER);

		winnerText = new JTextField("");
		winnerText.setEditable(false);
		winnerText.setForeground(Color.WHITE);
		winnerText.setBackground(new Color(37,36,32));
		winnerText.setFont(checkerFont);
		winnerText.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		winnerText.setHorizontalAlignment(JTextField.CENTER);

		timeLabel1 = new JLabel();
		timeLabel1.setForeground(Color.WHITE);
		timeLabel1.setHorizontalAlignment(JLabel.CENTER);
		timeLabel1.setBackground(new Color(37,36,32));
		timeLabel1.setOpaque(true);
		timeLabel1.setFont(checkerFont);

		timeLabel2 = new JLabel();
		timeLabel2.setForeground(Color.WHITE);
		timeLabel2.setHorizontalAlignment(JLabel.CENTER);
		timeLabel2.setBackground(new Color(37,36,32));
		timeLabel2.setOpaque(true);
		timeLabel2.setFont(checkerFont);

		timeLabel1.setText("10:00");
		second =0;
		minute =10;

		timeLabel2.setText("10:00");
		second2 =0;
		minute2 =10;

		turn = 0;
		timer1 = new Timer(100, this);
		timer2 = new Timer(100, this);
		multipleCapture = false;
        
		selected = new Color(212,108,81);
		dark = new Color(135,70,38);
		light = new Color(222,188,149);
		// gridbagconstraints sets visual aspects of conponents

		for (int row = 0; row < 8; row++)
		{
		    for (int col = 0; col < 8; col++)
		    {
				board[row][col] = new JButton();
				board[row][col].setPreferredSize(new Dimension(64,64));

				if ((row + col) % 2 == 0)
				{
					board[row][col].setBackground(light);
				}
				if ((row + col) % 2 == 1)
				{
					board[row][col].setBackground(dark);
					if (row == 0 || row == 1 || row == 2)
					{
						pieces[row][col] = new Regular(Color.BLACK, row, col);
					}

					if (row == 5 || row == 6 || row == 7)
					{
						pieces[row][col] = new Regular(Color.WHITE, row, col);
				    }
				}

				board[row][col].setBorder(new LineBorder(Color.BLACK));
				board[row][col].addActionListener(this);
				boardPanel.add(board[row][col]);
		    }
		}
		setBoard();
		p.add(boardPanel, c);

		sidePanel = new JPanel(new GridLayout(5,1));
		sidePanel.setPreferredSize(new Dimension(200, 514));

		c.gridx = 1;
		c.weightx = 1;
		c.weighty = 1;

		sidePanel.add(text2);
		sidePanel.add(timeLabel2);
		sidePanel.add(winnerText);
		sidePanel.add(timeLabel1);
		sidePanel.add(text1);

		p.add(sidePanel, c);
		f.setContentPane(p);
		initializeTimers();
	}

	public static void main(String a[])
    {
		Checkers app = new Checkers();

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//f.setSize(700,740);
		f.pack();
		f.setVisible(true);
   	}

	public void initializeTimers()
	{
		timer1 = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				second--;
				ddSecond = dFormat.format(second);
				ddMinute = dFormat.format(minute);	
				timeLabel1.setText(ddMinute + ":" + ddSecond);
				
				if(second==-1) {
					second = 59;
					minute--;
					ddSecond = dFormat.format(second);
					ddMinute = dFormat.format(minute);	
					timeLabel1.setText(ddMinute + ":" + ddSecond);
				}
				if(minute==0 && second==0) {
					checkForWinner();
				}
			}
		});		
		timer2 = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				second2--;
				ddSecond2 = dFormat.format(second2);
				ddMinute2 = dFormat.format(minute2);	
				timeLabel2.setText(ddMinute2 + ":" + ddSecond2);
				
				if(second2==-1) {
					second2 = 59;
					minute2--;
					ddSecond2 = dFormat.format(second2);
					ddMinute2 = dFormat.format(minute2);	
					timeLabel2.setText(ddMinute2 + ":" + ddSecond2);
				}
				if(minute2==0 && second2==0) {
					checkForWinner();
				}
			}
		});	
	}

	public void setBoard()
	{
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				if (pieces[i][j] != null)
				{
					board[i][j].setIcon(pieces[i][j].getIcon());
					if (board[i][j].getIcon() != null && pieces[i][j] == null)
					{
						board[i][j].setIcon(null);
						board[i][j].setBackground(dark);
					}
				}
				else if (board[i][j].getIcon() != null && pieces[i][j] == null)
				{
					board[i][j].setIcon(null);
					board[i][j].setBackground(dark);
				}
			}
		}
	}

	public boolean hasSelected()
	{
		for (JButton[] a : board)
		{
			for (JButton i : a)
			{
				if (i.getBackground() == selected)
				{
					return true;
				}
			}
		}
		return false;
	}

	public boolean hasJumper()
	{
		for (JButton[] a : board)
		{
			for (JButton i : a)
			{
				if (i.getBackground() == Color.BLUE)
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public int[] getSelectedSquare()
	{
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				if (board[i][j].getBackground() ==  selected)
				{
					int[] a = new int[2];
					a[0] = i;
					a[1] = j;
					return a;
				}
			}
		}
		return null;
	}

	public void checkForCaptures()
	{
		if (multipleCapture)
		{
			for (JButton[] a : board)
			{
				for (JButton button : a)
				{
					if (button.getBackground() == Color.BLUE)
					{
						button.setBackground(dark);
					}
				}
			}
			board[x][y].setBackground(Color.BLUE);
		}
		else{
			for (int i = 0; i < 8; i++)
			{
				for (int z = 0; z < 8; z++)
				{
					if (pieces[i][z] != null)
					{
						if (pieces[i][z].canCapture(pieces) == true)
						{	
							if (pieces[i][z].getColor() == Color.WHITE && turn % 2 == 0)
								board[i][z].setBackground(Color.BLUE);
							if (pieces[i][z].getColor() == Color.BLACK && turn % 2 == 1)
								board[i][z].setBackground(Color.BLUE);
						}
						else{
							board[i][z].setBackground(dark);
						}
					}
				}
			}
		}
	}

	public void freezeBoard()
	{
		for (JButton[] arr : board)
		{
			for (JButton elem : arr)
			{
				elem.removeActionListener(this);
			}
		}
	}

	public boolean canCapture()
	{
		for (Checker[] i : pieces)
		{
			for (Checker a : i)
			{
				if (a != null)
				{
					if (a.canCapture(pieces))
					{
						if (a.getColor() == Color.WHITE && turn % 2 == 0)
							return true;
						if (a.getColor() == Color.BLACK && turn % 2 == 1)
							return true;
					}
				}
			}
		}
		return false;
	}

	public void promote()
	{
		for (int i = 0; i < 8; i++)
		{
			if (pieces[0][i] instanceof Regular && (pieces[0][i]).getColor() == Color.WHITE)
			{
				pieces[0][i] = new King(Color.WHITE, 0, i);
			}
			if (pieces[7][i] instanceof Regular && (pieces[7][i]).getColor() == Color.BLACK)
			{
				pieces[7][i] = new King(Color.BLACK, 7, i);
			}
		}
	}

	public void checkForWinner()
	{
		if (minute == 0 && second == 0)
		{
			winnerText.setText("Black Wins!");
			freezeBoard();
			timer1.stop();
			return;
		}
		if (minute2 == 0 && second2 == 0)
		{
			winnerText.setText("White Wins!");
			freezeBoard();
			timer2.stop();
			return;
		}
		Color a;
		if (turn % 2 == 0)
		{
			a = Color.WHITE;
		}
		else{
			a = Color.BLACK;
		}
		for (Checker[] i : pieces)
		{
			for (Checker p : i)
			{
				if (p != null && p.getColor() == a)
				{
					if (p.isLocked(pieces) == false)
					{
						return;
					}
				}
			}
		}
		if (turn % 2 == 0)
		{
			winnerText.setText("Black Wins!");
		}
		else if (turn % 2 == 1)
		{
			winnerText.setText("White Wins!");
		}
		freezeBoard();
		timer1.stop();
		timer2.stop();
	}

	public void checkForDraw()
	{
		if (movesWithoutCapture >= 40)
		{
			winnerText.setText("Draw Game");
			for (JButton[] arr : board)
			{
				for (JButton elem : arr)
				{
					elem.removeActionListener(this);
				}
			}
		}
	}

	/**
	 * @param e 
	 */
	public void actionPerformed(ActionEvent e)
	{
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				if (e.getSource() == board[i][j])
				{
					// if the user intends to move a piece and has already selected a piece to move
					if (hasSelected())
					{
						// if the new position is the same square, it means the player does not want to move anymore
						// unselect the square
						if (board[i][j].getBackground() == selected)
						{
							board[i][j].setBackground(dark);
						}
						// wants to move to a different square
						else if (pieces[getSelectedSquare()[0]][getSelectedSquare()[1]] != null)
						{
							// gets the piece to move
							Checker a = pieces[getSelectedSquare()[0]][getSelectedSquare()[1]];
							// checks if it can capture
							if (a.canCapture(pieces))
							{
								if (a.canCaptureSquare(pieces, i, j))
								a.capture(pieces, i, j);
								try {
									if (pieces[i][j].canCapture(pieces) == false)
									{
										multipleCapture = false;
										turn++;
										movesWithoutCapture = 0;
										if (turn % 2 == 0)
										{
											timer1.start();
											timer2.stop();
										}
										else{
											timer2.start();
											timer1.stop();
										}
										checkForWinner();
									}
									else
									{
										multipleCapture = true;
										x = i;
										y = j;
									}
								} catch (Exception c) {
									//TODO: handle exception
								}
							}
								
							// only move if it can capture
							else if (a.canMove(pieces, i, j) && canCapture() == false)
							{
								// moves it
								a.move(pieces, i, j);
								turn++;
								movesWithoutCapture++;
								checkForDraw();
								if (turn % 2 == 0)
								{
									timer1.start();
									timer2.stop();
								}
								else{
									timer2.start();
									timer1.stop();
								}
								checkForWinner();
							}
						}
						promote();
						checkForCaptures();
						setBoard();
						
					}

					else if (pieces[i][j] != null)
					{
						if (turn % 2 == 0)
						{
							if (pieces[i][j].getColor() == Color.WHITE)
							{
								board[i][j].setBackground(selected);
							}
						}
						if (turn % 2 == 1)
						{								
							if (pieces[i][j].getColor() == Color.BLACK)
							{
								board[i][j].setBackground(selected);
							}
						}
					}
				}
			}
		}
		repaint();
	}
}

