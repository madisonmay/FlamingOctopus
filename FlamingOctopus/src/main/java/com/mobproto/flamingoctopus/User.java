package com.mobproto.flamingoctopus;

/**
 * Created by mmay on 11/19/13.
 */
public class User {
    String name;
    String number;
    Long score;

    public User(String name, String number, Long score) {
        this.name = name;
        this.number = number;
        this.score = score;
    }


    @Override
    public String toString() {
        return "User{" +
                "name=" + name +
                ", number='" + number + '\'' +
                ", score=" + score +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public Long getScore() {
        return score;
    }

}
