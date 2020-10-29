package vo;

public class Download {
	private int id;			// 表示
	private String name;    // 显示的名字
	private String path;    // 资源尽量放WEB-INF目录下，存放该地址
	private String description;
	private int size;       // 文件大小，字节为单位
	private int star;       // 星级
	private String image;   // 图片路径及名称
	public Download() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Download(int id, String name, String path, String description, int size, int star, String image) {
		super();
		this.id = id;
		this.name = name;
		this.path = path;
		this.description = description;
		this.size = size;
		this.star = star;
		this.image = image;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "Download [id=" + id + ", name=" + name + ", path=" + path + ", description=" + description + ", size="
				+ size + ", star=" + star + ", image=" + image + "]";
	}
	
	
}
