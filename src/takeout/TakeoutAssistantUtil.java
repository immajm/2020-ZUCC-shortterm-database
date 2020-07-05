package takeout;

import takeout.control.UserManager;
import takeout.itf.IUserManager;

public class TakeoutAssistantUtil {
	public static IUserManager userManager=new UserManager();//需要换成自行设计的实现类
}
