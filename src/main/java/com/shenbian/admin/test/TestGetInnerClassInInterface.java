package com.shenbian.admin.test;

/**
 * Created by qomo on 15-11-11.
 */
public class TestGetInnerClassInInterface implements InnerClassImplementsOut {
    @Override
    public void sayHello() {
        System.out.println("Hello,I'm testGetInnerClassInInterface!");
    }

    public static void main(String[] args) {
        InnerClassImplementsOut testGetInnerClassInInterface = new TestGetInnerClassInInterface();
        testGetInnerClassInInterface.sayHello();
        new InnerClassNormal().sayHaha();

        InnerClassImplementsOut.InnerClassImpl innerClass = new InnerClassImpl();
        innerClass.sayHello();


        InnerClassImplementsOut.InnerClassNormal innerClassNormal = new InnerClassNormal();
        innerClassNormal.sayHaha();

        InnerClassImplementsOut innerClassImplementsOut = () -> {
            System.out.println("haha.............");
        };

        innerClassImplementsOut.sayHello();
    }
}
