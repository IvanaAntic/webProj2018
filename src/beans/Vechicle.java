package beans;

import java.util.Date;

public class Vechicle {

	private Long id;
	private String mark;
	private String model;
	private VechicleType type;
	private String registration;
	private Date yearOfDistribution;
	private boolean used;
	private String note;	
	
	public Vechicle() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Vechicle(Long id, String mark, String model, VechicleType type, String registration, Date yearOfDistribution,
			boolean used, String note) {
		super();
		this.id = id;
		this.mark = mark;
		this.model = model;
		this.type = type;
		this.registration = registration;
		this.yearOfDistribution = yearOfDistribution;
		this.used = used;
		this.note = note;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getMark() {
		return mark;
	}
	
	public void setMark(String mark) {
		this.mark = mark;
	}
	
	public String getModel() {
		return model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	public VechicleType getType() {
		return type;
	}
	
	public void setType(VechicleType type) {
		this.type = type;
	}
	
	public String getRegistration() {
		return registration;
	}
	public void setRegistration(String registration) {
		this.registration = registration;
	}
	public Date getYearOfDistribution() {
		return yearOfDistribution;
	}
	public void setYearOfDistribution(Date yearOfDistribution) {
		this.yearOfDistribution = yearOfDistribution;
	}
	public boolean isUsed() {
		return used;
	}
	public void setUsed(boolean used) {
		this.used = used;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
	
	
	
	
}
