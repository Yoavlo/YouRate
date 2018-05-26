package yourate.yourate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    DatabaseReference myRef;
    Integer[]  allImagesMainTopic;
    ArrayList<String> allTextMainTopic;
    FirebaseDatabase database;
    DataSnapshot dataSnapshot;
    TextView textViewMainTopicTitle;
    GridAdapter mainTopicAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewMainTopicTitle=(TextView)findViewById(R.id.main_activity_mainTopicTitle);
        allTextMainTopic= new ArrayList<>();
        allImagesMainTopic = new Integer[]{
                R.drawable.car, R.drawable.books,
                R.drawable.baby, R.drawable.carrepair
        };


         allTextFromDB();

    }



    private void  allTextFromDB() {

       final ArrayList<String> allText= new ArrayList<>();
        database = FirebaseDatabase.getInstance();

        myRef = database.getReference("mainTopics");

        myRef.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot data) {
                dataSnapshot=data;


                for (DataSnapshot value: dataSnapshot.getChildren() )
                {
                 //     Log.d("firebase111", "Value is: " +   value.getValue().toString());
                    for(DataSnapshot value2: value.getChildren()) {
                        allText.add(value2.getKey());
                    }

                  //  allTextMainTopic.add(value.getValue().toString());

                }
                allTextMainTopic=allText;
                handleGridView();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("firebase111", "Failed to read value.", error.toException());
            }
        });
        Log.d("firebase111", "allText size before Return " +   allText.size());
    }

    private void handleGridView() {

         mainTopicAdapter = new GridAdapter(this, allTextMainTopic, allImagesMainTopic);
        gridView=(GridView)findViewById(R.id.activity_main_gridview);
        gridView.setAdapter(mainTopicAdapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ArrayList<String> subTopic= new ArrayList<>();

                for(DataSnapshot value:dataSnapshot.getChildren())
                {
                    if(value.getKey().equals(String.valueOf(position)) && gridView.getAdapter()==mainTopicAdapter)
                    {
//                        Iterable<DataSnapshot> vauleChild=  value.getChildren();
//                        Iterable<DataSnapshot> vauleGrandSon= vauleChild

                 //       Toast.makeText(MainActivity.this, "position: "+position + " value.getkey(): "+ value.getKey()+ "vaule: "+value.getValue(), Toast.LENGTH_SHORT).show();
                        for(DataSnapshot valueGranson: value.getChildren())
                        {
                            textViewMainTopicTitle.setText(valueGranson.getKey());
                            for(DataSnapshot valueGranGranSon: valueGranson.getChildren() ) {
                                subTopic.add(valueGranGranSon.getValue().toString());
                            }
                        }
                        GridAdapter subTopicGridAdapter = new GridAdapter(getApplication(), subTopic, getSubTopicImages(value.getKey()));
                        gridView.setAdapter(subTopicGridAdapter);
                    }
                    else if(gridView.getAdapter()!=mainTopicAdapter) //means the current grid is the subtopics
                    {
                       // Intent i= new Intent();
                 //       i.start();
                    }
                }




            }
        });


//        gridView=(GridView) findViewById(R.id.activity_main_gridview);
//        gridView.setAdapter(new GridAdapter(this));
//
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//
//                Toast.makeText(MainActivity.this, "position"+position, Toast.LENGTH_SHORT).show();
//
//
//            }
//        });
    }

    private Integer[] getSubTopicImages(String vaule) {
       // Integer [] images;
        if(vaule.equals("0"))
        {
             return new Integer[]{R.drawable.car,R.drawable.car};
        }
        else if( vaule.equals("1"))
        {
            return new Integer[]{R.drawable.books,R.drawable.books};
        }
        else if(vaule.equals("2"))
        {
            return new Integer[]{R.drawable.baby,R.drawable.baby};
        }
        else if(vaule.equals("3"))
        {
            return new Integer[]{R.drawable.carrepair};
        }
        return null;
    }
}
