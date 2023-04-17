package com.example.softwarepatternsca4.Interface;

import com.example.softwarepatternsca4.Activity.Transaction;
import com.example.softwarepatternsca4.Domain.Item;

import java.util.ArrayList;

public interface OnDataReceiveCallback {

    void onDataReceived(ArrayList list);

    void onDataReceived(int n);



}
