package com.mobproto.flamingoctopus;

public class Task {

    long _id;
    String contents;
    boolean longTerm;

    public Task(long id, String contents, boolean longTerm) {
        _id = id;
        this.contents = contents;
        this.longTerm = longTerm;
    }


    @Override
    public String toString() {
        return "Task{" +
                "_id=" + _id +
                ", contents='" + contents + '\'' +
                ", longTerm=" + longTerm +
                '}';
    }

    public long getId() {
        return _id;
    }

    public String getContents() {
        return contents;
    }
}
