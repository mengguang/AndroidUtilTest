package org.xmgu2008.mengguang.androidutiltest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.Utils;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.util.List;


@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

    @ViewById
    TextView logMessage;

    @Click
    void startButton() {
        doFileTest();
    }

    @Background
    void doFileTest() {
        String dir = getApplicationContext().getFilesDir().getPath();
        writeLogMessage(dir);
        if(FileUtils.isDir(dir)) {
            List<File> list = FileUtils.listFilesInDir(dir);
            for(File f: list) {
                writeLogMessage(f.getPath());
            }
            writeLogMessage("dir exists.");
            if(FileUtils.isFile(dir + "/testfile")) {
                String txt = FileIOUtils.readFile2String(dir + "/testfile");
                writeLogMessage(txt);
            } else {
                FileIOUtils.writeFileFromString(dir + "/testfile","Hello, World!");
            }
        } else {
            writeLogMessage("dir non exist, will create it.");
            FileUtils.createOrExistsDir(dir);
        }
    }

    @UiThread
    void writeLogMessage(String message) {
        logMessage.append(message + "\n");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils.init(getApplication());
    }
}
