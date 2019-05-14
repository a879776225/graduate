package com.acker.simplezxing.demo;

public class Truck {
    public String EffectDateTruckNumber;
    public String CargoNameTruckType;
    public String CargoTruckWeight;
    public int CargoTruckPrice;
    public String CargoTruckDate;
    public String Start;
    public String Whither;
    public String Linkman;
    public String Tel;
    public String Bewrite;
    public String CargoTruckNow;
    public String username;
    public int Auditing;

    public String getWhither() {
        return Whither;
    }

    public String getUsername() {
        return username;
    }

    public String getTel() {
        return Tel;
    }

    public String getStart() {
        return Start;
    }

    public String getLinkman() {
        return Linkman;
    }

    public String getBewrite() {
        return Bewrite;
    }

    public int getCargoTruckPrice() {
        return CargoTruckPrice;
    }

    public int getAuditing() {
        return Auditing;
    }

    public String getCargoNameTruckType() {
        return CargoNameTruckType;
    }

    public String getCargoTruckDate() {
        return CargoTruckDate;
    }

    public String getCargoTruckNow() {
        return CargoTruckNow;
    }

    public String getCargoTruckWeight() {
        return CargoTruckWeight;
    }

    public String getEffectDateTruckNumber() {
        return EffectDateTruckNumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLinkman(String linkman) {
        Linkman = linkman;
    }

    public void setBewrite(String bewrite) {
        Bewrite = bewrite;
    }

    public void setAuditing(int auditing) {
        Auditing = auditing;
    }

    public void setStart(String start) {
        Start = start;
    }

    public void setTel(String tel) {
        Tel = tel;
    }

    public void setWhither(String whither) {
        Whither = whither;
    }

    public void setCargoNameTruckType(String cargoNameTruckType) {
        CargoNameTruckType = cargoNameTruckType;
    }

    public void setCargoTruckDate(String cargoTruckDate) {
        CargoTruckDate = cargoTruckDate;
    }

    public void setCargoTruckNow(String cargoTruckNow) {
        CargoTruckNow = cargoTruckNow;
    }

    public void setCargoTruckPrice(int cargoTruckPrice) {
        CargoTruckPrice = cargoTruckPrice;
    }

    public void setCargoTruckWeight(String cargoTruckWeight) {
        CargoTruckWeight = cargoTruckWeight;
    }

    public void setEffectDateTruckNumber(String effectDateTruckNumber) {
        EffectDateTruckNumber = effectDateTruckNumber;
    }
    public String getAllInfo(String type){
        if (type == "车源信息") {
            return "类型：" + this.CargoNameTruckType + "\n" +
                    "出发日期：" + this.CargoTruckDate + "\n" +
                    "载重：" + this.CargoTruckWeight + "kg"+"\n" +
                    "出发地点：" + this.Start + "\t" +"To"+"\t"+
                    "目的地：" + this.Whither + "\n" +
                    "联系电话:" + this.Tel + "\n" +
                    "姓名：" + this.Linkman;
        } else {
            return "货品名称：" + this.CargoNameTruckType + "\n" +
                    "出发日期：" + this.CargoTruckDate + "\n" +
                    "质量：" + this.CargoTruckWeight + "kg"+"\n" +
                    "出发地点：" + this.Start + "\t" +"To"+ "\t"+
                    "目的地：" + this.Whither + "\n" +
                    "联系电话:" + this.Tel + "\n" +
                    "姓名：" + this.Linkman;
        }

    }
}
