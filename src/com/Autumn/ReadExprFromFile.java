package com.Autumn;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by coolAutumn on 3/5/16.
 */
public class ReadExprFromFile
{
    private File file;
    private FileInputStream fin;
    private String str_get;

    public ReadExprFromFile()
    {
        //从当前路径下读取名为expression.txt的文本文件
        file=new File("expression.txt");
        try {
            fin = new FileInputStream(file);
            str_get="";
        }catch (Exception e)
        {
            System.out.println("expression.txt not found");
            e.printStackTrace();
        }
    }

    /**
     * 用于从文件中取得表达式并且清除换行符
     * @return
     */
    public String getExpr ()
    {
        byte[] bytes=new byte[16];
        int hasread=0;
        int offset=0;
        try {
            while ((hasread = fin.read(bytes)) != -1) {
                str_get += new String(bytes, 0, hasread);
                offset += hasread;
            }
            if(str_get.length()<5)
            {
                throw new Exception("所写表达式无法构成一个完整的表达式");
            }
        }catch (Exception e){
            e.printStackTrace();
            System.exit(0);
        }
        finally {
            str_get=str_get.replaceAll("\n","");
            return str_get;
        }
    }
}
