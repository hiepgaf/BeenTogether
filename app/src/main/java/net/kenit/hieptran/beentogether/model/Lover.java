package net.kenit.hieptran.beentogether.model;

import net.kenit.hieptran.beentogether.utils.Utils;

import java.util.Date;

/**
 * Created by hiepthb on 04/07/2016.
 */
public class Lover {
    private String Name;
    private String ImagePath;
    private Date Birthday;
    private Utils.LOVER_TYPE Type;
    public Lover(String name, String imagePath) {
        ImagePath = imagePath;
        Name = name;
    }

    public Lover(String name, String imagePath, Utils.LOVER_TYPE type) {
        Name = name;
        ImagePath = imagePath;
        Type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public Date getBirthday() {
        return Birthday;
    }

    public void setBirthday(Date birthday) {
        Birthday = birthday;
    }

    public Utils.LOVER_TYPE getType() {
        return Type;
    }

    public void setType(Utils.LOVER_TYPE type) {
        Type = type;
    }
}
