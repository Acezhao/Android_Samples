/**
 * CSV Sample
 * Opencsv
 * 2019-02-01 K.OHWADA 
 */

package jp.ohwada.android.csv1;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import android.content.res.AssetManager;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * class CsvUtil
 * reference : http://opencsv.sourceforge.net/
 */
public class CsvUtil  {

        // debug
	private final static boolean D = true;
    	private final static String TAG = "CSV";
    	private final static String TAG_SUB = "CsvUtil";

        private final static String[] CSV_HEADERS = new String[] { "Name","Price","Quantity" };

        private final static String COMMA = ",";
        private final static String SPACE = " ";

        private static final String DIR_NAME = "csv";

    private  Context mContext;
	 private AssetManager mAssetManager;

	 private List<Shopping> mCache;

/**
 * constractor
 */
public CsvUtil(Context context) {
    mContext = context;
	mAssetManager = context.getAssets();
} // CsvUtil

/**
 *  readSample1
 */
public List<Shopping> readSample1() {

    InputStreamReader reader = getAssetInputStreamReader( "sample1.csv" ) ;
     mCache =  readCsv( reader, false );
    return mCache;

} // readSample1

/**
 *  readSample2
 */
public List<Shopping> readSample2() {

    InputStreamReader reader = getAssetInputStreamReader( "sample2.csv" ) ;
     mCache =  readCsv( reader,true );
    return mCache;

} // readSample2

/**
 *  writeTest
 */
public void writeTest() {

    if ( mCache == null ) {
            toast_long("no Cache");
            return;
    }

    File file = getStorageFile( "test.csv" );
    boolean ret = writeCsv(file, mCache );
    if (ret) {
        toast_long("write Successful");
    } else {
        toast_long("write faild");
    }

} // writeTest


    /**
     * readCsv
     */
    private List<Shopping> readCsv( Reader inputReader, boolean is_header ){

    if( inputReader == null ) return null;

    List<Shopping> list = new ArrayList<Shopping>();

     String[] nextLine;
    CSVReader reader = null;
    try {
        reader = new CSVReader( inputReader );
        if (is_header ) {
            // first line is header
            nextLine = reader.readNext();
        }
            while ((nextLine = reader.readNext()) != null) {
                    // name, price, quantity
                    String name =  nextLine[0] ;
                    double price =  parseDouble( nextLine[1] );
                    int quantity =  parseInt( nextLine[2] );
                Shopping s =  new Shopping(  name, price, quantity );
                log_d( s.toString() );
                list.add(s);
        } // while

        }catch (Exception ex) {
            if(D) ex.printStackTrace();
        } finally {
                try {
                     if( reader != null ) reader.close();
                      if( inputReader != null ) inputReader.close();
                }catch (Exception ex) {
                    if(D) ex.printStackTrace();
                }
        }

        try {
             if( inputReader != null ) inputReader.close();
        }catch (Exception ex) {
                    if(D) ex.printStackTrace();
        }

    return list;

    } // readCsv


    /**
     * writeCsv
     */
    private boolean writeCsv(File file, List<Shopping> list ) {

    if(file == null) return false;

    boolean is_error = false;
    FileWriter fileWriter = null;
            CSVWriter writer = null;
        try {
            fileWriter = new FileWriter(file);
            writer = new CSVWriter(fileWriter);
            writer.writeNext( CSV_HEADERS );

    for(Shopping s: list) {
                writer.writeNext( s.getWriteRow() );
            } // for

        }catch (Exception ex) {
            is_error = true;
            if (D )ex.printStackTrace();
        } finally {
            try {
                if( fileWriter != null ) fileWriter.close();
                if( writer != null )                 writer.close();
            }catch (Exception ex) {
                    if(D) ex.printStackTrace();
            }
        }

    return !is_error;

    } // writeCsv


 	/**
	 * getAssetInputStreamReader
	 */ 
private InputStreamReader getAssetInputStreamReader( String fileName ) {

        InputStreamReader reader = null;
    try {
        InputStream is = mAssetManager.open(fileName);
        reader = new InputStreamReader( is );
    } catch (Exception e){
			if (D) e.printStackTrace();
    }

    return reader;
} // getAssetInputStreamReader


 	/**
	 * getStorageFile
	 */ 
private File getStorageFile( String fileName ) {
        File root_dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() );
    File sub_dir = new File( root_dir,  DIR_NAME );
        if ( !sub_dir.exists() ) {
            sub_dir.mkdir();
        }
    File file = new File( sub_dir,  fileName );
    return file;
} // getStorageFile


/**
 * parseInt
 */
private int parseInt( String str ) {

            int num = 0;
                try {
                   num = Integer.parseInt( str.trim() );
                } catch (Exception ex) {
			        if (D) ex.printStackTrace();
                }

    return num;
} // parseInt


/**
 * parseDouble
 */
private double parseDouble( String str ) {

            double d = 0;
                try {
                   d = Double.parseDouble( str.trim() );
                } catch (Exception ex) {
			        // if (D) ex.printStackTrace();
                }

    return d;
} // parseDouble


/**
 * toast_long
 */
	private void toast_long( String msg ) {
		ToastMaster.makeText( mContext, msg, Toast.LENGTH_LONG ).show();
	} // toast_long

 	/**
	 * write into logcat
	 */ 
	private void log_d( String msg ) {
	    if (D) Log.d( TAG, TAG_SUB + " " + msg );
	} // log_d

} // class CsvUtil
