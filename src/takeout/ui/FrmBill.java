package takeout.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import takeout.control.OrderManager;

public class FrmBill extends JDialog {
	
	double original_cost=OrderManager.currentoriginal_cost;
	double final_cost=OrderManager.currentfinal_cost;
	double discount=original_cost-final_cost;
	
	private JPanel workPane = new JPanel();
	private JLabel label1 = new JLabel("商品原价：");
	private JLabel label11 = new JLabel(""+original_cost);
	private JLabel label2 = new JLabel("优惠金额：");
	private JLabel label22 = new JLabel(""+discount);
	private JLabel label3 = new JLabel("实付金额：");
	private JLabel label33 = new JLabel(""+final_cost);
	
	
	public FrmBill(JDialog f, String s, boolean b) {
		super(f, s, b);
		workPane.add(label1);
		workPane.add(label11);
		workPane.add(label2);
		workPane.add(label22);
		workPane.add(label3);
		workPane.add(label33);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(300, 300);
		//屏幕居中显示
		this.setSize(350,350);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
	}
}
