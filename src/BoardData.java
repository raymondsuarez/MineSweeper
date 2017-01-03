
public class BoardData {

	int i, j, m, n;
	public boolean[][] bombs = new boolean[10+2][10+2];
	public int[][] count = new int[10+2][10+2];
	public int BLANK = 9;

	public void resetDataGrid(){
		for (int i = 0; i <= 11; i++)
			for (int j = 1; j <= 11; j++){
				bombs[i][j] = false;
				count[i][j] = 0;
			}
	}
	
	public void initBombs(int m, int n, double p)
	{

		for (int i = 1; i <= m; i++)
			for (int j = 1; j <= n; j++)
				bombs[i][j] = (Math.random() < p);

	}

	public void printBombs(int m, int n){

		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++)
				if (bombs[i][j]) System.out.print("* ");
				else             System.out.print(". ");
			System.out.println();
		}
	}

	public void initAdjacentBombCount(int m, int n){

		for (int i = 1; i <= m; i++){
			for (int j = 1; j <= n; j++){
				for (int ii = i - 1; ii <= i + 1; ii++){
					for (int jj = j - 1; jj <= j + 1; jj++){
						if (bombs[ii][jj]) count[i][j]++;
					}                 
				}
			}
		}
	}
	
	//Check to see the cell has an Adjacent Count value
	public boolean checkForNumber(int m, int n){
		if ( count[m][n] > 0 & count[m][n] < 9 & !bombs[m][n])
			return true ;
		return false;
	}

	//Check if the cell is unmarked
	public boolean checkForBlank(int m, int n){
		if ( count[m][n] == 0 )
			return true ;
		return false;
	}
	
	//Check if the cell has been marked as a blank
	public boolean checkForMarked(int m, int n){
		if ( count[m][n] == BLANK )
			return true ;
		return false;
	}

	//Return the count of surrounding bombs
	public int getAdjacentCount(int m, int n){
		return count[m][n];
	}

	//Check if cell is a bomb
	public boolean checkForBomb(int m, int n){
		if (bombs[m][n])
			return true;
		return false;
	}

	//Check if a blank is next to a bomb count cell
	public boolean blankNextToBombCount(int m, int n){

		if (m+1<12 & n+1<12 & m-1>=0 & n-1>=0){ //Boundary case
			if (count[m+1][n]==BLANK || count[m-1][n]==BLANK || count[m][n+1]==BLANK || count[m][n-1]==BLANK || count[m-1][n-1]==BLANK || count[m-1][n+1]==BLANK || count[m+1][n+1]==BLANK || count[m+1][n-1]==BLANK )
				{	
					return true;
				}
		}
		return false;
	}

	public void printAdjacentBombCount(int m, int n){
		System.out.println();
		for (int i = 1; i <= m; i++){
			for (int j = 1; j <= n; j++){
				if (bombs[i][j]) System.out.print("* ");
				else             System.out.print(count[i][j] + " ");
			}
			System.out.println();
		}
	}


	public void markRecursiveSurroundingBlanks(int m, int n){

		if (count[m][n]==0){
			count[m][n]=BLANK;
			if (count[m+1][n]==0 & m+1<11){ 			//below
				markRecursiveSurroundingBlanks(m+1,n);
			}
			if (count[m-1][n]==0 & 0 < m-1 ){ 			//above
				markRecursiveSurroundingBlanks(m-1,n);
			}
			if (count[m][n+1]==0 & n+1 < 11){ 			//right
				markRecursiveSurroundingBlanks(m,n+1);
			}
			if (count[m][n-1]==0 & 0 < n-1){ 			//left
				markRecursiveSurroundingBlanks(m,n-1);
			}
			if (count[m-1][n-1]==0 & 0 < m-1 & 0 < n-1){ //diag upper left
				markRecursiveSurroundingBlanks(m-1,n-1);
			}
			if (count[m-1][n+1]==0 & 0 < m-1 & n+1 <11){ //diag upper right
				markRecursiveSurroundingBlanks(m-1,n+1);
			}
			if (count[m+1][n+1]==0 & m+1 < 11 & n+1 < 11){ //diag lower right
				markRecursiveSurroundingBlanks(m+1,n+1);
			}
			if (count[m+1][n-1]==0 & m+1 < 11 & 0 < n-1){ //diag lower left
				markRecursiveSurroundingBlanks(m+1,n-1);
			}
			return;

		}
	} 
}
