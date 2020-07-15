package takeout.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
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
import takeout.control.RiderManager;
import takeout.model.BeanPro_Evaluate;
import takeout.model.BeanRider_Account;
import takeout.util.BaseException;
import takeout.ui.FrmEva_RModify;

public class FrmR_Eva extends JDialog implements ActionListener{
	
	public static String Eva_Rriderid=null;
	public static String Eva_Rorderid=null;
	
	private JPanel toolBar = new JPanel();
	private Button btnEva = new Button("进行骑手评价");
	private Object tblTitle[]={"骑手编号","订单编号","评价内容","评价日期"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable Table=new JTable(tablmod);
	private void reloadAddTable(){
		try {
			List<BeanRider_Account> pro=(new RiderManager()).loadAllR_Eva();
			tblData =new Object[pro.size()][4];
			for(int i=0;i<pro.size();i++){
				tblData[i][0]=pro.get(i).getRider_id();
				tblData[i][1]=pro.get(i).getOrder_id();
				tblData[i][2]=pro.get(i).getRider_evaluate();
				tblData[i][3]=pro.get(i).getRecordtime();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.Table.validate();
			this.Table.repaint();
			
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public FrmR_Eva(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnEva);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//提取现有数据
		this.reloadAddTable();
		this.getContentPane().add(new JScrollPane(this.Table), BorderLayout.CENTER);
		//屏幕居中显示
		this.setSize(800,450);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		this.btnEva.addActionListener(this);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
	}
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==this.btnEva){
			int i= this.Table.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,"请选择想要评价的骑手","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			else {
				Eva_Rriderid=tblData[i][0].toString();
				Eva_Rorderid=tblData[i][1].toString();
				FrmEva_RModify dlg=new FrmEva_RModify(this,"增加评价",true);
				
				dlg.setVisible(true);
				this.reloadAddTable();
			}
		}
	}
}