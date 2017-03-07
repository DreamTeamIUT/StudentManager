package com.example.dd500076.studentmanager;

import java.util.ArrayList;

/**
 * Created by ft503084 on 07/03/17.
 */

public interface RequestMessageInterface {
    void onConnect(boolean connected, String token);
    void onStudentList(ArrayList<User> users);
    void onDelete(boolean deleted);
    void onAdd(boolean added);
}
