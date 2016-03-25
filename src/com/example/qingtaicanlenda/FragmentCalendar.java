package com.example.qingtaicanlenda;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentCalendar extends Fragment{
   
     @Override
     public void onCreate(Bundle savedInstanceState) {
         // TODO Auto-generated method stub
         super.onCreate(savedInstanceState);
     }
  
     @Override
     public View onCreateView(LayoutInflater inflater,
             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         // TODO Auto-generated method stub
         return inflater.inflate(R.layout.top, null);
     }

}
