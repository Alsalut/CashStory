package change;

// эта Activity отвечает за транзакции перемещения

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.alex.cashstory.IConstant;
import com.alex.cashstory.MainActivity;
import com.alex.cashstory.R;

import local_base.CreateTransaction;

public class ChangeActivity extends AppCompatActivity implements View.OnClickListener, IConstant
{
   private ImageButton imageButtonSafeCash, imageButtonCashSafe, imageButtonCashCard, imageButtonCardCash;
   private EditText editTextSum;

   private String newHistory;


   @Override
   protected void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.activity_change );

      initVariable(); // сопоставляем view переменным
      setListener(); // назначаем слушателя кнопкам
   }

   // сопоставляем view переменным
   private void initVariable()
   {
      imageButtonSafeCash = findViewById( R.id.image_button_change_safe_cash );
      imageButtonCashSafe = findViewById( R.id.image_button_change_cash_safe );
      imageButtonCashCard = findViewById( R.id.image_button_change_cash_card );
      imageButtonCardCash = findViewById( R.id.image_button_change_card_cash );

      editTextSum = findViewById( R.id.edit_text_change_sum );
   }

   // назначаем слушателя кнопкам
   private void setListener()
   {
      imageButtonSafeCash.setOnClickListener( this );
      imageButtonCashSafe.setOnClickListener( this );
      imageButtonCashCard.setOnClickListener( this );
      imageButtonCardCash.setOnClickListener( this );
   }

   // обрабатываем нажатия кнопок
   @Override
   public void onClick( View btn )
   {
      // получаем данные из полей ввода
      final String sum = editTextSum.getText().toString();

      // если поля ввода пустые, то нажатие обрабатывать не будем
      if( sum.equals( empty ) ) return;

      // создаём объект CreateTransaction
      CreateTransaction createTransaction = new CreateTransaction();

      // сохраняем изменения в Firebase
      // а в myLocalBase история всегда будет сохраняться из Firebase ( в MainActivity )
      String comment = "Перемещение";
      switch( btn.getId() )
      {
         case R.id.image_button_change_safe_cash:
            newHistory = createTransaction.changeSafeCash( sum, comment );
            break;

         case R.id.image_button_change_cash_safe:
            newHistory = createTransaction.changeCashSafe( sum, comment );
            break;

         case R.id.image_button_change_cash_card:
            newHistory = createTransaction.changeCashCard( sum, comment );
            break;

         case R.id.image_button_change_card_cash:
            newHistory = createTransaction.changeCardCash( sum, comment );
      }

      // сохраняем изменения в Firebase
      writeToFirebase( newHistory );

      // возвращаемся в MainActivity
      startActivity( new Intent( this, MainActivity.class ) );
   }
}