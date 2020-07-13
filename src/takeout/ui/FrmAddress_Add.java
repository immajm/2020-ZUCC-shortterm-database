package takeout.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import takeout.control.AddressManager;
import takeout.control.ProManager;
import takeout.control.UserManager;
import takeout.model.BeanAddress;
import takeout.model.BeanProduct;
import takeout.util.BaseException;

public class FrmAddress_Add extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	
	private JLabel labelAddressId = new JLabel("地址编号");
	private JLabel labelAddress = new JLabel("地址");
	private JLabel labelProvince = new JLabel("省");
	private JLabel labelCity = new JLabel("市");
	private JLabel labelRegion = new JLabel("区：");
	private JLabel labelTel = new JLabel("联系电话");
	//添加地址
	private JTextField edtAddressId = new JTextField(20);
	private JTextField edtAddress = new JTextField(20);
	private JTextField edtProvince = new JTextField(20);
	private JTextField edtCity = new JTextField(20);
	private JTextField edtRegion = new JTextField(20);
	private JTextField edtTel = new JTextField(20);
	
	
	public FrmAddress_Add(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelAddressId);
		workPane.add(edtAddressId);
		workPane.add(labelAddress);
		workPane.add(edtAddress);
		workPane.add(labelTel);
		workPane.add(edtTel);
		workPane.add(labelProvince);
		workPane.add(edtProvince);
		workPane.add(labelCity);
		workPane.add(edtCity);
		workPane.add(labelRegion);
		workPane.add(edtRegion);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(300, 300);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
		
		
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.btnOk){
			String addressid=this.edtAddressId.getText();
			String address=this.edtAddress.getText();
			String cusid=UserManager.currentUser.getUser_id();
			String tel=this.edtTel.getText().toString();
			String province=this.edtProvince.getText();
			String city=this.edtCity.getText();
			String region=this.edtRegion.getText();
			
			BeanAddress u=new BeanAddress();
			u.setAddress_id(addressid);
			u.setAddress(address);
			u.setCus_id(cusid);
			u.setTel(tel);
			u.setProvince(province);
			u.setCity(city);
			u.setRegion(region);
			
			try {
				(new AddressManager()).createAddress(u);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
}
