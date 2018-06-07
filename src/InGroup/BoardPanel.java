package InGroup;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;


public class BoardPanel extends JPanel {

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
        JScrollPane scrollPane = new JScrollPane(board_table);
        add(scrollPane);
        setSize(1000, 800);
        scrollPane.setBounds(0, 0, 400, 400);

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
