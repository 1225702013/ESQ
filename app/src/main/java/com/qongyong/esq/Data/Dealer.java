package Data;


import cn.bmob.v3.BmobObject;

public class Dealer extends BmobObject {

    private String num;
    private String pwd;
    private  String name;
    private  String bossnum;
    public Dealer() {
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

    public String getBossnum() {
        return bossnum;
    }

    public void setBossnum(String bossnum) {
        this.bossnum = bossnum;
    }
}
