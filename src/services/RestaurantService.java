package services;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Restaurant;
import beans.User;
import dao.Constants;
import dao.FavouriteDao;
import dao.RestaurantDao;

@Path("/restaurants")
public class RestaurantService {

	@GET
	@Path("/all")
	public Response getAll(){
		ArrayList<Restaurant> allRestaurants=RestaurantDao.loadRestaurants();
	
		return Response.ok(allRestaurants,MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/save")
	public Response saveRestaurant(Restaurant res) {
		System.out.println("res save"+res.getName());
		RestaurantDao.save(res);
		
		return Response.ok(new Restaurant(),MediaType.APPLICATION_JSON).build();
	}
	
	
	@DELETE
	@Path("/delete/{id}")
	public String deleteRes( @PathParam("id") Long id) {
		//ukloni i na strani 
			
		System.out.println("id restorana koga treba da obriemo je"+id);
		RestaurantDao.deleteRes(id);
		return "Restoran je obrisan";
	}	
	
	
	@POST
	@Path("/editRes")
	public String editRes(Restaurant res) {
		
		RestaurantDao.editRes(res);
		return "Restoran je editovan";
	}
	
	
	
	@POST
	@Path("/searchRes")
	public Response searchRes(Restaurant res) {
		
		ArrayList<Restaurant> returnRes=new ArrayList<>();
		System.out.println("/n usli smo u search res"+ "podaci koje imamo sa fronta" +"" + res.getName()+"--"+res.getAddress()+" ");
		returnRes=RestaurantDao.search(res);
		return Response.ok(returnRes,MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/favourites/{idRes}")
	public String favRes(@PathParam("idRes") Long idRes, @Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.KEY_LOGGED);
		
		if(FavouriteDao.proveriOmiljeno(idRes,user.getId())) {
			return "Vec dodato u omiljene";
		}
		
		FavouriteDao.saveFavourite(idRes,user.getId());
	
		System.out.println("-------------n usli smo u fav res"+ idRes);
		System.out.println("-------------n usli smo u logedd res"+ user.getLastname());
	
		return "Usesno sacuvano u omiljene";
	}
	
	
	
	
	
	
}
