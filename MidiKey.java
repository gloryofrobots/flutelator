package ua.ho.gloryofrobots.flutelator;

import java.util.*;

class MidiKey{
	public double frequency;
	public String name;
	public int number;
	MidiKey(String n,double f,int numb){
		name=n;
		frequency=f;
		number=numb;
		}
	public String toString(){
		return name+"id: "+number+" freq: "+new Double(frequency).toString();
		}
	}

