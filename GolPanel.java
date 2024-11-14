/*
	Autore: zhao luca
	data: 30. maggio 2021
	oggetto: una classe che simula Conway's Game of Life
*/
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.SwingUtilities;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;

class GolPanel extends JPanel implements ActionListener, MouseListener{
	int x, y;	//x e y di board
	int width, height;	//dimensione di board in quantità di celle
	
	int[][]board;
	int[][]nextBoard;	//lo stato precedente di board
	
	boolean started=true;
	
	int revolveCount;
	//di preferenza
	int cellSize;
	int initDensity;
	int revolveGapMillis;
	Color colorBackground, colorGrid, colorCell;
	boolean toggleGrid;
	boolean clickToRevolve;
	
//CONSTRUCTORS
	public GolPanel(int x, int y, boolean clickToRevolve, int cellSize, int initDensity, int revolveGapMillis, boolean toggleGrid, Color colorBackground, Color colorGrid, Color colorCell){
		this.x=x; this.y=y;
		
		//di preferenza
		this.cellSize=cellSize;
		this.initDensity=(100-initDensity);
		this.revolveGapMillis=revolveGapMillis;
		this.toggleGrid=toggleGrid;
		this.colorBackground=colorBackground;
		this.colorGrid=colorGrid;
		this.colorCell=colorCell;
		this.clickToRevolve=clickToRevolve;
		
		//initialization
		width=y/cellSize; height=x/cellSize;
		
		board=new int[height][width];
		nextBoard=new int[height][width];
		
		this.setSize(x,y);
		this.setLayout(null);
		this.setBackground(colorBackground);
		
		revolveCount=1;
		//mouse listener
		this.addMouseListener(this);
		
		//se non fosse click to revolve
		if(!clickToRevolve)
			new Timer(revolveGapMillis, this).start();
	}
	
//METHODS
	public void paintComponent(Graphics g){	//disegna ogni istanza di board
		super.paintComponent(g);
		
		if(toggleGrid)
			drawGrid(g);
		
		initializeBoard(g);
		displayBoard(g);
		
		((JFrame)SwingUtilities.getWindowAncestor(this)).setTitle("Generation: "+(revolveCount++));
//System.out.println(revolveCount+"");
	}
	
	private void drawGrid(Graphics g){	//disegna la griglia
		g.setColor(colorGrid);
		for(int i=0; i<height; i++){
			g.drawLine(0, (i*cellSize), x, (i*cellSize));	//disegna le righe
			g.drawLine((i*cellSize), 0, (i*cellSize), y);	//disegna colonne
		}
	}
	
	private void initializeBoard(Graphics g){	//inizializzazione di board
		if(started){
			started=false;
			for(int i=0; i<height;i++){
				for(int j=0; j<width;j++){
					if( ((int)(Math.random()*100))> initDensity)
						nextBoard[i][j]=1;
					else
						nextBoard[i][j]=0;
				}
			}
		}
	}
	
	private void displayBoard(Graphics g){	//disegna i rettangoli che rappresentano le cellule
		g.setColor(colorCell);
		
		copyArray();
		
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				if(nextBoard[i][j]==1){
					g.fillRect(i*cellSize, j*cellSize, cellSize, cellSize);
				}
			}
		}
	}
	
	private void copyArray(){	//metodo che copia il contenuto di board precedente in board presente
		for(int i=0; i<height;i++){
			for(int j=0; j<width;j++){
				board[i][j]=nextBoard[i][j];
			}
		}
		
	}
	
	private int neighborsOf(int i, int j){	//restituisce quantità di vicini vivi
		int aliveNeighbors=0;
		
		//per i neighbor sottostanti
		aliveNeighbors+=board[(i +height-1) % height] [(j+width-1) % width];
		aliveNeighbors+=board[(i+height) % height] [(j+width-1) % width];
		aliveNeighbors+=board[(i+height+1) % height] [(j+width-1) % width];
		//per i vicini in mezzo
		aliveNeighbors+=board[(i+height-1) % height] [(j+width) % width];
		aliveNeighbors+=board[(i+height+1) % height] [(j+width) % width];
		//per i vicini soprastanti
		aliveNeighbors+=board[(i+height-1) % height] [(j+width+1) % width];
		aliveNeighbors+=board[(i+height) % height] [(j+width+1) % width];
		aliveNeighbors+=board[(i+height+1) % height] [(j+width+1) % width];
		
		return aliveNeighbors;
	}
	
	//override
	public void actionPerformed(ActionEvent e){	//activated by Timer
		int alive;
		
		for(int i=0; i<height;i++){
			for(int j=0; j<width;j++){
				alive=neighborsOf(i,j);
				//le condizioni
				if((alive==3) || (alive==2 && board[i][j]==1))
					nextBoard[i][j]=1;
				else 
					nextBoard[i][j]=0;
			}
		}
		
		repaint();	//reinvokes method paintComponent
	}
	
//MOUSE EVENTS
	public void mousePressed(MouseEvent me){}
	public void mouseExited(MouseEvent me){}
	public void mouseEntered(MouseEvent me){}
	public void mouseReleased(MouseEvent me){}
	public void mouseClicked(MouseEvent me){
		if(clickToRevolve){
			int alive;
		
			for(int i=0; i<height;i++){
				for(int j=0; j<width;j++){
					alive=neighborsOf(i,j);
					//CONDIZIONI DI CONWAY
					//le condizioni
					if((alive==3) || (alive==2 && board[i][j]==1))
						nextBoard[i][j]=1;
					else 
						nextBoard[i][j]=0;
				}
			}
			
			repaint();	//reinvokes method paintComponent
		}
	}
}