package ua.ho.gloryofrobots.flutelator;

import java.util.*;
import java.io.*;
import ua.ho.gloryofrobots.flutelator.FluteHole;
import ua.ho.gloryofrobots.flutelator.Flute;
import ua.ho.gloryofrobots.flutelator.MakeFluteEngine;



class MakeFromIniFluteEngine implements MakeFluteEngine{
	String fileName;
	MakeFromIniFluteEngine(String filename){
		fileName=filename;
		
		
	}
	public  Properties prp=new Properties();
	
	public Flute make()  {
		try{
		prp.load(new FileInputStream(fileName));
		}catch(Exception e){
			System.out.println("Unable to open ini file");
			System.exit(-1);
			
		}
	
		double count_holes=parseIniStr("count_holes");
		Flute flute=new Flute(
							parseIniStr("flute.inside_diametr")
							,parseIniStr("flute.wall_thickness")
							,parseIniStr("flute.frequency")
							);
		flute.append( new FluteHole(60,parseIniStr("amb.diametr"),0.0) );
		
		
		for(int i=1;i<=count_holes;i++){
		
			flute.append(new FluteHole(parseIniStr("hole"+i+".diametr"),prp.getProperty("hole"+i+".name").replace("\"", "")));
			
		}
		return flute;
	}
	double parseIniStr(String prop){
		double val=new Double(0);
		try{
			 val=new Double(prp.getProperty(prop)).doubleValue();
		 	
		} 
		catch(NullPointerException e){
			System.out.println("Failure in parsing "+prop+ "parametr");
			System.exit(-1);
		}
		return val;
		
	}	
} 
