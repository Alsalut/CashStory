package modification;

// эта Activity отвечает за редактирование транзакций

import static com.alex.cashstory.MainActivity.arrayListHistory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.alex.cashstory.IConstant;
import com.alex.cashstory.MainActivity;
import com.alex.cashstory.R;

import java.util.ArrayList;

import local_base.LocalBase;

public class ModificationActivity extends AppCompatActivity implements View.OnClickListener, IConstant
{
   private Button buttonSaveChange, buttonDelTransaction, buttonDelHistory;
   private EditText editText;

   // индекс редактируемой транзакции
   private int position;

   public LocalBase objectLocalBase;

   @Override
   protected void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.activity_modification );

      initVariable(); // сопоставляем view переменным
   }

   @Override
   protected void onResume()
   {
      super.onResume();

      position = getPosition(); // получаем индекс редактируемой транзакции
      insertTextToEditText(); // вставляем редактируемый текст в поле editText
      setListener(); // назначаем слушателя кнопкам
   }

   // сопоставляем view переменным
   private void initVariable()
   {
      buttonSaveChange = findViewById( R.id.button_modification_save_change );
      buttonDelTransaction = findViewById( R.id.button_modification_del_transaction );
      buttonDelHistory = findViewById( R.id.button_modification_del_history );

      editText = findViewById( R.id.edit_text_modification );

      // создаём объект LocalBase
      objectLocalBase = new LocalBase( this );
   }

   // назначаем слушателя кнопкам
   private void setListener()
   {
      buttonSaveChange.setOnClickListener( this );
      buttonDelTransaction.setOnClickListener( this );
      buttonDelHistory.setOnClickListener( this );
   }

   // возвращаем индекс редактируемой транзакции
   private int getPosition()
   {
      return getIntent().getIntExtra( key, 0 );
   }

   // вставляем редактируемый текст в поле editText
   private void insertTextToEditText()
   {
      editText.setText( arrayListHistory.get( position ) );
   }

   // сохранение изменений в выбранной транзакции
   private void saveChangeTransaction()
   {
      // сохраняем изменения в arrayListHistory
      String text = editText.getText().toString();
      arrayListHistory.set( position, text );
   }

   // удаление выбранной транзакции
   private void removeTransaction()
   {
      // удаление выбранной транзакции
      arrayListHistory.remove( position );
   }

   // удаление всей истории
   private void removeHistory()
   {
      // удаление всей истории
      arrayListHistory.clear();
   }

   @Override
   public void onClick( View button )
   {
      // сохраняем резервную копию в LocalBase
      // в переменную backupLocalBase
      // чтобы откатиться назад,
      // если изменения сделаны ошибочно
      objectLocalBase.backupHistory();

      switch( button.getId() )
      {
         case R.id.button_modification_save_change:
            saveChangeTransaction(); // сохраняем изменения в arrayListHistory
            break;
         case R.id.button_modification_del_transaction:
            removeTransaction(); // удаление выбранной транзакции
            break;
         case R.id.button_modification_del_history:
            removeHistory(); // удаление всей истории
      }

      // сохраняем изменения в Firebase
      // а в myLocalBase история всегда будет сохраняться из Firebase ( в MainActivity )
      writeToFirebase( createStringNewHistory( arrayListHistory ) );

      // возвращаемся в MainActivity
      startActivity( new Intent( this, MainActivity.class ) );
   }

   // собираем строку обновлённой истории
   private String createStringNewHistory( ArrayList< String > arrayList )
   {
      // если история удалена вся
      // возвращаем пустую строку
      if( arrayListHistory.isEmpty() ) return null;

      // задаём первый элемент строки newHistory
      String newHistory = arrayListHistory.get( 0 );

      // собираем строку из элементов arrayListHistory
      // вставляя между ними разделитель splitHistory
      for( String element : arrayListHistory )
      {
         // первый элемент не обрабатываем
         if( !element.equals( arrayList.get( 0 ) ) )
            newHistory = newHistory + splitHistory + element;
      }

      return newHistory;
   }
}