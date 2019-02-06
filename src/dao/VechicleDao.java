package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import beans.Article;
import beans.Restaurant;
import beans.Vechicle;
import beans.VechicleType;

public class VechicleDao {

	public static ArrayList<Vechicle> loadVechicle(){
		ArrayList<Vechicle> allVechicles=new ArrayList<>();
		SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy");
		BufferedReader in=null;
		try {
			File file=new File(Constants.KEY_VECHICLES_FILE);
			in=new BufferedReader(new FileReader(file));
			String line="";
			while((line=in.readLine())!=null) {
				String tokens[]=line.split("\\|");
				String id=tokens[0];
				String mark=tokens[1];
				String model=tokens[2];
				String type=tokens[3];
				String registration=tokens[4];
				Date date=sdf.parse(tokens[5]);
				String used=tokens[6];
				String note=tokens[7];
				
				Vechicle v=new Vechicle(Long.parseLong(id),mark,model,VechicleType.valueOf(type),registration,date,Boolean.valueOf(used),note);
				allVechicles.add(v);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			if (in != null) {
				try {
					in.close();
				}
				catch (Exception e) { }
			}
		}
	
	return allVechicles;
	}	
	
	
	public static String getVechicleFileFormat(Vechicle v) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		return v.getId()+Constants.KEY_DB_SEPARATOR+
				v.getMark() +Constants.KEY_DB_SEPARATOR+
				v.getModel() + Constants.KEY_DB_SEPARATOR+
				v.getType()  + Constants.KEY_DB_SEPARATOR+
				v.getRegistration()  + Constants.KEY_DB_SEPARATOR+
				sdf.format(v.getYearOfDistribution()) + Constants.KEY_DB_SEPARATOR+
				v.isUsed()  + Constants.KEY_DB_SEPARATOR+
				v.getNote() + Constants.KEY_DB_SEPARATOR+
				"\n";
		
	}

	public static HashMap<Long,Vechicle> SeedData(){
		
		HashMap<Long,Vechicle> vechicle = new HashMap<>();
		for(int i=0;i<20;i++) {
			Vechicle v= new Vechicle();
			v.setId((long)i+1);
			v.setMark("Mark - "+i);
			v.setModel("Model - "+i);
			if(i<5) {
				v.setType(VechicleType.bicikl);
			}else if(i>6 && i<12) {
				v.setType(VechicleType.skuter);
			}else {
				v.setType(VechicleType.skuter);
			}
			v.setRegistration("1223645 - "+i);
			v.setYearOfDistribution(new Date());
			if(i<9) {
			v.setUsed(false);
			}else {
				v.setUsed(true);
			}
			v.setNote("Note - "+i);
			
			vechicle.put(v.getId(), v);
		}
		
		return vechicle;
	}


	public static void saveVechicleMap(HashMap<Long, Vechicle> vechicle) {
		// TODO Auto-generated method stub
		BufferedWriter writer=null;
		try {
			writer=new BufferedWriter(new FileWriter(Constants.KEY_VECHICLES_FILE));
			String save="";
			for(Vechicle v: vechicle.values()) {
				save+=getVechicleFileFormat(v);
			}
			writer.write(save);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(writer!=null){
				try{
					writer.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
			
	}


	public static void save(Vechicle v) {
		// TODO Auto-generated method stub
		ArrayList<Vechicle> vechicles=loadVechicle();
		int size=vechicles.size();
		Vechicle last=vechicles.get(size-1);
		Long idlast=last.getId();
		System.out.println("last id"+idlast);
		v.setId(idlast+1);
		vechicles.add(v);
		saveVechicles(vechicles);
		
		
	}
	
	
	private static void saveVechicles(ArrayList<Vechicle> vechicles) {
		BufferedWriter writer=null;
		try{
			writer=new BufferedWriter(new FileWriter(Constants.KEY_VECHICLES_FILE));
			String save="";
			for(Vechicle a: vechicles){
				save+=getVechicleFileFormat(a);
			}
			writer.write(save);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(writer!=null){
				try{
					writer.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}


	public static void deleteV(Long id) {
		// TODO Auto-generated method stub
		ArrayList<Vechicle> allV=loadVechicle();
		for(Vechicle a: (ArrayList<Vechicle>) allV.clone()) {
			if(a.getId().equals(id)) {
				System.out.println("Brisemo vozilo sa id"+ id);
				allV.remove(a);
				saveVechicles(allV);
			}
		}
	}

}
