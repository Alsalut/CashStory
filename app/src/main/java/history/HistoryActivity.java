package history;

// Так выглядит список транзакций :
//
// 08.нояб.2021 16:08:00
// 491.86
// 8000.00
// 0.00
// Пятёрочка
// С карты
// 46.18руб
//
// 08.нояб.2021 10:01:26
// 538.04
// 8000.00
// 0.00
// Ан за Теле2 на карту
// С карты
// 510руб

// Так выглядит строка с историей транзакций в памяти :
// 02.дек.2021 13:55:02&splitElement&6559.96&splitElement&35150.00&splitElement&0.00&splitElement&Перевёл О за декабрь&splitElement&С Карты&splitElement&5000.00&splitHistory&03.дек.2021 13:53:55&splitElement&11559.96&splitElement&35150.00&splitElement&0.00&splitElement&&splitElement&С Карты в Наличные&splitElement&25000.00&splitHistory&

// &splitHistory& - разделитель транзакций
// &splitElement& - разделитель элементов одной транзакции

// показывается только основная информация по транзакции
// скрывается инфо о сумме на карте, в наличных, в запасе
// при кратком нажатии на транзакцию
// отображается и эта информация

// При длительном нажатии на любом элементе списка
// переходим на ModificationActivity по редактированию истории транзакций

import static com.alex.cashstory.IConstant.key;
import static com.alex.cashstory.MainActivity.arrayListHistoryShort;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

import com.alex.cashstory.R;

import modification.ModificationActivity;
import search.SearchActivity;

public class HistoryActivity extends ListActivity implements View.OnClickListener, AdapterView.OnItemClickListener
{
   // кнопка поиска ( Лупа )
   ImageButton imageButtonFind;

   @Override
   protected void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.list_view_button );

      initVariable(); // сопоставляем view переменным
      setListener(); // назначаем слушателя кнопкам
   }

   @Override
   protected void onResume()
   {
      super.onResume();

      showHistory(); // выводим на экран историю транзакций
   }

   // сопоставляем view переменным
   private void initVariable()
   {
      imageButtonFind = findViewById( R.id.image_button_find );
   }

   // назначаем слушателя кнопкам
   private void setListener()
   {
      imageButtonFind.setOnClickListener( this );
   }

   // выводим на экран историю транзакций
   private void showHistory()
   {
      // создаём адаптор коллекции arrayListHistory
      ArrayAdapter< String > adapterArrayListHistory = new ArrayAdapter<>( this, R.layout.list_view, arrayListHistoryShort );

      // и подключаем его
      setListAdapter( adapterArrayListHistory );

      // задаём слушателя краткого нажатия ( OnItemClickListener )
      getListView().setOnItemClickListener( this );
   }

   @Override
   public void onClick( View button )
   {
      if( button.getId() == R.id.image_button_find )
         startActivity( new Intent( this, SearchActivity.class ) );
   }

   // При кратком нажатии на элементе списка
   // переходим на ModificationActivity
   // для редактирования истории транзакций
   @Override
   public void onItemClick( AdapterView< ? > adapterView, View element, int position, long id )
   {
      // создаём Intent
      final Intent intent = new Intent( this, ModificationActivity.class );
      // чтобы передать позицию нажатого элемента
      intent.putExtra( key, position );

      // переходим на ModificationActivity
      startActivity( intent );
   }
}