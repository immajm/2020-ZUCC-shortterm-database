package takeout.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import takeout.control.ProManager;
import takeout.model.BeanProduct;
import takeout.util.BaseException;

public class FrmPro extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("�����Ʒ");
	private Button btnDelete = new Button("ɾ����Ʒ");
	private Object tblTitle[]={"��Ʒ���","��Ʒ����","�۸�","�Żݼ۸�","��������"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable proTable=new JTable(tablmod);
	private void reloadProTable(){
		try {
			List<BeanProduct> pro=(new ProManager()).loadAllPro();
			tblData =new Object[pro.size()][5];
			for(int i=0;i<pro.size();i++){
				tblData[i][0]=pro.get(i).getPro_id();
				tblData[i][1]=pro.get(i).getPro_name();
				tblData[i][2]=pro.get(i).getPro_price();
				tblData[i][3]=pro.get(i).getPro_discount_amount();
				tblData[i][4]=pro.get(i).getType_id();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.proTable.validate();
			this.proTable.repaint();
			
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public FrmPro(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(this.btnDelete);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//��ȡ��������
		this.reloadProTable();
		this.getContentPane().add(new JScrollPane(this.proTable), BorderLayout.CENTER);
		
		//��Ļ������ʾ
		this.setSize(800, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		this.btnAdd.addActionListener(this);
		this.btnDelete.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
	}
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnAdd){
			FrmPro_add dlg=new FrmPro_add(this,"�����Ʒ",true);
			dlg.setVisible(true);
			//ˢ�±��
			this.reloadProTable();
		}
		else if(e.getSource()==this.btnDelete){
			int i=this.proTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ����Ʒ","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"ȷ��ɾ������Ʒ��","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String id=this.tblData[i][0].toString();
				try {
					(new ProManager()).deleteUser(id);
					this.reloadProTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
	}
	
	
}
