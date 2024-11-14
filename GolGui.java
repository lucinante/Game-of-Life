/*
	Autore: zhao luca
	data: 30. maggio 2021
	oggetto: una classe che funge da interfaccia grafica di GolPanel
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;

class GolGui extends JFrame implements WindowListener{
	//content pane
	Container container;
	
	//game of life panel
	GolPanel gol;
	//
	JButton btn_tmp;
//
	public GolGui(Dimension dimFrame, boolean clickToRevolve, int cellSize, int initDensity, int revolveGapMillis, boolean toggleGrid,  Color colorBackground, Color colorGrid, Color colorCell){
		//frame initialize
		this.setSize(dimFrame.width, dimFrame.height);
		this.setLocationRelativeTo(null);
		container=getContentPane();
		
		//components initialize
		GolPanel gol=new GolPanel((int)(dimFrame.width*1.5), (int)(dimFrame.height*1.5), clickToRevolve,cellSize, initDensity, revolveGapMillis, toggleGrid,  colorBackground, colorGrid, colorCell);

		//components merge
		container.add(gol);
		
		//frame visibility
		setVisible(true);
		
	}
	
	public void windowActivated(WindowEvent we){}
	public void windowClosed(WindowEvent we){}
	public void windowDeactivated(WindowEvent we){}
	public void windowDeiconified(WindowEvent we){}
	public void windowIconified(WindowEvent we){}
	public void windowClosing(WindowEvent we){}
	public void windowOpened(WindowEvent we){}
	
//
	public static void main(String[] pippo){
		GolGui test = new GolGui(new Dimension(1300,700), false, 1, 55, 1, false, Color.BLACK, Color.GREEN, Color.WHITE);
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}