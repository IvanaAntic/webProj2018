package services;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Favourite;
import beans.Restaurant;
import beans.User;
import dao.Constants;
import dao.FavouriteDao;

@Path("/favourites")
public class FavouriteService {

	@GET
	@Path("/all")
	public Response getAll( @Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.KEY_LOGGED);
		ArrayList<Restaurant> returnFav=new ArrayList<>();
		returnFav=FavouriteDao.getFav(user.getId());
		return Response.ok(returnFav,MediaType.APPLICATION_JSON).build();
	}
}
