package com.sh.zsh.code.shphotoselectorlibrary;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.widget.ImageView;
/**
 * Created by zhush on 2017/3/6
 * E-mail 405086805@qq.com
 * PS
 */

public class BitmapUtil {
	
	Context context;
	
	public BitmapUtil(Context context) {
		this.context=context;
	}

	/**
	 * 获取所有照片
	 * @return
     */
	public ArrayList<String>  listAlldir(){
    	Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    	Uri uri = intent.getData();
    	ArrayList<String> list = new ArrayList<String>();
    	String[] proj ={MediaStore.Images.Media.DATA};
    	Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);//managedQuery(uri, proj, null, null, null);
    	while(cursor.moveToNext()){
    		String path =cursor.getString(0);
    		list.add(new File(path).getAbsolutePath());
    	}
		return list;
    }
	
	public List<DireInfo> LocalImgFileList(){
		List<DireInfo> data=new ArrayList<DireInfo>();
		String filename="";
		List<String> allimglist=listAlldir();
		List<String> retulist=new ArrayList<String>();
		if (allimglist!=null) {
			Set set = new TreeSet();
			String []str;
			for (int i = 0; i < allimglist.size(); i++) {
				retulist.add(getfileinfo(allimglist.get(i)));
			}
			for (int i = 0; i < retulist.size(); i++) {
				set.add(retulist.get(i));
			}
			str= (String[]) set.toArray(new String[0]);
			for (int i = 0; i < str.length; i++) {
				filename=str[i];
				DireInfo ftl= new DireInfo();
				ftl.imageDire=filename;
				data.add(ftl);
			}
			
			for (int i = 0; i < data.size(); i++) {
				for (int j =  allimglist.size()-1; j >= 0; j--) {
					if (data.get(i).imageDire.equals(getfileinfo(allimglist.get(j)))) {
						data.get(i).photoContent.add(allimglist.get(j));
					}
				}
			}
		}
		return data;
	}

	public String getfileinfo(String data){
		String filename[]= data.split("/");
		if (filename!=null) {
			return filename[filename.length-2];
		}
		return null;
	}
	

}
