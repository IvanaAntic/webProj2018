package services;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Role;
import beans.Status;
import beans.Stavka;
import beans.User;
import dao.Constants;
import dao.StavkaDao;
import dao.UserDao;

@Path("/users")
public class UserService {

	

	
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(User user, @Context HttpServletRequest request){
		System.out.println("OK");
		
		ArrayList<User> users = UserDao.loadUsers();
		for(User u:users) {
			System.out.println("korisnik u password"+u.getPassword());
			System.out.println("korisnik u password"+user.getPassword());
			if(u.getUsername().equals(user.getUsername())) {  //prvo proverimo da li useranem postoji
				if(u.getPassword().equals(user.getPassword())) {
					//stavi ga na sesiju
					request.getSession().setAttribute(Constants.KEY_LOGGED, u);
					return Response.ok(u,MediaType.APPLICATION_JSON).build();
				}
				
			}
			
			
		}
		return Response.ok(new User(),MediaType.APPLICATION_JSON).build();
		
	}
	/*	for(User u:users){
			System.out.println("KORISNIK: " + u.getUsername() + " " + u.getPassword());
			System.out.println("KORISNIK: " + user.getUsername() + " " + user.getPassword());
			System.out.println("-----------------");
			if(u.getUsername().equals(user.getUsername())){
				if(u.getPassword().equals(user.getPassword())){
					//stavimo tog jednog na sesiju
					request.getSession().setAttribute(Constants.KEY_LOGGED_USER, u);
					return Response.ok(u, MediaType.APPLICATION_JSON).build();
				}else{
					return Response.ok(new User(), MediaType.APPLICATION_JSON).build();

				}
			}
			
		}
		
		return Response.ok(new User(), MediaType.APPLICATION_JSON).build();
	}*/
	
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerU(User userFront) {
		System.out.println("OK");
		System.out.println("OK"+userFront.getEmail());
		
		if(UserDao.getUserByUsername(userFront.getUsername())!=null) {
			return Response.status(409).entity("Korisnicko ime je zauzeto").build();
		}else {
			
			UserDao.save(userFront);
			
		}
		
		
		return Response.ok(userFront ).build();
		
		
	}
	@POST
	@Path("/logout")
	public static void logout( @Context HttpServletRequest request) {
		request.getSession().invalidate();
	}
	
	@POST
	@Path("/changeRole/{username}/{role}")
	public String changeRole(@Context HttpServletRequest request,@PathParam("username") String username,@PathParam("role") String role) {
		
		System.out.println("username koji zelimo da promenimo"+username);
		System.out.println("role koji zelimo da promenimo"+role);
	
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.KEY_LOGGED);
		if(user==null) {
			return "nyll";
		}
		
		if(user.getRole().equals(Role.administrator)) {
			UserDao.changeRole(username,role);
			System.out.println("Usli u if");
			
		}else {
		//	return "Niste administrator, ne mozete menjati ulogu";
		}
		return "Uloga uspesno promenjena";
	}
	
	
	//get ordeers that are idDeliverer 0-znaci nisu nikom dodeljene
	@GET
	@Path("/allpreuzimanje")
	public Response preuzimanje(@Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.KEY_LOGGED);
		ArrayList<Stavka>  allOrders=StavkaDao.loadStavka();
		ArrayList<Stavka> nepreuzete=new ArrayList<>();
		Long idDeliverer=(long) 0;
		if(user.getRole().equals(Role.dobavljac)) {
			//vrati sve poruzdbine koje nisu nikom dodeljene
			for(Stavka s: allOrders) {
				if(s.getDelivererId().equals(idDeliverer)   && s.getStatus().equals(Status.Poruceno)) {
					nepreuzete.add(s);
					
				}
			}
		}
		
		return Response.ok(nepreuzete,MediaType.APPLICATION_JSON).build();
		
	}
	
	//get ordeers that are idDeliverer 0-znaci nisu nikom dodeljene
	@GET
	@Path("/alldostavautoku")
	public Response utoku(@Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.KEY_LOGGED);
		ArrayList<Stavka>  allOrders=StavkaDao.loadStavka();
		ArrayList<Stavka> dostavautoku=new ArrayList<>();
		Long idDeliverer=(long) 0;
		if(user.getRole().equals(Role.dobavljac)) {
			//vrati sve poruzdbine koje nisu nikom dodeljene
			for(Stavka s: allOrders) {
				if(s.getStatus().equals(Status.Dostava_u_toku) && s.getDelivererId().equals(user.getId())) {
					//s.setStatus(Status.Dostavljeno);
					dostavautoku.add(s);
				}
			}
		}
		
		return Response.ok(dostavautoku,MediaType.APPLICATION_JSON).build();
		
	}
}
