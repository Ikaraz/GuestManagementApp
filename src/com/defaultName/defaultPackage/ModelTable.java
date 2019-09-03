package com.defaultName.defaultPackage;

public class ModelTable {
    private String id, firstName, lastName, sex, birthDate, docType, docNo, nation, birthPlace;

    public ModelTable(String id, String fName, String lName, String sex,
                      String bDate, String docType, String docNo, String nation, String bPlace) {
        this.id = id;
        this.firstName = fName;
        this.lastName = lName;
        this.sex = sex;
        this.birthDate = bDate;
        this.docType = docType;
        this.docNo = docNo;
        this.nation = nation;
        this.birthPlace = bPlace;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }
}
