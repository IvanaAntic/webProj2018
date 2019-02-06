package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;

import beans.ArticleType;
import beans.Restaurant;
import beans.RestaurantType;

public class RestaurantDao {

	public static ArrayList<Restaurant> loadRestaurants(){
		ArrayList<Restaurant> allRestaurants=new ArrayList<>();
		BufferedReader in=null;
		try {
		
			in=new BufferedReader(new FileReader(new File(Constants.KEY_RESTAURANTS_FILE)));
			String line="";
			while((line=in.readLine())!=null) {
				String tokens[]=line.split("\\|");
				String id=tokens[0];
				String name=tokens[1];
				
				String address=tokens[2];
				String type=tokens[3];
				Restaurant r=new Restaurant(Long.parseLong(id),name,address,RestaurantType.valueOf(type));
				allRestaurants.add(r);
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
		
		
		return allRestaurants;
	}

	//seed the data
	public static HashMap<Long,Restaurant> seedData(){
		HashMap<Long,Restaurant> restaurants=new HashMap<>();
		for(int i=0;i<100;i++) {
			
			Restaurant r=new Restaurant();
			r.setAddress("Address - "+i);
			r.setName("Restaurant - "+i);
			r.setId((long)i+1);
			if(i<5) {
				r.setType(RestaurantType.picerija);
			}else if(i>5 && i<15) {
				
				r.setType( RestaurantType.domaca_kuhinja );
			}else if(i>16 && i<25) {
				
				r.setType( RestaurantType.rostilj );
			}else if(i>30 && i<40) {
				
				r.setType( RestaurantType.kineski_restoran );
			}else if(i>41 && i<50) {
				
				r.setType( RestaurantType.indijski_restoran );
			}else {
				
				r.setType( RestaurantType.poslasticarnica );
			}
			restaurants.put(r.getId(), r);
			
		}
		return restaurants;
		
	}

	public static void saveRestaurantMap(HashMap<Long, Restaurant> restaurants) {
		// TODO Auto-generated method stub
		BufferedWriter writer=null;
		try{
			writer=new BufferedWriter(new FileWriter(Constants.KEY_RESTAURANTS_FILE));
			String save="";
			for(Restaurant s:restaurants.values()) {
				save+=saveRestaurantFileFormat(s);
			}
			writer.write(save);
			
			
		}catch(Exception e) {
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

	private static String saveRestaurantFileFormat(Restaurant s) {
		// TODO Auto-generated method stub
		
		return s.getId()+Constants.KEY_DB_SEPARATOR+
				s.getName()+Constants.KEY_DB_SEPARATOR+
				s.getAddress()+Constants.KEY_DB_SEPARATOR+
				s.getType()+Constants.KEY_DB_SEPARATOR+"\n";
	}

	public static void save(Restaurant res) {
		// TODO Auto-generated method stub
		ArrayList<Restaurant> allRes=loadRestaurants();
		int size=allRes.size()-1;
		Restaurant last=allRes.get(size);
		Long idlast=last.getId();
		res.setId(idlast+1);
		allRes.add(res);
		saveOneRes(allRes);
	}
	
	private static void saveOneRes(ArrayList<Restaurant> restaurants) {
		BufferedWriter writer=null;
		try{
			writer=new BufferedWriter(new FileWriter(Constants.KEY_RESTAURANTS_FILE));
			String save="";
			for(Restaurant s:restaurants) {
				save+=saveRestaurantFileFormat(s);
			}
			writer.write(save);
			
			
		}catch(Exception e) {
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

	@SuppressWarnings("unchecked")
	public static void deleteRes(Long id) {
		// TODO Auto-generated method stub
		
		ArrayList<Restaurant> restorani=loadRestaurants();
		for (Restaurant r: (ArrayList<Restaurant>) restorani.clone()) {
			if(r.getId().equals(id)) {
			System.out.println("Obrisi restoran sa nazivom"+r.getName());
			System.out.println("Obrisi restoran sa id"+r.getId());
			restorani.remove(r);
			saveOneRes(restorani);
			}
		}
		/*for(Restaurant r:restorani) {
			if(r.getId().equals(id)) {
				System.out.println("Obrisi restoran sa nazivom"+r.getName());
				System.out.println("Obrisi restoran sa id"+r.getId());
				//restorani.remove(r);
				restorani.remove(r);
				saveOneRes(restorani);
			}
		}*/
		
		
	}

	public static void editRes(Restaurant res) {
		// TODO Auto-generated method stub
		ArrayList<Restaurant> restorani=loadRestaurants();
		
		for (Restaurant r: restorani) {
			if(r.getId().equals(res.getId())) {
			System.out.println("Edituj SADA USAO U EDITRES ");
				r.setName(res.getName());
				r.setAddress(res.getAddress());
				r.setType(res.getType());
			saveOneRes(restorani);
			}
		}
	}

	public static ArrayList<Restaurant> search(Restaurant res) {
		 System.out.println("Usli ovde");
		// TODO Auto-generated method stub
		 ArrayList<Restaurant> allRes=loadRestaurants();
		 ArrayList<Restaurant> returnSearch=new ArrayList<>();
		 if(!res.getName().equals("")) {
			 System.out.println("Usli u if get name");
			 for(Restaurant r:allRes) {
				 if(r.getName().contains(res.getName())) {
					 returnSearch.add(r);
				 }
	
				
			 }
			 return returnSearch;
		}else if(!res.getAddress().equals("")){
			System.out.println("Usli u if get address");
			for(Restaurant r:allRes) {
				 if(r.getAddress().contains(res.getAddress())) {
					 returnSearch.add(r);
				 }
	
				
			 }
			 return returnSearch;
		
		}else if(!res.getType().equals(RestaurantType.none)){
			System.out.println("Usli u if get type");
			for(Restaurant r:allRes) {
				System.out.println("Usli u if get type"+r.getType());
				System.out.println("Usli u if get type"+res.getType());
				 if(r.getType().equals(res.getType())) {
					 System.out.println("Usli u if get type"+r.getType().equals(res.getType()));
					 returnSearch.add(r);
				 }
	
			 }
			 return returnSearch;
		}else if(!res.getName().equals("") && !res.getAddress().equals("") ) {
			System.out.println("Usli u if get address i name");
			for(Restaurant r:allRes) {
				 if(r.getAddress().contains(res.getAddress()) && r.getAddress().contains(res.getAddress())) {
					 returnSearch.add(r);
				 }
	
				
			 }
			 return returnSearch;
			
		}else if(!res.getName().equals("") && !res.getType().equals(RestaurantType.none)) {
			 System.out.println("Usli u if get name i kategorija");
			 for(Restaurant r:allRes) {
				 if(r.getName().contains(res.getName()) && r.getType().equals(res.getType())) {
					 returnSearch.add(r);
				 }
	
				
			 }
			 return returnSearch;
		}else if(!res.getAddress().equals("") && !res.getType().equals(RestaurantType.none)) {
			System.out.println("Usli u if get type i kategorija i adresa");
			for(Restaurant r:allRes) {
			
				 if(r.getType().equals(res.getType()) && r.getAddress().contains(res.getAddress())) {
				
					 returnSearch.add(r);
				 }
	
			 }
			 return returnSearch;
			
		}else if(!res.getAddress().equals("") && !res.getType().equals(RestaurantType.none) && !res.getName().equals("")) {
			System.out.println("Usli u if get type i kategorija i adresa i name");
			for(Restaurant r:allRes) {
				 if(r.getType().equals(res.getType()) && r.getAddress().contains(res.getAddress()) && r.getType().equals(res.getType())) {
						 returnSearch.add(r);
				 }
			 }
			 return returnSearch;
		}
		
		return returnSearch;
	
		
	}
	
	
	
	

	
	
	
	
}
