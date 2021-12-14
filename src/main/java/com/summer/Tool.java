package com.summer;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;

import java.io.*;

public class Tool {

    public void actionPerformed(AnActionEvent e,FileType fileType) {
        File parent = new File(e.getData(PlatformDataKeys.VIRTUAL_FILE).getPath());
        String  name = parent.getName();
        String[] strs =parent.getPath().split("src\\\\main\\\\java");
        String dirName = name.substring(0,1).toUpperCase()+(name.substring(1,name.length()));
        String packageName =  strs[strs.length-1].replace("\\",".");//com.siwei.tongche.ui.customer
        packageName = packageName.substring(1,packageName.length());
        String projectBasePath = e.getData(PlatformDataKeys.PROJECT).getBasePath();
        File file = new File(projectBasePath,"cudv");
        if(!file.exists()){
            return;
        }
        String applicationName = readText(file);//com.siwei.tsishan
        if(applicationName==null||applicationName.equals("")){
            return;
        }
        //0-applicationName 1-dirName  2-packageName 3-fileType
        String CTText = readResText(fileType).replace("{0}",applicationName).replace("{1}",dirName).replace("{2}",packageName).replace("{3}",fileType==FileType.activity?"AT":"FT");
        createFile(new File(parent,"{1}{3}.java".replace("{1}",dirName).replace("{3}",fileType==FileType.activity?"AT":"FT")),CTText);

        String UIText = readResText(FileType.ui).replace("{0}",applicationName).replace("{1}",dirName).replace("{2}",packageName).replace("{3}",fileType==FileType.activity?"AT":"FT");
        createFile(new File(parent,"{1}UI.java".replace("{1}",dirName)),UIText);

        String DEText = readResText(FileType.de).replace("{0}",applicationName).replace("{1}",dirName).replace("{2}",packageName).replace("{3}",fileType==FileType.activity?"AT":"FT");
        createFile(new File(parent,"{1}DE.java".replace("{1}",dirName)),DEText);

        String VAText = readResText(FileType.va).replace("{0}",applicationName).replace("{1}",dirName).replace("{2}",packageName).replace("{3}",fileType==FileType.activity?"AT":"FT");
        createFile(new File(parent,"{1}VA.java".replace("{1}",dirName)),VAText);

        String XMLText = readResText(FileType.xml).replace("{0}",applicationName).replace("{1}",dirName).replace("{2}",packageName).replace("{3}",fileType==FileType.activity?"AT":"FT");
        createFile(new File(parent,"{1}.xml".replace("{1}",dirName)),XMLText);
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

    public String readResText(FileType fileType){
        String fileName = "";
        switch (fileType){
            case activity:
                fileName = "Activity.text";
                break;
            case fragment:
                fileName = "Fragment.text";
                break;
            case ui:
                fileName = "UI.text";
                break;
            case de:
                fileName = "DE.text";
                break;
            case va:
                fileName = "VA.text";
                break;
            case xml:
                fileName = "XML.text";
                break;
        }
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer stringBuffer = new StringBuffer();
        String line = "";
        while (true){
            try {
                if ((line = bufferedReader.readLine())==null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            stringBuffer.append(line);
            stringBuffer.append("\n");
        }
        return stringBuffer.toString();
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
