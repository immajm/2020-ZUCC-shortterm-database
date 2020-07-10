package takeout.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import takeout.control.OrderManager;
import takeout.control.ProManager;
import takeout.model.BeanOrder_detail;
import takeout.model.BeanProduct;
import takeout.model.BeanShop;
import takeout.util.BaseException;
import takeout.util.DBUtil;
import takeout.util.DbException;

public class FrmCustomer extends JDialog implements ActionListener{
	
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("添加到购物车");
	private Button btnBuy = new Button("查看购物车");
	
    JComponent createPanel( String shopid) throws DbException {
    	DefaultTableModel tablmod=new DefaultTableModel();//存放表头和信息
    	JTable proTable;
    	Object tblData[][];//用于商品信息陈列
    	Object tblTitle[]={"商品编号","商品名称","价格","优惠价格","所属分类"};//表头
    	
    	proTable=new JTable(tablmod);
    	JPanel panel = new JPanel();
    	List<BeanProduct> result=new ArrayList<BeanProduct>();//商品结果集
    	Connection conn=null;
	    try {
	    	conn=DBUtil.getConnection();
	    	String sql="select pro_id,pro_name,pro_price,pro_discount_amount,type_id "
	    			+ "from product where shop_id ='"+shopid+"'";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				BeanProduct u=new BeanProduct();
				u.setPro_id(rs.getString(1));
				u.setPro_name(rs.getString(2));
				u.setPro_price(rs.getDouble(3));
				u.setPro_discount_amount(rs.getDouble(4));
				u.setType_id(rs.getString(5));
				result.add(u);
			}
			tblData =new Object[result.size()][5];
			for(int i=0;i<result.size();i++){
				tblData[i][0]=result.get(i).getPro_id();
				tblData[i][1]=result.get(i).getPro_name();
				tblData[i][2]=result.get(i).getPro_price();
				tblData[i][3]=result.get(i).getPro_discount_amount();
				tblData[i][4]=result.get(i).getType_id();
			}
	    }catch(SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
    	
    	tablmod.setDataVector(tblData,tblTitle);
    	proTable.validate();
    	proTable.repaint();		
    	panel.add(new JScrollPane(proTable));
        return panel;
    }
    
    public FrmCustomer() throws BaseException {
    	JFrame jf = new JFrame("以下为点单页面，欢迎点单");
	    jf.setSize(800, 800);
	    jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    jf.setLocationRelativeTo(null);
	    
	    toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnAdd);
		toolBar.add(btnBuy);
		
	    final JTabbedPane tabbedPane = new JTabbedPane();
	    tabbedPane.setTabPlacement(tabbedPane.LEFT);//选项卡垂直在左侧

	    Connection conn=null;
	    try {
	    	conn=DBUtil.getConnection();
	    	String sql="select shop_id,shop_name from shop group by shop_id";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				tabbedPane.addTab(rs.getString(2), new ImageIcon("image//pic2.png"),createPanel(rs.getString(1)));
			}
	    }catch(SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
	    jf.getContentPane().add(toolBar, BorderLayout.SOUTH);
		jf.getContentPane().add(tabbedPane, BorderLayout.NORTH);
	    jf.setVisible(true);
		//订单价格=∑（单价-单品优惠）*数量-优惠券-满减
	    tabbedPane.setSelectedIndex(1);
	    
	    
	    this.btnAdd.addActionListener(this);
		this.btnBuy.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
    }
    public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnAdd){
			int i=this.proTable.getSelectedRow();
			System.out.println(i);
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择商品","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确定加入购物车嘛吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String id=this.tblData[i][0].toString();
				try {
					(new OrderManager()).create_order(id);
				//	this.reloadProTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
		else if(e.getSource()==this.btnBuy){
			
		}
	}
	
	
}
