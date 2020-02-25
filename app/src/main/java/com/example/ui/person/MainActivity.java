package com.example.ui.person;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.R;
import com.example.data.constants.Constants;
import com.example.data.database.AppDatabase;
import com.example.data.model.PersonModel;
import com.example.ui.edit.EditActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnLongClickListener{

    PersonAdaptor personAdaptor;
    RecyclerView recyclerView;
    FloatingActionButton addFAB;
    PersonViewModel personViewModel;
    List<PersonModel> peopleList=new ArrayList<>();
    private Paint p = new Paint();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        addFAB = findViewById(R.id.addFAB);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        personViewModel = ViewModelProviders.of(this).get(PersonViewModel.class);
        personViewModel.init(this);
        personViewModel.getPersonData().observe(this, new Observer<List<PersonModel>>() {
            @Override
            public void onChanged(List<PersonModel> people) {

                peopleList.clear();
                peopleList.addAll(people);
                setupRecyclerView();
                personAdaptor.notifyDataSetChanged();
            }
        });

        addFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),EditActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupRecyclerView() {

        if (personAdaptor == null) {
            recyclerView.setHasFixedSize(true);
            personAdaptor = new PersonAdaptor(peopleList,this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(personAdaptor);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setNestedScrollingEnabled(true);
            initSwipe();
        } else {
            personAdaptor.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onLongClick(View v)
    {

        return true;
    }

    private void initSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {

                    PersonModel person = peopleList.get(position);
                    personViewModel.deleteItem(person);
                    personAdaptor.notifyItemRemoved(position);


                } else {

                    PersonModel person = peopleList.get(position);
                    Intent intent=new Intent(getApplicationContext(),EditActivity.class);
                    intent.putExtra(Constants.UPDATE_Person_Id,person.getId());
                    startActivity(intent);

                   /* PersonModel person = peopleList.get(position);
                    personViewModel.deleteItem(person);
                    personAdaptor.notifyItemRemoved(position);*/
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX > 0) {
                        p.setColor(getResources().getColor(R.color.colorGreen));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRoundRect(background,15,15 ,p);
                        icon = BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_edit);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    } else {
                        p.setColor(getResources().getColor(R.color.colorBlack));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRoundRect(background,15,15, p);
                        icon = BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_delete);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }


}
