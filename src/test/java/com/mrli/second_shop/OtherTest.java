package com.mrli.second_shop;

import org.junit.Test;

public class OtherTest {

    // @Test
    public void test(){
        String input = "/{version}/admin/backend-manager/{page}/{limit}";
        String regex = "/.*/admin/.*";
        System.out.println(input.matches(regex));
    }

   // @Test
    public void test2(){
        String path = OtherTest.class.getResource("").getPath();
        System.out.println(path);
    }
}
