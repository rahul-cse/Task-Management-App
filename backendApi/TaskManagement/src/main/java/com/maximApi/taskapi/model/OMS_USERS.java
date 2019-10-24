package com.maximApi.taskapi.model;


import javax.persistence.*;
import java.util.Date;

@Entity
public class OMS_USERS {



	    //@GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "OMS_USERS_SQ")
	    //@SequenceGenerator(name = "OMS_USERS_SQ", sequenceName = "OMS_USERS_SQ")
	    @Id
	    private Long ID;

	    @Column
	    private String FIRST_NAME;

	    @Column
	    private String LAST_NAME;

	    @Column
	    private String USERNAME;

	    @Column
	    private String PASS;

	    @Column
	    private Long CURRENCY_ID;

	    @Column
	    private Long AREA_ID;

	    @Column
	    private Long PARENT_ID;

	    @Column
	    private Long ROLE_ID;

	    @Column
	    private Date CREATION_DATE;

	    @Column
	    private String CONTACT_PERSON;

	    @Column
	    private String CONTACT_NUMBER;

	    @Column
	    private String ADDR1;

	    @Column
	    private String ADDR2;

	    @Column
	    private String TOWN;

	    @Column
	    private String CITY;

	    @Column
	    private String DISTRICT;

	    @Column
	    private Integer POSTCODE;

	    @Column
	    private String COUNTRY;

	    @Column
	    private String HINTS;

	    @Column
	    private String MOBILE1;

	    @Column
	    private String MOBILE2;

	    @Column
	    private String PHONE1;

	    @Column
	    private String PHONE2;

	    @Column
	    private String EMAIL;

	    @Column
	    private Double BALANCE;



	    @Column
	    private String NID;

	    @Column
	    private String INT_REF;

	    @Column
	    private Long STATUS;

	    @Column
	    private String CUSTOMER_ID;

	    @Column
	    private int MOBILE_VERIFIED;

	    @Column
	    private Long THANA_ID;

	    @Column
	    private Long DIST_ID;

	    @Column
	    private Long OTP=(long) 0;

	    @Column
	    private Date OTP_SDATE;

	    @Column
	    private Date OTP_VDATE;

	    @Column
	    private int OTP_COUNT = 0;

	    public Long getID() {
	        return ID;
	    }

	    public void setID(Long ID) {
	        this.ID = ID;
	    }

	    public String getFIRST_NAME() {
	        return FIRST_NAME;
	    }

	    public void setFIRST_NAME(String FIRST_NAME) {
	        this.FIRST_NAME = FIRST_NAME;
	    }

	    public String getLAST_NAME() {
	        return LAST_NAME;
	    }

	    public void setLAST_NAME(String LAST_NAME) {
	        this.LAST_NAME = LAST_NAME;
	    }

	    public String getUSERNAME() {
	        return USERNAME;
	    }

	    public void setUSERNAME(String USERNAME) {
	        this.USERNAME = USERNAME;
	    }

	    public String getPASS() {
	        return PASS;
	    }

	    public void setPASS(String PASS) {
	        this.PASS = PASS;
	    }

	    public Long getCURRENCY_ID() {
	        return CURRENCY_ID;
	    }

	    public void setCURRENCY_ID(Long CURRENCY_ID) {
	        this.CURRENCY_ID = CURRENCY_ID;
	    }

	    public Long getAREA_ID() {
	        return AREA_ID;
	    }

	    public void setAREA_ID(Long AREA_ID) {
	        this.AREA_ID = AREA_ID;
	    }

	    public Long getPARENT_ID() {
	        return PARENT_ID;
	    }

	    public void setPARENT_ID(Long PARENT_ID) {
	        this.PARENT_ID = PARENT_ID;
	    }

	    public Long getROLE_ID() {
	        return ROLE_ID;
	    }

	    public void setROLE_ID(Long ROLE_ID) {
	        this.ROLE_ID = ROLE_ID;
	    }

	    public Date getCREATION_DATE() {
	        return CREATION_DATE;
	    }

	    public void setCREATION_DATE(Date CREATION_DATE) {
	        this.CREATION_DATE = CREATION_DATE;
	    }

	    public String getCONTACT_PERSON() {
	        return CONTACT_PERSON;
	    }

	    public void setCONTACT_PERSON(String CONTACT_PERSON) {
	        this.CONTACT_PERSON = CONTACT_PERSON;
	    }

	    public String getCONTACT_NUMBER() {
	        return CONTACT_NUMBER;
	    }

	    public void setCONTACT_NUMBER(String CONTACT_NUMBER) {
	        this.CONTACT_NUMBER = CONTACT_NUMBER;
	    }

	    public String getADDR1() {
	        return ADDR1;
	    }

	    public void setADDR1(String ADDR1) {
	        this.ADDR1 = ADDR1;
	    }

	    public String getADDR2() {
	        return ADDR2;
	    }

	    public void setADDR2(String ADDR2) {
	        this.ADDR2 = ADDR2;
	    }

	    public String getTOWN() {
	        return TOWN;
	    }

	    public void setTOWN(String TOWN) {
	        this.TOWN = TOWN;
	    }

	    public String getCITY() {
	        return CITY;
	    }

	    public void setCITY(String CITY) {
	        this.CITY = CITY;
	    }

	    public String getDISTRICT() {
	        return DISTRICT;
	    }

	    public void setDISTRICT(String DISTRICT) {
	        this.DISTRICT = DISTRICT;
	    }

	    public int getPOSTCODE() {
	        return POSTCODE;
	    }

	    public void setPOSTCODE(int POSTCODE) {
	        this.POSTCODE = POSTCODE;
	    }

	    public String getCOUNTRY() {
	        return COUNTRY;
	    }

	    public void setCOUNTRY(String COUNTRY) {
	        this.COUNTRY = COUNTRY;
	    }

	    public String getHINTS() {
	        return HINTS;
	    }

	    public void setHINTS(String HINTS) {
	        this.HINTS = HINTS;
	    }

	    public String getMOBILE1() {
	        return MOBILE1;
	    }

	    public void setMOBILE1(String MOBILE1) {
	        this.MOBILE1 = MOBILE1;
	    }

	    public String getMOBILE2() {
	        return MOBILE2;
	    }

	    public void setMOBILE2(String MOBILE2) {
	        this.MOBILE2 = MOBILE2;
	    }

	    public String getPHONE1() {
	        return PHONE1;
	    }

	    public void setPHONE1(String PHONE1) {
	        this.PHONE1 = PHONE1;
	    }

	    public String getPHONE2() {
	        return PHONE2;
	    }

	    public void setPHONE2(String PHONE2) {
	        this.PHONE2 = PHONE2;
	    }

	    public String getEMAIL() {
	        return EMAIL;
	    }

	    public void setEMAIL(String EMAIL) {
	        this.EMAIL = EMAIL;
	    }

	    public Double getBALANCE() {
	        return BALANCE;
	    }

	    public void setBALANCE(Double BALANCE) {
	        this.BALANCE = BALANCE;
	    }

	    public String getNID() {
	        return NID;
	    }

	    public void setNID(String NID) {
	        this.NID = NID;
	    }

	    public String getINT_REF() {
	        return INT_REF;
	    }

	    public void setINT_REF(String INT_REF) {
	        this.INT_REF = INT_REF;
	    }

	    public Long getSTATUS() {
	        return STATUS;
	    }

	    public void setSTATUS(Long STATUS) {
	        this.STATUS = STATUS;
	    }

	    public String getCUSTOMER_ID() {
	        return CUSTOMER_ID;
	    }

	    public void setCUSTOMER_ID(String CUSTOMER_ID) {
	        this.CUSTOMER_ID = CUSTOMER_ID;
	    }

	    public Long getTHANA_ID() {
	        return THANA_ID;
	    }

	    public void setTHANA_ID(Long THANA_ID) {
	        this.THANA_ID = THANA_ID;
	    }

	    public Long getDIST_ID() {
	        return DIST_ID;
	    }

	    public void setDIST_ID(Long DIST_ID) {
	        this.DIST_ID = DIST_ID;
	    }

	    public Long getOTP() {
	        return OTP;
	    }

	    public void setOTP(Long OTP) {
	        this.OTP = OTP;
	    }

	    public Date getOTP_SDATE() {
	        return OTP_SDATE;
	    }

	    public void setOTP_SDATE(Date OTP_SDATE) {
	        this.OTP_SDATE = OTP_SDATE;
	    }

	    public Date getOTP_VDATE() {
	        return OTP_VDATE;
	    }

	    public void setOTP_VDATE(Date OTP_VDATE) {
	        this.OTP_VDATE = OTP_VDATE;
	    }

	    public int getOTP_COUNT() {
	        return OTP_COUNT;
	    }

	    public void setOTP_COUNT(int OTP_COUNT) {
	        this.OTP_COUNT = OTP_COUNT;
	    }
	    
	    
	    @Override
	    public String toString() {
	        return "OMS_USERS{" +
	                "ID=" + ID +
	                ", FIRST_NAME='" + FIRST_NAME + '\'' +
	                ", LAST_NAME='" + LAST_NAME + '\'' +
	                ", USERNAME='" + USERNAME + '\'' +
	                ", PASS='" + PASS + '\'' +
	                ", CURRENCY_ID=" + CURRENCY_ID +
	                ", AREA_ID=" + AREA_ID +
	                ", PARENT_ID=" + PARENT_ID +
	                ", ROLE_ID=" + ROLE_ID +
	                ", CREATION_DATE=" + CREATION_DATE +
	                ", CONTACT_PERSON='" + CONTACT_PERSON + '\'' +
	                ", CONTACT_NUMBER='" + CONTACT_NUMBER + '\'' +
	                ", ADDR1='" + ADDR1 + '\'' +
	                ", ADDR2='" + ADDR2 + '\'' +
	                ", TOWN='" + TOWN + '\'' +
	                ", CITY='" + CITY + '\'' +
	                ", DISTRICT='" + DISTRICT + '\'' +
	                ", POSTCODE=" + POSTCODE +
	                ", COUNTRY='" + COUNTRY + '\'' +
	                ", HINTS='" + HINTS + '\'' +
	                ", MOBILE1='" + MOBILE1 + '\'' +
	                ", MOBILE2='" + MOBILE2 + '\'' +
	                ", PHONE1='" + PHONE1 + '\'' +
	                ", PHONE2='" + PHONE2 + '\'' +
	                ", EMAIL='" + EMAIL + '\'' +
	                ", BALANCE=" + BALANCE +
	                ", NID='" + NID + '\'' +
	                ", INT_REF='" + INT_REF + '\'' +
	                ", STATUS=" + STATUS +
	                ", CUSTOMER_ID='" + CUSTOMER_ID + '\'' +
	                ", THANA_ID=" + THANA_ID +
	                ", DIST_ID=" + DIST_ID +
	                ", OTP=" + OTP +
	                ", OTP_SDATE=" + OTP_SDATE +
	                ", OTP_VDATE=" + OTP_VDATE +
	                ", OTP_COUNT=" + OTP_COUNT +
	                '}';
	    }

		public int getMOBILE_VERIFIED() {
			return MOBILE_VERIFIED;
		}

		public void setMOBILE_VERIFIED(int mOBILE_VERIFIED) {
			MOBILE_VERIFIED = mOBILE_VERIFIED;
		}
	

	
}
