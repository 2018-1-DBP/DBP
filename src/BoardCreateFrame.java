import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardCreateFrame extends JFrame implements ActionListener {
    private String id;
    private int gnum;

    JTextField tfContent, tfName;
    JTextArea taIntro;
    JButton btnInsert, btnCancel;

    GridBagLayout gb;
    GridBagConstraints gbc;

    public BoardCreateFrame(int gunmb, String id) {
        super("게시판생성");
        this.gnum = gunmb;

        this.id = id;

        gb = new GridBagLayout();
        setLayout(gb);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JLabel bName = new JLabel("제목 :");
        tfName = new JTextField(10);
        gbAdd(bName, 0, 0, 1, 1);
        gbAdd(tfName, 1, 0, 3, 1);



        JLabel bContent = new JLabel("내용 :");
        tfContent = new JTextField( 30);
        gbAdd(bContent, 0, 2, 1, 1);
        gbAdd(tfContent, 1, 2, 3, 1);

        JPanel pButton = new JPanel();
        btnInsert = new JButton("게시판 작성");
        btnCancel = new JButton("뒤로가기");

        pButton.add(btnInsert);
        pButton.add(btnCancel);
        gbAdd(pButton, 0, 3, 4, 1);

        btnInsert.addActionListener(this);
        btnCancel.addActionListener(this);

        setSize(600, 400);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }

    private void gbAdd(JComponent c, int x, int y, int w, int h) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        gb.setConstraints(c, gbc);
        gbc.insets = new Insets(2, 2, 2, 2);
        add(c, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnInsert) {
            insertBoard();
            this.dispose();
        } else if(e.getSource() == btnCancel) {
            new InGroupFrame(gnum, id);
            this.dispose();
        }
    }


    private void insertBoard() {
        //GroupData groupData = getViewData();
        BoardData boardData = getViewData();
        if(boardData.getGnumber() != -1) {
            DatabaseConnect databaseConnect = new DatabaseConnect();
            boolean ok = databaseConnect.insertBoard(boardData);

            if (ok) {
                JOptionPane.showMessageDialog(this, "개시판 작성에 성공했습니다.");
                new InGroupFrame(gnum, id);
            } else {
                JOptionPane.showMessageDialog(this, "게시판 작성에 실패했습니다.");
            }
        } else
            JOptionPane.showMessageDialog(this, "게시판 작성에 실패했습니다.");
    }

    public BoardData getViewData() {
        DatabaseConnect databaseConnect = new DatabaseConnect();
        BoardData boardData = new BoardData();

        int gnumber = gnum;
        String name = tfName.getText();
       // String interest = tfInterest.getText();
        String content = tfContent.getText();
        String uid = id;

        boardData.setGnumber(gnumber);
        boardData.setName(name);
        boardData.setContent(content);
        boardData.setId(uid);
        return boardData;
    }
}
