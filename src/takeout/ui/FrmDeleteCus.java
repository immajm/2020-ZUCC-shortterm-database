package takeout.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import takeout.control.CusManager;
import takeout.model.BeanCustomer;
import takeout.util.BaseException;

public class FrmDeleteCus extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	private Button btnOk = new Button("�޸�");
//	private JLabel labelName = new JLabel("�û��ǳƣ�");
//	private JLabel labelPwd = new JLabel("���룺");
	private Object tblTitle[]={"�û����","�û�����","�û�����"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable proTable=new JTable(tablmod);
	private void reloadTable(){
		try {
			List<BeanCustomer> pro=(new CusManager()).loadAll();
			tblData =new Object[pro.size()][3];
			for(int i=0;i<pro.size();i++){
				tblData[i][0]=pro.get(i).getCus_id();
				tblData[i][1]=pro.get(i).getCus_name();
				tblData[i][2]=pro.get(i).getCus_pwd();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.proTable.validate();
			this.proTable.repaint();
			
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FrmDeleteCus(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnOk);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//��ȡ��������
		this.reloadTable();
		this.getContentPane().add(new JScrollPane(this.proTable), BorderLayout.CENTER);
		//��Ļ������ʾ
		this.setSize(800,450);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		this.btnOk.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
			
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnOk){
			int i= this.proTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,"��ѡ����Ҫɾ�����û�","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			else {
				String id=tblData[i][0].toString();
				try {
					(new CusManager()).delete(id);
				} catch (BaseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				this.reloadTable();
			}
		}
			
	}
}
