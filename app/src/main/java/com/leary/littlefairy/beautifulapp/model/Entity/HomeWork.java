package com.leary.littlefairy.beautifulapp.model.Entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/17.
 */
public class HomeWork implements Serializable {
    private String id;

    private String className;

    private String releaseTime;

    private String week;

    private String subjectId;

    private String code;

    private String subjectName;

    private String description;
    private String subjectPic;

    public String getSubjectPic() {
        return subjectPic;
    }

    public void setSubjectPic(String subjectPic) {
        this.subjectPic = subjectPic;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setClassName(String className){
        this.className = className;
    }
    public String getClassName(){
        return this.className;
    }
    public void setReleaseTime(String releaseTime){
        this.releaseTime = releaseTime;
    }
    public String getReleaseTime(){
        return this.releaseTime;
    }
    public void setWeek(String week){
        this.week = week;
    }
    public String getWeek(){
        return this.week;
    }
    public void setSubjectId(String subjectId){
        this.subjectId = subjectId;
    }
    public String getSubjectId(){
        return this.subjectId;
    }

}