package com.emre.one_to_many_java.models;

import com.parse.ParseObject;

public class ParseObjectModel {
    ParseObject object;
    boolean isChecked = false;

    public ParseObjectModel(ParseObject object) {
        this.object = object;
    }

    public ParseObject getObject() {
        return object;
    }

    public ParseObjectModel setObject(ParseObject object) {
        this.object = object;
        return this;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public ParseObjectModel setChecked(boolean checked) {
        isChecked = checked;
        return this;
    }
}
