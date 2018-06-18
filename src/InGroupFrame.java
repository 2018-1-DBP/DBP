
import InGroup.BoardPanel;
import InGroup.FileBoardPanel;
import InGroup.ManagePanel;

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
    private JButton createBoard;
    private JButton backBtn;
    int group_number;
    String id;

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

        // 게시판 버튼
        createBoard = new JButton("게시글 작성");
        createBoard.addActionListener(this);
        backBtn = new JButton("뒤로 가기");
        backBtn.addActionListener(this);

        boardPanel = new BoardPanel(model, createBoard, backBtn);
        fileBoardPanel = new FileBoardPanel();
        managePanel = new ManagePanel();



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
        else if(e.getSource() == backBtn) {
            new MainFrame(id);
            this.dispose();

        }
    }
}
    /*private String id;
    private Vector group_col;
    private JTable group_table;
    private JButton createButton, mygroupButton, logoutButton;
    private DatabaseConnect databaseConnect;

    public InGroupFrame(String id) {
        super("이거모임?");
        this.id = id;
        databaseConnect = new DatabaseConnect();

        group_col = new Vector<String>();
        group_col.add("모임 이름");
        group_col.add("관심사");
        group_col.add("모임장");

        DefaultTableModel model = new DefaultTableModel(databaseConnect.getGroupList(), group_col) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        group_table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(group_table);
        add(scrollPane);
        scrollPane.setBounds(0, 0, 400, 400);

        createButton = new JButton("글 작성");
        createButton.setBounds(450, 10, 100, 30);
        createButton.addActionListener(this);
        mygroupButton = new JButton("테스트");
        mygroupButton.setBounds(450, 50, 100, 30);
        logoutButton = new JButton("뒤로가기");
        logoutButton.setBounds(450, 90, 100, 30);
        logoutButton.addActionListener(this);

        add(createButton);
        add(mygroupButton);
        add(logoutButton);

        setSize(600,400);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == createButton) {
            new GroupCreateFrame(id);
            this.dispose();
        } else if(e.getSource() == logoutButton) {
            new MainFrame(id);
            this.dispose();
        }
    }
}
*/