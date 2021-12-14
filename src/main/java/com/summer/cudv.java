package com.summer;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;

import java.io.*;

public class cudv extends AnAction {


    @Override
    public void actionPerformed(AnActionEvent e) {
        File parent = new File(e.getData(PlatformDataKeys.VIRTUAL_FILE).getPath());
        String  name = parent.getName();
        String[] strs =parent.getPath().split("src\\\\main\\\\java");
        String dirName = name.substring(0,1).toUpperCase()+(name.substring(1,name.length()));
        String packageName =  strs[strs.length-1].replace("\\",".");//com.siwei.tongche.ui.customer
        packageName = packageName.substring(1,packageName.length());
        String importXActivity = "import {0}.base.ui.XActivity;".replace("{0}",packageName);


        String projectBasePath = e.getData(PlatformDataKeys.PROJECT).getBasePath();
        File file = new File(projectBasePath,"cudv");
        if(!file.exists()){
            return;
        }
        String xpath = readText(file);//com.siwei.tsishan
        if(xpath==null||xpath.equals("")){
            return;
        }



        String CTText = ("package {1};\n" +
                "import {2}.base.ui.XActivity;\npublic class {0}CT extends XActivity<{0}UI,{0}DE,{0}VA> {\n\n\n}").replace("{0}",dirName).replace("{1}",packageName).replace("{2}",xpath);
        createFile(new File(parent,"{0}CT.java".replace("{0}",dirName)),CTText);

        String UIText =  "package {0};\nimport {1}.base.ui.UI;\npublic class {2}UI extends UI<Ct{2}Binding> {\n}".replace("{0}",packageName).replace("{1}",xpath).replace("{2}",dirName);
        createFile(new File(parent,"{0}UI.java".replace("{0}",dirName)),UIText);

        String DEText =  "package {0};\nimport {1}.base.ui.DE;\npublic class {2}DE extends DE{\n}".replace("{0}",packageName).replace("{1}",xpath).replace("{2}",dirName);
        createFile(new File(parent,"{0}DE.java".replace("{0}",dirName)),DEText);

        String VAText =  "package {0};\nimport {1}.base.ui.VA;\npublic class {2}VA extends VA{\n}".replace("{0}",packageName).replace("{1}",xpath).replace("{2}",dirName);
        createFile(new File(parent,"{0}VA.java".replace("{0}",dirName)),VAText);

        String xmlText = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<layout xmlns:app=\"http://schemas.android.com/apk/res-auto\"\n" +
                "    >\n" +
                "    <data>\n" +
                "        <variable\n" +
                "            name=\"{0}\"\n" +
                "            type=\"String\" />\n" +
                "    </data>\n" +
                "    <LinearLayout\n" +
                "        xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
                "        xmlns:tools=\"http://schemas.android.com/tools\"\n" +
                "        android:layout_width=\"match_parent\"\n" +
                "        android:layout_height=\"match_parent\"\n" +
                "        android:orientation=\"vertical\"\n" +
                "    </LinearLayout>\n" +
                "</layout>\n";
        xmlText = xmlText.replace("{0}",dirName);

        createFile(new File(parent,"{0}.xml".replace("{0}",dirName.toLowerCase())),xmlText);

    }

    private void createFile(File file,String text){
        if(file.exists()){
            return;
        }
        try {
            file.createNewFile();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(text);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public String readText(File file){
        BufferedReader bufferedReader = null;
        StringBuffer stringBuffer  = new StringBuffer();
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String temp = "";
            while ((temp = bufferedReader.readLine())!=null){
                stringBuffer.append(temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bufferedReader!=null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuffer.toString();
    }
}
