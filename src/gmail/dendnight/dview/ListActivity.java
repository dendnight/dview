package gmail.dendnight.dview;

import gmail.dendnight.utils.DictParam;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

/**
 * 图库列表
 * 
 * <pre>
 * Description
 * Author:		dendnight
 * Version:		1.0  
 * Create at:	2013年10月21日 下午3:00:15  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
public class ListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏状态栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_list);
		Intent intent = getIntent();
		String text = intent.getStringExtra(DictParam.TITLE);
		Toast.makeText(getApplicationContext(), "选中:" + text, Toast.LENGTH_SHORT).show();

	}
}
