package ua.ho.gloryofrobots.flutelator;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.GroupLayout.*;
import java.util.regex.*;
//import java.applet.*;

import java.util.*;
//import java.net.*;
//import java.io.*;

import java.util.Random;

import ua.ho.gloryofrobots.flutelator.MakeFlute;

/* This is very pretty java gui shit code example. */
/*don`t write everything like this*/

class LanguageData{
	
	Map<String,String> data=new HashMap<String,String>();
	public String[] tableHead={"hole type" ,"frequency"  ,"diameter"
					, "<html><body>calculated distance <br />from end of flute</body></html>"
					  ,"finger spacing"};//,"local cutoff frequency"
	
	LanguageData(){
		data.put("label1","Measurement in");
		data.put("unit_inch","inches");
		data.put("unit_cm","cm");
		data.put("label2","Flute attributes");
		data.put("id_text","inside diametr");
		data.put("wt_text","wall thickness");
		data.put("button_add_hole","Add Hole");
		data.put("label3"," enter count of holes");
		data.put("label4","choose root note");
		data.put("sheet_button_text","make sheet");
		data.put("calc_button_text","Calculate");
		data.put("emb_label","embouchure");
		data.put("input_empty","Please, check the fields highlighted by red!");
		data.put("end_label","all closed");
		}
		String get(String key){
			return data.get(key);
			}
	}
class LanguageDataRu extends LanguageData{
	
	
	public String[] tableHead={"Тип отв." ,"Частота"  ,"Диаметр"
					, "<html><body>Расстояние <br />от конца флейты</body></html>"
					  ,"Раст. между пальцами  "};//,"local cutoff frequency"
	
	LanguageDataRu(){
		data.put("label1","Measurement in");
		data.put("unit_inch","inches");
		data.put("unit_cm","cm");
		data.put("label2","Flute attributes");
		data.put("id_text","inside diametr");
		data.put("wt_text","wall thickness");
		data.put("button_add_hole","Add Hole");
		data.put("label3"," enter count of holes");
		data.put("label4","choose root note");
		data.put("sheet_button_text","make sheet");
		data.put("calc_button_text","Calculate");
		data.put("emb_label","embouchure");
		data.put("input_empty","Please, check the fields highlighted by red!");
		data.put("end_label","all closed");
		}
		String get(String key){
			return data.get(key);
			}
	}

public class Flutelator extends JFrame{
	private static final long serialVersionUID = 1L;
	
	protected Color GuiColor=new Color(255,255,255);
	protected int defaultCountHoles=5;
	protected double defaultWallThickness=0.2;
	protected double defaultInsideDiametr=1;
	protected int defaultRootMidi=62;
	protected MidiKeys midikeys;
	protected int sizeX=1000;
	protected int sizeY=700;
	
	protected JLabel label2,label1,id_text,wt_text,label3,label4;
	protected Choice countHolesChoice,rootNoteChoice;
	protected JButton fluteSheetButton,calcButton;
	protected ButtonGroup bgunits;
	protected JRadioButton unitCmRadio,unitInchRadio;
	
	protected JPanel mainPanel,topPanel,sheetPanel;
	protected JScrollPane scroll;
	
	
	
	
	JTextField insideDiametrText,wallThicknessText;
	
	LanguageData guiTexts=new LanguageData();
	
	protected String getGuiText(String id){
		return guiTexts.get(id);
		} 
	
Flutelator(){
	super("Flutelator 1.0");
	setDefaultCloseOperation(EXIT_ON_CLOSE); 
	createGUI();	
	setVisible(true);
	}
	

private void createGUI(){

	

		
		Container contentPane=getContentPane();
		mainPanel=new JPanel(new BorderLayout());
		GridBagLayout gbag=new GridBagLayout();
		GridBagConstraints gbc=new GridBagConstraints();
		topPanel=new JPanel();
		topPanel.setBackground(new Color(255,255,255));
		topPanel.setLayout(gbag);
		sheetPanel=new JPanel();
		//contentPane.setLayout(new FlowLayout());
		mainPanel.add(topPanel,BorderLayout.NORTH);
		
		
		//mainPanel.setSize(1000,700);
		//setContentPane(scroll);
		scroll=new JScrollPane(mainPanel);
		contentPane.add(scroll);
		//contentPane.setOpaque(true);
		setSize(sizeX,sizeY);
		
		
		
		midikeys=new MidiKeys();
		midikeys.makeKeys();
		 
	    
		/**************** units radiobuttons*********************************/
		
		
		label1=new JLabel(getGuiText("label1"));
		bgunits=new ButtonGroup();
		unitCmRadio=new JRadioButton(getGuiText("unit_cm"),true);
		unitInchRadio=new JRadioButton(getGuiText("unit_inch"),false);
		unitCmRadio.setBackground(GuiColor); unitInchRadio.setBackground(GuiColor);
		bgunits.add(unitCmRadio);
		bgunits.add(unitInchRadio);
		//unitInchRadio.setPreferredSize(new Dimension(20,20));
		//Checkbox urrrrr=new Checkbox("dfgdsfgsdfg");
		
		
		
		//add(unitCmRadio);
		//add(unitInchRadio);
		
		
		/**************** labels and buttons*********************************/
		//label2=new JLabel(getGuiText("label2"));
		id_text=new JLabel(getGuiText("id_text"));
		wt_text=new JLabel(getGuiText("wt_text"));
		
		insideDiametrText=new JTextField(5); wallThicknessText=new JTextField(5);
		insideDiametrText.setText(defaultInsideDiametr+"");
		wallThicknessText.setText(defaultWallThickness+"");
		
		label3=new JLabel(getGuiText("label3"));
		label4=new JLabel(getGuiText("label4"));
		countHolesChoice=makeCountHolesChoice();
		
		
		rootNoteChoice=makeMidiChoice(defaultRootMidi);
		
		fluteSheetButton=new JButton(getGuiText("sheet_button_text"));
		fluteSheetButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
					showFluteSheet();
				}
			});		
		calcButton=new JButton(getGuiText("calc_button_text"));
		
		calcButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
					Calculate();
					
				}
			
			});
		calcButton.setEnabled(false);
		
		
		
		/*************************Composing**************************/
		gbc.weightx=0;
		gbc.anchor=GridBagConstraints.WEST;
		//gbc.anchor = GridBagConstraints.NORTHWEST; 
		gbc.fill   = GridBagConstraints.NONE; 
		gbc.gridheight = 1;
		gbc.gridwidth  = 1;
		
		gbc.gridy=0;
		gbc.gridx=0; gbag.setConstraints(label1,gbc); 
		gbc.gridx=1; gbag.setConstraints(unitCmRadio,gbc);
		gbc.gridx=2; gbag.setConstraints(unitInchRadio,gbc); 
	
		gbc.insets=new Insets(1,1,1,1);
		//gbc.gridx=0; gbc.gridy=1; gbag.setConstraints(label2,gbc);
		
		gbc.gridy=1;
		gbc.gridx=0; gbag.setConstraints(id_text,gbc);
		gbc.gridx=1; gbag.setConstraints(insideDiametrText,gbc);
		
		
		gbc.gridx=2;  gbag.setConstraints(wt_text,gbc);
		gbc.gridx=3;  gbag.setConstraints(wallThicknessText,gbc);
		
		gbc.gridy=3;
		gbc.gridx=0; gbag.setConstraints(label3,gbc);
		gbc.gridx=1; gbag.setConstraints(countHolesChoice,gbc);
		
		
		gbc.gridx=2; gbag.setConstraints(label4,gbc);
		gbc.gridx=3; gbag.setConstraints(rootNoteChoice,gbc);
		gbc.insets=new Insets(4,4,4,4);
		
		gbc.gridy=5;
		gbc.gridx=0;  gbag.setConstraints(fluteSheetButton,gbc);
		gbc.gridx=1;  gbag.setConstraints(calcButton,gbc);
		
		/******************************Attaching *******************************/
		topPanel.add(label1); 
		topPanel.add(unitInchRadio);  
		topPanel.add(unitCmRadio);
		//topPanel.add(label2);
		
		
		topPanel.add(id_text);
		topPanel.add(insideDiametrText);
		topPanel.add(wt_text);
		topPanel.add(wallThicknessText);
		
		topPanel.add(label3);
		topPanel.add(label4);
		
		topPanel.add(countHolesChoice);
		topPanel.add(rootNoteChoice);
		topPanel.add(fluteSheetButton);
		topPanel.add(calcButton);
		
		//contentPane.add(box1);
		insideDiametrText.requestFocusInWindow();
		//sheetPanel=createFluteSheet(defaultCountHoles,defaultRootMidi);
		
		mainPanel.add(sheetPanel,BorderLayout.CENTER);
		//mainPanel.add(createFluteSheet(defaultCountHoles,defaultRootMidi),BorderLayout.CENTER);
		
		//Choice c=makeMidiChoice(defaultRootMidi);
		
		
		
		
		/*
		 * Box box1=Box.createHorizontalBox();
		Box box2=Box.createHorizontalBox();
		 * box1.add(label1); box1.add(Box.createRigidArea(new Dimension(13,0)));
		box1.add(unitCmRadio); box1.add(Box.createRigidArea(new Dimension(13,0))); box1.add(unitInchRadio);
		box1.add(Box.createRigidArea(new Dimension(233,0)));
		box2.add(label2);
		contentPane.add(box1); contentPane.add(box2);
		 * */
		
		//add(c);

}
public JPanel createFluteSheet(int countHoles,int rootKey){
		JPanel FlutePanel=new JPanel();
		FlutePanel.setBackground(GuiColor);
		GridBagLayout grid=new GridBagLayout();
		FlutePanel.setLayout(grid);
		GridBagConstraints gc=new GridBagConstraints();
		JLabel l;
		JTextField textfield;
		freqInputs =new ArrayList<JTextField>();
		diametrInputs =new ArrayList<JTextField>();
		distanceInputs =new ArrayList<JTextField>();
		fingerInputs =new ArrayList<JTextField>();
		cutoffInputs =new ArrayList<JTextField>();
		freqChoices =new ArrayList<Choice>();
		int curKey=0;
		
		gc.weightx=1;
		gc.anchor=GridBagConstraints.WEST;
		gc.fill   = GridBagConstraints.NONE; 
		gc.gridheight = 1;
		gc.gridwidth  = 1;
		gc.insets=new Insets(1,30,1,1);
		
		for(int i=0;i<guiTexts.tableHead.length;i++){
			l=new JLabel(guiTexts.tableHead[i]);
			gc.gridx=i;
			gc.gridy=0;
			grid.setConstraints(l,gc);
			FlutePanel.add(l);
			}
			
		for(int i=0;i<=countHoles;i++){
			gc.gridx=0;
			gc.gridy=i+1;
			
			if(i==0){
				l=new JLabel(getGuiText("emb_label")); grid.setConstraints(l,gc); FlutePanel.add(l);
				gc.gridx=1;
				l=new JLabel("---------"); grid.setConstraints(l,gc); FlutePanel.add(l);
				/* init 0 frequency but not added to GUI!!!*/
				textfield=initTextField(fingerInputs);
				textfield=initTextField(freqInputs);
				textfield.setText("0.0");
				freqChoices.add(new Choice());
				
				gc.gridx=2; 
				JTextField td0 =initTextField(diametrInputs,"1.0");grid.setConstraints(td0,gc); FlutePanel.add(td0);
				gc.gridx=3; 
				JTextField tdist0 =initTextField(distanceInputs); makeNotEditableInput(tdist0); grid.setConstraints(tdist0,gc);   FlutePanel.add(tdist0);
				
								
				
				
				continue;
				}
			
			//defaultFluteModel;
			//JTextField tdiam,tdist,tfreq,tcutoff,tfinger;
			
			
			l=new JLabel(new Integer(i).toString());
			grid.setConstraints(l,gc);
			FlutePanel.add(l);
			
			
			/***************************************/
			gc.gridx=1;
			
			if(curKey==0) curKey=rootKey+(countHoles*2)-i;
			else{ 
				if(i==(countHoles))
					curKey=curKey-1;
				else curKey=curKey-2;
			}
			
		
			Box b=makeFrequencyBox(curKey);
			grid.setConstraints(b,gc);  FlutePanel.add(b);
			/***************************************/
			
			
			
			//FlutePanel.add(tfreq); FlutePanel.add(c); grid.setConstraints(tfreq,gc);
			gc.gridx=2;   textfield=initTextField(diametrInputs,"1.0");  grid.setConstraints(textfield,gc); FlutePanel.add(textfield);
			gc.gridx=3;   textfield=initTextField(distanceInputs); grid.setConstraints(textfield,gc); makeNotEditableInput(textfield); FlutePanel.add(textfield); 
			gc.gridx=4;   textfield=initTextField(fingerInputs);   grid.setConstraints(textfield,gc); makeNotEditableInput(textfield);FlutePanel.add(textfield);
			gc.gridx=5;   textfield=initTextField(cutoffInputs)   ;grid.setConstraints(textfield,gc); makeNotEditableInput(textfield);// FlutePanel.add(textfield);
			
			
			
			
			}
		
				gc.gridy++;
				gc.gridx=0;
				l=new JLabel(getGuiText("end_label")); grid.setConstraints(l,gc); FlutePanel.add(l);
				gc.gridx=1; 
				Box b=makeFrequencyBox(rootKey);
				grid.setConstraints(b,gc);  FlutePanel.add(b); 
				gc.gridx=2; 
				l=new JLabel("---------"); grid.setConstraints(l,gc); FlutePanel.add(l);
				gc.gridx=3; 
				textfield =initTextField(distanceInputs); textfield.setText("0.0"); makeNotEditableInput(textfield); grid.setConstraints(textfield,gc);   FlutePanel.add(textfield);
				
		return FlutePanel;
		
		}
protected Box makeFrequencyBox(int curKey){
				Box b=Box.createVerticalBox();
				Choice c =makeMidiChoice(curKey);  freqChoices.add(c);
			    JTextField textfield=initTextField(freqInputs); textfield.setText(midikeys.keyToFrequency(curKey)+"");
				b.add(c); b.add(b.createRigidArea(new Dimension(10,10)));   b.add(textfield); 
				//b.setBorder(new javax.swing.border.MatteBorder(new Insets(4,4,4,4), Color.GRAY)); 
					c.addItemListener(new ItemListener(){
					
					public void itemStateChanged(ItemEvent ie){
						//String s;
						Choice c =(Choice)ie.getSource();
						int id=freqChoices.indexOf(c);
						int key=c.getSelectedIndex();
						double freq =midikeys.keyToFrequency(key);
						JTextField t=freqInputs.get(id);
						t.setText(freq+"");
						//System.out.println(id); 
						}
					
					});
				return b;
				}



	public Choice makeMidiChoice(int selected){
		Choice c=new Choice();
		MidiKey[] k=midikeys.getKeys();
		
		int i=0;
		while(i<k.length){
			c.add(k[i].name+" ("+Math.round(k[i].frequency)+")");
			i++;
			}
			c.select(selected);
		return c;
		}
	
	public Choice makeCountHolesChoice(){
		Choice c=new Choice();
		for(int i=2;i<=20;i++){
			c.add(new Integer(i).toString());
			}
			c.select(defaultCountHoles+"");
		return c;
		}
	
	
		
	public ArrayList<JTextField> freqInputs;
	public ArrayList<JTextField> diametrInputs;
	public ArrayList<JTextField> distanceInputs;
	public ArrayList<JTextField> fingerInputs;
	public ArrayList<JTextField> cutoffInputs ;
	public ArrayList<Choice> freqChoices;
	public int[] defaultFluteModel={1,2,2,2,1,2,2};
	
	
	protected JTextField initTextField(ArrayList list){
		JTextField t=new JTextField(10);
		//attachActionListenerOnlyInt(t);
		list.add(t);
		return t;
		}
	protected JTextField initTextField(ArrayList list,String val){
		JTextField t=initTextField(list);
		t.setText(val);
		return t;
		}
	protected void makeNotEditableInput(JTextField t){
		t.setEditable(false);  t.setBorder(new javax.swing.border.EmptyBorder(new Insets(1,1,1,1)));
		}
	
	public double jtextToDouble(JTextField t){
			try{
				return new Double(t.getText()).doubleValue();
			}
			catch(Exception e){
				return -1.0;
			}
		}
	
	public boolean checkInput(JTextField t){
		double d=jtextToDouble(t);
		if(d==-1){
			t.setBackground(Color.MAGENTA );
			JOptionPane.showMessageDialog(mainPanel,getGuiText("input_empty"));
			return false;
			}
		else {
			t.setBackground(Color.WHITE );
			return true;
		}
		}
	public void showFluteSheet(){
		int rootkey=rootNoteChoice.getSelectedIndex();
		int countholes=new Integer(countHolesChoice.getSelectedItem()).intValue();
		
		
		if(!checkInput(wallThicknessText) || !checkInput(insideDiametrText)) return;
		
		
			
			
		calcButton.setEnabled(true);
		mainPanel.remove(sheetPanel);
		sheetPanel=createFluteSheet(countholes,rootkey);
		mainPanel.add(sheetPanel,BorderLayout.CENTER);
		mainPanel.doLayout();
		mainPanel.validate();
		mainPanel.update(mainPanel.getGraphics());
		insideDiametrText.setBackground(Color.WHITE );
		wallThicknessText.setBackground(Color.WHITE );
		
	}
	
	public void Calculate(){
		
		
		
		if(!checkInput(wallThicknessText) || !checkInput(insideDiametrText)) return;
		
		int rootkey=rootNoteChoice.getSelectedIndex();
		int countholes=new Integer(countHolesChoice.getSelectedItem()).intValue();
		double freqEnd= jtextToDouble(freqInputs.get(countholes+1));  
		double wallThickness=jtextToDouble(wallThicknessText); 
		double insideDiametr= jtextToDouble(insideDiametrText);
		int midikey;
		double freq;
		double diam;
		
		Flute flute=new Flute(insideDiametr,wallThickness,freqEnd);
		
		Tools.setUnit((unitInchRadio.isSelected())?Tools.INCHES:Tools.CM);
		
		freq=jtextToDouble(freqInputs.get(0)); 
		diam= jtextToDouble(diametrInputs.get(0));
		flute.append(new FluteHole(0 ,diam ,freq));
		
		for(int i=countholes; i>=1;i--){
			midikey=0;
			
			freq=jtextToDouble(freqInputs.get(i)); 
			diam= jtextToDouble(diametrInputs.get(i));
			if(!checkInput(freqInputs.get(i)) || !checkInput(diametrInputs.get(i))) {
				//System.out.println(i+"///"+freq+"--"+diam); 
				
				return;
				}
			flute.append(new FluteHole(midikey ,diam ,freq));
		
		}
		
		Worker worker=new Worker();
		worker.work(flute);
		
		//System.out.println("********************************************/");
		//System.out.println(flute);
		
		

		
		
		
		FluteHole[] holes =flute.getHolesArray();
		
		distanceInputs.get(0).setText(Tools.round(holes[0].distance,2)+"");
		
		//cutoffInputs.get(i).setText(hole.cutoff_frequency+"");
		

		
		for(int i=1; i<holes.length;i++){
			
			
			distanceInputs.get(i).setText(Tools.round(holes[holes.length-i].distance,2)+"");
			try{
				fingerInputs.get(i).setText(Tools.round(holes[holes.length-i].finger_spacing,2)+"");
			}
			catch(Exception e){
				e.printStackTrace(System.out);
				} 
			//cutoffInputs.get(i).setText(hole.cutoff_frequency+"");
			
			} 
		/*
		 * 
		 * 
	public ArrayList<JTextField> freqInputs =new ArrayList<JTextField>();
	public ArrayList<JTextField> diametrInputs =new ArrayList<JTextField>();
	public ArrayList<JTextField> distanceInputs =new ArrayList<JTextField>();
	public ArrayList<JTextField> fingerInputs =new ArrayList<JTextField>();
	public ArrayList<JTextField> cutoffInputs =new ArrayList<JTextField>();
	public ArrayList<Choice> freqChoices =new ArrayList<Choice>();
	public int[] defaultFluteModel={1,2,2,2,1,2,2};
		Flute flute2=new Flute(1,0.2,246.94);
		flute2.append(new FluteHole(0 ,1.0 ,0.0));
		flute2.append(new FluteHole(0 ,1.0 ,277.18));
		flute2.append(new FluteHole(0 ,1.0 ,293.66));
		flute2.append(new FluteHole(0 ,1.0 ,329.63));
		flute2.append(new FluteHole(0 ,1.0 ,369.99));
		flute2.append(new FluteHole(0 ,1.0 ,415.3));
		flute2.append(new FluteHole(0 ,1.0 ,466.16));
		flute2.append(new FluteHole(0 ,1.0 ,523.25));
		flute2.append(new FluteHole(0 ,1.0 ,587.329));
		


		Worker worker=new Worker();
		worker.work(flute);
		System.out.println(flute);
		*/
		}
	public static void main(String[] args){
		Flutelator flutelator=new Flutelator();
		
		}
	}

