package com.example.springweek05.autoconfig;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Klass { 
    
    List<Student> students = new ArrayList<>();
    
    public void dong(){
        System.out.println(this.getStudents());
    }
    
}
