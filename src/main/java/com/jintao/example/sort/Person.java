package com.jintao.example.sort;

/**
 * Created with IDEA
 * author:jinjueYang
 * Date:2018/4/10
 * Time:16:07
 */
public class Person {
    private int age;
    private int height;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeight() {
        return height;
    }

    public Person(int age, int height) {
        this.age = age;
        this.height = height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (age != person.age) return false;
        return height == person.height;
    }

    @Override
    public int hashCode() {
        int result = age;
        result = 31 * result + height;
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +

                "age=" + age +
                ", height=" + height +
                '}';
    }
}
