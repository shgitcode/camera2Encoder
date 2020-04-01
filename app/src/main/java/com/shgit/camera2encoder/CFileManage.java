package com.shgit.camera2encoder;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CFileManage {
    private final static  String TAG = "CFileManage";
    private final int READ_DATA_BUF_LEN = 2048;

    // 输出文件
    private File m_cAudOutputFile = null;
    private FileOutputStream m_cAudioFos = null;
    private BufferedOutputStream m_cAudioBos = null;
    // 输入文件
    private File m_cAudInputFile = null;
    private FileInputStream m_cAudioFis = null;
    private BufferedInputStream m_cAudioBis = null;

    public  String m_sSavePath = null;

    /*
     *  fileName : 文件名：cap.h264 / cap.pcm
     * 创建存储音视频的文件
     * */
    public void createSavedFile(String fileName){

        //System.currentTimeMillis()
        m_sSavePath = Environment.getExternalStorageDirectory().getAbsolutePath()  +"/test/"+ fileName;

        Log.v(TAG, " save file:" + m_sSavePath);

        m_cAudOutputFile = new File(m_sSavePath);
        if (!m_cAudOutputFile.getParentFile().exists()) {
            m_cAudOutputFile.getParentFile().mkdirs();
        }

        try {
            m_cAudOutputFile.createNewFile();
            m_cAudioFos = new FileOutputStream(m_cAudOutputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        m_cAudioBos = new BufferedOutputStream(m_cAudioFos, 200 * 1024);

    }


    public int writeSavedFile(byte[] data, int length) {
        if (m_cAudioBos == null) {
            return 1;
        }

        try {
            m_cAudioBos.write(data, 0, length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public String getSavedFileName(){
        return m_sSavePath;
    }

    public void closeSavedFile() {
        try {
            if (m_cAudioBos != null) {
                m_cAudioBos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (m_cAudioBos != null) {
                try {
                    m_cAudioBos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    m_cAudioBos = null;
                }
            }
        }
        m_cAudOutputFile = null;
    }

    /*
     *  filePath:需要打开已存储音视频的文件路径名
     * */
    public int openExistFile(String filePath){
        Log.v(TAG, " open filePath :" + filePath);
        m_cAudInputFile = new File(filePath);
        if (!m_cAudInputFile.exists()) {
            Log.v(TAG, " open filePath [" + filePath+"] not exist!");
            return 1;
        }

        try {
            m_cAudioFis = new FileInputStream(m_cAudInputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int readExistFile(byte[] sBuf){

        int nRet = -1;

        if (m_cAudioFis == null) {
            return nRet;
        }

        if (sBuf == null) {
            return nRet;
        }

        try {
            /*
             * nRet: must be grater zero;
             * */
            nRet = m_cAudioFis.read(sBuf, 0, sBuf.length);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return nRet;
    }

    public void closeExistFile(){

        if (m_cAudioFis != null) {
            try {
                m_cAudioFis.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                m_cAudioFis = null;
            }
        }

        m_cAudInputFile = null;

    }
}

