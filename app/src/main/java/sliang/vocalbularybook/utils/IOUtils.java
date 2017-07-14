package sliang.vocalbularybook.utils;

import java.io.File;
import java.io.IOException;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

/**
 * Created by Administrator on 2017/7/13.
 */


/**
 * @name ${PROJECT_NAME}
 * @class name：${PACKAGE_NAME}
 * @class describe
 * @anthor ${USER}
 * @time ${DATE}
 * @change
 * @chang time
 * @class describe
 */
public class IOUtils {



    public static  void write(String filePath,String input){

        BufferedSink bufferedSink = null;
        try {
//            String path = Environment.getExternalStorageDirectory().getPath();
//            File file = new File(path, filePath);
            File file = new File(filePath);
            Sink sink = Okio.appendingSink(file);
            bufferedSink = Okio.buffer(sink);
            bufferedSink.writeUtf8(input);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bufferedSink) {
                    bufferedSink.flush();
                    bufferedSink.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    };
    public static  void read(String filePath){
        BufferedSource bufferedSource = null;
        String outputFile="F:\\Others\\dictionCopy.txt";
        try {
            File file1 = new File(outputFile);
            if(file1.exists()){
                file1.delete();
            }
//            String path = Environment.getExternalStorageDirectory().getPath();
//            File file = new File(path, filePath);
            File file = new File(filePath);
            Source source = Okio.source(file);
            bufferedSource = Okio.buffer(source);
            String read="";
            do{
                read = bufferedSource.readUtf8Line();
                if(read==""){
                    continue;
                }
                int firsNoSpaceCharNum = IOUtils.getFirsNoSpaceCharNum(read);
                int firstSpaceCharNum = IOUtils.getFirstSpaceCharNum(firsNoSpaceCharNum, read);
                 String word = read.substring(firsNoSpaceCharNum, firstSpaceCharNum);

                write(outputFile,word+"\n");
            }while (read!=null);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bufferedSource) {
                    bufferedSource.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    /**
     *
     *
     * 获取字符串中的第一个非空格字符位置
     *
     *  @param
     *  @return
     *
     */
    public static int getFirsNoSpaceCharNum(String input){
        return getFirsNoSpaceCharNum(0,input);
    }


   /**
    *
    *
    * 获取字符串中从第num个字符开始的的第一个非空格字符位置
    *
    *  @param
    *  @return
    *
    */

    public static int getFirsNoSpaceCharNum(int num,String input){
        boolean spaceChar=true;
        for(int i=num; i<input.length();i++){
            char c = input.charAt(i);
             spaceChar &= Character.isSpaceChar(c);
            if(!spaceChar){
                return i;
            }
        }
        return -1;
    }


    /**
     *
     * 获取 从第num个字符开始的第一个空格的位置
     *
     *  @param
     *
     *  @return
     *
     */
    public static  int getFirstSpaceCharNum(int num,String input){
        boolean spaceChar=false;
        for(int i=num; i<input.length();i++){
            char c = input.charAt(i);
            spaceChar |= Character.isSpaceChar(c);
            if(spaceChar){
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        String inputFile="F:\\Others\\diction.txt";
        String outputFile="";
        read(inputFile);
    }
}
