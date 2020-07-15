package takeout.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import takeout.control.ProManager;
import takeout.control.RiderManager;
import takeout.model.BeanPro_Evaluate;
import takeout.model.BeanRider_Account;
import takeout.util.BaseException;

public class FrmEva_RModify extends JDialog implements ActionListener {
	public static String EvaComment =null;
	public static Date EvaDate =null;
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	
	private JLabel labelComment = new JLabel("评价内容");
	//评价
	private JTextField edtComment = new JTextField(20);
	private JTextField edtLevel = new JTextField(20);
	
	public FrmEva_RModify(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelComment);
		workPane.add(edtComment);
		
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
			String orderid=FrmR_Eva.Eva_Rorderid;
			String riderid=FrmR_Eva.Eva_Rriderid;
			
		    if(!edtComment.getText().equals("好")&&!edtComment.getText().equals("差")) {
		    	JOptionPane.showMessageDialog(null,  "骑手评价只能填‘好’或‘差’","提示",JOptionPane.ERROR_MESSAGE);
				return;
		    }
		    BeanRider_Account u= new BeanRider_Account();
			u.setOrder_id(orderid);
			u.setRider_id(riderid);
			u.setRider_evaluate(edtComment.getText());
				EvaComment=edtComment.getText();
				
			try {
				(new RiderManager()).ModifyREva(u);
				(new RiderManager()).salary(FrmR_Eva.Eva_Rorderid,FrmR_Eva.Eva_Rriderid);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
}
