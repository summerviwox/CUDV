package com.summer;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;

import java.io.*;

public class CUDVFT extends AnAction {


    Tool tool = new Tool();

    @Override
    public void actionPerformed(AnActionEvent e) {
        tool.actionPerformed(e,FileType.fragment);
    }
}
