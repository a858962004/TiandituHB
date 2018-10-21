package com.gangbeng.tiandituhb.bean;

import java.io.Serializable;

/**
 * @author zhanghao
 * @date 2018-10-21
 */

public class DKHCInfo implements Serializable {
    private String ID;//id
    private String DKID;//地块编号
    private String GeometryType;//标注类型
    private String GeometryStr;//坐标列表
    private String AddLoginName;//添加信息的登录名
    private String AddTime;//添加时间
    private String UpdateTime;//最后更新时间
    private String Area;//地块面积
    private String result;//核查结果
    private String checkman;//核查人
    private String checkTime;//核查时间
    private String submitstate;//提交状态
    private String infostate;//添加地块时为0，添加信息点时为1
    private String PicUrl;
    private String Note;
    private String Address;//地块地址
    private String Owner;//地块权属人
    private String County;//所属区县
    private String Villege;//所属村社区
    private String town;//所属街镇

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDKID() {
        return DKID;
    }

    public void setDKID(String DKID) {
        this.DKID = DKID;
    }

    public String getGeometryType() {
        return GeometryType;
    }

    public void setGeometryType(String geometryType) {
        GeometryType = geometryType;
    }

    public String getGeometryStr() {
        return GeometryStr;
    }

    public void setGeometryStr(String geometryStr) {
        GeometryStr = geometryStr;
    }

    public String getAddLoginName() {
        return AddLoginName;
    }

    public void setAddLoginName(String addLoginName) {
        AddLoginName = addLoginName;
    }

    public String getAddTime() {
        return AddTime;
    }

    public void setAddTime(String addTime) {
        AddTime = addTime;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCheckman() {
        return checkman;
    }

    public void setCheckman(String checkman) {
        this.checkman = checkman;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getSubmitstate() {
        return submitstate;
    }

    public void setSubmitstate(String submitstate) {
        this.submitstate = submitstate;
    }

    public String getInfostate() {
        return infostate;
    }

    public void setInfostate(String infostate) {
        this.infostate = infostate;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    public String getCounty() {
        return County;
    }

    public void setCounty(String county) {
        County = county;
    }

    public String getVillege() {
        return Villege;
    }

    public void setVillege(String villege) {
        Villege = villege;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
