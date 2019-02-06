package beans;

public class Favourite {
	 private Long idF;
	 private Long  userId;
	 private Long idRes;
	 
		public Favourite() {
			super();
			// TODO Auto-generated constructor stub
		}
		public Favourite(Long idF, Long userId, Long idRes) {
			super();
			this.idF = idF;
			this.userId = userId;
			this.idRes = idRes;
		}
		 
	 
	public Long getIdF() {
		return idF;
	}
	public void setIdF(Long idF) {
		this.idF = idF;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getIdRes() {
		return idRes;
	}
	public void setIdRes(Long idRes) {
		this.idRes = idRes;
	}

	 
	 
	 
}
