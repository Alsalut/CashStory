package local_base;

// этот класс получает данные о приходе, расходе или перемещении
// и формирует строку текущей транзакции

import static com.alex.cashstory.MainActivity.arrayLastTransaction;
import static com.alex.cashstory.MainActivity.stringHistory;

import com.alex.cashstory.IConstant;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateTransaction implements IConstant
{
   // формируем массив последней транзакции
   public String toCard( String sum, String comment )
   {
      // меняем значения нужных ячеек массива
      arrayLastTransaction[1] = roundTwoDigit.format( stringToDouble( arrayLastTransaction[1] ) + stringToDouble( sum ) );
      arrayLastTransaction[4] = comment;
      arrayLastTransaction[5] = toCard;
      arrayLastTransaction[6] = sum;

      // возвращаем строку обновлённой истории
      return createNewHistory();
   }

   // формируем массив последней транзакции
   public String toCash( String sum, String comment )
   {
      // меняем значения нужных ячеек массива
      arrayLastTransaction[2] = roundTwoDigit.format( stringToDouble( arrayLastTransaction[2] ) + stringToDouble( sum ) );
      arrayLastTransaction[4] = comment;
      arrayLastTransaction[5] = toCash;
      arrayLastTransaction[6] = sum;

      // возвращаем строку обновлённой истории
      return createNewHistory();
   }

   // формируем массив последней транзакции
   public String fromCard( String sum, String comment )
   {
      // меняем значения нужных ячеек массива
      arrayLastTransaction[1] = roundTwoDigit.format( stringToDouble( arrayLastTransaction[1] ) - stringToDouble( sum ) );
      arrayLastTransaction[4] = comment;
      arrayLastTransaction[5] = fromCard;
      arrayLastTransaction[6] = sum;

      // возвращаем строку обновлённой истории
      return createNewHistory();
   }

   // формируем массив последней транзакции
   public String fromCash( String sum, String comment )
   {
      // меняем значения нужных ячеек массива
      arrayLastTransaction[2] = roundTwoDigit.format( stringToDouble( arrayLastTransaction[2] ) - stringToDouble( sum ) );
      arrayLastTransaction[4] = comment;
      arrayLastTransaction[5] = fromCash;
      arrayLastTransaction[6] = sum;

      // возвращаем строку обновлённой истории
      return createNewHistory();
   }

   // формируем массив последней транзакции
   public String changeSafeCash( String sum, String comment )
   {
      // меняем значения нужных ячеек массива
      arrayLastTransaction[3] = roundTwoDigit.format( stringToDouble( arrayLastTransaction[3] ) - stringToDouble( sum ) );
      arrayLastTransaction[2] = roundTwoDigit.format( stringToDouble( arrayLastTransaction[2] ) + stringToDouble( sum ) );
      arrayLastTransaction[4] = comment;
      arrayLastTransaction[5] = fromSafeToCash;
      arrayLastTransaction[6] = sum;

      // возвращаем строку обновлённой истории
      return createNewHistory();
   }

   // формируем массив последней транзакции
   public String changeCashSafe( String sum, String comment )
   {
      // меняем значения нужных ячеек массива
      arrayLastTransaction[2] = roundTwoDigit.format( stringToDouble( arrayLastTransaction[2] ) - stringToDouble( sum ) );
      arrayLastTransaction[3] = roundTwoDigit.format( stringToDouble( arrayLastTransaction[3] ) + stringToDouble( sum ) );
      arrayLastTransaction[4] = comment;
      arrayLastTransaction[5] = fromCashToSafe;
      arrayLastTransaction[6] = sum;

      // возвращаем строку обновлённой истории
      return createNewHistory();
   }

   // формируем массив последней транзакции
   public String changeCashCard( String sum, String comment )
   {
      // меняем значения нужных ячеек массива
      arrayLastTransaction[2] = roundTwoDigit.format( stringToDouble( arrayLastTransaction[2] ) - stringToDouble( sum ) );
      arrayLastTransaction[1] = roundTwoDigit.format( stringToDouble( arrayLastTransaction[1] ) + stringToDouble( sum ) );
      arrayLastTransaction[4] = comment;
      arrayLastTransaction[5] = fromCashToCard;
      arrayLastTransaction[6] = sum;

      // возвращаем строку обновлённой истории
      return createNewHistory();
   }

   // формируем массив последней транзакции
   public String changeCardCash( String sum, String comment )
   {
      // меняем значения нужных ячеек массива
      arrayLastTransaction[1] = roundTwoDigit.format( stringToDouble( arrayLastTransaction[1] ) - stringToDouble( sum ) );
      arrayLastTransaction[2] = roundTwoDigit.format( stringToDouble( arrayLastTransaction[2] ) + stringToDouble( sum ) );
      arrayLastTransaction[4] = comment;
      arrayLastTransaction[5] = fromCardToCash;
      arrayLastTransaction[6] = sum;

      // возвращаем строку обновлённой истории
      return createNewHistory();
   }

   // формируем и возвращаем строку обновлённой истории
   private String createNewHistory()
   {
      // получаем текущее время и форматируем его к красивому виду
      // формируем строку новой транзакции - строка вида :
      // 03.дек.2021 13:55:02&splitElement&6559.96&splitElement&35150.00&splitElement&0.00&splitElement&Перевёл О за декабрь&splitElement&С Карты&splitElement&5000.00
      // добавляем остальную историю
      // формируем и возвращаем строку новой истории транзакций
      return   new SimpleDateFormat( timePattern ).format( new Date( System.currentTimeMillis() ) )
               + splitElement + arrayLastTransaction[1]
               + splitElement + arrayLastTransaction[2]
               + splitElement + arrayLastTransaction[3]
               + splitElement + arrayLastTransaction[4]
               + splitElement + arrayLastTransaction[5]
               + splitElement + arrayLastTransaction[6]
               + splitHistory + stringHistory;
   }
}
