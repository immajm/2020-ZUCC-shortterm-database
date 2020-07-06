package takeout.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import takeout.TakeoutAssistantUtil;
import takeout.control.AdminManager;
import takeout.control.CusManager;
import takeout.control.RiderManager;
import takeout.control.ShopManager;
import takeout.model.BeanAdmin;
import takeout.util.BaseException;

public class FrmRegister extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("注册");
	private Button btnCancel = new Button("取消");
	
	private JLabel labelUser = new JLabel("用户id：");
	private JLabel labelName = new JLabel("用户昵称：");
	private JLabel labelPwd = new JLabel("密码：");
	private JLabel labelPwd2 = new JLabel("再次输入密码：");
	private JLabel labelwho = new JLabel("选择身份：");
	private JLabel labelsex = new JLabel("性别：");
	private JLabel labeltel = new JLabel("手机号：");
	private JLabel labelcity = new JLabel("城市：");
	
	private JTextField edtUserId = new JTextField(20);
	private JTextField edtUserName = new JTextField(20);
	private JPasswordField edtPwd = new JPasswordField(20);
	private JPasswordField edtPwd2 = new JPasswordField(20);
	String[] whoData = new String[] {"管理员","商家","顾客","骑手"};
	final JComboBox<String> comboBox_who = new JComboBox<String>(whoData);
	String[] sexData = new String[] {"男","女"};
	final JComboBox<String> comboBox_sex = new JComboBox<String>(sexData);
	
	public FrmRegister(Dialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(250, 350);
		//this.validate();//使生效
		//if(e.getStateChange()==ItemEvent.SELECTED&& comboBox_who.getSelectedItem().toString().equals("顾客")) {
			workPane.add(labelUser);
			workPane.add(edtUserId);
			workPane.add(labelName);
			workPane.add(edtUserName);
			workPane.add(labelPwd);
			workPane.add(edtPwd);
			workPane.add(labelPwd2);
			workPane.add(edtPwd2);
		/*	workPane.add(labelsex);
			workPane.add(labeltel);
			workPane.add(labelcity);*/
			workPane.add(labelwho);//先选择身份
			workPane.add(comboBox_who);
			this.getContentPane().add(workPane, BorderLayout.CENTER);
			this.setSize(250, 350);
			// 屏幕居中显示
			double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
			double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
			this.setLocation((int) (width - this.getWidth()) / 4,
					(int) (height - this.getHeight()) / 2);

			this.validate();//使生效
			
			this.btnCancel.addActionListener(this);
			this.btnOk.addActionListener(this);
			
	}
	/*public FrmRegister(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelUser);
		workPane.add(edtUserId);
		workPane.add(labelName);
		workPane.add(edtUserName);
		workPane.add(labelPwd);
		workPane.add(edtPwd);
		workPane.add(labelPwd2);
		workPane.add(edtPwd2);
		workPane.add(labelwho);
		workPane.add(comboBox);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(300, 500);
		this.btnCancel.addActionListener(this);
		this.btnOk.addActionListener(this);
	}*///这一段好像没有也没事
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel)
			this.setVisible(false);
		else if(e.getSource()==this.btnOk){
			String userid=this.edtUserId.getText();
			String username=this.edtUserName.getText();
			String pwd1=new String(this.edtPwd.getPassword());
			String pwd2=new String(this.edtPwd2.getPassword());
			String who=comboBox_who.getSelectedItem().toString();
			try {
				if("管理员".equals(who)){
					AdminManager s =new AdminManager();
					s.reg(userid,username,pwd1,pwd2);
					this.setVisible(false);
				}else if("商家".equals(who)){
					ShopManager s =new ShopManager();
					s.reg(userid,username,pwd1,pwd2);
					this.setVisible(false);
				}else if("顾客".equals(who)) {
					CusManager s =new CusManager();
					s.reg(userid,username,pwd1,pwd2);
					this.setVisible(false);
				}else if("骑手".equals(who)) {
					RiderManager s =new RiderManager();
					s.reg(userid,username,pwd1,pwd2);
					this.setVisible(false);
				}
				
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
		}
			
		
	}


}
