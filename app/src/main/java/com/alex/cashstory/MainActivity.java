package com.alex.cashstory;

// что нужно ещё сделать ?
// после внесения изменений в ModificationActivity
// рассчитать изменения во всех транзакциях,
// зависящих от внесённых изменений
// и соответственно изменить эти транзакции

// этот класс :
// показывает информацию о финансах

// дополнительно :
// если закомментировать setListener(); , initFirebase(); и showCashInfo();
// то, раскомментировав ( в onResume() ) одну из других функций
// можно сохранить историю :
// из LocalBase в файл ( файл сохраняется в Смартфоне )
// из файла в LocalBase
// из LocalBase в Firebase

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

import change.ChangeActivity;
import history.HistoryActivity;
import local_base.LocalBase;
import minus.MinusActivity;
import plus.PlusActivity;

public class MainActivity extends AppCompatActivity implements IConstant, View.OnClickListener
{
   public TextView tvCard, tvCash, tvSafe, tvAll;
   public Button btnHistory, btnPlus, btnMinus, btnChange;

   // строка истории
   public static String stringHistory;

   // коллекция arrayListHistory
   public static ArrayList< String > arrayListHistory;
   public static ArrayList< String > arrayListHistoryShort;

   // arrayLastTransaction - это массив элементов последней транзакции
   public static String[] arrayLastTransaction;

   public LocalBase objectLocalBase;

   @Override
   protected void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.activity_main );

      initVariable(); // сопоставляем view переменным
      setListener(); // назначаем слушателя кнопкам
      initFirebase(); // подключаемся к Firebase
   }

   @Override
   protected void onResume()
   {
      super.onResume();

      showCashInfo(); // показываем информацию о финансах

      saveHistoryArrayToFirebase(); // записываем в Firebase ArrayList
      //objectLocalBase.saveFromLocalBaseToFile( this ); // сохраняем из LocalBase в файл
      //objectLocalBase.saveFromFileToLocalBase( this ); // сохраняем строку из файла в LocalBase
      //saveFromLocalBaseToFirebase(); // сохраняем из LocalBase в Firebase
      //replaceSplit(); // замена splitElement на newLine
   }

   private void replaceSplit()
   {
      String text = objectLocalBase.getHistoryFromLocalBase();

      objectLocalBase.putHistoryToLocalBase( text.replaceAll( splitElement, newLine ) );

      Log.d( myLog, "Строка = " + text );
   }

   // сопоставляем view переменным
   private void initVariable()
   {
      tvCard = findViewById( R.id.textViewCard );
      tvCash = findViewById( R.id.textViewCash );
      tvSafe = findViewById( R.id.textViewSafe );
      tvAll = findViewById( R.id.textViewAll );

      btnHistory = findViewById( R.id.buttonHistory );
      btnPlus = findViewById( R.id.buttonPlus );
      btnMinus = findViewById( R.id.buttonMinus );
      btnChange = findViewById( R.id.buttonChange );

      // создаём объект LocalBase
      objectLocalBase = new LocalBase( this );

      // создаём коллекцию arrayListHistory и arrayListHistoryShort
      arrayListHistory = new ArrayList<>();
      arrayListHistoryShort = new ArrayList<>();
   }

   // назначаем слушателя кнопкам
   private void setListener()
   {
      btnHistory.setOnClickListener( this );
      btnPlus.setOnClickListener( this );
      btnMinus.setOnClickListener( this );
      btnChange.setOnClickListener( this );
   }

   // подключаемся к Firebase
   private void initFirebase()
   {
      // слушатель базы данных
      refHistory.addValueEventListener( new ValueEventListener()
      {
         @Override
         public void onDataChange( @NonNull DataSnapshot dataSnapshot )
         {
            // получаем строку Истории из Firebase
            final String getHistoryFromFirebase = dataSnapshot.getValue( String.class );

            // сохраняем строку из Firebase в LocalBase
            objectLocalBase.putHistoryToLocalBase( getHistoryFromFirebase );
         }

         @Override
         public void onCancelled( @NonNull DatabaseError databaseError )
         {
         }
      } );
   }

   // показываем информацию о финансах
   private void showCashInfo()
   {
      // получаем строку истории из myLocalBase
      stringHistory = objectLocalBase.getHistoryFromLocalBase();

      // очищаем arrayListHistory
      arrayListHistory.clear();

      // разбиваем строку stringHistory ( по разделителю splitHistory )
      // и заполняем этими элементами коллекцию arrayListHistory
      arrayListHistory.addAll( Arrays.asList( stringHistory.split( splitHistory ) ) );

      createListHistoryShort(); // создаём список только с основной информацией

      // arrayLastTransaction - это массив элементов последней транзакции
      arrayLastTransaction = arrayListHistory.get( 0 ).split( splitElement );

      // создаём переменные о текущих финансах
      String infoCard, infoCash, infoSafe, infoAll;

      // заполняем данные о текущих финансах
      if( arrayLastTransaction.length == 1 )
      {
         // если истории ещё нет, заполняем массив строками из нулей
         arrayLastTransaction = new String[]{ zero, zero, zero, zero, zero, zero, zero };

         // если в памяти нет никакой информации, то выведутся на экран нули
         infoCard = zero;
         infoCash = zero;
         infoSafe = zero;
         infoAll = zero;
      }
      else
      {
         // иначе - выводим информацию о текущих финансах
         infoCard = arrayLastTransaction[1];
         infoCash = arrayLastTransaction[2];
         infoSafe = arrayLastTransaction[3];

         infoAll = roundTwoDigit.format( stringToDouble( infoCard ) + stringToDouble( infoCash ) + stringToDouble( infoSafe ) );
      }

      // выводим информацию о финансах в TextView
      tvCard.setText( infoCard );
      tvCash.setText( infoCash );
      tvSafe.setText( infoSafe );
      tvAll.setText( infoAll );
   }

   // создаём список только с основной информацией
   private void createListHistoryShort()
   {
      arrayListHistoryShort.clear();

      for( String element : arrayListHistory )
      {
         final String[] array = element.split( splitElement );

         final String string =   array[0] + splitElement +
                                 array[4] + splitElement +
                                 array[5] + splitElement +
                                 array[6];

         arrayListHistoryShort.add( string );
      }
   }

   // выполнится при нажатии на кнопку
   @Override
   public void onClick( View buttonClick )
   {
      switch( buttonClick.getId() )
      {
         case R.id.buttonHistory:
            startActivity( new Intent( this, HistoryActivity.class ) ); // переходим на Activity
            break;
         case R.id.buttonPlus:
            startActivity( new Intent( this, PlusActivity.class ) ); // переходим на Activity
            break;
         case R.id.buttonMinus:
            startActivity( new Intent( this, MinusActivity.class ) ); // переходим на Activity
            break;
         case R.id.buttonChange:
            startActivity( new Intent( this, ChangeActivity.class ) ); // переходим на Activity
      }
   }

   // сохраняем из LocalBase в Firebase
   private void saveFromLocalBaseToFirebase()
   {
      refHistory.setValue( objectLocalBase.getHistoryFromLocalBase() );
   }

   // сохраняем в Firebase как массив
   private void saveHistoryArrayToFirebase()
   {
      refHistoryArray.setValue( arrayListHistory );
   }

   // выполнится при нажатии кнопки "Назад"
   @Override
   public void onBackPressed()
   {
      super.onBackPressed();

      // полное закрытие приложения
      finishAndRemoveTask();
   }
}