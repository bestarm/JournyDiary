package com.tc.networking;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.AsyncTask;

public class HttpClient {
	private String url;
    private HttpURLConnection con;
    private OutputStream out;
    
	private String delimiter = "--";
    private String boundary =  "SwA"+Long.toString(System.currentTimeMillis())+"SwA";

	public HttpClient(String url) {		
		this.url = url;
	}
	
	public byte[] downloadImage(String imgName) {
		ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
		try {
			System.out.println("URL ["+url+"] - Name ["+imgName+"]");
			
			HttpURLConnection con = (HttpURLConnection) ( new URL(url)).openConnection();
			con.setRequestMethod("POST");
			con.setDoInput(true);
			con.setDoOutput(true);
			con.connect();
			con.getOutputStream().write( ("name=" + imgName).getBytes());
			
			InputStream is = con.getInputStream();
			byte[] b = new byte[1024];
			
			while ( is.read(b) != -1)
				// write buffer b to outputstream byteOutStream
				byteOutStream.write(b);
			
			con.disconnect();
		}
		catch(Throwable t) {
			t.printStackTrace();
		}
		
		return byteOutStream.toByteArray();
	}

	public void connectForMultipart() throws Exception {
		con = (HttpURLConnection) ( new URL(url)).openConnection();
		con.setRequestMethod("POST");
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
		con.connect();
		out = con.getOutputStream();
	}
	
	public void addTextPart(String value) throws Exception {
		out.write( (delimiter + boundary + "\r\n").getBytes());
		out.write( "Content-Type: text/plain\r\n".getBytes());
		out.write( ("Content-Disposition: form-data\r\n").getBytes());;
		out.write( ("\r\n" + value + "\r\n").getBytes());
	}
	
	public void addFilePart(String paramName, String fileName, byte[] data) throws Exception {
		out.write( (delimiter + boundary + "\r\n").getBytes());
		out.write( ("Content-Disposition: form-data; name=\"" + paramName +  "\"; filename=\"" + fileName + "\"\r\n"  ).getBytes());
		out.write( ("Content-Type: application/octet-stream\r\n"  ).getBytes());
		out.write( ("Content-Transfer-Encoding: binary\r\n"  ).getBytes());
		out.write("\r\n".getBytes());
   
		out.write(data);
		
		out.write("\r\n".getBytes());
	}
	
	public void finishMultipart() throws Exception {
		out.write( (delimiter + boundary + delimiter + "\r\n").getBytes());
	}
	
	public String getResponse() throws Exception {
		InputStream is = con.getInputStream();
		byte[] b1 = new byte[1024];
		StringBuffer buffer = new StringBuffer();
		
		while ( is.read(b1) != -1)
			buffer.append(new String(b1));
		
		con.disconnect();
		
		return buffer.toString();
	}

	public void uploadJournyJSON(String JSON){
		new UploadTask().execute(JSON);
	}

	private class UploadTask extends AsyncTask<String,Void,Void>{

		@Override
		protected Void doInBackground(String... params) {
				try {
					connectForMultipart();
					addTextPart(params[0]);
					finishMultipart();
				} catch (Exception e) {
					e.printStackTrace();
				}

			return null;
		}
	}
}