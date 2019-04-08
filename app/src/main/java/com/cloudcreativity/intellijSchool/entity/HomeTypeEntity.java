package com.cloudcreativity.intellijSchool.entity;

import java.util.List;

/**
 * Created by Administrator on 2019/4/4.
 */

public class HomeTypeEntity {
    private Result result;

    public void setResult(Result result){
        this.result = result;
    }
    public Result getResult(){
        return this.result;
    }
    public class Status {
        private int code;

        private String msg;

        public void setCode(int code){
            this.code = code;
        }
        public int getCode(){
            return this.code;
        }
        public void setMsg(String msg){
            this.msg = msg;
        }
        public String getMsg(){
            return this.msg;
        }

    }
    public class Data {
        private int total;

        private List<Disciplines> disciplines ;

        public void setTotal(int total){
            this.total = total;
        }
        public int getTotal(){
            return this.total;
        }
        public void setDisciplines(List<Disciplines> disciplines){
            this.disciplines = disciplines;
        }
        public List<Disciplines> getDisciplines(){
            return this.disciplines;
        }

    }
    public class Disciplines {
        private String id;

        private String name;

        private String code;

        private String introduction;

        private String show_in_menu;

        private String order_in_menu;

        private String datetime_created;

        private String p_id;

        private String t_id;

        private String d_id;

        private String datetime_updated;

        private String created_at;

        private String modified_at;

        private String picture;

        private String total_video;

        private int total_course;

        private String total_lesson;

        public void setId(String id){
            this.id = id;
        }
        public String getId(){
            return this.id;
        }
        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
        public void setCode(String code){
            this.code = code;
        }
        public String getCode(){
            return this.code;
        }
        public void setIntroduction(String introduction){
            this.introduction = introduction;
        }
        public String getIntroduction(){
            return this.introduction;
        }
        public void setShow_in_menu(String show_in_menu){
            this.show_in_menu = show_in_menu;
        }
        public String getShow_in_menu(){
            return this.show_in_menu;
        }
        public void setOrder_in_menu(String order_in_menu){
            this.order_in_menu = order_in_menu;
        }
        public String getOrder_in_menu(){
            return this.order_in_menu;
        }
        public void setDatetime_created(String datetime_created){
            this.datetime_created = datetime_created;
        }
        public String getDatetime_created(){
            return this.datetime_created;
        }
        public void setP_id(String p_id){
            this.p_id = p_id;
        }
        public String getP_id(){
            return this.p_id;
        }
        public void setT_id(String t_id){
            this.t_id = t_id;
        }
        public String getT_id(){
            return this.t_id;
        }
        public void setD_id(String d_id){
            this.d_id = d_id;
        }
        public String getD_id(){
            return this.d_id;
        }
        public void setDatetime_updated(String datetime_updated){
            this.datetime_updated = datetime_updated;
        }
        public String getDatetime_updated(){
            return this.datetime_updated;
        }
        public void setCreated_at(String created_at){
            this.created_at = created_at;
        }
        public String getCreated_at(){
            return this.created_at;
        }
        public void setModified_at(String modified_at){
            this.modified_at = modified_at;
        }
        public String getModified_at(){
            return this.modified_at;
        }
        public void setPicture(String picture){
            this.picture = picture;
        }
        public String getPicture(){
            return this.picture;
        }
        public void setTotal_video(String total_video){
            this.total_video = total_video;
        }
        public String getTotal_video(){
            return this.total_video;
        }
        public void setTotal_course(int total_course){
            this.total_course = total_course;
        }
        public int getTotal_course(){
            return this.total_course;
        }
        public void setTotal_lesson(String total_lesson){
            this.total_lesson = total_lesson;
        }
        public String getTotal_lesson(){
            return this.total_lesson;
        }

    }
    public class Result {
        private Status status;

        private Data data;

        public void setStatus(Status status){
            this.status = status;
        }
        public Status getStatus(){
            return this.status;
        }
        public void setData(Data data){
            this.data = data;
        }
        public Data getData(){
            return this.data;
        }

    }

}
