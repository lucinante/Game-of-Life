/*
	Autore: zhao luca
	data: 30. maggio 2021
	oggetto: una classe che funge da Launcher di GolGui
*/
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.util.Hashtable;
import java.util.StringTokenizer;

class GolLauncher extends JFrame implements ActionListener, ChangeListener, WindowListener, ItemListener, DocumentListener{
	final String SETTINGSFILENAME="GolLauncherSettings.txt";
	Container container;
	//attributi relativi alla gui di label
	JPanel pnl_title;
	
	//attributi relativi alla gui di panello d'impostazioni
	JScrollPane scrollPane;
	JPanel pnl_settings;
		//labels
	JLabel lbl_cellSize;
	JLabel lbl_density;
	JLabel lbl_revolveGapMillis;
	JLabel lbl_toggleGrid;
	JLabel lbl_dimFrame;
	JLabel lbl_color;
	JLabel lbl_clickToRevolve;
			//secondary labels
	JLabel lbl_util_width, lbl_util_height, lbl_util_background, lbl_util_grid, lbl_util_cell;
		//general
	JSlider slide_cellSize;	JLabel lbl_slide_cellSize;
	JSlider slide_density;	JLabel lbl_slide_density;
	JSlider slide_revolveGapMillis; JLabel lbl_slide_revolveGapMillis;
	JCheckBox check_toggleGrid;
	JCheckBox check_clickToRevolve;
		//frame dim
	JPanel pnl_dimFrame;
	JTextField text_frameHeight;
	JTextField text_frameWidth;
	
		//colors
	JPanel pnl_colors;
	JButton btn_colorBackground, btn_colorGrid, btn_colorCell;
	
	//attributi relativi alla gui di panello di bottoni
	JPanel pnl_buttons;
	JButton btn_launch;
	
	//Attributi relativi a mangable settings
	int cellSize;
	int density;
	int revolveGapMillis;
	boolean toggleGrid;
	boolean clickToRevolve;
	Dimension dimFrame;
	Color colorBackground, colorGrid, colorCell;
	
//
	GolLauncher(){
		//per rendere visibile il window per triggerare window listener
		this.setVisible(true);
	//initialization
		initialize();
	//merge
		merge();
	//color theme
		setColorTheme();
	//frame visibility
		this.setVisible(true);
	}
//	
	public void addToolTipTexts(){
		lbl_clickToRevolve.setToolTipText("may slow down your pc with larger board dimensions");
		lbl_density.setToolTipText("may cause overpopulation with a high initial density");
	}
	
	private void addHashtables(){
		Hashtable<Integer, JLabel> table_slide_cellSize, table_slide_density, table_slide_revolveGapMillis;
	//
		table_slide_cellSize=new Hashtable<Integer, JLabel>();
		table_slide_density=new Hashtable<Integer, JLabel>();
		table_slide_revolveGapMillis=new Hashtable<Integer, JLabel>();
	//
		JLabel lbl1, lbl2, lbl3, lbl4, lbl5, lbl6;
		lbl1=new JLabel("1");
		lbl2=new JLabel("10  ");
		lbl3=new JLabel("0");
		lbl4=new JLabel("100   ");
		lbl5=new JLabel("1");
		lbl6=new JLabel("1000  ");
		
		Color c5=new Color(45,20,44);	//darkest
		
		lbl1.setOpaque(true);	lbl1.setForeground(Color.WHITE);
		lbl2.setOpaque(true);	lbl2.setForeground(Color.WHITE);
		lbl3.setOpaque(true);	lbl3.setForeground(Color.WHITE);
		lbl4.setOpaque(true);	lbl4.setForeground(Color.WHITE);
		lbl5.setOpaque(true);	lbl5.setForeground(Color.WHITE);
		lbl6.setOpaque(true);	lbl6.setForeground(Color.WHITE);
		
		lbl1.setBackground(c5);
		lbl2.setBackground(c5);
		lbl3.setBackground(c5);
		lbl4.setBackground(c5);
		lbl5.setBackground(c5);
		lbl6.setBackground(c5);
		//
		table_slide_cellSize.put(1, lbl1);
		table_slide_cellSize.put(5, lbl_slide_cellSize);
		table_slide_cellSize.put(9, lbl2);
		
		table_slide_density.put(0, lbl3);
		table_slide_density.put(50, lbl_slide_density);
		table_slide_density.put(100, lbl4);
		
		table_slide_revolveGapMillis.put(0, lbl5);
		table_slide_revolveGapMillis.put(500, lbl_slide_revolveGapMillis);
		table_slide_revolveGapMillis.put(1000, lbl6);
	//
		slide_cellSize.setPaintLabels(true);
		slide_density.setPaintLabels(true);
		slide_revolveGapMillis.setPaintLabels(true);
		
		slide_cellSize.setLabelTable(table_slide_cellSize);
		slide_density.setLabelTable(table_slide_density);
		slide_revolveGapMillis.setLabelTable(table_slide_revolveGapMillis);
	}
	
	private void setFonts(){
		//
		Font f1=new Font("Monospaced", Font.BOLD, 20);
		lbl_cellSize.setFont(f1);
		lbl_density.setFont(f1);
		lbl_revolveGapMillis.setFont(f1);
		lbl_toggleGrid.setFont(f1);
		lbl_dimFrame.setFont(f1);
		lbl_color.setFont(f1);
		lbl_clickToRevolve.setFont(f1);
		lbl_slide_cellSize.setFont(f1);
		lbl_slide_density.setFont(f1);
		lbl_slide_revolveGapMillis.setFont(f1);
		lbl_util_width.setFont(f1);
		lbl_util_height.setFont(f1);
		lbl_util_background.setFont(f1);
		lbl_util_grid.setFont(f1);
		lbl_util_cell.setFont(f1);
		text_frameHeight.setFont(f1);
		text_frameWidth.setFont(f1);
		//
		Font f2=new Font("Monospaced", Font.BOLD, 40);
		btn_launch.setFont(f2);
		//

	}
	
	private void setColorTheme(){
		Color c5=new Color(45,20,44);	//darkest
		Color c4=new Color(81,10,50);
		Color c3=new Color(128,19,54);
		Color c2=new Color(199,44,65);
		Color c1=new Color(238,69,64);	//lightest
		
	//background
		pnl_settings.setBackground(c5);
		//panels background
		check_clickToRevolve.setBackground(c5);
		check_toggleGrid.setBackground(c5);
		pnl_dimFrame.setOpaque(true);	pnl_dimFrame.setBackground(c5);
		pnl_colors.setOpaque(true);	pnl_colors.setBackground(c5);
		slide_cellSize.setOpaque(true);	slide_cellSize.setBackground(c5);
		slide_density.setOpaque(true);	slide_density.setBackground(c5);
		slide_revolveGapMillis.setOpaque(true);	slide_revolveGapMillis.setBackground(c5);
		//text background
		lbl_clickToRevolve.setOpaque(true);	lbl_clickToRevolve.setBackground(c5);
		lbl_toggleGrid.setOpaque(true);	lbl_toggleGrid.setBackground(c5);
		lbl_dimFrame.setOpaque(true);	lbl_dimFrame.setBackground(c5);
		lbl_clickToRevolve.setOpaque(true);	lbl_clickToRevolve.setBackground(c5);
		lbl_color.setOpaque(true);	lbl_color.setBackground(c5);
		lbl_cellSize.setOpaque(true);	lbl_cellSize.setBackground(c5);
		lbl_density.setOpaque(true);	lbl_density.setBackground(c5);
		lbl_revolveGapMillis.setOpaque(true);	lbl_revolveGapMillis.setBackground(c5);
		lbl_util_width.setOpaque(true);	lbl_util_width.setBackground(c5);
		lbl_util_height.setOpaque(true);	lbl_util_height.setBackground(c5);
		lbl_util_background.setOpaque(true);	lbl_util_background.setBackground(c5);
		lbl_util_grid.setOpaque(true);	lbl_util_grid.setBackground(c5);
		lbl_util_cell.setOpaque(true);	lbl_util_cell.setBackground(c5);
		lbl_slide_cellSize.setOpaque(true);	lbl_slide_cellSize.setBackground(c5);
		lbl_slide_density.setOpaque(true);	lbl_slide_density.setBackground(c5);
		lbl_slide_revolveGapMillis.setOpaque(true);	lbl_slide_revolveGapMillis.setBackground(c5);
	//texts
		lbl_clickToRevolve.setForeground(c1);
		lbl_toggleGrid.setForeground(c1);
		lbl_dimFrame.setForeground(c1);
		lbl_clickToRevolve.setForeground(c1);
		lbl_color.setForeground(c1);
		lbl_cellSize.setForeground(c1);
		lbl_density.setForeground(c1);
		lbl_revolveGapMillis.setForeground(c1);
		lbl_util_width.setForeground(c1);
		lbl_util_height.setForeground(c1);
		lbl_util_background.setForeground(c1);
		lbl_util_grid.setForeground(c1);
		lbl_util_cell.setForeground(c1);
		lbl_slide_cellSize.setForeground(c1);
		lbl_slide_density.setForeground(c1);
		lbl_slide_revolveGapMillis.setForeground(c1);
	//button settings background
		pnl_buttons.setBackground(c4);
		btn_launch.setBackground(c3);
		btn_launch.setForeground(Color.WHITE);
	//text areas
		text_frameWidth.setOpaque(true);	text_frameWidth.setBackground(c3);
		text_frameHeight.setOpaque(true);	text_frameHeight.setBackground(c3);
		
		text_frameWidth.setForeground(Color.WHITE);
		text_frameHeight.setForeground(Color.WHITE);
	}
	
	private void addListeners(){
		//change listener & document listener
		check_clickToRevolve.addItemListener(this);
		check_toggleGrid.addItemListener(this);
		slide_cellSize.addChangeListener(this);
		slide_density.addChangeListener(this);
		slide_revolveGapMillis.addChangeListener(this);
		
		text_frameWidth.getDocument().addDocumentListener(this);
		text_frameHeight.getDocument().addDocumentListener(this);
		
		//action listeners
		btn_launch.addActionListener(this);
		btn_colorBackground.addActionListener(this);
		btn_colorGrid.addActionListener(this);
		btn_colorCell.addActionListener(this);
	}
//INITIALIZATION
	private void initialize(){
		
	//frame & contentpane
		this.addWindowListener(this);
		this.setSize(1000, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Game of Life Launcher v1.0.0");
		this.setVisible(true);
		container=this.getContentPane();
	//utility labels
		lbl_util_width=new JLabel("Width: ", JLabel.RIGHT);
		lbl_util_height=new JLabel("Height: ", JLabel.RIGHT);
		lbl_util_background=new JLabel("Background: ", JLabel.RIGHT);
		lbl_util_cell=new JLabel("Cell: ", JLabel.RIGHT);
		lbl_util_grid=new JLabel("Grid:", JLabel.RIGHT);
	//setting panel
		pnl_settings=new JPanel();
		scrollPane=new JScrollPane(pnl_settings);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
			//click to revolve
		lbl_clickToRevolve=new JLabel("Click to revolve [*] : ");
		check_clickToRevolve=new JCheckBox("",clickToRevolve);
			//colors
		lbl_color=new JLabel("Colors: ");
		btn_colorBackground=new JButton("");
		btn_colorGrid=new JButton("");
		btn_colorCell=new JButton("");
		
				//set size
		btn_colorBackground.setPreferredSize(new Dimension(20,20));
		btn_colorGrid.setPreferredSize(new Dimension(20,20));
		btn_colorCell.setPreferredSize(new Dimension(20,20));
				
				//set colors
		btn_colorBackground.setOpaque(true);
		btn_colorGrid.setOpaque(true);
		btn_colorCell.setOpaque(true);
		
		btn_colorBackground.setBackground(colorBackground);
		btn_colorGrid.setBackground(colorGrid);
		btn_colorCell.setBackground(colorCell);
		
			//frame dimension
		pnl_dimFrame=new JPanel();
		pnl_dimFrame.setLayout(new FlowLayout());
		lbl_dimFrame=new JLabel("Frame size/board size: ");
		
		text_frameHeight=new JTextField();
		text_frameWidth=new JTextField();
		
		text_frameHeight.setText(""+((int)dimFrame.getHeight()));
		text_frameWidth.setText(""+((int)dimFrame.getWidth()));
		
		text_frameHeight.setPreferredSize(new Dimension(100,20));
		text_frameWidth.setPreferredSize(new Dimension(100,20));
	
			//value settings
		lbl_cellSize=new JLabel("Cell size: ");
		lbl_density=new JLabel("Initial density [*] : ");
		lbl_revolveGapMillis=new JLabel("Revolve time [ms]: ");
		lbl_toggleGrid=new JLabel("Toggle grid: ");
		
		slide_cellSize=new JSlider(JSlider.HORIZONTAL, 1, 9, cellSize);	lbl_slide_cellSize=new JLabel(""+slide_cellSize.getValue());	slide_cellSize.addChangeListener(this);
		slide_density=new JSlider(JSlider.HORIZONTAL, 0, 100, density); lbl_slide_density=new JLabel(""+slide_density.getValue());	slide_density.addChangeListener(this);
		slide_revolveGapMillis=new JSlider(JSlider.HORIZONTAL, 0, 1000, revolveGapMillis); lbl_slide_revolveGapMillis=new JLabel(""+slide_revolveGapMillis.getValue());	slide_revolveGapMillis.addChangeListener(this);
		check_toggleGrid= new JCheckBox("", toggleGrid);
		
	//button panel
		pnl_buttons=new JPanel();
		btn_launch=new JButton("LAUNCH");
	//listeners
		addListeners();
	//font
		setFonts();
	//hashtables
		addHashtables();
	//tooltiptexts
		addToolTipTexts();
	}
//MERGE
	private void merge(){
	//layouts
		container.setLayout(new BorderLayout());
		pnl_settings.setLayout(new GridLayout(9,2, 10,10));
		pnl_buttons.setLayout(new GridLayout(1,3));
	//setting panel
			//BLANK
		pnl_settings.add(new JLabel(""));
		pnl_settings.add(new JLabel(""));
			//click to revolve
		pnl_settings.add(lbl_clickToRevolve);
		pnl_settings.add(check_clickToRevolve);
			//toggle grid
		pnl_settings.add(lbl_toggleGrid);
		pnl_settings.add(check_toggleGrid);

			//dim frame
		pnl_dimFrame.add(lbl_util_width);
		pnl_dimFrame.add(text_frameWidth);
		pnl_dimFrame.add(lbl_util_height);
		pnl_dimFrame.add(text_frameHeight);
		pnl_settings.add(lbl_dimFrame);
		pnl_settings.add(pnl_dimFrame);
			//color selection
		pnl_colors=new JPanel();
		pnl_colors.setLayout(new FlowLayout());
		
		pnl_colors.add(lbl_util_background);
		pnl_colors.add(btn_colorBackground);
		pnl_colors.add(lbl_util_grid);
		pnl_colors.add(btn_colorGrid);
		pnl_colors.add(lbl_util_cell);
		pnl_colors.add(btn_colorCell);
		
		pnl_settings.add(lbl_color);
		pnl_settings.add(pnl_colors);

			//VALUE SETTINGS
		pnl_settings.add(lbl_cellSize);
		pnl_settings.add(slide_cellSize);
		pnl_settings.add(lbl_density);
		pnl_settings.add(slide_density);
		pnl_settings.add(lbl_revolveGapMillis);
		pnl_settings.add(slide_revolveGapMillis);
		
	//button panel
		pnl_buttons.add(new JLabel(""));
		pnl_buttons.add(btn_launch);
		pnl_buttons.add(new JLabel(""));
	//container
		container.add(scrollPane, BorderLayout.CENTER);
		container.add(pnl_buttons, BorderLayout.SOUTH);
	}
	
//save & load
	private void saveLastSettings(){
		FileWriter fw;
		PrintWriter fOut;
		
		try{
			fw=new FileWriter("./"+SETTINGSFILENAME,false);
			fOut=new PrintWriter(fw);
			//
			fOut.println(cellSize);
			fOut.println(density);
			fOut.println(revolveGapMillis);
			fOut.println(toggleGrid);	System.out.println("TOGGLE GRID VAL:"+toggleGrid);
			fOut.println(clickToRevolve);
			
			fOut.println(dimFrame.width);
			fOut.println(dimFrame.height);
			//coloori
			fOut.println(colorBackground.getRGB());
			fOut.println(colorGrid.getRGB());
			fOut.println(colorCell.getRGB());
//System.out.println("SAVED COLOR BACKGROUND: "+colorBackground.getRed()+" "+colorBackground.getGreen()+" "+colorBackground.getBlue());	
//System.out.println("\t\tCOLOR GRID: "+colorGrid.getRed()+" "+colorGrid.getGreen()+" "+colorGrid.getBlue());
//System.out.println("\t\tCOLOR CELL: "+colorCell.getRed()+" "+colorCell.getGreen()+" "+colorCell.getBlue());
			
		//
			fOut.flush();
			fw.close();
//System.out.println("CHANGES SAVED");
		}catch(Exception e){
			///////////////////////////////OPTIONPANE SHOWMESSAGEDIALOG
			}
	}
	
	private void loadLastSettings(){
		FileReader fr;
		BufferedReader fIn;
		StringTokenizer st;
		try{
			fr=new FileReader("./"+SETTINGSFILENAME);
			fIn=new BufferedReader(fr);
			int width, height;
			int red,green,blue;

			cellSize=Integer.parseInt(fIn.readLine());
			density=Integer.parseInt(fIn.readLine());
			revolveGapMillis=Integer.parseInt(fIn.readLine());
			toggleGrid=Boolean.parseBoolean(fIn.readLine());
			clickToRevolve=Boolean.parseBoolean(fIn.readLine());
			
			height=Integer.parseInt(fIn.readLine());
			width=Integer.parseInt(fIn.readLine());
			dimFrame=new Dimension(height,width);


			//colori di background grid e cell
			colorBackground=new Color(Integer.parseInt(fIn.readLine()));
//System.out.println("LOAD SETTINGS COLOR BACKG: "+colorBackground.getRed()+" "+colorBackground.getGreen()+" "+colorBackground.getBlue());
			colorGrid=new Color(Integer.parseInt(fIn.readLine()));
//System.out.println("\t\tCOLOR GRID: "+colorGrid.getRed()+" "+colorGrid.getGreen()+" "+colorGrid.getBlue());
			colorCell=new Color(Integer.parseInt(fIn.readLine()));
//System.out.println("\t\tCOLOR CELL: "+colorCell.getRed()+" "+colorCell.getGreen()+" "+colorCell.getBlue());

			fr.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
//LISTENERS
	//ACTION LISTENERS
	//override
	public void actionPerformed(ActionEvent ae){
		
	//colors
		if(ae.getActionCommand().equals("")){
			//change button color
			((JButton)ae.getSource()).setBackground(new JColorChooser().showDialog(null,"Pick a color",Color.BLACK));
			
			colorBackground=btn_colorBackground.getBackground();
			colorGrid=btn_colorGrid.getBackground();
			colorCell=btn_colorCell.getBackground();
//System.out.println("ACTION PERFORMED COLOR BACKGROUND: "+colorBackground.getRed()+" "+colorBackground.getGreen()+" "+colorBackground.getBlue());	
//System.out.println("\tCOLOR GRID: "+colorGrid.getRed()+" "+colorGrid.getGreen()+" "+colorGrid.getBlue());
//System.out.println("\tCOLOR CELL: "+colorCell.getRed()+" "+colorCell.getGreen()+" "+colorCell.getBlue());
		}
		
	//launch
		if(ae.getActionCommand().equals("LAUNCH")){
			try{	
				cellSize=slide_cellSize.getValue();
				density=slide_density.getValue();
				revolveGapMillis=slide_revolveGapMillis.getValue();
				toggleGrid=check_toggleGrid.isSelected();
				clickToRevolve=check_clickToRevolve.isSelected();
				
				//modificarlo a farlo eseguire in new process
				new GolGui(dimFrame, clickToRevolve, cellSize, density, revolveGapMillis, toggleGrid, btn_colorBackground.getBackground(), btn_colorGrid.getBackground(), btn_colorCell.getBackground() );
				
			}catch(Exception e){
				JOptionPane.showMessageDialog(this, "Incorrect frame dimension, please insert integer only!");
			}
		}
		
	}
	//CHANGE LISTENER
	//override
	public void stateChanged(ChangeEvent ce){
		//cambiare tutti i valori di variabili quando c'è un change
		cellSize=slide_cellSize.getValue();
		density=slide_density.getValue();
		revolveGapMillis=slide_revolveGapMillis.getValue();

		//per sliders
		JSlider tmp=(JSlider)ce.getSource();
		int val=tmp.getValue();
		if(tmp==slide_cellSize)
			lbl_slide_cellSize.setText(String.format("%d",val));
		else if(tmp==slide_density)
			lbl_slide_density.setText(String.format("%d",val));
		else
			lbl_slide_revolveGapMillis.setText(String.format("%d",val));
		
	}
	//WINDOW LISTENER
	public void windowActivated(WindowEvent we){}
	public void windowClosed(WindowEvent we){}
	public void windowDeactivated(WindowEvent we){}
	public void windowDeiconified(WindowEvent we){}
	public void windowIconified(WindowEvent we){}
	
	public void windowClosing(WindowEvent we){
		this.saveLastSettings();
//System.out.println("saved");
	}
	
	public void windowOpened(WindowEvent we){
		boolean hasSettings=false;
		String[] files=new File("./").list();
		
		for(int i=0;i<files.length && !hasSettings ;i++){
			if(files[i].equals(SETTINGSFILENAME)){
				hasSettings=true;
			}
		}
//System.out.println("HASSETTINGS: "+hasSettings);
		//IF FILE EXISTS THEN LOAD ELSE INITIALIZE
		if(hasSettings){
			this.loadLastSettings();
//System.out.println("ENTERED");
		}else{
//System.out.println("NOT ENTERED");
			cellSize=1;
			density=50;
			revolveGapMillis=1;
			toggleGrid=false;
			clickToRevolve=false;
			dimFrame=new Dimension(1300,700);
			colorBackground=Color.WHITE;
			colorGrid=Color.GREEN;
			colorCell=Color.BLACK;
		}
			
//System.out.println("back:"+colorBackground.getRed());
//System.out.println("grid:"+colorGrid.getRed());
//System.out.println("cell:"+colorCell.getRed());
	}
	//DOCUMENT LISTENER
	public void changedUpdate(DocumentEvent e){textChangedWarn();}
	public void removeUpdate(DocumentEvent e){}
	public void insertUpdate(DocumentEvent e){textChangedWarn();}
	public void textChangedWarn(){
		dimFrame=new Dimension(Integer.parseInt(text_frameWidth.getText()),Integer.parseInt(text_frameHeight.getText()));
	}
//
	public static void main(String[] obamas){
		new GolLauncher();
	}
	
	//ITEM LISTENER
	public void itemStateChanged(ItemEvent e){
//System.out.println("ITEM CHANGED");
		//cambiare tutti i valori di variabili quando c'è un change;
		toggleGrid=check_toggleGrid.isSelected();
		clickToRevolve=check_clickToRevolve.isSelected();
	}

}