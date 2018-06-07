import java.sql.*;
import java.util.Vector;

/**
 * JDBC를 이용하여 Java와 Oracle DB 사이를 연결해주는 클래스
 */
public class DatabaseConnect {
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
    private static final String PASS = "system";
    private static final String USER = "system";

    public Connection getConn() {
        Connection con = null;

        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL,USER,PASS);
        }catch(Exception e) {
            e.printStackTrace();
        }

        return con;
    }

    /**
     * @param id : 로그인 화면에 입력된 ID
     * @return : 해당 ID에 맞는 Password
     * 입력된 ID의 Password를 DB로부터 가져오는 함수
     */
    public String getPassword(String id) {
        String pw = null;

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConn();
            String sql = "select pw from tbl_user where id = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, id);

            rs = ps.executeQuery();

            if(rs.next()) {
                pw = rs.getString("pw");
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        return pw;
    }
    public Vector getBoardList(String gname) {
        Vector data = new Vector();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConn();
            String sql = "select * from tbl_board where gnumber = (select gnumber from tbl_group where name='" + gname + "')";
            System.out.println(sql);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while(rs.next()) {
                int gnumber = rs.getInt("gnumber");
                String name = rs.getString("name");
                String content = rs.getString("content");
                int id = rs.getInt("id");
                Object bfile = rs.getObject("bfile");

                Vector row = new Vector();
                row.add(gnumber);
                row.add(name);
                row.add(content);
                row.add(id);
                row.add(bfile);

                data.add(row);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    public Vector getGroupList() {
        Vector data = new Vector();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConn();
            String sql = "select * from tbl_group";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while(rs.next()) {
                int gumber = rs.getInt("gnumber");
                String name = rs.getString("name");
                String interest = rs.getString("interest");
                String master = rs.getString("master");
                String intro = rs.getString("intro");

                Vector row = new Vector();
                row.add(name);
                row.add(interest);
                row.add(master);

                data.add(row);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * @param userData
     * @return
     * 회원가입 시 사용자 정보 DB에 삽입하는 함수
     */
    public boolean insertUser(UserData userData) {
        boolean ok = false;

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = getConn();
            String sql = "INSERT INTO tbl_user(id,pw,name,interest,intro) VALUES(?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, userData.getId());
            ps.setString(2, userData.getPwd());
            ps.setString(3, userData.getName());
            ps.setString(4, userData.getInterest());
            ps.setString(5, userData.getIntro());

            int r = ps.executeUpdate();

            if(r>0) {
                ok = true;
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }

        return ok;
    }

    /**
     * 생성된 모임을 DB에 삽입 해주는 함수
     * @param groupData
     * @return
     */
    public boolean insertGroup(GroupData groupData) {
        boolean ok = false;

        Connection con = null;
        PreparedStatement ps = null;

        System.out.println(groupData.getGnumber() + "/" + groupData.getName() + "/" + groupData.getInterest() + "/" + groupData.getIntro());
        try {
            con = getConn();

            String sql = "INSERT INTO tbl_group(gnumber,name,interest,master,intro) VALUES(?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, groupData.getGnumber());
            ps.setString(2, groupData.getName());
            ps.setString(3, groupData.getInterest());
            ps.setString(4, groupData.getMaster());
            ps.setString(5, groupData.getIntro());

            int r = ps.executeUpdate();

            if(r>0) {
                ok = true;
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }

        return ok;
    }

    /**
     * 만들어진 모임의 개수를 세어주는 함수
     * 모임의 개수는 모임 번호를 입력하는데 사용
     * @return
     */
    public int getGroupCount(){
        int gnumber = -1;

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConn();

            String sql = "SELECT count(*) as gnumber FROM tbl_group";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            rs.next();

            gnumber = rs.getInt("gnumber") + 1;
            System.out.println(gnumber);

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return gnumber;
    }

    public boolean deleteMember(String id,String pwd) {
        boolean ok = false;
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = getConn();
            String sql = "DELETE FROM tb_staff WHERE id =? AND pwd=?";

            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, pwd);
            int r = ps.executeUpdate();

            if(r>0)
                ok = true;
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return ok;
    }
}
