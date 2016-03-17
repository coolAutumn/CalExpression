package com.Autumn;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by coolAutumn on 3/5/16.
 */
public class ManiExprStr
{
    private String str;
    private boolean showAlliden;
    private Map<String,Double> idenAndVal;
    private List<String> tokenList;
    boolean checkBracket=true;
    int LeftBrackets=0;
    int RightBrackets=0;

    //这个start用来当遇到括号时进行递归时进行调用
    int start=0;
    public ManiExprStr(String str_get)
    {
        str=str_get;
        idenAndVal=new HashMap<>();
        tokenList=new ArrayList<>();
        init();
        getToken();
        meanAnal();
        if(RightBrackets!=LeftBrackets) {
            System.out.println("括号未配对成功.");
            System.exit(0);
        }
        System.out.println(idenAndVal);
    }

    private void init()
    {
        //清除文本中的空格
        str=str.replaceAll(" ","");
        char[] arr=str.toCharArray();
        int length=arr.length;
        if(arr[length-1]=='l'&arr[length-2]=='l'&arr[length-3]=='a')
        {
            showAlliden=true;
            str=new String(arr,0,length-3);
        }
        else {
            showAlliden=false;
        }
    }

    //进行词法分析,将各个token存入tokenList
    private void getToken()
    {
        char[] arr=str.toCharArray();
        int offset=0;
        int iter=0;
        boolean hasEnd=false;
        while(iter<arr.length)
        {
            char in=arr[iter];
            try {
                switch (in) {
                    case '=':
                        if((iter - offset)!=0) {
                            tokenList.add(new String(arr, offset, iter - offset));
                        }
                        tokenList.add(new String(arr, iter , 1));
                        offset = iter + 1;
                        break;
                    case '+':
                        if((iter - offset)!=0) {
                            tokenList.add(new String(arr, offset, iter - offset));
                        }
                        tokenList.add(new String(arr, iter , 1));
                        offset = iter + 1;
                        break;
                    case '-':
                        if((iter - offset)!=0) {
                            tokenList.add(new String(arr, offset, iter - offset));
                        }
                        tokenList.add(new String(arr, iter , 1));
                        offset = iter + 1;
                        break;
                    case '*':
                        if((iter - offset)!=0) {
                            tokenList.add(new String(arr, offset, iter - offset));
                        }
                        tokenList.add(new String(arr, iter , 1));
                        offset = iter + 1;
                        break;
                    case '/':
                        if((iter - offset)!=0) {
                            tokenList.add(new String(arr, offset, iter - offset));
                        }
                        tokenList.add(new String(arr, iter , 1));
                        offset = iter + 1;
                        break;
                    case '%':
                        if((iter - offset)!=0) {
                            tokenList.add(new String(arr, offset, iter - offset));
                        }
                        tokenList.add(new String(arr, iter , 1));
                        offset = iter + 1;
                        break;
                    case ';':
                        if((iter - offset)!=0) {
                            tokenList.add(new String(arr, offset, iter - offset));
                        }
                        tokenList.add(new String(arr, iter , 1));
                        offset = iter + 1;
                        break;
                    case '(':
                        if((iter - offset)!=0) {
                            tokenList.add(new String(arr, offset, iter - offset));
                        }
                        tokenList.add(new String(arr, iter , 1));
                        offset = iter + 1;
                        break;
                    case ')':
                        if((iter - offset)!=0) {
                            tokenList.add(new String(arr, offset, iter - offset));
                        }
                        tokenList.add(new String(arr, iter , 1));
                        offset = iter + 1;
                        break;
                }
            }catch (Exception e)
            {
                System.out.println("不是正确的表达式");
                e.printStackTrace();
                System.exit(0);
            }
            iter++;
        }
        try {
            if (tokenList.size() == 0)
            {
                throw new Exception("没有完整的表达式");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            System.exit(0);
        }
    }

    //进行词义分析
    private void meanAnal()
    {
        String iden="";
        String beforeS="";
        List<String> exprList=new ArrayList<>();
        for(String s:tokenList)
        {
            switch (s)
            {
                case "=":
                    iden=beforeS;
                    exprList.clear();
                    break;
                case ";":
                    start=0;
                    Double res=calExpr(exprList);
                    exprList.clear();
                    idenAndVal.put(iden,res);
                    break;
                default:
                    beforeS=s;
                    exprList.add(s);
                    break;
            }
        }
    }

    //根据所得中缀表达式进行计算
    private double calExpr(List<String> exprList)
    {
        double res=0.0;
        Stack<String> operandStack=new Stack<>();
        Stack<String> operatorStack=new Stack<>();
        boolean oneOperand=false;
        boolean needCal=false;
        for(int i=start;i<exprList.size();i++)
        {
            String s=exprList.get(i);
            try {
                switch (s) {
                    case "+":
                        operatorStack.push(s);
                        oneOperand=false;
                        needCal=false;
                        break;
                    case "-":
                        oneOperand=false;
                        operatorStack.push(s);
                        needCal=true;
                        break;
                    case "*":
                        operatorStack.push(s);
                        if(oneOperand) {
                            needCal=false;
                        }
                        else{
                            needCal=true;
                        }
                        break;
                    case "/":
                        operatorStack.push(s);
                        if(oneOperand) {
                            needCal=false;
                        }
                        else{
                            needCal=true;
                        }
                        break;
                    case "%":
                        operatorStack.push(s);
                        if (oneOperand) {
                            needCal=false;
                        }
                        else{
                            needCal=true;
                        }
                        break;
                    case "(":
                        LeftBrackets+=1;
                        checkBracket=false;
                        start=i+1;
                        operandStack.push(""+calExpr(exprList));
                        if(operandStack.size()==1)
                        {
                            oneOperand=true;
                        }
                        else {
                            oneOperand=false;
                        }
                        i=start-1;
                        break;
                    case ")":
                        RightBrackets+=1;
                        start=i+1;
                        i=tokenList.size();
                        break;
                    default:
                        operandStack.push(s);
                        if(RightBrackets>LeftBrackets)
                        {
                            throw new Exception("括号未配对");
                        }
                        if(needCal)
                        {
                            double temp4=Double.valueOf(operandStack.pop());
                            double temp3=0;
                            if(operandStack.size()!=0) {
                                temp3 = Double.valueOf(operandStack.pop());
                            }
                            String opeartor=operatorStack.pop();
                            operandStack.push(""+calSimpleExpr(temp3,temp4,opeartor));
                            needCal=false;
                        }
                        if(oneOperand)
                        {
                            double temp2=Double.valueOf(operandStack.pop());
                            double temp1=0;
                            if(operandStack.size()!=0) {
                                temp1 = Double.valueOf(operandStack.pop());
                            }
                            String opeartor=operatorStack.pop();
                            operandStack.push(""+calSimpleExpr(temp1,temp2,opeartor));
                        }
                        if(operandStack.size()==1)
                        {
                            oneOperand=true;
                        }
                        else {
                            oneOperand=false;
                        }
                        break;
                }
            }catch (Exception e)
            {
                System.out.println("貌似你的数字里掺了点东西.或者你数学计算符号前后漏了点数字.");
            }
        }
        while(operandStack.size()>1)
        {
            double temp2=Double.valueOf(operandStack.pop());
            double temp1=Double.valueOf(operandStack.pop());
            String opeartor=operatorStack.pop();
            operandStack.push(""+calSimpleExpr(temp1,temp2,opeartor));
        }
        try {
            res = Double.valueOf(operandStack.get(0));
            if(checkBracket)
            {
                throw new Exception("貌似括号什么的没有配对成功.");
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("貌似你的数字里掺了点东西.或者你数学计算符号前后漏了点数字.");
            System.exit(0);
        }
        return res;
    }

    //一个简单的双目表达式计算
    private double calSimpleExpr(double left,double right,String op)
    {
        switch (op)
        {
            case "+":
                return left+right;
            case "-":
                return left-right;
            case "*":
                return left*right;
            case "/":
                return left/right;
            case "%":
                return left%right;
        }
        return 0;
    }

}
