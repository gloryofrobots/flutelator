package ua.ho.gloryofrobots.flutelator;

import ua.ho.gloryofrobots.flutelator.MidiKey;
import ua.ho.gloryofrobots.flutelator.Tools;
import java.util.*;

class MidiKeys{
	private double frequency_a4=440;
	private int key_a4=69;
	public String[] gamma={"C ","C#","D ","D#","E ","F ","F#","G ","G#","A ","A#","B "};
	protected MidiKey keys[]=new MidiKey[128];
	public double keyToFrequency(int key){
		
		return Tools.round(frequency_a4*Math.pow(2,(key-key_a4)*1.0/12),2);
		}
	
	public double frequencyToKey(double frequency){
	
		return Math.round(12*Tools.logx(frequency/frequency_a4,2))+key_a4;
	}
	
	public MidiKey getKeyForName(String name){
		for(MidiKey key:keys){
			//System.out.println(key.name+"--"+name);
			if(key.name.equals(name)) return key;  
					
						
			}
			return new MidiKey("Unknown",0,0);
		}
 	public void makeKeys(){
		
		for(int key=0;key<=127;key++)
			keys[key]=new MidiKey(keyToName(key),keyToFrequency(key),key);
		}
	public MidiKey[] getKeys(){
		return keys;
		}
	public void showKeyInfo(){
		for(MidiKey k:keys){
			System.out.println(k);
			
			}
		
		}
	public String keyToName(int key){
		String result="";
		if(key==0) return "Amb";
		try{
			result=gamma[key%12]+""+((key/12)-1);
			
			}
		catch(Exception e ){
			e.printStackTrace(System.out);
			}
		
		return result;
		
		}
		
	}
