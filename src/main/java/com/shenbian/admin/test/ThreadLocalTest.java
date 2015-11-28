package com.shenbian.admin.test;

import java.util.Random;

/**
 * Created by qomo on 15-11-12.
 */
public class ThreadLocalTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {

            new Thread(() -> {
                int data = new Random().nextInt();
                System.out.println("data---" + data);
                MyData.getInstance().setAge(data);
                MyData.getInstance().setName(Thread.currentThread() + "-" + data);
                new ThreadA().getData();
                new ThreadB().getData();
            }).start();
        }
    }

    static class ThreadA {
        public void getData() {
            System.out.println("ThreadA -- " + Thread.currentThread().getName() + "-age=" + MyData.getInstance().getAge() + ",name-" + MyData.getInstance().getName());
        }
    }

    static class ThreadB {
        public void getData() {
            System.out.println("ThreadB -- " + Thread.currentThread().getName() + "-age=" + MyData.getInstance().getAge() + ",name-" + MyData.getInstance().getName());
        }
    }


}

class MyData {
    private String name;
    private int age;
    private static MyData instance = null;
    private static ThreadLocal<MyData> map = new ThreadLocal<MyData>();

    private MyData() {
    }

    public static MyData getInstance() {
        instance = map.get();
        if (instance == null) {
            instance = new MyData();
            map.set(instance);
        }
        return instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
