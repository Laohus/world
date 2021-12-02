package com.personal.world.common;

public class ResponseInfo {

    /*成功返回状态*/
    private String SUCCESS_CODE = "0";
    /*失败返回状态*/
    private String FAIL_CODE = "1";

    private String ACCOUNT_SUCCESS="success";

    private String ACCOUNT_NO_FOUND="输入的账号不存在，请重新输入！";

    private String ACCOUNT_FOUNDED="输入的账号已存在，请重新输入！";

    private String PROJECT_FOUNDED="输入的项目名称已存在，请重新输入！";

    private String ACCOUNT_ERROR="输入的账号密码不正确，请重新输入！";

    private String NEW_BLOG="添加博客失败，请稍后重试!";

    private String ADD_COMMENT="添加评论失败，请稍后重试!";

    private String SESSION_TIMEOUT="会话已失效,请重新登陆!";

    private String MISSING_PARAMETER="缺失必填参数";

    private String NOT_ADMIN="非管理员账户不允许修改账户信息！";

    private String REGISTERED_USERS_ERROR="注册用户失败，详情请查看日志！";

    private String MODIFY_INFORMATION="修改个人信息失败！";

    private String ADD_INFORMATION="新增个人信息失败！";

    private String BLOGS_NOTFOUND="博客不存在！";

    public String getSUCCESS_CODE(){
        return SUCCESS_CODE;
    }

    public void setSUCCESS_CODE(String SUCCESS_CODE){
        this.SUCCESS_CODE=SUCCESS_CODE;
    }

    public String getFAIL_CODE(){
        return FAIL_CODE;
    }

    public void setFAIL_CODE(String FAIL_CODE){
        this.FAIL_CODE=FAIL_CODE;
    }

    public String getACCOUNT_SUCCESS(){
        return ACCOUNT_SUCCESS;
    }

    public void setACCOUNT_SUCCESS(String ACCOUNT_SUCCESS){
        this.ACCOUNT_SUCCESS=ACCOUNT_SUCCESS;
    }

    public String getACCOUNT_NO_FOUND(){
        return ACCOUNT_NO_FOUND;
    }

    public void setACCOUNT_NO_FOUND(String ACCOUNT_NO_FOUND){
        this.ACCOUNT_NO_FOUND=ACCOUNT_NO_FOUND;
    }

    public String getACCOUNT_FOUNDED(){ return ACCOUNT_FOUNDED; }

    public void setACCOUNT_FOUNDED(String ACCOUNT_FOUNDED){
        this.ACCOUNT_FOUNDED=ACCOUNT_FOUNDED;
    }

    public String getPROJECT_FOUNDED(){ return PROJECT_FOUNDED; }

    public void setPROJECT_FOUNDED(String PROJECT_FOUNDED){
        this.PROJECT_FOUNDED=PROJECT_FOUNDED;
    }

    public String getACCOUNT_ERROR(){
        return ACCOUNT_ERROR;
    }

    public void setACCOUNT_ERROR(String ACCOUNT_ERROR){
        this.ACCOUNT_ERROR=ACCOUNT_ERROR;
    }

    public String getNEW_BLOG(){
        return NEW_BLOG;
    }

    public void setNEW_BLOG(String NEW_BLOG){
        this.NEW_BLOG=NEW_BLOG;
    }

    public String getSESSION_TIMEOUT(){
        return SESSION_TIMEOUT;
    }

    public void setSESSION_TIMEOUT(String SESSION_TIMEOUT){
        this.SESSION_TIMEOUT=SESSION_TIMEOUT;
    }

    public String getMISSING_PARAMETER(){
        return MISSING_PARAMETER;
    }

    public void setMISSING_PARAMETER(String MISSING_PARAMETER){
        this.MISSING_PARAMETER=MISSING_PARAMETER;
    }

    public String getNOT_ADMIN(){
        return NOT_ADMIN;
    }

    public void setNOT_ADMIN(String NOT_ADMIN){
        this.NOT_ADMIN=NOT_ADMIN;
    }

    public String getADD_COMMENT(){
        return ADD_COMMENT;
    }

    public void setADD_COMMENT(String ADD_COMMENT){
        this.ADD_COMMENT=ADD_COMMENT;
    }

    public String getREGISTERED_USERS_ERROR(){
        return REGISTERED_USERS_ERROR;
    }

    public void setREGISTERED_USERS_ERROR(String REGISTERED_USERS_ERROR){
        this.REGISTERED_USERS_ERROR=REGISTERED_USERS_ERROR;
    }

    public String getMODIFY_INFORMATION(){
        return MODIFY_INFORMATION;
    }

    public void setMODIFY_INFORMATION(String MODIFY_INFORMATION){
        this.MODIFY_INFORMATION=MODIFY_INFORMATION;
    }

    public String getADD_INFORMATION(){
        return ADD_INFORMATION;
    }

    public void setADD_INFORMATION(String ADD_INFORMATION){
        this.ADD_INFORMATION=ADD_INFORMATION;
    }

    public String getBLOGS_NOTFOUND(){
        return BLOGS_NOTFOUND;
    }

    public void setBLOGS_NOTFOUND(String BLOGS_NOTFOUND){
        this.BLOGS_NOTFOUND=BLOGS_NOTFOUND;
    }

}
