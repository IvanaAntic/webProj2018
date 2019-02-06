package beans;
/*
○ Restorani:
■ Naziv
■ Adresa
■ Kategorija

*/
public class Restaurant {
	private Long id;
	private String name;
	private String address;
	private RestaurantType type;

	
	public Restaurant() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Restaurant(Long id, String name, String address, RestaurantType type) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.type = type;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public RestaurantType getType() {
		return type;
	}
	public void setType(RestaurantType type) {
		this.type = type;
	}
	
	
	
	
	
}
