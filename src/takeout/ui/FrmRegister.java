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
	private Button btnOk = new Button("ע��");
	private Button btnCancel = new Button("ȡ��");
	
	private JLabel labelUser = new JLabel("�û�id��");
	private JLabel labelName = new JLabel("�û��ǳƣ�");
	private JLabel labelPwd = new JLabel("���룺");
	private JLabel labelPwd2 = new JLabel("�ٴ��������룺");
	private JLabel labelwho = new JLabel("ѡ����ݣ�");
	private JLabel labelsex = new JLabel("�Ա�");
	private JLabel labeltel = new JLabel("�ֻ��ţ�");
	private JLabel labelcity = new JLabel("���У�");
	
	private JTextField edtUserId = new JTextField(20);
	private JTextField edtUserName = new JTextField(20);
	private JPasswordField edtPwd = new JPasswordField(20);
	private JPasswordField edtPwd2 = new JPasswordField(20);
	String[] whoData = new String[] {"����Ա","�̼�","�˿�","����"};
	final JComboBox<String> comboBox_who = new JComboBox<String>(whoData);
	String[] sexData = new String[] {"��","Ů"};
	final JComboBox<String> comboBox_sex = new JComboBox<String>(sexData);
	
	public FrmRegister(Dialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(250, 350);
		//this.validate();//ʹ��Ч
		//if(e.getStateChange()==ItemEvent.SELECTED&& comboBox_who.getSelectedItem().toString().equals("�˿�")) {
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
			workPane.add(labelwho);//��ѡ�����
			workPane.add(comboBox_who);
			this.getContentPane().add(workPane, BorderLayout.CENTER);
			this.setSize(250, 350);
			// ��Ļ������ʾ
			double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
			double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
			this.setLocation((int) (width - this.getWidth()) / 4,
					(int) (height - this.getHeight()) / 2);

			this.validate();//ʹ��Ч
			
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
	}*///��һ�κ���û��Ҳû��
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
				if("����Ա".equals(who)){
					AdminManager s =new AdminManager();
					s.reg(userid,username,pwd1,pwd2);
					this.setVisible(false);
				}else if("�̼�".equals(who)){
					ShopManager s =new ShopManager();
					s.reg(userid,username,pwd1,pwd2);
					this.setVisible(false);
				}else if("�˿�".equals(who)) {
					CusManager s =new CusManager();
					s.reg(userid,username,pwd1,pwd2);
					this.setVisible(false);
				}else if("����".equals(who)) {
					RiderManager s =new RiderManager();
					s.reg(userid,username,pwd1,pwd2);
					this.setVisible(false);
				}
				
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
		}
			
		
	}


}
