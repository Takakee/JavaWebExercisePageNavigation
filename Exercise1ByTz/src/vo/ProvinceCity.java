package vo;

public class ProvinceCity {
	private String id;
	private String chrName;
	public ProvinceCity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProvinceCity(String id, String chrName) {
		super();
		this.id = id;
		this.chrName = chrName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getChrName() {
		return chrName;
	}
	public void setChrName(String chrName) {
		this.chrName = chrName;
	}
	@Override
	public String toString() {
		return "ProvinceCity [id=" + id + ", chrName=" + chrName + "]";
	}
	
}
