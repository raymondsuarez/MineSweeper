import java.awt.event.*;
import javax.swing.*;

class CustomMouseListener implements MouseListener{

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}	
}


public class MineSweeper {

	public static BoardData boarddata = new BoardData();
	public static BoardWindow boardwindow = new BoardWindow();
	public static MineSweeper minesweeper = new MineSweeper();
	public static long startTime;
	public static CustomMouseListener mouseListener = new CustomMouseListener();

	public void startListeners(){

		ActionListener timerEvent = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
//				System.out.println("actionPerformed(): Timer Event occurred.");
				boardwindow.setNewDate(startTime);
			}
		};
		new Timer(1000, timerEvent).start();
		startTime = System.currentTimeMillis();

		ActionListener btnListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				JToggleButton toggleBtn = (JToggleButton) evt.getSource();
				boardwindow.printRowCol( toggleBtn );
//				System.out.println("actionPerformed(): Left Button Pressed");

				if (!boardwindow.checkforResetButton()){

					int row = boardwindow.getRow() + 1;
					int col = boardwindow.getCol() + 1;

					if ( boarddata.checkForBlank(row, col) == true )
						minesweeper.showSurroundingBlanks(row, col);

					if ( boarddata.checkForNumber(row,col) == true )
						boardwindow.showNumber(row-1,col-1, boarddata.getAdjacentCount(row,col));

					if ( boarddata.checkForBomb(row, col) == true )
						minesweeper.showAllBombs(row, col);

					if (boarddata.checkForMarked(row, col) == true)
						boardwindow.showBlank(row-1, col-1);
				}else{
//					System.out.println("actionPerformed(): Pressed ResetButton");
					boardwindow.resetResetButton(2);
					startTime = System.currentTimeMillis();
					boarddata.resetDataGrid();
					boardwindow.resetButtonGrid();
					boarddata.initBombs(10, 10, .15);
//					boarddata.printBombs(10, 10);
					boarddata.initAdjacentBombCount(10, 10);
//					boarddata.printAdjacentBombCount(10, 10);    


				}
			}
		};

		CustomMouseListener mouseListener = new CustomMouseListener(){

			@Override
			public void mouseClicked(MouseEvent evt) {
				if (evt.getButton() == MouseEvent.BUTTON3){
//					System.out.println("mouseClicked(): Right Button Pressed");
					boardwindow.markButton(evt);
				}
			}

		};

		boardwindow.prepareGUI(btnListener, mouseListener);
	}


	private void showSurroundingBlanks(int row, int col){

//		System.out.printf("showSurroundingBlanks(): zero found: %d %d%n", row, col);  //   for debugging
		boarddata.markRecursiveSurroundingBlanks(row, col);
		for (int i=0; i<10; i++)
			for (int j=0; j<10; j++)
			{
				if (boarddata.checkForMarked(i+1, j+1)==true)
					boardwindow.showBlank(i, j);
				if (boarddata.blankNextToBombCount(i+1, j+1)==true & 
						boarddata.checkForMarked(i+1, j+1)==false & 
						!boarddata.checkForBomb(i+1, j+1))
					boardwindow.showNumber(i, j, boarddata.getAdjacentCount(i+1, j+1));
			}
//		boarddata.printAdjacentBombCount(10, 10);
	}

	private void showAllBombs(int row, int col){

		boardwindow.resetResetButton(1);
		boardwindow.showBomb(row-1,col-1);
		for (int k=0; k<10; k++) 
			for (int l=0; l<10; l++)
				if (boarddata.checkForBomb(k+1,l+1))
					boardwindow.showBomb(k,l);
	}

	public static void main(String args[]) 
	{ 
		int x = 10; 
		int y = 10; 
		double probability = 0.15;

		boarddata.initBombs(x, y, probability);
//		boarddata.printBombs(x, y);

		boarddata.initAdjacentBombCount(x, y);
//		boarddata.printAdjacentBombCount(x, y);    

		minesweeper.startListeners();


	}
}
