package beans;
/*	Artikli – Jela i pića koja se poručuju:
○ Naziv
○ Jedinična cena
○ Opis
○ Količina (grami za jelo, mililitri za piće)
*/
public class Article {
	
	private Long id;
	private String name;
	private double price;
	private String description;
	private double quantity;
	private ArticleType type;
	private Long restaurantId;
	
	
	public Article() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Article(Long id, String name, double price, String description, double quantity, ArticleType type,
			Long restaurantId) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
		this.quantity = quantity;
		this.type = type;
		this.restaurantId = restaurantId;
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double d) {
		this.quantity = d;
	}
	public ArticleType getType() {
		return type;
	}
	public void setType(ArticleType type) {
		this.type = type;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}
	
	
	
	
	
	
}
