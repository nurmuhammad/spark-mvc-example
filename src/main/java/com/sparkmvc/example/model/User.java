package com.sparkmvc.example.model;

import com.sparkmvc.model.aModel;

/**
 * @author nurmuhammad
 */

public class User extends aModel {
    public long id;
    public String email;
    public String password;
    public String salt;

    public boolean status;

    public int created;
    public int changed;
}
