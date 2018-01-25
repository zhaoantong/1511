package com.bwie.myokhttp.demo;

/**
 * Created by rookie on 2018/1/24.
 */

public class DataBean {

    /**
     * msg : 登录成功
     * code : 0
     * data : {"age":null,"appkey":"371e47e623133780","appsecret":"5CFF413B698B540E2B1AE17AF8309E98","createtime":"2018-01-24T15:27:59","email":null,"fans":null,"follow":null,"gender":null,"icon":"https://www.zhaoapi.cn/images/1516776675474img.png","latitude":null,"longitude":null,"mobile":"13800138016","money":null,"nickname":null,"password":"8F669074CAF5513351A2DE5CC22AC04C","praiseNum":null,"token":"9811406A31799CC70AE1B30A66C8ACC0","uid":11972,"userId":null,"username":"13800138016"}
     */

    private String msg;
    private String code;
    private DataBean1 data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean1 getData() {
        return data;
    }

    public void setData(DataBean1 data) {
        this.data = data;
    }

    public static class DataBean1 {
        /**
         * age : null
         * appkey : 371e47e623133780
         * appsecret : 5CFF413B698B540E2B1AE17AF8309E98
         * createtime : 2018-01-24T15:27:59
         * email : null
         * fans : null
         * follow : null
         * gender : null
         * icon : https://www.zhaoapi.cn/images/1516776675474img.png
         * latitude : null
         * longitude : null
         * mobile : 13800138016
         * money : null
         * nickname : null
         * password : 8F669074CAF5513351A2DE5CC22AC04C
         * praiseNum : null
         * token : 9811406A31799CC70AE1B30A66C8ACC0
         * uid : 11972
         * userId : null
         * username : 13800138016
         */

        private Object age;
        private String appkey;
        private String appsecret;
        private String createtime;
        private Object email;
        private Object fans;
        private Object follow;
        private Object gender;
        private String icon;
        private Object latitude;
        private Object longitude;
        private String mobile;
        private Object money;
        private Object nickname;
        private String password;
        private Object praiseNum;
        private String token;
        private int uid;
        private Object userId;
        private String username;

        public Object getAge() {
            return age;
        }

        public void setAge(Object age) {
            this.age = age;
        }

        public String getAppkey() {
            return appkey;
        }

        public void setAppkey(String appkey) {
            this.appkey = appkey;
        }

        public String getAppsecret() {
            return appsecret;
        }

        public void setAppsecret(String appsecret) {
            this.appsecret = appsecret;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public Object getFans() {
            return fans;
        }

        public void setFans(Object fans) {
            this.fans = fans;
        }

        public Object getFollow() {
            return follow;
        }

        public void setFollow(Object follow) {
            this.follow = follow;
        }

        public Object getGender() {
            return gender;
        }

        public void setGender(Object gender) {
            this.gender = gender;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public Object getLatitude() {
            return latitude;
        }

        public void setLatitude(Object latitude) {
            this.latitude = latitude;
        }

        public Object getLongitude() {
            return longitude;
        }

        public void setLongitude(Object longitude) {
            this.longitude = longitude;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public Object getMoney() {
            return money;
        }

        public void setMoney(Object money) {
            this.money = money;
        }

        public Object getNickname() {
            return nickname;
        }

        public void setNickname(Object nickname) {
            this.nickname = nickname;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Object getPraiseNum() {
            return praiseNum;
        }

        public void setPraiseNum(Object praiseNum) {
            this.praiseNum = praiseNum;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public Object getUserId() {
            return userId;
        }

        public void setUserId(Object userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
