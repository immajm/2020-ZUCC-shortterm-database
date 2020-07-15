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
	private JButton btnLogin = new JButton("��½");
	private JButton btnCancel = new JButton("�˳�");
	private JButton btnRegister = new JButton("ע��");

	private JLabel labelUser = new JLabel("�û�id��");
	private JLabel labelPwd = new JLabel("���룺");
	private JLabel labelwho = new JLabel("ѡ����ݣ�");
	private JTextField edtUserId = new JTextField(20);
	private JPasswordField edtPwd = new JPasswordField(20);
	String[] whoData = new String[] {"�̼�","�˿�","����","����Ա"};
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
		// ��Ļ������ʾ
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();//ʹ��Ч

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
			/*1����Ա2�̼�3�ͻ�4����*/
			try {
				if("����Ա".equals(comboBox.getSelectedItem().toString())) {
					user=sum.loadUser(1,userid);
					if(pwd.equals(user.getPwd())){
						UserManager.currentUser=user;
						setVisible(false);
					}
					else{
						JOptionPane.showMessageDialog(null,  "�������","������ʾ",JOptionPane.ERROR_MESSAGE);
					}
				}else if("�̼�".equals(comboBox.getSelectedItem().toString())) {
					user=sum.loadUser(2,userid);
					if(pwd.equals(user.getPwd())){
						UserManager.currentUser=user;
						setVisible(false);
					}
					else{
						JOptionPane.showMessageDialog(null,  "�������","������ʾ",JOptionPane.ERROR_MESSAGE);
					}
				}else if("�˿�".equals(comboBox.getSelectedItem().toString())){
					user=sum.loadUser(3,userid);
					if(pwd.equals(user.getPwd())){
						UserManager.currentUser=user;
						setVisible(false);
					}
					else{
						JOptionPane.showMessageDialog(null,  "�������","������ʾ",JOptionPane.ERROR_MESSAGE);
					}
				}else if("����".equals(comboBox.getSelectedItem().toString())){
					user=sum.loadUser(4,userid);
					if(pwd.equals(user.getPwd())){
						UserManager.currentUser=user;
						setVisible(false);
					}
					else{
						JOptionPane.showMessageDialog(null,  "�������","������ʾ",JOptionPane.ERROR_MESSAGE);
					}
				}
			
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "������ʾ",JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource() == this.btnCancel) {
			System.exit(0);
		} else if(e.getSource()==this.btnRegister){
			FrmRegister dlg=new FrmRegister(this,"ע��",true);
			dlg.setVisible(true);
		}
		
	}
	

}
