package com.jin.tool.shipping;

/**
 * {"1":"4","2":"4","3":"9","4":"11"}
 * "services":[{"name":"Priority","id":1},{"id":2,"name":"Xpresspost"},
 * {"id":3,"name":"Expedited Parcel™ or flat rate box"},
 * {"id":4,"name":"Regular Parcel™"}],
 */
public class ServicesOfCanadaPost {
    String s1;
    String s2;
    String s3;
    String s4;
    public ServicesOfCanadaPost(String s1, String s2, String s3, String s4) {
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
        this.s4 = s4;
    }
    public ServicesOfCanadaPost() {

    }
    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public String getS2() {
        return s2;
    }

    public void setS2(String s2) {
        this.s2 = s2;
    }

    public String getS3() {
        return s3;
    }

    public void setS3(String s3) {
        this.s3 = s3;
    }

    public String getS4() {
        return s4;
    }

    public void setS4(String s4) {
        this.s4 = s4;
    }
}
