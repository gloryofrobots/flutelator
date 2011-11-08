package ua.ho.gloryofrobots.flutelator;

import java.util.regex.*;
import java.util.*;
import java.io.*;
import ua.ho.gloryofrobots.flutelator.*;





class MakeFlute{
	
	public static void showHelp(){
		
		
		System.out.println("...... Help :");
		System.out.println("...... usage  java -jar MakeFluteFolder/MakeFlute.jar path_to_config_file [options]  ,where options:");
		System.out.println("......  -k: show all supported midi keys   ");
		System.out.println("......  -h this message   ");
		System.out.println("......  -d use the config file 'flute.ini' from current system dir ");
		System.out.println("......  -c show   config file example");
		System.out.println("...... example   java -jar c:/MakeFlute/MakeFlute.jar \"c:\\MakeFlute\\flute.ini\" -k ");
		
		System.out.println("*********************** developer: Birulia Alex **************************");
		System.out.println("******************** feedback: gloryofrobots@gmail.com *************************");
		System.out.println("********************************GoodLuck****************************************");
		
		}
	
	public static void showConfigExample(){
		
		System.out.println("********************Example of simple configuration file***********************");
		System.out.println("flute.inside_diametr=4.0");
		System.out.println("flute.wall_thickness=0.3372");
		System.out.println("flute.frequency=329");
		System.out.println("#important!! ");
		System.out.println("count_holes=5");

		System.out.println("amb.diametr=1");



		System.out.println("#NOTE NAME ALWAYS CONSISTS OF 3 CHARS ");
		System.out.println("#EXAMPLE : 'G 4' or 'G#4'");
		System.out.println("hole1.diametr=1");
		System.out.println("hole1.name=\"G 4\"");

		System.out.println("hole2.diametr=1");
		System.out.println("hole2.name=\"A 4\"");

		System.out.println("hole3.diametr=0.8");
		System.out.println("hole3.name=\"B 4\"");

		System.out.println("hole4.diametr=1");
		System.out.println("hole4.name=\"D 5\"");

		System.out.println("hole5.diametr=1.2");
		System.out.println("hole5.name=\"E 5\"");
		System.out.println("******************Please use the -h option for more information**********************");
		}
	
	public static void main(String args[]) throws Exception{
     
     /*
		Flute flute=new Flute(2.25,0.278,294);
		flute.append(new FluteHole(0 ,1 ,0.0));
		flute.append(new FluteHole(0 ,1 ,349));
		flute.append(new FluteHole(0 ,1 ,392));
		flute.append(new FluteHole(0 ,1 ,440));
		flute.append(new FluteHole(0 ,1 ,523));
		flute.append(new FluteHole(0 ,1 ,587));
      */
			/*
		Flute flute=new Flute(1.67,0.3,262.0);
		flute.append(new FluteHole(60,1 ,0.0));
		flute.append(new FluteHole(0.4 ,"D 4"));
		flute.append(new FluteHole(1 ,"E 4"));
		flute.append(new FluteHole(0.6 ,"F 4"));
		flute.append(new FluteHole(1 ,"G 4"));
		flute.append(new FluteHole(1.2 ,"A 4"));
		flute.append(new FluteHole(1.2 ,"B 4"));
		flute.append(new FluteHole(1.2 ,"C 5"));*/
		System.out.println("******************************MakeFlute v1.0***********************************");
		String fn="",param="";
		if(args.length>0){
			fn=args[0];
			
				for (int i=0;i<args.length;i++){
					if(args[i].equals("-h")) MakeFlute.showHelp();
					else if(args[i].equals("-d")) fn="flute.ini";
					else if(args[i].equals("-c")) MakeFlute.showConfigExample();
					else if(args[i].equals("-k")) Tools.Midi.showKeyInfo();
				}
			
		}
		else{ 	        
			MakeFlute.showHelp();
		}
		if(fn.length()<3) System.exit(-1);
		
			
		MakeFluteEngine engine=new MakeFromIniFluteEngine(fn);
		try{
		Flute flute=engine.make();
		Worker worker=new Worker();
		worker.work(flute);
		//Tools.Midi.showKeyInfo();
		System.out.println(flute);
		}
		catch(Exception e){
			e.printStackTrace(System.out);
			
			
		}
	}
	
	
}
