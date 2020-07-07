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

import takeout.control.ProManager;
import takeout.model.BeanProduct;
import takeout.util.BaseException;

public class FrmPro_add extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelId = new JLabel("商品编号：");
	private JLabel labelName = new JLabel("商品名称：");
	private JLabel labelPrice = new JLabel("价格");
	private JLabel labelDiscount = new JLabel("优惠：");
	private JLabel labelType = new JLabel("类型：");
	//添加商家
	private JTextField edtId = new JTextField(20);
	private JTextField edtName = new JTextField(20);
	private JTextField edtPrice = new JTextField(20);
	private JTextField edtDiscount = new JTextField(20);
	private JTextField edtType = new JTextField(20);
	public FrmPro_add(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelId);
		workPane.add(edtId);
		workPane.add(labelName);
		workPane.add(edtName);
		workPane.add(labelPrice);
		workPane.add(edtPrice);
		workPane.add(labelDiscount);
		workPane.add(edtDiscount);
		workPane.add(labelType);
		workPane.add(edtType);
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
			String id=this.edtId.getText();
			String name=this.edtName.getText();
			double price=Double.parseDouble(this.edtPrice.getText().toString());
			double discount=Double.parseDouble(this.edtDiscount.getText().toString());
			String type=this.edtType.getText();
			
			BeanProduct pro=new BeanProduct();
			pro.setPro_id(id);
			pro.setPro_name(name);
			pro.setPro_price(price);
			pro.setPro_discount_amount(discount);
			pro.setType_id(type);
			try {
				(new ProManager()).createPro(pro);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
}
