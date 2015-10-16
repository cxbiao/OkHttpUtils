package com.bryan.okhttpdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bryan.okhttpdemo.domain.Course;
import com.bryan.okhttpdemo.utils.OkHttpClientManager;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TestActivity extends Activity
{

    private TextView mTv;
    private ImageView mImageView;
    private OkHttpClient mOkHttpClient;
    
    public static final String SERVER_IP="http://192.168.6.59:8080/web/";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        mOkHttpClient = new OkHttpClient();

        mTv = (TextView) findViewById(R.id.id_textview);
        mImageView = (ImageView) findViewById(R.id.id_imageview);

    }

    public void getCourse(View view)
    {
       OkHttpClientManager.getAsyn(SERVER_IP+"ListServlet", new OkHttpClientManager.ResultCallback<String>() {

		@Override
		public void onError(Request request, Exception e) {
			e.printStackTrace();
			
		}


		@Override
		public void onResponse(String response) {
			mTv.setText(response);
			
		}
	});
    }


    public void getCourses(View view)
    {
        OkHttpClientManager.getAsyn(SERVER_IP+"ListServlet?format=json",
                new OkHttpClientManager.ResultCallback<List<Course>>()
                {
                    @Override
                    public void onError(Request request, Exception e)
                    {
                        e.printStackTrace();
                    }


					@Override
					public void onResponse(List<Course> response) {
						 Log.e("TAG", response.size() + "");
	                     mTv.setText(response.get(1).title);
						
					}
                });


    }

    

    public void getHtml(View view)
    {
        OkHttpClientManager.getAsyn("https://github.com/cxbiao", new OkHttpClientManager.ResultCallback<String>()
        {
            @Override
            public void onError(Request request, Exception e)
            {
                e.printStackTrace();
            }

            @Override
            public void onResponse(String u)
            {
                mTv.setText(Html.fromHtml(u));
            }
        });


    }


    public void getHttpsHtml(View view)
    {
        //https://192.168.56.1:8443/
        //https://kyfw.12306.cn/otn/
        //https://192.168.187.1:8443/
        OkHttpClientManager.getAsyn("https://kyfw.12306.cn/otn/", new OkHttpClientManager.ResultCallback<String>()
        {
            @Override
            public void onError(Request request, Exception e)
            {
                e.printStackTrace();
            }

            @Override
            public void onResponse(String u)
            {
                mTv.setText(Html.fromHtml(u));
            }
        }, this);


    }

    public void getImage(View view)
    {
        OkHttpClientManager.getDisplayImageDelegate().displayImage(mImageView, "http://pic.joke01.com/uppic/12-12/17/17230409.jpg");
    }

    public void uploadFile(View view) throws IOException
    {
        File file = new File(Environment.getExternalStorageDirectory(), "abc.mp3");
        
//        OkHttpClientManager.getInstance().getPostDelegate().postAsyn(SERVER_IP+"UploadFileServlet",
//        		file,//
//                new OkHttpClientManager.ResultCallback<String>()
//                        {
//                            @Override
//                            public void onError(Request request, Exception e)
//                            {
//                                e.printStackTrace();
//                            }
//
//                            @Override
//                            public void onResponse(String filePath)
//                            {
//                            	Toast.makeText(getBaseContext(), "上传成功:"+filePath, 0).show();
//                            }
//                        }
//        );

      
        OkHttpClientManager.getUploadDelegate().postAsyn(SERVER_IP+"UploadFileServlet",//
        		"formfile",//
        		file,//
                new OkHttpClientManager.Param[]{
                        new OkHttpClientManager.Param("filename", "发如雪"),
                        new OkHttpClientManager.Param("filedes", "周杰伦的哥")},
                new OkHttpClientManager.ResultCallback<String>()
                        {
                            @Override
                            public void onError(Request request, Exception e)
                            {
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(String filePath)
                            {
                            	Toast.makeText(getBaseContext(), "上传成功:"+filePath, Toast.LENGTH_SHORT).show();
                            }
                        }
       ,this);
    }


    public void downloadFile(View view) throws Exception
    {
       //不能下载中文
        OkHttpClientManager.getDownloadDelegate().downloadAsyn(SERVER_IP+"files/qa.docx",
                Environment.getExternalStorageDirectory().getAbsolutePath(),
                new OkHttpClientManager.ResultCallback<String>()
                {
                    @Override
                    public void onError(Request request, Exception e)
                    {
                    	e.printStackTrace();
                    }

                    @Override
                    public void onResponse(String response)
                    {
                    	//下载后的文件路径
                    	Toast.makeText(getBaseContext(), "下载成功:"+response, Toast.LENGTH_SHORT).show();
                    }
                });
    	
    }


}
