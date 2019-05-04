/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khmerlibrary.model;

/**
 *
 * @author SRUN VANNARA
 */
public class Member {

    private int m_id;
    private String name, latin, gender, bd, village, commune, district, province, phone;

    public Member(int m_id, String name, String latin, String gender, String bd, String village, String commune, String district, String province, String phone) {
        this.m_id = m_id;
        this.name = name;
        this.latin = latin;
        this.gender = gender;
        this.bd = bd;
        this.village = village;
        this.commune = commune;
        this.district = district;
        this.province = province;
        this.phone = phone;
    }

    public Member(int m_id, String name, String latin, String gender, String phone) {
        this.m_id = m_id;
        this.name = name;
        this.latin = latin;
        this.gender = gender;
        this.phone = phone;
    }

    public int getM_id() {
        return m_id;
    }

    public void setM_id(int m_id) {
        this.m_id = m_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatin() {
        return latin;
    }

    public void setLatin(String latin) {
        this.latin = latin;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBd() {
        return bd;
    }

    public void setBd(String bd) {
        this.bd = bd;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



}
