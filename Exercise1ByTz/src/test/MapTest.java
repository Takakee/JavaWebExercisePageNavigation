package test;

import java.util.HashMap;
import java.util.Map;

public class MapTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		String str = "33";
		map.put("pageSize", str);
//		int pagesize = (int)map.get("pageSize");
		int pagesize = Integer.parseInt(map.get("pageSize").toString());
		System.out.println("object是string类型，要这样转int类型，pagesize："+pagesize);
		String pagesizeStr = (String)map.get("pageSize");
		System.out.println("object是string类型，要这样保持string类型pagesize："+pagesizeStr);
	}

}
