package edu.jiangnan.dm.Model;

/**
 * Created by Jelly Zhou on 2015/3/22.
 */
public class ContactModel {
    private String name;
    private String job;
    private String telephone;
    private String email;
    private String organization;
    private String address;
    private byte[] qrcode;
    private String cardId;

    public ContactModel() {}

    public ContactModel(String name, String job, String telephone, String email, String organization,
                        String address, byte[] qrcode,String cardId) {
        this.name = name;
        this.job = job;
        this.telephone = telephone;
        this.email = email;
        this.organization = organization;
        this.address = address;
        this.qrcode = qrcode;
        this.cardId = cardId;
    }

    public String getName() {
        return this.name;
    }
    public void setName( String name) {
        this.name = name;
    }

    public String getJob() {
        return this.job;
    }
    public void setJob( String job) {
        this.job = job;
    }

    public String getTelephone() {
        return this.telephone;
    }
    public void setTelephone( String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return this.email;
    }
    public void setEmail( String email) {
        this.email = email;
    }

    public String getOrganization() {
        return this.organization;
    }
    public void setOrganization( String organization) {
        this.organization = organization;
    }

    public String getAddress() {
        return this.address;
    }
    public void setAddress( String address) {
        this.address = address;
    }

    public byte[] getQrcode() {
        return this.qrcode;
    }
    public void setQrcode( byte[] qrcode) {
        this.qrcode = qrcode;
    }

    public String getCardId() {
        return this.cardId;
    }
    public void setCardId( String cardId) {
        this.cardId = cardId;
    }

}
