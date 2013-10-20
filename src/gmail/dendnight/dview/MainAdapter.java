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
import android.widget.TextView;

/**
 * 
 * <pre>
 * Description
 * Author:		dendnight
 * Version:		1.0  
 * Create at:	2013年10月20日 下午2:35:30  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
public class MainAdapter extends BaseAdapter {

	private List<Map<String, Object>> listItem;// 数据集

	private LayoutInflater mainContainer; // 视图容器

	// 自定义控件集合
	public final class ListItemView {
		public ImageView image;// 图片
		public TextView title;// 文件夹名
		public TextView count;// 图片数量
		public TextView path;// 图片父文件夹
		public TextView date;// 最后修改时间
	}

	public MainAdapter(Context context, List<Map<String, Object>> listItem) {
		this.mainContainer = LayoutInflater.from(context);
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
			convertView = mainContainer.inflate(R.layout.activity_main_item, null);

			// 创建自定义视图
			listItemView = new ListItemView();
			listItemView.image = (ImageView) convertView.findViewById(R.id.image);
			listItemView.title = (TextView) convertView.findViewById(R.id.title);

			listItemView.count = (TextView) convertView.findViewById(R.id.count);
			listItemView.path = (TextView) convertView.findViewById(R.id.path);
			listItemView.date = (TextView) convertView.findViewById(R.id.date);

			// 设置自定义视图至布局文件
			convertView.setTag(listItemView);

		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		// 设置值
		listItemView.image.setImageBitmap((Bitmap) listItem.get(position).get(DictParam.IMAGE));
		listItemView.title.setText((String) listItem.get(position).get(DictParam.TITLE));
		listItemView.path.setText((String) listItem.get(position).get(DictParam.PATH));

		listItemView.count.setText((String) listItem.get(position).get(DictParam.COUNT));
		listItemView.date.setText((String) listItem.get(position).get(DictParam.DATE));
		return convertView;
	}
}
