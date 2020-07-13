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

import takeout.control.AddressManager;
import takeout.control.ProManager;
import takeout.model.BeanAddress;
import takeout.model.BeanProduct;
import takeout.util.BaseException;

public class FrmMe  extends JDialog implements ActionListener{////这个改成注册会员
	private JPanel toolBar = new JPanel();
	private Button btnAdddress = new Button("");
	
	
	public FrmMe(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnAdddress);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		this.getContentPane().add(new JScrollPane(this.proTable), BorderLayout.CENTER);
		//屏幕居中显示
		this.setSize(800,450);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		this.btnAdddress.addActionListener(this);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
	}
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==this.btnAdddress){
			FrmAddress_Add dlg=new FrmAddress_Add(this,"",true);
			dlg.setVisible(true);
			this.reloadAddTable();
		}
		
		
	}
}
