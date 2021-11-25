package com.personal.world.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class BlogDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate (JdbcTemplate JdbcTemplate) {
        this.jdbcTemplate = JdbcTemplate;
    }

    /*添加博客 */
    public boolean AddBlog(Map<String, String> BlogData){

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatterLine= new SimpleDateFormat("yyyy年MM月dd日");

        String Key = "INSERT into`blog` (`name`,`category`,`createtime`,`user`,`content`,`timeline`,`openid`) VALUES(?,?,?,?,?,?,?);";
        return jdbcTemplate.update(Key,BlogData.get("BlogTitle"),BlogData.get("ClassLfy"),
                formatter.format(date),BlogData.get("username"),BlogData.get("content"),formatterLine.format(date)
                ,BlogData.get("openid"))>0;
    }


    /*博客中时间线查询*/
    public List<Map<String, Object>> QueryTimeLine(String name){

        String Key ="SELECT DISTINCT timeline FROM `blog` where user = ? and openid=\"\";";

        return jdbcTemplate.queryForList(Key,name);
    }

    /*博客中时间线qq查询*/
    public List<Map<String, Object>> QueryTimeLine2(String openid){

        String Key ="SELECT DISTINCT timeline FROM `blog` where openid = ?;";

        return jdbcTemplate.queryForList(Key,openid);
    }

    /*博客中时间线查询博客名qq*/
    public List<Map<String, Object>> QueryTimeName(String line , String openid){

        String Key ="SELECT `name` FROM blog WHERE timeline = ? and openid = ?;";

        return jdbcTemplate.queryForList(Key,line,openid);
    }

    /*博客中时间线查询博客名*/
    public List<Map<String, Object>> QueryTimeName2(String line,String username){

        String Key ="SELECT `name` FROM blog WHERE timeline = ? and `user`=? AND openid=\"\" ;";

        return jdbcTemplate.queryForList(Key,line,username);
    }

    /*根据博客名查询数据qq*/
    public List<Map<String, Object>> QueryName(String name , String openid){

        String Key ="SELECT `user`,`category`,`content`,`createtime` FROM blog WHERE `name` = ? and openid = ?;";

        return jdbcTemplate.queryForList(Key,name,openid);
    }

    /*根据博客名查询数据*/
    public List<Map<String, Object>> QueryName2(String name,String username){

        String Key ="SELECT `user`,`category`,`content`,`createtime` FROM blog WHERE `name` = ?  and `user`=? AND openid=\"\" ;";

        return jdbcTemplate.queryForList(Key,name,username);
    }

    /*根据openid查询用户名*/
    public String QueryName(String openid){

        String Key ="SELECT `name` FROM `user` WHERE openid=?;";

        return jdbcTemplate.queryForObject(Key,String.class,openid);
    }


    /*项目 */
    public List<Map<String, Object>> QueryProjectData(){

        String Key = "SELECT * FROM `project`;";
        return jdbcTemplate.queryForList(Key);

    }

    /*项目2 */
    public List<Map<String, Object>> QueryProjectData2(String name){

        String Key = "SELECT * FROM `project` where `name`=? ;";
        return jdbcTemplate.queryForList(Key,name);

    }



    /*项目数查询*/
    public String QueryProjectcount(){

        String Key ="SELECT MAX(serial) FROM project";
        return jdbcTemplate.queryForObject(Key,String.class);
    }

    /*项目数删除*/
    public Boolean DelProject(String serial){

        String Key ="DELETE FROM `project` WHERE serial=?;";
        return jdbcTemplate.update(Key, serial) != 0;
    }

}
