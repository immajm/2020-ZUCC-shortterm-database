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

import takeout.control.FullManager;
import takeout.control.ProManager;
import takeout.model.BeanFull;
import takeout.model.BeanProduct;
import takeout.util.BaseException;

public class FrmFull_add extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelId = new JLabel("满减编号：");
	private JLabel labeldemand = new JLabel("满减要求金额：");
	private JLabel labelreduction= new JLabel("优惠金额");
	private JLabel labeltag = new JLabel("可否与优惠券叠加");
	
	private JTextField edtId = new JTextField(20);
	private JTextField edtdemand = new JTextField(20);
	private JTextField edtreduction = new JTextField(20);
	private JTextField edttag = new JTextField(20);
	public FrmFull_add(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelId);
		workPane.add(edtId);
		workPane.add(labeldemand);
		workPane.add(edtdemand);
		workPane.add(labelreduction);
		workPane.add(edtreduction);
		workPane.add(labeltag);
		workPane.add(edttag);
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
			double demand=Double.parseDouble(this.edtdemand.getText());
			double reduction=Double.parseDouble(this.edtreduction.getText());
			String tag=this.edttag.getText();
			
			BeanFull full=new BeanFull();
			full.setFull_id(id);
			full.setFull_demand(demand);
			full.setFull_reduction(reduction);
			full.setTag(tag);
			try {
				(new FullManager()).createFull(full);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
}
