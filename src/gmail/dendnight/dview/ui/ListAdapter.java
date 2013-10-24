package gmail.dendnight.dview.ui;

import gmail.dendnight.dview.R;
import gmail.dendnight.dview.dict.DictParam;

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
 * 自定义列表
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
public class ListAdapter extends BaseAdapter {

	private List<Map<String, Object>> listItem;// 数据集

	private LayoutInflater mainContainer; // 视图容器

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
		/** 文件夹名 */
		public TextView folder;
		/** 图片数量 */
		public TextView count;
		/** 图片父文件夹 */
		public TextView path;
		/** 最后修改时间 */
		public TextView date;
		/** 文件夹编号 */
		private String folderId;

		public String getFolderId() {
			return folderId;
		}

		public void setFolderId(String folderId) {
			this.folderId = folderId;
		}

	}

	public ListAdapter(Context context, List<Map<String, Object>> listItem) {
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
			convertView = mainContainer.inflate(R.layout.activity_list_item, null);

			// 创建自定义视图
			listItemView = new ListItemView();
			listItemView.image = (ImageView) convertView.findViewById(R.id.list_image);
			listItemView.folder = (TextView) convertView.findViewById(R.id.list_folder);

			listItemView.count = (TextView) convertView.findViewById(R.id.list_count);
			listItemView.path = (TextView) convertView.findViewById(R.id.list_path);
			listItemView.date = (TextView) convertView.findViewById(R.id.list_date);

			// 设置自定义视图至布局文件
			convertView.setTag(listItemView);

		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		// 设置值
		listItemView.image.setImageBitmap((Bitmap) listItem.get(position).get(DictParam.IMAGE));
		listItemView.folder.setText((String) listItem.get(position).get(DictParam.FOLDER));
		listItemView.path.setText((String) listItem.get(position).get(DictParam.PATH));

		listItemView.count.setText((String) listItem.get(position).get(DictParam.COUNT));
		listItemView.date.setText((String) listItem.get(position).get(DictParam.DATE));
		listItemView.setFolderId((String) listItem.get(position).get(DictParam.FOLDER_ID));
		return convertView;
	}
}
