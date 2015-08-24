
# OkHttp的应用示例，基本都是鸿祥的代码，只是将gson改成了fastjson,这个效率更高些

###异步Get访问
```
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
```
###文件上传
```
public void uploadFile(View view) throws IOException
    {
        File file = new File(Environment.getExternalStorageDirectory(), "abc.mp3");

        OkHttpClientManager.postAsyn(SERVER_IP+"UploadFileServlet",//
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
                        Log.e("TAG", filePath);
                    }
                },//
                file,//
                "formfile",//
                new OkHttpClientManager.Param[]{
                        new OkHttpClientManager.Param("filename", "发如雪"),
                        new OkHttpClientManager.Param("filedes", "周杰伦的哥")}
        );
    }
```

###文件下载
```
       //不能下载中文
        OkHttpClientManager.downloadAsyn(SERVER_IP+"files/qa.docx",
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
                    	Log.e("TAG", response);
                    }
                });
```