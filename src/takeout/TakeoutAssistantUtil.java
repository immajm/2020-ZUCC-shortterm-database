package takeout;

import takeout.control.AdminManager;
import takeout.itf.IAdminManager;

public class TakeoutAssistantUtil {
	public static IAdminManager userManager=new AdminManager();//需要换成自行设计的实现类
	//public static IAdminManager userManager=new CusManager();
}
