package takeout.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import takeout.control.OrderManager;
import takeout.control.PossessManager;
import takeout.model.BeanPossess;
import takeout.util.BaseException;

public class FrmPossess extends JDialog implements ActionListener{
	public static String currentcoupon=null ;
	public static double currentcoupon_discount=0;
	public static Date coupon_endtime;
	
	
	private JPanel toolBar = new JPanel();
	private Button btnCollect = new Button("������ȯ");
	private Button btnOk = new Button("ȷ��");
	private Button btnCancel = new Button("ȡ��");
	
	private Object tblTitle[]={"�Ż�ȯ���","�̼ұ��","�Żݽ��","����","����ʱ��"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable proTable=new JTable(tablmod);
	private void reloadCouponTable(){
		try {
			List<BeanPossess> pro=(new PossessManager()).loadPossess_cus();
			tblData =new Object[pro.size()][5];
			for(int i=0;i<pro.size();i++){
				tblData[i][0]=pro.get(i).getCoupon_id();
				tblData[i][1]=pro.get(i).getShop_id();
				tblData[i][2]=pro.get(i).getDiscount();
				tblData[i][3]=pro.get(i).getQuantity();
				tblData[i][4]=pro.get(i).getCoupon_endtime();
				coupon_endtime=(Date) tblData[i][4];
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.proTable.validate();
			this.proTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public FrmPossess(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnCollect);
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		this.reloadCouponTable();
		this.getContentPane().add(new JScrollPane(this.proTable), BorderLayout.CENTER);
		//��Ļ������ʾ
		this.setSize(800,450);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		
		this.btnCollect.addActionListener(this);
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.btnOk){
			int i=this.proTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ���Ż�ȯ","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(JOptionPane.showConfirmDialog(this,"ȷ��ʹ����","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				
					currentcoupon = tblData[i][0].toString();//ʹ�ã��Ż�ȯ����-1//���һ��ѡ��������ѡ���������
					currentcoupon_discount=(int)Double.parseDouble(tblData[i][2].toString());
					try {
						(new PossessManager()).minus_quantity();
					} catch (BaseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
			}
			this.setVisible(true);
			reloadCouponTable();
		}
		else if(e.getSource()==this.btnCollect){
			FrmCollect dlg=new FrmCollect(this,"������ȯ��",true);
			dlg.setVisible(true);
			this.reloadCouponTable();
		}
	}
}
