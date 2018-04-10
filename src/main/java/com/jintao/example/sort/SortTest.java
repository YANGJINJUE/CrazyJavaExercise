package com.jintao.example.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created with IDEA
 * author:jinjueYang
 * Date:2018/4/10
 * Time:16:08
 */
public class SortTest {
    public static void main(String[] args) {
        List<Person> list = new ArrayList<Person>();
        for(int i = 0; i < 100; i++){
            if(i % 2 == 0)
                list.add(new Person(i % 6,i % 4));
            else
                list.add(new Person(i % 7,i % 3));
        }

        Collections.sort(list, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                if(o1.getAge() > o2.getAge()){
                    return -1;
                }

                if(o1.getAge() == o2.getAge()){
                    if(o1.getHeight() > o2.getHeight()){
                        return -1;
                    }
                }

                return 1;
            }
        });

        System.out.println(list);
    }
}
