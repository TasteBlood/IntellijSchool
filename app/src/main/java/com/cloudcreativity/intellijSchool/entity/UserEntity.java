package com.cloudcreativity.intellijSchool.entity;

public class UserEntity {
    private String userName;
    private String studentId;
    private String realName;
    private String gender;
    private String birth;
    private String headPic;
    private String dormNum;
    private String token;
    private DormitoryEntity dormitoryDomain;
    private int dmId;
    private int uid;
    private String schoolName;

    public DormitoryEntity getDormitoryDomain() {
        return dormitoryDomain;
    }

    public void setDormitoryDomain(DormitoryEntity dormitoryDomain) {
        this.dormitoryDomain = dormitoryDomain;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getDormNum() {
        return dormNum;
    }

    public void setDormNum(String dormNum) {
        this.dormNum = dormNum;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getDmId() {
        return dmId;
    }

    public void setDmId(int dmId) {
        this.dmId = dmId;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public class DormitoryEntity{
        private String dormitoryNum;

        public String getDormitoryNum() {
            return dormitoryNum;
        }

        public void setDormitoryNum(String dormitoryNum) {
            this.dormitoryNum = dormitoryNum;
        }
    }
}
