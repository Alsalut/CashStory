package search;

// эта Activity предназначена
// для поиска текста,
// введённого в SearchActivity,
// во всей истории
// и вывода транзакций с найденным текстом
// в виде списка

import static com.alex.cashstory.IConstant.key;
import static com.alex.cashstory.IConstant.noResult;
import static com.alex.cashstory.MainActivity.arrayListHistoryShort;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.alex.cashstory.R;

import java.util.ArrayList;

import modification.ModificationActivity;

public class SearchResultActivity extends ListActivity implements AdapterView.OnItemClickListener
{
   private String text; // для текста для поиска

   private ArrayList< String > arrayListSearchResult; // для результата поиска
   private ArrayList< Integer > arrayListHistoryIndex; // для индексов найденных транзакций

   @Override
   protected void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );

      getTextForSearch(); // получаем текст для поиска
   }

   @Override
   protected void onResume()
   {
      super.onResume();

      createSearchList(); // создаём список транзакций с найденным текстом
      showSearchResult(); // выводим на экран список транзакций с найденным текстом
   }

   // получаем текст для поиска
   private void getTextForSearch()
   {
      text = getIntent().getStringExtra( key );
   }

   // создаём список транзакций с найденным текстом
   private void createSearchList()
   {
      // выделяем память для arrayListSearchResult
      arrayListSearchResult = new ArrayList<>();

      // выделяем память для arrayListHistoryIndex
      arrayListHistoryIndex = new ArrayList<>();

      // очищаем arrayListSearchResult и arrayListHistoryIndex
      arrayListSearchResult.clear();
      arrayListHistoryIndex.clear();

      // в цикле проходим по всем транзакциям
      // и находим транзакции с искомым текстом
      // заполняем этими транзакциями список arrayListSearchResult
      // и заполняем индексами этих транзакций в общем списке arrayListHistory список arrayListHistoryIndex
      for( int index = 0; index < arrayListHistoryShort.size(); index++ )
      {
         // проверяем, содержит ли транзакция поисковый текст :
         // и строку и поисковый текст
         // переводим в нижний регистр,
         // чтобы регистр символов
         // не влиял на поиск
         if( ( ( arrayListHistoryShort.get( index ) ).toLowerCase() ).contains( text.toLowerCase() ) )
         {
            // добавляем эту транзакцию в arrayListSearchResult
            arrayListSearchResult.add( arrayListHistoryShort.get( index ) );

            // добавляем индекс этой транзакции в arrayListHistoryIndex
            arrayListHistoryIndex.add( index );
         }
      }

      // если поиск не дал результатов
      // т.е. список arrayListSearchResult пуст
      if( arrayListSearchResult.size() == 0 )
      {
         // вывести сообщение,
         // что ( "Совпадений нет" )
         arrayListSearchResult.add( noResult );
      }
   }

   // выводим на экран список транзакций с найденным текстом
   private void showSearchResult()
   {
      // создаём адаптор коллекции arrayListHistory
      final ArrayAdapter< String > adapterArrayListSearchResult = new ArrayAdapter<>( this, R.layout.list_view, arrayListSearchResult );

      // и подключаем его
      setListAdapter( adapterArrayListSearchResult );

      // задаём слушателя длительного нажатия ( OnItemClickListener )
      getListView().setOnItemClickListener( this );
   }

   // При кратком нажатии на элементе списка
   // переходим на ModificationActivity
   // для редактирования транзакций из поиска
   @Override
   public void onItemClick( AdapterView< ? > adapterView, View element, int position, long id )
   {
      // создаём Intent
      final Intent intent = new Intent( this, ModificationActivity.class );
      // передаём индекс нажатой транзакции
      intent.putExtra( key, arrayListHistoryIndex.get( position ) );

      // переходим на ModificationActivity
      startActivity( intent );
   }
}