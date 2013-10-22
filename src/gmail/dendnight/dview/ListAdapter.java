package gmail.dendnight.dview;

import gmail.dendnight.utils.DictParam;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * 
 * <pre>
 * Description
 * Author:		dendnight
 * Version:		1.0  
 * Create at:	2013年10月21日 下午3:26:33  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
public class ListAdapter extends BaseAdapter {

	private List<Map<String, Object>> listItem;// 数据集

	private LayoutInflater listContainer; // 视图容器

	/**
	 * 自定义控件集合
	 * 
	 * <pre>
	 * Description
	 * Author:		dendnight
	 * Version:		1.0  
	 * Create at:	2013年10月21日 下午2:25:48  
	 *  
	 * 修改历史:
	 * 日期    作者    版本  修改描述
	 * ------------------------------------------------------------------
	 * 
	 * </pre>
	 */
	public final class ListItemView {
		/** 图片 */
		public ImageView image;
		/** 图片路径 */
		private String path;

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

	}

	public ListAdapter(Context context, List<Map<String, Object>> listItem) {
		this.listContainer = LayoutInflater.from(context);
		this.listItem = listItem;
	}

	@Override
	public int getCount() {
		return this.listItem != null ? this.listItem.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return this.listItem.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ListItemView listItemView = null;

		if (null == convertView) {
			// 获取布局文件视图
			convertView = listContainer.inflate(R.layout.activity_list_item, null);

			// 创建自定义视图
			listItemView = new ListItemView();
			listItemView.image = (ImageView) convertView.findViewById(R.id.list_image);

			// 设置自定义视图至布局文件
			convertView.setTag(listItemView);

		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		// 设置值
		listItemView.image.setImageBitmap((Bitmap) listItem.get(position).get(DictParam.IMAGE));
		listItemView.setPath((String) listItem.get(position).get(DictParam.PATH));
		return convertView;
	}
}
