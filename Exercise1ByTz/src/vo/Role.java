package vo;

import java.util.ArrayList;
import java.util.List;

public class Role {
	private String roleName;
	private String resource;
	
	
	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Role(String roleName, String resource) {
		super();
		this.roleName = roleName;
		this.resource = resource;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public List<String> getUrls(Role role)
	{
		ArrayList<String> urlList = new ArrayList<String>();
		String[] strArray = role.getResource().split(",|£¬");
		
		for(int i=0; i<strArray.length; i++)
		{
			urlList.add(strArray[i]);
		}
		return urlList;
		
	}
	@Override
	public String toString() {
		return "Role [roleName=" + roleName + ", resource=" + resource + "]";
	}
	
	
}
