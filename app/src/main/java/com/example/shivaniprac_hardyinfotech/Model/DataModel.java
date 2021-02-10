package com.example.shivaniprac_hardyinfotech.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataModel {

    @SerializedName("diseaseData")
    @Expose
    private List<DiseaseDatum> diseaseData = null;

    @SerializedName("memberData")
    @Expose
    private List<MemberDatum> memberData = null;

    public List<MemberDatum> getMemberData() {
        return memberData;
    }

    public void setMemberData(List<MemberDatum> memberData) {
        this.memberData = memberData;
    }

    public List<DiseaseDatum> getDiseaseData() {
        return diseaseData;
    }

    public void setDiseaseData(List<DiseaseDatum> diseaseData) {
        this.diseaseData = diseaseData;
    }

    public class MemberDatum {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("disease_ids")
        @Expose
        private String diseaseIds;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDiseaseIds() {
            return diseaseIds;
        }

        public void setDiseaseIds(String diseaseIds) {
            this.diseaseIds = diseaseIds;
        }

    }

    public class DiseaseDatum {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("title")
        @Expose
        private String title;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

    }
}
