package ua.ho.gloryofrobots.flutelator;
import java.util.*;
import ua.ho.gloryofrobots.flutelator.MidiKeys;

class Tools{
	final public static Map<String,Double> units=new HashMap<String,Double>();
	final public static String INCHES="inches";
	final public static String CM="cm";
	public static MidiKeys Midi=new MidiKeys();
	protected static double unit;
	static{
		Midi.makeKeys();
		units.put(INCHES,new Double(13584)); units.put(CM,new Double(34500));
		unit=units.get(CM).doubleValue() ;
		}
	static void setUnit(String unitName){
		try{
		unit=units.get(unitName).doubleValue() ;
		}
		catch(Exception e ){
			e.printStackTrace(System.out);
			}
		}
		
	static double getUnit(){
		return unit;
		}
		
	static double logx(double y,double x){
		double result=Math.log(y)/Math.log(x);
		return result;
	}
	static double round(double x,int signNumber){
		double signk=Math.pow(10,signNumber);
		return Math.round(x*signk)/signk;
		//return Math.round(x);
		
		}
	//(int)(Math.log(x)/Math.log(2)+1e-10))
	}
