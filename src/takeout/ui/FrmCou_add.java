package takeout.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import takeout.control.CouponManager;
import takeout.control.PossessManager;
import takeout.control.UserManager;
import takeout.model.BeanCoupon;
import takeout.util.BaseException;

public class FrmCou_add extends JDialog implements ActionListener{
	
	public static String current_couidadd=null;
	public static int current_couquantityadd;
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("ȷ��");
	private Button btnCancel = new Button("ȡ��");
	private JLabel labelId = new JLabel("�Ż�ȯ��ţ�");
	private JLabel labelMoney = new JLabel("�Ż�ȯ��");
	private JLabel labelQuantity = new JLabel("����Ҫ����");
	private JLabel labelStart = new JLabel("��ʼ���ڣ�");
	private JLabel labelEnd = new JLabel("�������ڣ�");
	//����Ż�ȯ
	private JTextField edtId = new JTextField(20);
	private JTextField edtMoney = new JTextField(20);
	private JTextField edtQuantity = new JTextField(20);
	private JTextField edtStart = new JTextField(20);
	private JTextField edtEnd = new JTextField(20);
	public FrmCou_add(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelId);
		workPane.add(edtId);
		workPane.add(labelMoney);
		workPane.add(edtMoney);
		workPane.add(labelQuantity);
		workPane.add(edtQuantity);
		workPane.add(labelStart);
		workPane.add(edtStart);
		workPane.add(labelEnd);
		workPane.add(edtEnd);
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(300, 400);
		// ��Ļ������ʾ
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
			String id=this.edtId.getText();
			double money=Double.parseDouble(this.edtMoney.getText().toString());
			int quantity=(int)Double.parseDouble(this.edtQuantity.getText().toString());
			String start=this.edtStart.getText();  
			String end=this.edtEnd.getText(); 
	        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");  //����ĸ�ʽһ��Ҫһ����Ȼ�����
	        java.sql.Date startDate=null,endDate=null; 
			try {
				java.util.Date d = sdf.parse(start);
				startDate = new java.sql.Date(d.getTime());
				d = sdf.parse(end);
				endDate = new java.sql.Date(d.getTime());
			} catch (ParseException e2) {
				e2.printStackTrace();
			} 
			
	        
			BeanCoupon cou=new BeanCoupon();
			cou.setCoupon_id(id);
			cou.setDiscount(money);
			cou.setOrder_quantity(quantity);
			cou.setCoupon_starttime(startDate);
			cou.setCoupon_endtime(endDate);
			
			try {
				(new CouponManager()).createCou(cou);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
			}
			//�Ż�ȯ���ɺ����ɼ��������в��֣����Ѷ�����
			try {
				(new PossessManager()).createCollect(id,quantity);
			} catch (BaseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
	}
	
}
