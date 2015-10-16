package com.bryan.okhttpdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;

public class MainActivity extends Activity {

	
	public static final String SERVER_IP="http://192.168.6.59:8080/web/";
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
	}

	public void testGet(View v) {
		//创建一个okHttpClient对象
		OkHttpClient okClient=new OkHttpClient();
		//创建一个Request
		Request request=new Request.Builder()
		   .url(SERVER_IP)
		   //.url("https://github.com/cxbiao")
		   .build();
		//new call
		final Call call=okClient.newCall(request);
		//请求加入调度
		
	
		call.enqueue(new Callback() {
			
			@Override
			public void onResponse(Response response) throws IOException {
				//这是在子线程中
				String ret=response.body().string();
				
				Log.e("GET", ret);
			
			}
			
			@Override
			public void onFailure(Request response, IOException e) {
				Log.e("ERROR", e.getMessage());
				
			}
		});

	}

	public void testPost(View v) {
		OkHttpClient okClient=new OkHttpClient();
        FormEncodingBuilder builder=new FormEncodingBuilder();
        builder.add("username", "cxb");
        builder.add("password", "cxb");
        Request request=new Request.Builder()
                     .url(SERVER_IP+"LoginServlet")
                     .post(builder.build())
                     .build();
        okClient.newCall(request).enqueue(new Callback() {
			
			@Override
			public void onResponse(Response response) throws IOException {
				String ret=response.body().string();
				Looper.prepare();
				Toast.makeText(getBaseContext(), ret, 0).show();
				Looper.loop();
			}
			
			@Override
			public void onFailure(Request response, IOException e) {
				Log.e("ERROR", e.getMessage());
				
			}
		});
        
        
	}

	public void testUpload(View v) {
		OkHttpClient okClient=new OkHttpClient();
         File file=new File(Environment.getExternalStorageDirectory(),"abc.mp3");
         RequestBody fileBody=RequestBody.create(MediaType.parse("application/octet-stream"), file);
         RequestBody requestBody=new MultipartBuilder()
         .type(MultipartBuilder.FORM)
         .addPart(Headers.of( 
        		 "Content-Disposition", 
                 "form-data; name=\"filename\""),
                 RequestBody.create(null, "发如雪"))
          .addPart(Headers.of(
        		  "Content-Disposition",
        	       "form-data; name=\"formfile\"; filename=\"abc.mp3\""),fileBody)
          .build();
           
         Request request=new Request.Builder()
         .url(SERVER_IP+"UploadFileServlet")
         .post(requestBody)
         .build();
         
         okClient.newCall(request).enqueue(new Callback() {
			
			@Override
			public void onResponse(Response response) throws IOException {
				String ret=response.body().string();
				Looper.prepare();
				Toast.makeText(getBaseContext(), ret, 0).show();
				Looper.loop();
				
			}
			
			@Override
			public void onFailure(Request arg0, IOException arg1) {
				// TODO Auto-generated method stub
				
			}
		});
                 
	}
	
	

	
	
	
}
