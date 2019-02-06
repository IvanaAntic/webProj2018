package beans;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Stavke porudžbine (svaka stavka porudžbine je jedno jelo ili piće sa
količinom [brojem porcija] koja se poručuje).
 * 
 * 
 * */
public class Stavka {

	private Long idStavka;
	private Long idArticle;
	private int amount;
	private Long customerId;
	private Long delivererId;
			
	private double price;
	private String note;
	private Status status;

	private Date orderDate;
	private boolean deleted;
	 SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	




	public Stavka(Long idStavka, Long idArticle, int amount, Long customerId, Long delivererId, double price,
			String note, Status status) {
		super();
		this.idStavka = idStavka;
		this.idArticle = idArticle;
		this.amount = amount;
		this.customerId = customerId;
		this.delivererId = delivererId;
		this.price = price;
		this.note = note;
		this.status = status;
		this.orderDate = orderDate;
	}




	public Stavka(Long idStavka, Long idArticle, int amount, Long customerId, Long delivererId, double price,
			String note, Status status, boolean deleted) {
		super();
		this.idStavka = idStavka;
		this.idArticle = idArticle;
		this.amount = amount;
		this.customerId = customerId;
		this.delivererId = delivererId;
		this.price = price;
		this.note = note;
		this.status = status;
	
		this.deleted = deleted;
		
	}




	public Stavka() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public Stavka(Long idStavka, Long idArticle, int amount) {
		super();
		this.idStavka = idStavka;
		this.idArticle = idArticle;
		this.amount = amount;
		
	}




	public Long getCustomerId() {
		return customerId;
	}




	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}




	public Long getDelivererId() {
		return delivererId;
	}




	public void setDelivererId(Long delivererId) {
		this.delivererId = delivererId;
	}




	public double getPrice() {
		return price;
	}




	public void setPrice(double price) {
		this.price = price;
	}




	public String getNote() {
		return note;
	}




	public void setNote(String note) {
		this.note = note;
	}




	public Status getStatus() {
		return status;
	}




	public void setStatus(Status status) {
		this.status = status;
	}







	public Date getOrderDate() {
		return orderDate;
	}




	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}




	public Long getIdStavka() {
		return idStavka;
	}
	public void setIdStavka(Long idStavka) {
		this.idStavka = idStavka;
	}
	public Long getIdArticle() {
		return idArticle;
	}
	public void setIdArticle(Long idArticle) {
		this.idArticle = idArticle;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}

	
	
	public SimpleDateFormat getSimpleDate() {
		return simpleDate;
	}




	public void setSimpleDate(SimpleDateFormat simpleDate) {
		this.simpleDate = simpleDate;
	}



	@Override
	public String toString() {
		return "Stavka [ idArticle=" + idArticle + ", amount=" + amount + "]";
	}




	public boolean isDeleted() {
		return deleted;
	}




	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
	
	
	
	
	
	
}
