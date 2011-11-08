package ua.ho.gloryofrobots.flutelator;

import ua.ho.gloryofrobots.flutelator.FluteHole;
import ua.ho.gloryofrobots.flutelator.Tools;
import java.util.*;

class Flute{
	public double inside_diametr;
	public double wall_thickness;
	public double frequency_end;
	public double end;
	public int count_holes;
	boolean complete=false;
	public java.util.List<FluteHole> holes =new ArrayList<FluteHole>();
	Flute( double insd,double wt,double fe){
		inside_diametr=insd;
		wall_thickness=wt;
		frequency_end=fe;
		}
		
	 public String toString(){
		String s="";
		s+="inside diametr:"+inside_diametr+" frequency end:"+frequency_end+" wall_thickness: "+wall_thickness+" width: " +Tools.round(end, 2)+"  \n\r";
		int n=0;
		for(FluteHole h : holes){
			n++;
			s+=n+"."+h+"\n\r";
			}
			
		return s;
		}
	public void append(int midi_key,double diam,double frequency){
			holes.add(new FluteHole(midi_key,diam,frequency));
		}
	public void append(FluteHole hole){
		holes.add(hole);
		}
		/*
	public void append(Collection<FluteHole> h){
		Collections.addAll(holes,h);
		
		}*/
	public void append(LinkedList<FluteHole> h){
		for(FluteHole i : h) append(i);
		
		}
	public int getCountHoles(){
		count_holes=holes.size();
		return count_holes;
		}
	FluteHole getHole(int key){
		return holes.get(key);
		}
	FluteHole[] getHolesArray(){
		return holes.toArray(new FluteHole[0]);
		}
	
	}
