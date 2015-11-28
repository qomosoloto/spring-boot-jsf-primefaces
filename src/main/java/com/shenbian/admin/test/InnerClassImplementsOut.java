package com.shenbian.admin.test;

/**
 * Created by qomo on 15-11-11.
 */
public interface InnerClassImplementsOut {

    void sayHello();

    class InnerClassImpl implements InnerClassImplementsOut {

        @Override
        public void sayHello() {
            System.out.println("Hello,I'm implementation!");
        }
    }

    class InnerClassNormal {

        public void sayHaha() {
            System.out.println("Haha,I'm haha!");
        }
    }

    static void main(String[] args) {
        InnerClassImpl innerClass = new InnerClassImpl();
        innerClass.sayHello();


    }
}
