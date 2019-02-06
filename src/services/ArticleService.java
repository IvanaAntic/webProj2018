package services;

import java.util.ArrayList;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Article;
import dao.ArticleDao;
import dao.RestaurantDao;

@Path("/articles")
public class ArticleService {

	
	//nabavi sve artikle
	
	@GET
	@Path("/all")
	public Response getAll() {
		ArrayList<Article> allArticles =ArticleDao.loadArticles();
		return Response.ok(allArticles,MediaType.APPLICATION_JSON).build();
	}
	
	
	@POST
	@Path("/save")
	public Response saveArticle(Article article) {
		
		ArticleDao.save(article);
		return Response.ok(new Article(),MediaType.APPLICATION_JSON).build();
	}

	
	@DELETE
	@Path("/delete/{id}")
	public String deleteRes( @PathParam("id") Long id) {
		//ukloni i na strani 
			
		System.out.println("id restorana koga treba da obriemo je"+id);
		ArticleDao.deleteA(id);
		return "Restoran je obrisan";
	}	
	@POST
	@Path("/search")
	public Response searchArticle(Article a) {
		System.out.println("USLI SMO U SEARCH");
		ArrayList<Article> returnArticle =new ArrayList<>();
		System.out.println("id res in a"+a.getRestaurantId());
		returnArticle=ArticleDao.search(a);
		
		return Response.ok(returnArticle,MediaType.APPLICATION_JSON).build();
	}
	
	
	@POST
	@Path("/editArticle")
	public String editArticle(Article a) {
		System.out.println("USLI SMO U izmenu"+a.getDescription()+"ID:"+a.getId());
		ArticleDao.editArticle(a);
		
		return "uspesno izmenjeno";
	}

}
