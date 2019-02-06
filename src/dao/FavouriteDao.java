package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import beans.Favourite;
import beans.Restaurant;

public class FavouriteDao {

	
	
	
	public static ArrayList<Favourite> loadFavourite(){
		ArrayList<Favourite> allFav=new ArrayList<>();
		BufferedReader in=null;
		try {
			File file=new File(Constants.KEY_FAV_FILE);
			in=new BufferedReader(new FileReader(file));
			String line="";
			while((line=in.readLine())!=null) {
				String tokens[]=line.split("\\|");
				String id=tokens[0];
				String idU=tokens[1];
				String idR=tokens[2];
				Favourite fav=new Favourite(Long.parseLong(id),Long.parseLong(idU),Long.parseLong(idR));
				allFav.add(fav);
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
		
		
		
		return allFav;
	}

	public static void saveFavourite(Long idRes, Long idUser) {
		// TODO Auto-generated method stub
		ArrayList<Favourite> allFav=loadFavourite();
		int size=allFav.size()-1;
		Favourite last=allFav.get(size);
		Long idLast=last.getIdF();
		Favourite save=new Favourite(idLast+1,idUser,idRes);
		allFav.add(save);
		saveToFile(allFav);
	}

	private static void saveToFile(ArrayList<Favourite> allFav) {
		// TODO Auto-generated method stub
		
		BufferedWriter writer=null;
		
		try {
			writer=new BufferedWriter(new FileWriter(Constants.KEY_FAV_FILE));
			String save="";
			for(Favourite f:allFav) {
				save+=fileFormat(f);
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

	private static String fileFormat(Favourite f) {
		// TODO Auto-generated method stub
		return f.getIdF()+Constants.KEY_DB_SEPARATOR+
				f.getUserId()+Constants.KEY_DB_SEPARATOR
				+f.getIdRes() + Constants.KEY_DB_SEPARATOR+
				"\n";
	}

	public static ArrayList<Restaurant> getFav(Long idUser) {
		// TODO Auto-generated method stub
		ArrayList<Favourite> allFav=loadFavourite();
		ArrayList<Restaurant> allRes=RestaurantDao.loadRestaurants();
		ArrayList<Restaurant> returnFavRes=new ArrayList<>();
		for(Favourite f:allFav) {
			if(f.getUserId().equals(idUser)) {
				for(Restaurant r:allRes) {
					if(f.getIdRes().equals(r.getId())) {
						returnFavRes.add(r);
						System.out.println("restoran"+r.getName() +"" +r.getAddress());
					
					}
				}
			
			}
		}
		return returnFavRes;
	}

	public static boolean proveriOmiljeno(Long idRes, Long idUser) {
		// TODO Auto-generated method stub
		ArrayList<Favourite> allFav=loadFavourite();
		for(Favourite f:allFav) {
			if(f.getUserId().equals(idUser) && f.getIdRes().equals(idRes)) {
				//znaci vec postoji
				return true;
			}
			
		}
		//ne postoji sve ok
		return false;
		
	}
	
	
	
}
