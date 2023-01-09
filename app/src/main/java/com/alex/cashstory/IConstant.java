package com.alex.cashstory;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.ParseException;

public interface IConstant
{
   // Tag Log
   String myLog = "myLog";

   // формат показа даты
   String timePattern = "dd.MMM.yyyy HH:mm:ss";

   // имя локальной базы данных
   String myLocalBase = "myLocalBase", historyLocalBase = "historyLocalBase", backupLocalBase = "backupLocalBase";
   String FILE_NAME = "content.txt";

   // init constants
   String history = "history";
   String historyArray = "historyArray";
   String key = "key", newLine = "\n", zero = "0", empty = "";
   String all = "all", card = "card", cash = "cash", safe = "safe";
   String toCard = "На карту", toCash = "В наличные";
   String fromCard = "С карты", fromCash = "Из наличных";
   String fromCashToSafe = "Из наличных в Запас", fromSafeToCash = "Из запаса в наличные";
   String fromCardToCash = "С карты в наличные", fromCashToCard = "Из наличных на карту";
   String noResult = "Совпадений нет.";

   // split constants
   String splitHistory = "&splitHistory&";
   String splitElement = "\n"; // "&splitElement&";

   // init Firebase
   FirebaseDatabase baseOnline = FirebaseDatabase.getInstance();

   // init Firebase reference
   DatabaseReference refHistory = baseOnline.getReference( history );
   DatabaseReference refHistoryArray = baseOnline.getReference( historyArray );

   // округление double до 2-х знаков после запятой
   DecimalFormat roundTwoDigit = new DecimalFormat( "0.00" );

   // преобразование String в double
   default double stringToDouble( String text )
   {
      double num = 0;

      try
      {
         num = roundTwoDigit.parse( text ).doubleValue();
      }
      catch( ParseException e )
      {
         Log.d( myLog, e.getMessage() );
      }

      return num;
   }

   // сохраняем изменения в Firebase
   default void writeToFirebase( String stringNewHistory )
   {
      refHistory.setValue( stringNewHistory );
   }
}
