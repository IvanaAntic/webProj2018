package services;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Vechicle;
import dao.VechicleDao;

@Path("/vechicles")
public class VechicleService {

	
	
	@GET
	@Path("/all")
	public Response allVechicles() {
		ArrayList<Vechicle> allVechicles=VechicleDao.loadVechicle();
		return Response.ok(allVechicles,MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/add")
	public Response addOne(Vechicle v) {
		System.out.println("Add one vehicle");
		VechicleDao.save(v);
		return Response.ok(new Vechicle(),MediaType.APPLICATION_JSON).build();
	}
	
	
	
	@DELETE
	@Path("/delete/{id}")
	public String remove(@PathParam("id") Long id) {
		VechicleDao.deleteV(id);
		return "Uspesno obrisano!";
	}
}
