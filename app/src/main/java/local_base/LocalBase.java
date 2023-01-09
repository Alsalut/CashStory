package local_base;

// этот класс :
// получает данные из LocalBase
// делает резервную копию данных
// восстанавливает данные из резервной копии

import static com.alex.cashstory.IConstant.FILE_NAME;
import static com.alex.cashstory.IConstant.backupLocalBase;
import static com.alex.cashstory.IConstant.empty;
import static com.alex.cashstory.IConstant.historyLocalBase;
import static com.alex.cashstory.IConstant.myLocalBase;
import static com.alex.cashstory.IConstant.myLog;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class LocalBase extends AppCompatActivity
{
   private final SharedPreferences sharedPreferences;
   private final SharedPreferences.Editor editor;

   // конструктор
   public LocalBase( Context context )
   {
      sharedPreferences = context.getSharedPreferences( myLocalBase, MODE_PRIVATE );
      editor = sharedPreferences.edit();
   }

   // возвращаем строку истории из LocalBase
   public String getHistoryFromLocalBase()
   {
      return sharedPreferences.getString( historyLocalBase, empty );
   }

   // сохраняем строку истории в LocalBase
   // в переменную historyLocalBase
   public void putHistoryToLocalBase( String text )
   {
      editor.putString( historyLocalBase, text );
      editor.apply();
   }

   // сохраняем резервную копию в LocalBase
   // в переменную backupLocalBase
   public void backupHistory()
   {
      editor.putString( backupLocalBase, getHistoryFromLocalBase() );
      editor.apply();
   }

   // восстанавливаем строку истории в LocalBase из резервной копии
   // в переменную historyLocalBase
   public void restoreHistory()
   {
      editor.putString( historyLocalBase, sharedPreferences.getString( backupLocalBase, empty ) );
      editor.apply();
   }

   // сохраняем из LocalBase в файл
   public void saveFromLocalBaseToFile( Context context )
   {
      FileOutputStream fos = null;

      try
      {
         fos = context.openFileOutput( FILE_NAME, MODE_PRIVATE );
         fos.write( getHistoryFromLocalBase().getBytes() );

         Log.d( myLog, "Файл сохранён" );
      }
      catch( IOException ex )
      {
         Log.d( myLog, ex.getMessage() );
      }
      finally
      {
         try
         {
            if( fos != null ) fos.close();
         }
         catch( IOException ex )
         {
            Log.d( myLog, ex.getMessage() );
         }
      }
   }

   // сохраняем из файла в LocalBase
   public void saveFromFileToLocalBase( Context context )
   {
      FileInputStream fin = null;

      try
      {
         fin = context.openFileInput( FILE_NAME );
         byte[] bytes = new byte[fin.available()];
         fin.read( bytes );

         String text = new String( bytes );

         // сохраняем text в myLocalBase
         putHistoryToLocalBase( text );
         backupHistory();

         Log.d( myLog, "Строка = " + text );
      }
      catch( IOException ex )
      {
         Log.d( myLog, ex.getMessage() );
      }
      finally
      {
         try
         {
            if( fin != null ) fin.close();
         }
         catch( IOException ex )
         {
            Log.d( myLog, ex.getMessage() );
         }
      }
   }
}
