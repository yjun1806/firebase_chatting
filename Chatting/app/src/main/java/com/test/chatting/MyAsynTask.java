package com.test.chatting;

import android.os.AsyncTask;

//AsynTask<doLn에서 사용할 변수종류, onProgressUpdate에서 사용할 변수 종류, onPostExecute에서 사용할 변수종류>
public class MyAsynTask extends AsyncTask<Integer, Integer, Integer> {
    @Override
    protected Integer doInBackground(Integer... integers) { // 백그라운드에서 작업중일때
        return null;
    }

    @Override
    protected void onPostExecute(Integer integer) { // 백그라운드 작업이 완료된 후의 결과를 얻는다.
        super.onPostExecute(integer);
    }

    @Override
    protected void onProgressUpdate(Integer... values) { // doIn이 실행되는 도중 publishProgress를 호출할때 실행된다.
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPreExecute() { // 백그라운드 작업 시작전에 호출되는 메소드
        super.onPreExecute();
    }
}
