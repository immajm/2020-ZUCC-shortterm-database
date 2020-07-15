package takeout.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import takeout.TakeoutAssistantUtil;
import takeout.control.UserManager;
import takeout.model.BeanAdmin;
import takeout.model.BeanUser;
import takeout.util.BaseException;

public class FrmLogin extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private JButton btnLogin = new JButton("登陆");
	private JButton btnCancel = new JButton("退出");
	private JButton btnRegister = new JButton("注册");

	private JLabel labelUser = new JLabel("用户id：");
	private JLabel labelPwd = new JLabel("密码：");
	private JLabel labelwho = new JLabel("选择身份：");
	private JTextField edtUserId = new JTextField(20);
	private JPasswordField edtPwd = new JPasswordField(20);
	String[] whoData = new String[] {"商家","顾客","骑手","管理员"};
	final JComboBox<String> comboBox = new JComboBox<String>(whoData);
	

	public FrmLogin(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnRegister);
		toolBar.add(btnLogin);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		workPane.add(labelUser);
		workPane.add(edtUserId);
		workPane.add(labelPwd);
		workPane.add(edtPwd);
		workPane.add(labelwho);
		workPane.add(comboBox);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
	
		this.setSize(320, 200);	
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();//使生效

		btnLogin.addActionListener(this);
		btnCancel.addActionListener(this);
		this.btnRegister.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED) {
					//System.out.println(comboBox.getSelectedIndex()+ "=" +comboBox.getSelectedItem().toString());
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnLogin) {
			UserManager sum=new UserManager();
			BeanUser user=new BeanUser();
			String userid=this.edtUserId.getText();
			String pwd=new String(this.edtPwd.getPassword());
			/*1管理员2商家3客户4骑手*/
			try {
				if("管理员".equals(comboBox.getSelectedItem().toString())) {
					user=sum.loadUser(1,userid);
					if(pwd.equals(user.getPwd())){
						UserManager.currentUser=user;
						setVisible(false);
					}
					else{
						JOptionPane.showMessageDialog(null,  "密码错误","错误提示",JOptionPane.ERROR_MESSAGE);
					}
				}else if("商家".equals(comboBox.getSelectedItem().toString())) {
					user=sum.loadUser(2,userid);
					if(pwd.equals(user.getPwd())){
						UserManager.currentUser=user;
						setVisible(false);
					}
					else{
						JOptionPane.showMessageDialog(null,  "密码错误","错误提示",JOptionPane.ERROR_MESSAGE);
					}
				}else if("顾客".equals(comboBox.getSelectedItem().toString())){
					user=sum.loadUser(3,userid);
					if(pwd.equals(user.getPwd())){
						UserManager.currentUser=user;
						setVisible(false);
					}
					else{
						JOptionPane.showMessageDialog(null,  "密码错误","错误提示",JOptionPane.ERROR_MESSAGE);
					}
				}else if("骑手".equals(comboBox.getSelectedItem().toString())){
					user=sum.loadUser(4,userid);
					if(pwd.equals(user.getPwd())){
						UserManager.currentUser=user;
						setVisible(false);
					}
					else{
						JOptionPane.showMessageDialog(null,  "密码错误","错误提示",JOptionPane.ERROR_MESSAGE);
					}
				}
			
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误提示",JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource() == this.btnCancel) {
			System.exit(0);
		} else if(e.getSource()==this.btnRegister){
			FrmRegister dlg=new FrmRegister(this,"注册",true);
			dlg.setVisible(true);
		}
		
	}
	

}
