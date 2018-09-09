package Data;


import cn.bmob.v3.BmobObject;

public class User extends BmobObject {

    private String num;
    private String pwd;
    private  String name;
    private int MemberType;
    private int MemberTime;

    public User() {
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMemberType() {
        return MemberType;
    }

    public void setMemberType(int memberType) {
        MemberType = memberType;
    }

    public int getMemberTime() {
        return MemberTime;
    }

    public void setMemberTime(int memberTime) {
        MemberTime = memberTime;
    }
}
