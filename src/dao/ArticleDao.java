package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import beans.Article;
import beans.ArticleType;
import beans.Restaurant;
import beans.Vechicle;

public class ArticleDao {

	
	//load from file
	public static ArrayList<Article> loadArticles(){
		ArrayList<Article> articles=new ArrayList<>();
		BufferedReader in=null;
		try {
			File file = new File(Constants.KEY_ARTICLES_FILE);
			in= new BufferedReader(new FileReader(file));
			String line="";
			while((line=in.readLine())!=null) {
				String tokens[]=line.split("\\|");
				String id=tokens[0];
				String price=tokens[1];
				String name=tokens[2];
				String description=tokens[3];
				String quantity=tokens[4];
				String type=tokens[5];
				String idRestaurant=tokens[6];
				Article a= new Article(Long.parseLong(id),name,Double.parseDouble(price),description,Double.parseDouble(quantity),ArticleType.valueOf(type),Long.parseLong(idRestaurant));
				
				articles.add(a);
			}
		}catch(Exception e) {
			//e.printStackTrace();
		}finally{
			if (in != null) {
				try {
					in.close();
				}
				catch (Exception e) { }
			}
		
		}
		
		return articles;
	}
	
	
	public static String getArticleFileFormat(Article a){
		return a.getId()+Constants.KEY_DB_SEPARATOR +
				a.getPrice()+Constants.KEY_DB_SEPARATOR +
				a.getName()+Constants.KEY_DB_SEPARATOR +
				a.getDescription()+Constants.KEY_DB_SEPARATOR +
				a.getQuantity()+Constants.KEY_DB_SEPARATOR +
				a.getType()+Constants.KEY_DB_SEPARATOR +
				a.getRestaurantId() + Constants.KEY_DB_SEPARATOR + 
				"\n";
	}
	
	//seed the data
	public static HashMap<Long, Article> SeedData() {
		HashMap<Long, Article> articles = new HashMap<>();
		for (int i = 0;	i < 100;	i++){
			Article a = new Article();
			a.setDescription("Description - " + i);
			a.setId((long)i + 1);
			a.setName("Article - " + i);
			a.setPrice(16.32F + i + Math.sqrt(i+20));
			a.setQuantity(12.44 + i);
			a.setType( i%2 == 0 ? ArticleType.food : ArticleType.drink);
			a.setRestaurantId( (long) i % 3 + 1);
			articles.put(a.getId(), a);
		}
		return articles;
	}

	
	
	public static void saveArticleMap(HashMap<Long, Article> articles){
		BufferedWriter writer=null;
		try{
			writer=new BufferedWriter(new FileWriter(Constants.KEY_ARTICLES_FILE));
			String save="";
			for(Article a: articles.values()){
				save+=getArticleFileFormat(a);
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
	
	public static void save(Article article) {
		ArrayList<Article> allArticles=loadArticles();
		int size=allArticles.size();
		//100
		Article last=allArticles.get(size-1);
		Long idlast=last.getId();
		System.out.println("last id"+idlast);
		article.setId(idlast+1);
		System.out.println("last id now is "+article.getId());
		allArticles.add(article);
		saveArticles(allArticles);
		
	}




	private static void saveArticles(ArrayList<Article> allArticles) {
		// TODO Auto-generated method stub
		BufferedWriter writer=null;
		try{
			writer=new BufferedWriter(new FileWriter(Constants.KEY_ARTICLES_FILE));
			String save="";
			for(Article a: allArticles){
				save+=getArticleFileFormat(a);
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


	public static void deleteA(Long id) {
		// TODO Auto-generated method stub
		
			// TODO Auto-generated method stub
			ArrayList<Article> allA=loadArticles();
			for(Article a: (ArrayList<Article>) allA.clone())  {
				if(a.getId().equals(id)) {
					System.out.println("Brisemo ARTIKAL sa id"+ id);
					allA.remove(a);
					saveArticles(allA);
				}
			}
	}


	public static ArrayList<Article> search(Article a) {
		// TODO Auto-generated method stub
		ArrayList<Article> allA=loadArticles();
		ArrayList<Article> searchA=new ArrayList<>();

		if(!a.getName().equals("") && a.getType().equals(ArticleType.none) && a.getRestaurantId().equals(Long.parseLong("0"))) {
			System.out.println("trazimo po nazivu");
			for(Article article:allA) {
				if(article.getName().contains(a.getName())) {
					searchA.add(article);				}
			}
			
			return searchA;
			
		}else if(!a.getType().equals(ArticleType.none)) {
			System.out.println("trazimo po tipu"+a.getType());
			for(Article article:allA) {
				if(article.getType().equals(a.getType())) {
					searchA.add(article);				}
			}
			
			return searchA;
			
			
			
			
		}else if(!a.getRestaurantId().equals(Long.parseLong("0"))) {
			System.out.println("trazimo po restoranu");
			for(Article article:allA) {
				if(article.getRestaurantId().equals(a.getRestaurantId())) {
					searchA.add(article);				
				}
			}
			
			return searchA;
			
		}else if(!(a.getPrice()==0)) {
			System.out.println("trazimo po ceni");
			for(Article article:allA) {
				if(article.getPrice()==(a.getPrice())) {
					searchA.add(article);				
				}
			}
			
			return searchA;
		}else if(!a.getType().equals(ArticleType.none) && !(a.getPrice()==0) ) {
			System.out.println("trazimo po ceni i tipu");
			for(Article article:allA) {
				if(article.getPrice()==(a.getPrice()) && article.getType().equals(a.getType())) {
					searchA.add(article);				
				}
			}
			
			return allA;
		}else if(!a.getType().equals(ArticleType.none) && !(a.getName().equals(""))) {
			System.out.println("trazimo po name i tipu");
			for(Article article:allA) {
				if(article.getName().contains(a.getName())&& article.getType().equals(a.getType())) {
					searchA.add(article);				
				}
			}
			
			return searchA;
		}else if(!a.getType().equals(ArticleType.none) && !a.getRestaurantId().equals(Long.parseLong("0")) ) {
			System.out.println("trazimo po restorau i tipu");
			for(Article article:allA) {
				if(article.getName().contains(a.getName())&& article.getRestaurantId().equals(a.getRestaurantId())) {
					searchA.add(article);				
				}
			}
			
			return searchA;
		}else if(!(a.getPrice()==0)  && !(a.getName().equals("")) ) {
			System.out.println("trazimo po name i price");
			for(Article article:allA) {
				if(article.getName().contains(a.getName())&& article.getPrice()==(a.getPrice())) {
					searchA.add(article);				
				}
			}
			
			return searchA;
		}else if(!(a.getPrice()==0)  && !a.getRestaurantId().equals(Long.parseLong("0")) ) {
			System.out.println("trazimo po price i restoranu");
			for(Article article:allA) {
				if(article.getPrice()==(a.getPrice()) && article.getRestaurantId().equals(a.getRestaurantId())) {
					searchA.add(article);				
				}
			}
			
			return searchA;
		}else if(!(a.getName().equals(""))  && !a.getRestaurantId().equals(Long.parseLong("0")) ) {
			System.out.println("trazimo po name i restoranu");
			for(Article article:allA) {
				if(article.getName().contains(a.getName()) && article.getRestaurantId().equals(a.getRestaurantId())) {
					searchA.add(article);				
				}
			}
			
			return searchA;
		}else if(!(a.getName().equals(""))  && !a.getRestaurantId().equals(Long.parseLong("0")) && !(a.getPrice()==0) && !a.getType().equals(ArticleType.none) ) {
			System.out.println("trazimo po name i restoranu price i tipu");
			for(Article article:allA) {
				if(article.getName().contains(a.getName()) && article.getRestaurantId().equals(a.getRestaurantId()) && article.getPrice()==(a.getPrice()) && article.getType().equals(a.getType())) {
					searchA.add(article);				
				}
			}
			
			return searchA;
		}
		else if(!(a.getName().equals(""))  && !(a.getPrice()==0) && !a.getType().equals(ArticleType.none) ) {
			System.out.println("trazimo po name  price i tipu");
			for(Article article:allA) {
				if(article.getName().contains(a.getName()) && article.getPrice()==(a.getPrice()) && article.getType().equals(a.getType())) {
					searchA.add(article);				
				}
			}
			
			return searchA;
		}else if(!a.getRestaurantId().equals(Long.parseLong("0")) && !(a.getPrice()==0) && !a.getType().equals(ArticleType.none) ) {
			System.out.println("trazimo po  restoranu price i tipu");
			for(Article article:allA) {
				if( article.getRestaurantId().equals(a.getRestaurantId()) && article.getPrice()==(a.getPrice()) && article.getType().equals(a.getType())) {
					searchA.add(article);				
				}
			}
			
			return searchA;
		}else if(!(a.getName().equals(""))  && !a.getRestaurantId().equals(Long.parseLong("0")) && !(a.getPrice()==0) ) {
			System.out.println("trazimo po name i restoranu price ");
			for(Article article:allA) {
				if(article.getName().contains(a.getName()) && article.getRestaurantId().equals(a.getRestaurantId()) && article.getPrice()==(a.getPrice())) {
					searchA.add(article);				
				}
			}
			
			return searchA;
		}else if(!(a.getName().equals(""))  && !(a.getPrice()==0) && !a.getType().equals(ArticleType.none) ) {
			System.out.println("trazimo po name i price i tipu");
			for(Article article:allA) {
				if(article.getName().contains(a.getName()) && article.getPrice()==(a.getPrice()) && article.getType().equals(a.getType())) {
					searchA.add(article);				
				}
			}
			
			return searchA;
		}else if(!(a.getName().equals(""))  && !a.getRestaurantId().equals(Long.parseLong("0"))  && !a.getType().equals(ArticleType.none) ) {
			System.out.println("trazimo po name i restoranu price");
			for(Article article:allA) {
				if(article.getName().contains(a.getName()) && article.getRestaurantId().equals(a.getRestaurantId()) && article.getType().equals(a.getType())) {
					searchA.add(article);				
				}
			}
			
			return searchA;
		}
		
		
		
		
		
		return searchA;
	
	}


	public static Article findOne(Long id) {
		// TODO Auto-generated method stub
		ArrayList<Article> all=loadArticles();
		for(Article a:all) {
			if(a.getId().equals(id)) {
				return a;
			}
		}
		return null;
	}


	public static void editArticle(Article a) {
		// TODO Auto-generated method stub
		ArrayList<Article> all=loadArticles();
		for(Article article:all) {
			if(article.getId().equals(a.getId())) {
				article.setDescription(a.getDescription());
				article.setName(a.getName());
				article.setPrice(a.getPrice());
				article.setQuantity(a.getQuantity());
				article.setType(a.getType());
				saveArticles(all);
				
			}
		}
	}
	
	
	
	
}
