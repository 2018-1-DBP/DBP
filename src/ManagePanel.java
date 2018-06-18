
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

public class ManagePanel extends JPanel implements MouseListener {
    private String uid;
    private String did;
    private String gmaster;
    private Vector board_col;
    private JTable board_table;
    private int group_number;
    private JButton deleteUser;
    private JButton backBtn;

    private ManagePanel panel;

    public ManagePanel() {
        panel = this;
    }

    public ManagePanel(DefaultTableModel model, JButton delete, JButton back, int gnum, String id, String master) {
        this.uid = id;
        this.gmaster = master;
        this.group_number = gnum;
        panel = this;
        board_table = new JTable(model);
        board_table.setBackground(Color.WHITE);
        board_table.setSize(1000,800);
        board_table.setRowHeight(50);
        board_table.addMouseListener(this);

        JScrollPane scrollPane = new JScrollPane(board_table);

        scrollPane.setBounds(1000, 1000, 400, 800);
        add(scrollPane);
        DefaultTableCellRenderer table = new DefaultTableCellRenderer();
        table.setHorizontalAlignment(SwingConstants.CENTER);
        TableColumnModel tc = board_table.getColumnModel();
        for(int i=0; i<tc.getColumnCount(); i++) {
            tc.getColumn(i).setCellRenderer(table);
        }
        setSize(1500, 1000);

        deleteUser = delete;
        deleteUser.setBounds(450, 10, 100, 30);
        deleteUser.addActionListener(actionListener);
        if(!uid.equals(gmaster)) deleteUser.setEnabled(false);
        else deleteUser.setEnabled(true);
        backBtn = back;
        backBtn.setBounds(450, 10, 100, 30);

        add(deleteUser);
        add(backBtn);

    }

    private ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if(source == deleteUser) {
                //new deleteUserFrame(did);
                int result = JOptionPane.showConfirmDialog(null, "정말 강퇴하시겠습니까", "Confirm", JOptionPane.YES_NO_OPTION);
                // 선택 없이
                if(result == JOptionPane.CLOSED_OPTION) {

                } else if(result == JOptionPane.YES_OPTION) {
                        new deleteUserFrame(group_number, did);
                        //parent.dispose();
                        //new InGroupFrame(group_number, uid);
                } else {

                }

            }
        }
    };

    @Override
    public void mouseClicked(MouseEvent e) {
        int rowIndex = board_table.getSelectedRow();
        did = (String) board_table.getValueAt(rowIndex, 0);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}