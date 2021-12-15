package com.mydemo.toolslist.socket;

public class CreateObject {
    private String region;

    public static void main(String[] args) {
        String str = "abc";

        CreateObject location = new CreateObject();
        location.setRegion("南山");

        String a = new String("king").intern();
        String b = new String("king").intern();
        if (a == b) {
            System.out.print("a==b");
        } else {
            System.out.print("a!=b");
        }
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
