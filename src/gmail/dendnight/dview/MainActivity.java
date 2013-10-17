package gmail.dendnight.dview;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {

	/** 图片 */
	private static final String IMAGE = "image";

	/** 文件夹路径 */
	private static final String PATH = "path";

	/** 图片名称 */
	private static final String TITLE = "title";

	/** 同一文件夹下图片数量 */
	private static final String COUNT = "count";

	/** 根目录*/
	private static final String ROOT = "/";
	
	/** 列表数据*/
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ListView listView = (ListView) findViewById(R.id.listView);
		
		SimpleAdapter simpleAdapter = new SimpleAdapter(
				this, imageData(ROOT),
				R.layout.activity_main_item,
				new String[] { IMAGE, PATH, TITLE, COUNT },
				new int[] { R.id.image, R.id.path, R.id.title,R.id.count }
		);

		listView.setAdapter(simpleAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * 获取图片数据<br>
	 * 判断是文件夹里是否有图片,如果有则将第一张图片生成位图显示在对应位置
	 * @return
	 */
	private List<Map<String, Object>> imageData(String url) {
		Map<String, Object> map = null;
		File[] files = new File(url).listFiles();

		// 遍历文件系统
		for (File file : files) {

			// 判断是否是文件夹
			if(file.isDirectory()){
				imageData(file.getAbsolutePath());
			}
			
			// 判断是否是图片文件
			if(isImage(file)){

				map = new HashMap<String, Object>();
				map.put(IMAGE, R.drawable.ic_launcher);
				map.put(PATH, file.getAbsolutePath());
				map.put(TITLE, file.getName());
				map.put(COUNT, file.length());

				list.add(map);
			}
		}
		return list;
	}
	
	/**
	 * 是图片文件,且后缀为正常图片后缀
	 * @param file
	 * @return
	 */
	private boolean isImage(File file){
        
		return true;
    }  
}
