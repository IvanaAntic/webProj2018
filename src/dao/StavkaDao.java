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

import beans.Article;
import beans.Restaurant;
import beans.Status;
import beans.Stavka;



public class StavkaDao {

	
	
	public static ArrayList<Stavka> loadStavka(){
		ArrayList<Stavka> allStavka=new ArrayList<>();
		BufferedReader in=null;
		try {
		
			in=new BufferedReader(new FileReader(new File(Constants.KEY_STAVKA_FILE)));
			String line="";
			while((line=in.readLine())!=null) {
				String tokens[]=line.split("\\|");
				String id=tokens[0];
				String idArticle=tokens[1];
				String amount=tokens[2];
				String cId=tokens[3];
				String dId=tokens[4];
				String price=tokens[5];
				String note=tokens[6];
				String status=tokens[7];
				String deletes=tokens[8];
				 //SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				 Stavka s=new Stavka(Long.parseLong(id),Long.parseLong(idArticle),Integer.parseInt(amount),Long.parseLong(cId),Long.parseLong(dId),Double.parseDouble(price),note,Status.valueOf(status),Boolean.valueOf(deletes));
				
				 Date orderDate = s.getSimpleDate().parse(tokens[8]);
				s.setOrderDate(orderDate);
				
				 allStavka.add(s);
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
		
		
		return allStavka;
	}
	
	

	public static String saveStavkaFileFormat(Stavka s) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		//1|1|2|1|2|200|not3|Poruceno|null|
	 SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return s.getIdStavka()+Constants.KEY_DB_SEPARATOR+
				s.getIdArticle()+Constants.KEY_DB_SEPARATOR+
				s.getAmount()+Constants.KEY_DB_SEPARATOR+
				s.getCustomerId()+Constants.KEY_DB_SEPARATOR+
				s.getDelivererId()+Constants.KEY_DB_SEPARATOR+
				s.getPrice()+Constants.KEY_DB_SEPARATOR+
				s.getNote()+Constants.KEY_DB_SEPARATOR+
				s.getStatus()+Constants.KEY_DB_SEPARATOR+
				simpleDate.format(s.getOrderDate())+Constants.KEY_DB_SEPARATOR+
				s.isDeleted() +Constants.KEY_DB_SEPARATOR+"\n";
	}
	

	public static void saveOne(Stavka stavka,Long userId) {
		ArrayList<Stavka> stavke=loadStavka();
		
		if(!stavke.isEmpty()) {
			int size=stavke.size()-1;
			Stavka poslednja=stavke.get(size);
			Long idPoslenja=poslednja.getIdStavka();
			stavka.setIdStavka(idPoslenja+1);
		}
		
		if(stavke.isEmpty()) {
			Long size=(long) 1;
			stavka.setIdStavka(size);
		}
		
		
		stavka.setCustomerId(userId);
		stavka.setDelivererId(Long.parseLong("0"));
		stavka.setPrice(izracunajCenu(stavka.getIdArticle(),stavka.getAmount()));
		stavka.setNote("Note-1");
		stavka.setStatus(Status.Poruceno);
		stavka.setOrderDate(new Date());
		stavka.setDeleted(false);
		
		
		stavke.add(stavka);
		
		
		save(stavke);	
	}
	
	private static double izracunajCenu(Long idArticle, int amount) {
		// TODO Auto-generated method stub
		double cena=0;
		Article artikl=ArticleDao.findOne(idArticle);
		double cenaArtikla=artikl.getPrice();
		cena=cenaArtikla*amount;
		return cena;
	}



	public static void save(ArrayList<Stavka> stavke) {
		BufferedWriter writer=null;
		try{
			writer=new BufferedWriter(new FileWriter(Constants.KEY_STAVKA_FILE));
			String save="";
			for(Stavka s:stavke) {
				save+=saveStavkaFileFormat(s);
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
	
	
	
	
	
	
}
