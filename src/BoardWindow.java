import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.*;
import java.util.*;

public class BoardWindow
{
	private JFrame gameWindow; 
	private JPanel controlPanel = new JPanel();
	private JPanel boardPanel = new JPanel();
	private JToggleButton[][] buttonGrid = new JToggleButton[10][10];
	private int COL, ROW;
	private JToggleButton resetButton = new JToggleButton();
	private JLabel label1 = new JLabel("", JLabel.CENTER);
	private JLabel label2 = new JLabel("", JLabel.CENTER);
	private SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
	private int flagcount = 10;
	private String time;
	private String flagcountstr = String.format("%04d", 10); 

		
	public void prepareGUI(ActionListener btnListener, MouseListener mouseListener)
	{
		
		//Pretty window
		JFrame.setDefaultLookAndFeelDecorated(true);
		
		//Create Window and major panels
		ImageIcon javaIcon1;
		
		
		gameWindow = new JFrame("Mine Sweeper by Dad");
		gameWindow.setSize(new Dimension(450,450));
		gameWindow.setResizable(false);
	    gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Create Control Panel, button, timer and points objects
		gameWindow.add(controlPanel, BorderLayout.NORTH);
		label1.setOpaque(true);
		label1.setBackground(Color.BLACK);
		label1.setForeground(Color.RED);
		label1.setFont(new Font("sans-serif", Font.PLAIN, 30));
		label1.setFont(new Font("sans-serif", Font.PLAIN, 30));
		label1.setText(flagcountstr);
		
		javaIcon1 = new ImageIcon(getClass().getResource("/java.png"));
		resetButton.setIcon(javaIcon1);
		resetButton.setPreferredSize(new Dimension(40, 40));

		label2.setOpaque(true);
		label2.setBackground(Color.BLACK);
		label2.setForeground(Color.RED);
		
		time = formatter.format( 0 );
		label2.setFont(new Font("sans-serif", Font.PLAIN, 30));
		label2.setText(time);

		controlPanel.setLayout(new FlowLayout( FlowLayout.CENTER, 75, 12));
		controlPanel.add( label1 );
		controlPanel.add(resetButton);
		controlPanel.add( label2 );
		
		resetButton.addActionListener(btnListener);

		//Create Board Panel with the grid
		gameWindow.add(boardPanel, BorderLayout.SOUTH);
		boardPanel.setPreferredSize(new Dimension(450,350)); 

		boardPanel.setLayout(new GridLayout(10, 10));
		for (int row = 0; row < buttonGrid.length; row++) {
			for (int col = 0; col < buttonGrid[row].length; col++) {		
				buttonGrid[row][col] = new JToggleButton();
				buttonGrid[row][col].setText(""); 
				buttonGrid[row][col].setFont(new Font("sans-serif", Font.BOLD, 16));
				buttonGrid[row][col].addActionListener(btnListener);
				buttonGrid[row][col].addMouseListener(mouseListener);
				boardPanel.add(buttonGrid[row][col]);
			}
		}
		gameWindow.setVisible(true); 
	}

	public void setNewDate(long startTime){

		long elapsedTime = (new Date()).getTime() - startTime;
	    label2.setText(formatter.format(elapsedTime));
	}
	
	public int getRow(){
		return ROW;
	}

	public int getCol(){
		return COL;
	}

	public void markButton(MouseEvent evt){

		ImageIcon flagIcon;
		flagIcon = new ImageIcon(getClass().getResource("/flag.png"));

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)                 
				if (buttonGrid[i][j] == evt.getSource() & !buttonGrid[i][j].getModel().isSelected() ){
					buttonGrid[i][j].setSelected(false);
					buttonGrid[i][j].setIcon(flagIcon);
					flagcountstr = String.format("%04d", --flagcount);
					label1.setText(flagcountstr);
				}			
	}


	public void resetResetButton(int i){
		ImageIcon javaIcon;
		if ( i == 1){
			javaIcon = new ImageIcon(getClass().getResource("/shifty.gif"));
		}else{
			javaIcon = new ImageIcon(getClass().getResource("/java.png"));
			flagcount = 10;
			label1.setText("0010");
		}
		resetButton.setIcon(javaIcon);
		
	}
	
	public void showBomb(int m, int n){

		ImageIcon bombIcon;
		bombIcon = new ImageIcon(getClass().getResource("/bomb.png"));

		buttonGrid[m][n].setSelected(true);
		buttonGrid[m][n].setIcon( null );
		buttonGrid[m][n].setIcon(bombIcon);
	} 

	public void resetButtonGrid(){

		time = formatter.format( 0 );
		label2.setText(time);
		for (int m=0; m<10; m++)
			for (int n=0; n<10; n++){
				buttonGrid[m][n].setSelected(false);
				buttonGrid[m][n].setIcon( null );
				buttonGrid[m][n].setText("");
			}
			resetButton.setSelected(false);
			
	}
	
	
	public void showBlank(int m, int n){

		buttonGrid[m][n].setSelected(true);
		buttonGrid[m][n].setIcon( null );
		buttonGrid[m][n].setText("");
	}

	public void showNumber(int m, int n, int adjcount){

		String text = String.format("%d", adjcount);
		
		switch (adjcount){
		
		case 1: buttonGrid[m][n].setForeground(Color.BLUE);
				break;
		case 2: buttonGrid[m][n].setForeground(Color.GREEN);
				break;
		case 3: buttonGrid[m][n].setForeground(Color.RED);
				break;
		case 4: buttonGrid[m][n].setForeground(Color.GRAY);
				break;
		case 5: buttonGrid[m][n].setForeground(Color.MAGENTA);
				break;
		case 6: buttonGrid[m][n].setForeground(Color.ORANGE);
				break;
		case 7: buttonGrid[m][n].setForeground(Color.PINK);
				break;
		case 8: buttonGrid[m][n].setForeground(Color.YELLOW);
				break;
		}
		buttonGrid[m][n].setSelected(true);
		buttonGrid[m][n].setIcon( null );
		buttonGrid[m][n].setText(text);
		
	}

	public boolean checkforResetButton(){ 
		if (resetButton.getModel().isSelected() )
			return true;
		else return false;
	}
	public void printRowCol(JToggleButton toggleBtn){

		for (int row = 0; row < buttonGrid.length; row++) {
			for (int col = 0; col < buttonGrid[row].length; col++) {
				if (buttonGrid[row][col] == toggleBtn) {
					ROW = row;
					COL = col;
					//  System.out.printf("Selected row and column: %d %d%n", row, col);
				}
			}
		}
	} 
}


