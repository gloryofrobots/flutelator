package ua.ho.gloryofrobots.flutelator;

import ua.ho.gloryofrobots.flutelator.Tools;
import java.util.*;


class FluteHole{
	public int midi_key;
	public double diametr;
	public double frequency;
	public double location;
	public double finger_spacing;
	public double distance;
	public double cutoff_frequency;
	FluteHole(){
		
		}
		
	FluteHole(int midi_key,double diam,double freq ){
		midi_key=midi_key;
		diametr=diam;
		frequency=freq;
		}
	FluteHole(double diam,String name){
		MidiKey key;
		key=Tools.Midi.getKeyForName(name);
		midi_key=key.number;
		diametr=diam;
		frequency=key.frequency;
		
			
		}
	public String toString(){
		String s= " diametr: "+diametr+" location: "+location+"+ distance: "+distance+" frequency: "+frequency
					+" cutoff_frequency: "+cutoff_frequency+" finger_spacing: "+finger_spacing+" midi_key: "+midi_key;
		
		
		s=Tools.Midi.keyToName(midi_key)  +"   diametr:   "+diametr+"    distance: "+Tools.round(distance,2)
							+" location: "+Tools.round(location,2)+"  frequency: "+frequency+"     spacing: "+Tools.round(finger_spacing,2);
		//System.out.println(s);
		return s;
		}
	}


