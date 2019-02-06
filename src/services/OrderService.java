package services;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Article;
import beans.Role;
import beans.Status;
import beans.Stavka;
import beans.User;
import dao.ArticleDao;
import dao.Constants;

import dao.StavkaDao;

@Path("/order")
public class OrderService {

	
	
	/*@POST
	@Path("/addOne")
	public Response saveOrder(Order stavka,@Context HttpServletRequest request) {
		
		for(int i=0;i<stavka.getStavka().size();i++) {
			for(Stavka s: stavka.getStavka()) {

				System.out.print("ovde smo id"+s.getIdArticle());

				System.out.print("ovde smo  amount"+s.getAmount());
			}
			
			
		}
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.KEY_LOGGED);
		
		OrderDao.addOrder(stavka.getStavka(),user.getId());
		
		
		
		
		
		return Response.ok().build();
	}
	*/
	
	
	@POST
	@Path("/stavka")
	public static Response saveStavkaToFile(Stavka stavka,@Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.KEY_LOGGED);
		StavkaDao.saveOne(stavka,user.getId());
		return Response.ok().build();
	}
	
	
	
	@GET
	@Path("/all")
	public static Response getAllOrders(@Context HttpServletRequest request) {
		System.out.print("Get all orders");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.KEY_LOGGED);
		ArrayList<Stavka> allOrders=StavkaDao.loadStavka();
		ArrayList<Stavka> myOrders=new ArrayList<>();
		for(Stavka s:allOrders) {
			if(user.getId().equals(s.getCustomerId())) {
				myOrders.add(s);
				
			}
		}
		
		
		return Response.ok(myOrders,MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/{idArticle}")
	public static Response saveStavkaToFile(@PathParam("idArticle") Long idArticle,@Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.KEY_LOGGED);
	
		
		ArrayList<Article> allArticle=ArticleDao.loadArticles();
		
		for(Article a: allArticle) {
			if(a.getId().equals(idArticle)) {
				return Response.ok(a,MediaType.APPLICATION_JSON).build();
			}
			
		}
	
		
		return Response.ok().build();
	}
	
	@POST
	@Path("/utoku/{idStavka}")
	public static String preuzmi(@PathParam("idStavka") Long idStavka,@Context HttpServletRequest request) {
		System.out.println("usli smo u preuzmi id je" );
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.KEY_LOGGED);
		System.out.println("usli smo u preuzmi id je Stavka" +idStavka);
		System.out.println("Ko nam je na sesiji " +user.getId());
		
		ArrayList<Stavka> allStavka=StavkaDao.loadStavka();
		//proveri dostavljaca da li ima neke porudzbine u toku
		for(Stavka s: allStavka) {
			
			
				if(s.getDelivererId().equals(user.getId())) {
					//da li on ima neke u tmu
					if(s.getStatus().equals(Status.Dostava_u_toku)) {
						System.out.print("Nas user ima dostavu u toku ");
					return "Nije moguce imas dostvau u toku";
					}
				}
			
		}
		
		
		for(Stavka s: allStavka) {
			if(s.getIdStavka().equals(idStavka)) {
				System.out.print("usli smo u IF" );
				s.setDelivererId(user.getId());
				s.setStatus(Status.Dostava_u_toku);
				//sacuvaj u bazi
				
				
			}
		}
		StavkaDao.save(allStavka);
		return "Ok preuzeta porudzbina";
	}
	
	@POST
	@Path("/dostavljeno/{idStavka}")
	public static Response dostavljeno(@PathParam("idStavka") Long idStavka,@Context HttpServletRequest request) {
		System.out.print("usli smo u preuzmi id je" );
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.KEY_LOGGED);
		System.out.print("usli smo u preuzmi id je" +idStavka);
		System.out.print("usli smo u preuzmi id je" +user.getId());
		ArrayList<Stavka> allStavka=StavkaDao.loadStavka();
		for(Stavka s: allStavka) {
			if(s.getIdStavka().equals(idStavka)) {
				System.out.print("usli smo u IF" );
				
				s.setStatus(Status.Dostavljeno);
				//sacuvaj u bazi
				
				
			}
		}
		StavkaDao.save(allStavka);
		return Response.ok().build();
	}
	@GET
	@Path("/allAdmin")
	public static Response adminOrders() {
		System.out.print("Get all orders for admin panel");
		
		ArrayList<Stavka> allOrders=StavkaDao.loadStavka();
		ArrayList<Stavka> returnOrders=new ArrayList<>();
		//sve koje su logicki neobrisane
		//TODO
		for(Stavka s:allOrders) {
			System.out.print("Get all orders for admin panel for--- "+s.isDeleted());
			System.out.print("Get all orders for admin panel for---");
			if(!s.isDeleted()) {
				returnOrders.add(s);
			}
		}
		
		return Response.ok(returnOrders,MediaType.APPLICATION_JSON).build();
	}
	
	
	@DELETE
	@Path("/delete/{idStavka}")
	public static Response obrisi(@PathParam("idStavka") Long idStavka,@Context HttpServletRequest request) {
		ArrayList<Stavka> allStavka=StavkaDao.loadStavka();
		System.out.print("usli smo u preuzmi id je" );
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.KEY_LOGGED);
		
			for(Stavka s: allStavka) {
				if(s.getIdStavka().equals(idStavka)) {
					s.setDeleted(true);
					//sacuvaj u bazi
				}
			}
		
		StavkaDao.save(allStavka);
		return Response.ok().build();
	}
	
}
