package com.example.revobanga;

public class MemberData {
    private String name;
   private String color;

    public MemberData(String name) {
        this.name = name;
     this.color = color;
    }

    // Add an empty constructor so we can later parse JSON into MemberData using Jackson
    public MemberData() {
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

   public String getColor() {
      return color;
    }
}
