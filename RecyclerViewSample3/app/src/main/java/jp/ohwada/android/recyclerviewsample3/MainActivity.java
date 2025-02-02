/**
 * RecyclerView sample
 * 2018-03-01 K.OHWADA 
 */

package jp.ohwada.android.recyclerviewsample3;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


    /*
     * class MainActivity
     * with SortedList
     */ 
public class MainActivity extends Activity {

   	// debug
    	private final static String TAG_SUB = "MainActivity";

   private  static final int LIST_ITEM_NUM = 4;

    private SampleAdapter mAdapter;

private RecyclerView mRecyclerView;

    /*
     * == onCreate ==
     */ 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
log_d( "onCreate");
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView_1);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        Button btnAdd = (Button) findViewById(R.id.Button_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                procAdd();
            }
        }); // btnAdd


        Button btnRemove = (Button) findViewById(R.id.Button_remove);
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                procRemove();
            }
        }); // btnRemove

        mAdapter = initAdapter();
        mRecyclerView.setAdapter( mAdapter);

        // mRecyclerView.addItemDecoration(new SampleDecorator());
        // mRecyclerView.setItemAnimator( new SampleAnimator() );

    } //onCreate


private SampleAdapter initAdapter() {
        SampleAdapter adapter = new SampleAdapter();
        adapter.setOnItemClickListener( new SampleAdapter.OnItemClickListener() {
        @Override
        public void onItemClick( int position, String codename ){
log_d( "onItemClick");
toast_short( codename );
}
}); // setOnItemClickListener

        // set init data
        for (int i=0; i<LIST_ITEM_NUM; i++ ) {
            SampleData data = AndroidVersion.getRandomSampleData();
            adapter.add(data);
        }

    return adapter;
} // initAdapter


/**
	 * procAdd
	 * add random value item  into RecyclerView
	 */
	private void procAdd() {
                SampleData data = AndroidVersion.getRandomSampleData();
    String msg =  "add : " + data.getText();
        log_d(  msg );
        toast_short( msg );
         mAdapter.add(data);
    } // procAdd


/**
	 * procRemove
	 * remove item at random in RecyclerView
	 */
	private void procRemove() {
        int count = mAdapter.getItemCount();
        int posision = (int)(Math.random()*(count - 1));
        String msg =  "remove : " + posision;
        log_d(  msg );
        toast_short( msg );
        mAdapter.remove(posision);
    } // procRemove



/**
	 * toast_short
	 */
	private void toast_short( String msg ) {
		ToastMaster.makeText( this, msg, Toast.LENGTH_SHORT ).show();
	} // toast_shor


 	/**
	 * write into logcat
	 */ 
	private void log_d( String msg ) {
	    if (Constant.DEBUG) Log.d( Constant.TAG, TAG_SUB + " " + msg );
	} // log_d

} // class MainActivity
