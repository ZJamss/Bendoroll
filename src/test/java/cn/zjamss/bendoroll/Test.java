package cn.zjamss.bendoroll;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Program: cn.zjamss.bendoroll
 * @Description:
 * @Author: ZJamss
 * @Create: 2022-08-06 13:42
 **/
public class Test {

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("\\/.*(?=\\/)");
        Matcher matcher = pattern.matcher("/public/test.txt");
        if (matcher.find())
            System.out.println(matcher.group());
    }
}
