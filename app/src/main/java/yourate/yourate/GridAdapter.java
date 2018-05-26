package yourate.yourate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> allText;
    Integer [] allImages;


    public GridAdapter(Context c) {
        mContext = c;
    }

    public GridAdapter(Context c, ArrayList<String> allText, Integer []allImages  ) {

        this.allImages=allImages;
        this.allText=allText;
        mContext = c;
    }

    public int getCount() {
        return allImages.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView==null)
        {


            view = new View(mContext);
            view = inflater.inflate(R.layout.grid_view, null);
            TextView textViewAndroid = (TextView) view.findViewById(R.id.android_gridview_text);
            ImageView imageViewAndroid = (ImageView) view.findViewById(R.id.android_gridview_image);
            textViewAndroid.setText(allText.get(position));
            imageViewAndroid.setImageResource(allImages[position]);
        //    imageViewAndroid.setImageResource(mThumbIds[position]);
        } else {
            view = (View) convertView;
        }

        return view;




//        ImageView imageView;
//        if (convertView == null) {
//            // if it's not recycled, initialize some attributes
//            imageView = new ImageView(mContext);
//            imageView.setLayoutParams(new ViewGroup.LayoutParams(85, 85));
//
//          //  imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//          //  imageView.setPadding(8, 8, 8, 8);
//        } else {
//            imageView = (ImageView) convertView;
//        }
//
//        imageView.setImageResource(mThumbIds[position]);
//        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.car, R.drawable.books,
            R.drawable.baby, R.drawable.carrepair,


    };
}
