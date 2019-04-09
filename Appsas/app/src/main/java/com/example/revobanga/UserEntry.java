package com.example.revobanga;

public class UserEntry {

    private int mID;
    private String mName;
    private String mpassword;

    public UserEntry()
    {
        mID = 0;
        mName = "";
        mpassword = "";
    }

    public UserEntry(int id, String name, String pass)
    {
        mID = id;
        mName = name;
        mpassword = pass;
    }

    public void setID(int val)
    {
        mID = val;
    }

    public int getID()
    {
        return mID;
    }

    public void setName(String val)
    {
        mName = val;
    }

    public String getName()
    {
        return mName;
    }

    public void setPassword(String val)
    {
        mpassword = val;
    }

    public String getPassword()
    {
        return mpassword;
    }
}
