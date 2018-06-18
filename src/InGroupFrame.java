

import InGroup.BoardPanel;
import InGroup.FileBoardPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class InGroupFrame extends JFrame implements ActionListener {
    private JTabbedPane mainPanel;

    private BoardPanel boardPanel;
    private FileBoardPanel fileBoardPanel;
    private ManagePanel managePanel;
    private DatabaseConnect databaseConnect;

    private Vector board_col;
    private Vector user_col;
    private JButton createBoard;
    private JButton backBtn;

    private JButton deleteUser;
    private JButton mbackBtn;
    int group_number;
    String id;
    String master;

    public InGroupFrame(int gnumber, String id) {
        this.group_number = gnumber;

        this.id = id;
        board_col = new Vector<String>();
        //board_col.add("모임 이름");
        board_col.add("제목");
        board_col.add("내용");
        board_col.add("아이디");
        //board_col.add("파일");
        databaseConnect = new DatabaseConnect();
        DefaultTableModel model = new DefaultTableModel(databaseConnect.getBoardList(gnumber), board_col) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        user_col = new Vector<String>();
        user_col.add("아이디");
        user_col.add("등급");
        DefaultTableModel mgmodel = new DefaultTableModel(databaseConnect.getGroupMember(gnumber), user_col) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // 게시판 버튼
        createBoard = new JButton("게시글 작성");
        createBoard.addActionListener(this);
        backBtn = new JButton("뒤로 가기");
        backBtn.addActionListener(this);
        mbackBtn = new JButton("뒤로 가기");
        mbackBtn.addActionListener(this);
        deleteUser = new JButton("회원 강퇴");
        deleteUser.addActionListener(this);

        boardPanel = new BoardPanel(model, createBoard, backBtn);
        fileBoardPanel = new FileBoardPanel();

        master = databaseConnect.getMaster(gnumber);

        managePanel = new ManagePanel(mgmodel, deleteUser, mbackBtn, group_number, id, master);

        mainPanel = new JTabbedPane();
        mainPanel.addTab("BOARD", boardPanel);
       // mainPanel.addTab("FILE", fileBoardPanel);
        mainPanel.addTab("MANAGE" ,managePanel);

        add(mainPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == createBoard) {
            new BoardCreateFrame(group_number, id);
            this.dispose();
        }
        else if(e.getSource() == backBtn || e.getSource() == mbackBtn) {
            new MainFrame(id);
            this.dispose();
        }
        else if(e.getSource()==deleteUser) {
            this.dispose();
            new InGroupFrame(group_number,id);
        }
    }
}
