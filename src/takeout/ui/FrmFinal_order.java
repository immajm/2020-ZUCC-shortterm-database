package takeout.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import takeout.control.OrderManager;
import takeout.util.BaseException;
import takeout.util.DBUtil;
import takeout.util.DbException;

public class FrmFinal_order extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	private Button btnOk = new Button("ȷ��");
	private Button btnAddress = new Button("ѡ���ջ���ַ");
	private Button btnCoupon = new Button("ѡ���Ż�ȯ");
	
	//����ʱ���Ϊ�ղ����Խ���
	public FrmFinal_order(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnAddress);
		toolBar.add(btnCoupon);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
	//	this.getContentPane().add(new JScrollPane(this.otable), BorderLayout.CENTER);
		
		//��Ļ������ʾ
		this.setSize(800, 450);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnAddress.addActionListener(this);
		this.btnOk.addActionListener(this);
		this.btnCoupon.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
	}
	public void actionPerformed(ActionEvent e){
		
		if(e.getSource()==this.btnOk){
			
			int cnt=-1;
			try {
				cnt = new OrderManager().count_order();
			} catch (BaseException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			if(JOptionPane.showConfirmDialog(this,"ȷ����ʼ������","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				if(cnt==0) {
					JOptionPane.showMessageDialog(null, "���ﳵ�տ���Ҳ","��ʾ",JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(FrmAddress.currentaddress==null) {
					JOptionPane.showMessageDialog(null, "��ѡ���ջ���ַ","��ʾ",JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
					new OrderManager().createOrd_all();
					FrmCustomer.generate_orderid=1;//flag��Ϊ1����һ����ʼ����ȡ������
					(new OrderManager()).load_Eva();//�������ɺ�������Ʒ���۱����в��֣�������
				} catch (BaseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				FrmBill dlg=new FrmBill(this,"�˵�",true);
				dlg.setVisible(true);
			}
		}
		else if(e.getSource()==this.btnAddress) {
			FrmAddress dlg=new FrmAddress(this,"ѡ���ַ",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.btnCoupon) {
			FrmPossess dlg=new FrmPossess(this,"ѡ���Ż�ȯ��һ������һ�ţ�",true);
			dlg.setVisible(true);
		}
	}
   
}
