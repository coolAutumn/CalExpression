package com.Autumn;

public class Main {

    public static void main(String[] args) {
	// write your code here
        ReadExprFromFile re=new ReadExprFromFile();
        ManiExprStr mani=new ManiExprStr(re.getExpr());
    }
}
