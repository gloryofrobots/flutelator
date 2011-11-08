package ua.ho.gloryofrobots.flutelator;

import ua.ho.gloryofrobots.flutelator.FluteHole;
import ua.ho.gloryofrobots.flutelator.Flute;
import ua.ho.gloryofrobots.flutelator.MidiKey;
import ua.ho.gloryofrobots.flutelator.MidiKeys;
import ua.ho.gloryofrobots.flutelator.Tools;
import java.util.*;

class 	CigaretsLessException extends Exception{
	 private static final long serialVersionUID = 41L;
	}
class 	BadHolesDataException extends Exception{
	 private static final long serialVersionUID = 40L;
	}
class NotCompletedFluteException extends Exception{
	 private static final long serialVersionUID = 39L;
	}
	


class Worker{
	private double money=0;
	private  int cigarets=20;
	protected Flute flute;
	
	// effective wall thickness, i.e. height of air column at open finger holes;
	// air column extends out past end of hole 3/4 of the hole diameter
	double effective_wall_thickness(double diam){
		return (1.0*flute.wall_thickness) + (0.75*diam);
	}
	// Closed hole for tone hole n.  The length of the vibrating air column is
	// effectively increased by each closed tone hole which exists above the
	// first open tone hole. Corrections m
	double closed_hole_corrections(double diam){
		return 0.25 * flute.wall_thickness *  Math.pow( (diam/flute.inside_diametr),2 );
	}
	// Calculates the distance from physical open end of flute to effective end of
	// vibrating air column.  The vibrating air column ends beyond the end of the
	// flute and C_end is always positive. NOTE: Closed hole corrections must be added to
	// this value!
	double distance_from_open_end(){
		return 0.6133 * flute.inside_diametr / 2;
	}
	// Calculates the effective distance from the first ("single") tone hole to
	// the end of the vibrating air column when only that hole is open.
	
	// NOTE: closed hole corrections must be added to this value!
	/*
	def distance_from_first_hole(){
		return  te(1)/( (Df[1]/Bore)*(Df[1]/Bore) + te(1)/(Xend-Xf[1]) );
	}
	// Calculates the effective distance from the second and subsequent tone holes
	// to the end of the vibrating air column when all holes below are open.
	// NOTE: closed hole corrections must be added to this value!
	// NOTE: the value of this correction is invalid if the frequency of the note
	// played is above the cutoff frequency f_c.
	def distance_from_second_and_subsequent_hole(){
		return ((Xf[n-1]-Xf[n])/2)*(Math.sqrt(1+4*(te(n)/(Xf[n-1]-Xf[n]))*(Bore/Df[n])*(Bore/Df[n]))-1)
	}
	*/	
	
	// C_emb = distance from theoretical start of air column to center of embouchure hole;
	// the air column effectively extends beyond the blow hole center by this distance.
	// (the cork face should be about 1 to 1.5 embouchure diameters from emb. center)
	//C_emb := (Bore/Demb)*(Bore/Demb)*(wall+0.75*Demb); // per spreadsheet
	//C_emb := (Bore/Demb)*(Bore/Demb)*(Bore/2 + wall + 0.6133*Demb/2); // an alternative
	//C_emb := (Bore/Demb)*(Bore/Demb)*10.84*wall*Demb/(Bore + 2*wall); // kosel's empirical fit
	double distance_to_embouchure(){
		FluteHole hole0=flute.getHole(0);
		
		return Math.pow( (flute.inside_diametr/hole0.diametr) ,2) *10.84*flute.wall_thickness*hole0.diametr/(1.0*flute.inside_diametr + 2*flute.wall_thickness);
	}
	// Calculates the cutoff frequency above which the open hole correction
	// is not valid.  Instrument should be designed so that all second register
	// notes are well below this frequency.
	
	double cutoff_frequency(int key){
		double s=0.0;
		
		if (key==1)
			s=flute.end-flute.getHole(key).location;
		else
			s=flute.getHole(key-1).location-flute.getHole(key).location;
		
		return Tools.getUnit()/2/Math.PI*flute.getHole(key).location
		                      /flute.inside_diametr
		                       /Math.sqrt(Math.abs( effective_wall_thickness(flute.getHole(key).diametr)*s  ));
	
	}
	double remember_school(double a,double b,double c){
		double square_root=Math.sqrt(Math.abs((Math.pow(b,2)) - 4*a*c));
		return (-b-square_root)/(2*a);
	}
	
	double drink_bear_and_draw_point(int key,int correction_point){
		double unit=Tools.getUnit();
		FluteHole hole=flute.getHole(key);
		
		double location=unit*0.5/hole.frequency; 
		if (correction_point>0){
			for(int i=correction_point;i<=flute.count_holes-1;i++){
				location-=closed_hole_corrections(flute.getHole(i).diametr);
				}
		}
		return location;
	}
	protected void drill(){
		try{
			have_a_smoke();
			maybe_not();
		}catch (Exception e){
			e.printStackTrace(System.out);
			System.exit(-1);
			}
		double unit=Tools.getUnit();
		FluteHole[] holes=flute.getHolesArray();
		flute.end=(unit*0.5/flute.frequency_end)-distance_from_open_end();
		holes[0].location=flute.end;
		//inititalize count of added holes to flute
		flute.getCountHoles();
		for (int i=1;i<flute.count_holes;i++)
				flute.end-=closed_hole_corrections(holes[i].diametr);
			
		double location=drink_bear_and_draw_point(1,2);
		
		double a = Math.pow( (holes[1].diametr/flute.inside_diametr),2);
		
		double b = -1*(flute.end +location)*(holes[1].diametr/flute.inside_diametr)*(holes[1].diametr/flute.inside_diametr);

		double c = flute.end *location* (holes[1].diametr/flute.inside_diametr)*(holes[1].diametr/flute.inside_diametr) 
					+ effective_wall_thickness(holes[1].diametr)*(location-flute.end);
		holes[1].location = remember_school(a,b,c);
		
		
		for(int num=2;num<flute.count_holes;num++){
			location=drink_bear_and_draw_point(num,num);
			
			if (num < flute.count_holes)
				location=drink_bear_and_draw_point(num,num);
			else
				location=drink_bear_and_draw_point(num,num-1);
			a = 2;
			b = -1* holes[num-1].location - 3*location + effective_wall_thickness(holes[num].diametr)
					*(flute.inside_diametr/holes[num].diametr)*(flute.inside_diametr/holes[num].diametr);
			c = holes[num-1].location*(location - effective_wall_thickness(holes[num].diametr)
					*(flute.inside_diametr/holes[num].diametr)*(flute.inside_diametr/holes[num].diametr)) + Math.pow(location,2);
			holes[num].location=remember_school(a,b,c);
		}

		// set embouchure hole location
		holes[0].location = distance_to_embouchure();
	}
	protected void finishing_touch(){
		FluteHole[] holes=flute.getHolesArray();
		holes[0].distance=flute.end-holes[0].location;
		holes[1].finger_spacing=flute.end-holes[1].location;
		
		for(int i=1;i<flute.count_holes;i++){
			holes[i].distance=flute.end-holes[i].location;
			
			if(holes[i].finger_spacing==0.0)
				holes[i].finger_spacing=holes[i-1].location-holes[i].location;
			
			
				
			holes[i].cutoff_frequency=cutoff_frequency(i);
		}
	
	
	}
	
	public void work(Flute f){
		flute=f;
		drill();
		finishing_touch();
		}
	public  Flute getMadedFlute() throws NotCompletedFluteException{
		if(flute.complete) return flute;
		else throw new NotCompletedFluteException();
		}
	private void have_a_smoke() throws CigaretsLessException{
		
		cigarets--;
		if(cigarets<=0) throw new CigaretsLessException();
	}
	
	private void maybe_not() throws BadHolesDataException{
		if(flute.holes.size()<2)
			throw new BadHolesDataException();
	}

}

