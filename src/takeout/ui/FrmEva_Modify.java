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
import takeout.model.BeanPro_Evaluate;
import takeout.util.BaseException;

public class FrmEva_Modify extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	
	private JLabel labelComment = new JLabel("评价内容");
	private JLabel labelLevel = new JLabel("星级");
	//评价
	private JTextField edtComment = new JTextField(20);
	private JTextField edtLevel = new JTextField(20);
	
	public FrmEva_Modify(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelComment);
		workPane.add(edtComment);
		workPane.add(labelLevel);
		workPane.add(edtLevel);
		
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
			String orderid=FrmEva.Eva_orderid;
			String proid=FrmEva.Eva_proid;
			Date date=null;
			Date date0 = new Date();       //当前时间转为String
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    String createdate = sdf.format(date0);
		    try {
		         String time = createdate;//String转为util.Date
		         sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		         java.util.Date ot=sdf.parse(time);
		         date = new java.sql.Date(ot.getTime());//util.Date转为sql.Date
		    } catch (ParseException e1) {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
		    }
			
			BeanPro_Evaluate u= new BeanPro_Evaluate();
			u.setOrder_id(orderid);
			u.setPro_id(proid);
			u.setComment(edtComment.getText());
			u.setComment_date((java.sql.Date) date);
			u.setPro_level((int)Double.parseDouble(edtLevel.getText()));
			try {
				(new ProManager()).ModifyEva(u);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
}
