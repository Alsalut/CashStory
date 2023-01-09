package minus;

// эта Activity отвечает за транзакции расхода

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

public class MinusActivity extends AppCompatActivity implements View.OnClickListener, IConstant
{
   private ImageButton imageButtonCardMinus, imageButtonCashMinus;
   private EditText editTextSum, editTextComment;

   private String newHistory;

   @Override
   protected void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.activity_minus );

      initVariable(); // сопоставляем view переменным
      setListener(); // назначаем слушателя кнопкам
   }

   // сопоставляем view переменным
   private void initVariable()
   {
      imageButtonCardMinus = findViewById( R.id.image_button_minus_card );
      imageButtonCashMinus = findViewById( R.id.image_button_minus_cash );

      editTextComment = findViewById( R.id.edit_text_minus_comment );
      editTextSum = findViewById( R.id.edit_text_minus_sum );
   }

   // назначаем слушателя кнопкам
   private void setListener()
   {
      imageButtonCardMinus.setOnClickListener( this );
      imageButtonCashMinus.setOnClickListener( this );
   }

   // обрабатываем нажатия кнопок
   @Override
   public void onClick( View btn )
   {
      // получаем данные из полей ввода
      final String sum = editTextSum.getText().toString();
      final String comment = editTextComment.getText().toString();

      // если поля ввода пустые, то нажатие обрабатывать не будем
      if( sum.equals( empty ) || comment.equals( empty ) ) return;

      // создаём объект CreateTransaction
      CreateTransaction createTransaction = new CreateTransaction();

      // сохраняем изменения в Firebase
      // а в myLocalBase история всегда будет сохраняться из Firebase ( в MainActivity )
      switch( btn.getId() )
      {
         case R.id.image_button_minus_card:
            newHistory = createTransaction.fromCard( sum, comment );
            break;

         case R.id.image_button_minus_cash:
            newHistory = createTransaction.fromCash( sum, comment );
      }

      // сохраняем изменения в Firebase
      writeToFirebase( newHistory );

      // возвращаемся в MainActivity
      startActivity( new Intent( this, MainActivity.class ) );
   }
}