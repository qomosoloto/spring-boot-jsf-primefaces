package com.shenbian.admin.test;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by qomo on 15-11-11.
 */
@Getter
@Setter
public class InnerClassExtendsOutClass {

    private String name;

    public InnerClassExtendsOutClass(String name) {
        this.name = name;
    }

    class InnerClassExtendsOut extends InnerClassExtendsOutClass {
        public InnerClassExtendsOut(String name) {
            super(name);
        }
    }

    @Getter
    @Setter
    class InnerClassNormal {
        private String name;

        public InnerClassNormal(String name) {
            this.name = name;

        }
    }

    public static void main(String[] args) {
        InnerClassExtendsOutClass innerClassExtendsOutClass = new InnerClassExtendsOutClass("PARENT AND OUT CLASS");
        InnerClassExtendsOutClass.InnerClassExtendsOut innerClass = innerClassExtendsOutClass.new
                InnerClassExtendsOut("CHILDREN AND " +
                "EXTENSION");

        InnerClassNormal innerClassNormal = innerClassExtendsOutClass.new InnerClassNormal
                ("NORMAL INNER CLASS");
        System.out.println(innerClassExtendsOutClass.getName());
        System.out.println(innerClass.getName());
        System.out.println(innerClassNormal.getName());

    }
}
