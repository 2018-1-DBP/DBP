package InGroup;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;


public class
BoardPanel extends JPanel {

    private String id;
    private Vector board_col;
    private JTable board_table;

    private JButton createBoard;
    private JButton backBtn;


    private BoardPanel panel;

    public BoardPanel() {
        panel = this;
    }

    public BoardPanel(DefaultTableModel model, JButton create, JButton back) {
        panel = this;
        board_table = new JTable(model);
        board_table.setBackground(Color.WHITE);
        board_table.setSize(1000,800);
        board_table.getColumn("내용").setPreferredWidth(200);
        board_table.setRowHeight(50);

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

        createBoard = create;
        createBoard.setBounds(450, 10, 100, 30);

        backBtn = back;
        backBtn.setBounds(450, 10, 100, 30);

        add(createBoard);
        add(backBtn);

    }

    private ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
         Object source = e.getSource();
         if(source == createBoard) {

            }
            else if(source == backBtn) {
               //new MainFrame();
            }
        }
    };

/*    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createBoard) {
            new GroupCreateFrame(id);
            InGroupFrame.dispose();
        } else if (e.getSource() == backBtn) {
            new MainFrame();
            this.dispose();
        }
    }*/
}
