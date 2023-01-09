package search;

// эта Activity предназначена
// для ввода текста для поиска,
// и передачи этого текста
// в SearchResultActivity
// для поиска
// во всей истории

import static com.alex.cashstory.IConstant.key;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.alex.cashstory.R;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener
{
   private Button buttonSearch;
   private EditText editTextSearch;

   @Override
   protected void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.activity_search );

      initVariable(); // сопоставляем view переменным
      setListener(); // назначаем слушателя кнопкам
   }

   // сопоставляем view переменным
   private void initVariable()
   {
      buttonSearch = findViewById( R.id.button_search );
      editTextSearch = findViewById( R.id.edit_text_search );
   }

   // назначаем слушателя кнопкам
   private void setListener()
   {
      buttonSearch.setOnClickListener( this );
   }

   // обрабатываем нажатия кнопок
   @Override
   public void onClick( View view )
   {
      // получаем данные из полей ввода
      final String textForSearch = editTextSearch.getText().toString();

      // передаём текст в SearchResultActivity
      final Intent intent = new Intent( this, SearchResultActivity.class );
      intent.putExtra( key, textForSearch );
      startActivity( intent );
   }
}