package gmail.dendnight.dview;

import android.os.Bundle;
import android.provider.MediaStore;
import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.widget.ListView;

public class MainActivity extends FragmentActivity implements
		LoaderCallbacks<Cursor> {

	public static final String[] STORE_IMAGES = {
			MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
			MediaStore.Images.Media._ID };

	private ListView listView = null;
	private SimpleCursorAdapter simpleCursorAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = (ListView) findViewById(R.id.listView);

		simpleCursorAdapter = new SimpleCursorAdapter(this,
				R.layout.activity_main_item, null, STORE_IMAGES, new int[] {
						R.id.txtTitle, R.id.txtNum }, 0);
		listView.setAdapter(simpleCursorAdapter);
		getSupportLoaderManager().initLoader(0, null, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		CursorLoader cursorLoader = new CursorLoader(this,
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, STORE_IMAGES,
				null, null, null);
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		simpleCursorAdapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		simpleCursorAdapter.swapCursor(null);
	}

}
