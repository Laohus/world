package com.personal.world.Dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.personal.world.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserDao extends User {


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate (JdbcTemplate JdbcTemplate) {
        this.jdbcTemplate = JdbcTemplate;
    }

    private UserDao userDao;

    @Autowired
    public void setUserDao (UserDao UserDao) {
        this.userDao = UserDao;
    }


    /*用户名查询*/
    public String QueryUser(String username){

        String Key ="SELECT COUNT(*) FROM `user` WHERE NAME=? ;";
        return jdbcTemplate.queryForObject(Key,String.class,username);
    }

    /*openid查询*/
    public String QueryOpenid(String openid){

        String Key ="SELECT COUNT(*) FROM `user` WHERE opeid=? ;";
        return jdbcTemplate.queryForObject(Key,String.class,openid);
    }

    /*用户名和密码查询 */
    public String QueryUserPass(String username,String password){

        String Key ="SELECT COUNT(*) FROM `user` WHERE NAME=? AND PASSWORD=?;";
        return jdbcTemplate.queryForObject(Key,String.class,username,password);
    }

    /*修改密码 */
    public boolean ModUser(Map<String, String> UserData){

        String Key = "UPDATE `user` SET `password`=? ,`email`=? ,`lines`=? WHERE `name` = ?;";
        return jdbcTemplate.update(Key,UserData.get("password"),UserData.get("email"),UserData.get("line")
                ,UserData.get("username"))>0;

    }

    /*查询用户数据 */
    public List<Map<String, Object>> QueryUserData(int page, int limit){

        String Key = "SELECT * FROM `user` LIMIT ? , ?;";
        return jdbcTemplate.queryForList(Key,(page-1)*limit,page*limit);

    }

    /*查询qq用户数据 */
    public List<Map<String, Object>> QueryUserData2(String openid){

        String Key = "SELECT `name`,age,sex,Head FROM `user` WHERE `openid`=?;";
        return jdbcTemplate.queryForList(Key,openid);

    }

    /*查询系统用户数据 */
    public List<Map<String, Object>> QuerySystemData(String username){

        String Key = "SELECT `name`,age,sex,Head FROM `user` WHERE `name`=?;";
        return jdbcTemplate.queryForList(Key,username);

    }

    /*查询qq博客数据总数 */
    public Integer BlogCount(String openid){

        String Key = "SELECT count(*) from `blog` WHERE `openid`=?;";
        return jdbcTemplate.queryForObject(Key,Integer.class,openid);

    }

    /*查询系统博客数据总数 */
    public Integer BlogSystemCount(String username){

        String Key = "SELECT count(*) from `blog` WHERE `name`=?;";
        return jdbcTemplate.queryForObject(Key,Integer.class,username);

    }

    /*添加用户数据 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date;

    public boolean adduser(Map<String, String> UserData){

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String Key = "INSERT into`user` (`name`,`password`,`CreationTime`,`age`,`sex`,`source`,`Head`,`openid`) VALUES(?,?,?,?,?,?,?,?);";
        return jdbcTemplate.update(Key,UserData.get("name"),UserData.get("password"),formatter.format(date),
                UserData.get("age"),UserData.get("sex"),UserData.get("source"),UserData.get("Head"),UserData.get("openid"))>0;

    }

    /*修改QQ用户数据 */
    public boolean updateUser(Map<String, String> UserData){

        String Key = "UPDATE `user` SET `name`=? ,age=? ,sex=? ,Head=? WHERE openid=?;";
        return jdbcTemplate.update(Key,UserData.get("name"), UserData.get("age"),UserData.get("sex"),
                UserData.get("Head"),UserData.get("openid"))>0;

    }


//    /*删除用户数据 */
//    public boolean DelUser(JSONArray User){
//
//        for (Object name : User) {
//            String Key = "DELETE FROM `user` WHERE `name`=? ;";
//            if (jdbcTemplate.update(Key,name) == 0) {
//                return false;
//            }
//        }
//        return true;
//
//    }

    /*删除用户数据 */
    public boolean DelUser(String User){

        String Key = "DELETE FROM `user` WHERE `name`=? ;";

        return jdbcTemplate.update(Key, User) != 0;



    }


}
