package com.example.lesson5animations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // ----- Class constants -----------------------------------------------
    /**
     * The type of animation in the example: "Value Animation"
     * <p>
     * Тип рассматриваемой анимации в примере : "Value Animation"
     */
    private final static int TYPE_VALUE_ANIMATION = 0;

    /**
     * The type of animation in the example: "View Animation"
     * <p>
     * Тип рассматриваемой анимации в примере : "View Animation"
     */
    private final static int TYPE_VIEW_ANIMATION = 1;

    /**
     * The type of animation in the example: "Property Animation"
     * <p>
     * Тип рассматриваемой анимации в примере : "Property Animation"
     */
    private final static int TYPE_PROPERTY_ANIMATION = 2;


    // ----- Class static members ------------------------------------------
    /**
     * A static constant that contains the type of animation
     * that will be applied to the demonstration in the application.
     * The value of this constant must be manually changed before
     * compiling the application.
     * <p>
     * Статическая константа, которая содержит тип анимации, которая
     * будет применяться для демонстрации в приложении. Значение этой
     * константы нужно менять перед компиляцией приложения.
     */
    private final static int animationType = MainActivity.TYPE_VALUE_ANIMATION;

    // ----- Class members -------------------------------------------------
    /**
     * Movies list
     * <p>
     * Список фильмов
     */
    private ListView lvMovies;


    // ----- Class methods -------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ----- Toolbar -------------------------------------------------------
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        toolbar.setSubtitle("Movies List");

        toolbar.setNavigationIcon(R.drawable.baseline_add_circle_outline_24);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("#####", "Navigation Icon Click");
            }
        });

        // ----- Initializing the lvMovies field -------------------------------
        // ----- Инициализация поля lvMovies -----------------------------------
        this.lvMovies = (ListView) this.findViewById(R.id.lvMovies);

        // ----- Create a movie collection -------------------------------------
        // ----- Создание коллекции фильмов ------------------------------------
        String[] genres =
                {
                        "Action", "Fantastic", "Drama", "Melodrama",
                        "Comedy", "Adventure", "Cartoon", "Thriller", "LoveStory"
                };

        ArrayList<MyMovie> movies = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            movies.add(new MyMovie(
                    this.makeMovieTitle(),
                    genres[(int) (Math.random() * genres.length)],
                    2000 + (int) (Math.random() * 17)));
        }


        // ----- Selecting the Layout Resource File for the ListView Item ------
        // ----- Выбор файла макета раскладки виджетов для элемента списка -----
        int movieListItemLayoutId;
        switch (MainActivity.animationType) {
            // ----- List item for "Value Animation" -------------------------------
            case MainActivity.TYPE_VALUE_ANIMATION:
            default:
                movieListItemLayoutId = R.layout.list_item_value_animation;
                break;

            // ----- List item for "View Animation" --------------------------------
            case MainActivity.TYPE_VIEW_ANIMATION:
                movieListItemLayoutId = R.layout.list_item_view_animation;
                break;

            // ----- List item for "Property Animation" ----------------------------
            case MainActivity.TYPE_PROPERTY_ANIMATION:
                movieListItemLayoutId = R.layout.list_item_value_animation;
                break;
        }

        // ----- Create a Data Adapter and assign it to the lvMovies list ------
        // ----- Создание адаптера Данных и назначение его списку lvMovies -----
        ArrayAdapter<MyMovie> adapter =
                new ArrayAdapter<MyMovie>(this,
                        movieListItemLayoutId, R.id.tvTitle, movies) {

                    @Override
                    public View getView(int position,
                                        View convertView, ViewGroup parent) {
                        // ----- Receiving a widget that represents the current list item ------
                        // ----- Получение виджета, который представляет текущий элемент списка
                        View view = super.getView(position, convertView, parent);

                        // ----- Obtaining a link to the MyMovie object that is displayed in this widget
                        // ----- Получение ссылки на объект MyMovie который отображатся в этом виджете
                        MyMovie F = this.getItem(position);

                        /*
                         * Getting links to TextView widgets to display information
                         * about the movie in them
                         *
                         * Получение ссылок на виджеты TextView для
                         * отображения в них информации о фильме
                         * ---------------------------------------------------------------------
                         */
                        TextView tvTitle =
                                (TextView) view.findViewById(R.id.tvTitle);
                        TextView tvGenre =
                                (TextView) view.findViewById(R.id.tvGenre);
                        TextView tvYear =
                                (TextView) view.findViewById(R.id.tvYear);

                        // ----- Writing to widgets TextView information about the current movie
                        // ----- Запись в виджeты TextView информации о текущем фильме ---------
                        tvTitle.setText(F.title);
                        tvGenre.setText(F.genre);
                        tvYear.setText(String.valueOf(F.year));

                        // ----- Hide operations panel if necessary ----------------------------
                        // ----- Спрятать панель операций если нужно ---------------------------
                        final LinearLayout llButtonHolder = (LinearLayout)
                                view.findViewById(R.id.llButtonHolder);
                        final LinearLayout llItemHolder = (LinearLayout)
                                view.findViewById(R.id.llItemHolder);
                        final TextView tvDelete = (TextView) view.findViewById(R.id.tvDelete);
                        final TextView tvEdit = (TextView) view.findViewById(R.id.tvEdit);

                        switch (MainActivity.animationType) {
                            // ----- "Value Animation" and "Property Animation" --------------------
                            case MainActivity.TYPE_VALUE_ANIMATION:
                            case MainActivity.TYPE_PROPERTY_ANIMATION:
                            default: {
                                if (llButtonHolder.getWidth() != 0) {
                                    // ----- The width of the operation panel becomes 0 --------------------
                                    // ----- Ширина панели операций становится = 0 -------------------------
                                    FrameLayout.LayoutParams LP = new FrameLayout.LayoutParams(
                                            0, FrameLayout.LayoutParams.MATCH_PARENT);
                                    LP.gravity = Gravity.RIGHT;
                                    llButtonHolder.setLayoutParams(LP);

                                    // ----- The font size = 0 for the widgets R.id.tvDelete and R.id.tvEdit
                                    // ----- Размер шрифта = 0 для виджетов R.id.tvDelete и R.id.tvEdit ----
                                    tvDelete.setTextSize(TypedValue.COMPLEX_UNIT_SP, 0);
                                    tvEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 0);

                                    // ----- The horizontal position for the element panel -----------------
                                    // ----- Позиция по горизонтали для панели элемента = 0 ----------------
                                    llItemHolder.setX(0);
                                }
                            }
                            break;

                            // ----- "View Animation" ----------------------------------------------
                            case MainActivity.TYPE_VIEW_ANIMATION:
                                if (llItemHolder.getTag() != null) {
                                    // ----- Run an instant animation to shift the element panel backward -
                                    // ----- Запуск мгновенной анимации на сдвиг панели элемента назад ----
                                    Animation animItem = AnimationUtils.loadAnimation(
                                            MainActivity.this,
                                            R.anim.view_animation_item_panel_fall);
                                    animItem.setFillAfter(true);
                                    animItem.setDuration(0);
                                    llItemHolder.startAnimation(animItem);

                                    // ----- Running instant animation to disappear the operation panel ----
                                    // ----- Запуск мгновенной анимации на исчезновение панели операций ----
                                    Animation animOperations = AnimationUtils.loadAnimation(
                                            MainActivity.this,
                                            R.anim.view_animation_operations_panel_fall);
                                    animOperations.setDuration(0);
                                    llButtonHolder.startAnimation(animOperations);

                                    // ----- The operations panel becomes invisible ------------------------
                                    // ----- Панель операций делается невидимой ----------------------------
                                    llButtonHolder.setVisibility(View.INVISIBLE);

                                    // ----- Set the value of the tag field to null ------------------------
                                    // ----- Устанавливаем значение поля tag в null ------------------------
                                    llItemHolder.setTag(null);

                                }
                                break;

                        }


                        // ----- Return the list item view -------------------------------------
                        return view;
                    }

                };

        // ----- Assigning the Data Adapter to the android.widget.ListView ------
        // ----- Назначение Адаптера Данных списку android.widget.ListView ------
        this.lvMovies.setAdapter(adapter);


        // ----- Assigning a click event to a ListView item ---------------------
        // ----- Назачение события нажатия по элементу списка ListView ----------
        this.lvMovies.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        switch (MainActivity.animationType) {
                            // ----- Demonstrates the use of "Value Animation" ---------------------
                            // ----- Демонстрируется применение "Value Animation" ------------------
                            case MainActivity.TYPE_VALUE_ANIMATION:
                            default:
                                MainActivity.this.toggleValueAnimation(view);
                                break;

                            // ----- Demonstrates the use of "View Animation" ----------------------
                            // ----- Демонстрируется применение "View Animation" -------------------
                            case MainActivity.TYPE_VIEW_ANIMATION:
                                MainActivity.this.toggleViewAnimation(view);
                                break;

                            // ----- Demonstrates the use of "Property Animation" ------------------
                            // ----- Демонстрируется применение "Property Animation" ---------------
                            case MainActivity.TYPE_PROPERTY_ANIMATION:
                                MainActivity.this.togglePropertyAnimation(view);
                                break;

                        }
                    }
                });

    }

    /**
     * Run animation "Value Animation" for execution.
     *
     * @param view - the widget of the list item panel
     *             above which it is necessary to apply the animation.
     *             <p>
     *             Запуск анимации "Value Animation" на исполнение.
     * @param view - Ссылка на виджет панели элемента списка, над которым
     *             необходимо применять анимацию.
     */
    private void toggleValueAnimation(View view) {
        // ----- Getting links to widgets to which animation is applied --------
        // ----- Получение ссылок на виджеты к которым применяется анимация ----
        final LinearLayout llButtonHolder = (LinearLayout)
                view.findViewById(R.id.llButtonHolder);
        final LinearLayout llItemHolder = (LinearLayout)
                view.findViewById(R.id.llItemHolder);
        final TextView tvDelete = (TextView) view.findViewById(R.id.tvDelete);
        final TextView tvEdit = (TextView) view.findViewById(R.id.tvEdit);

        // ----- Select the required resource file for the animation -----------
        // ----- Выбор необходимого файла ресурсов для анимации ----------------
        AnimatorSet asRise;

        if (llButtonHolder.getWidth() == 0) {
            // ----- The operations panel must be shown ----------------------------
            // ----- Панель операций необходимо показать ---------------------------
            asRise = (AnimatorSet)
                    AnimatorInflater.loadAnimator(this, R.animator.value_animation_item_rise);
        } else {
            // ----- The operation panel must be hidden ---------------------------
            // ----- Панель операций необходимо спрятать --------------------------
            asRise = (AnimatorSet)
                    AnimatorInflater.loadAnimator(this, R.animator.value_animation_item_fall);
        }

        // ----- Interpolator assignment --------------------------------------
        // ----- Назначение Интерполятора -------------------------------------
        asRise.setInterpolator(new AccelerateDecelerateInterpolator());

        // ----- Getting the list of animations "Value Animation" -------------
        // ----- Получение списка анимаций "Value Animation" ------------------
        ArrayList<Animator> arrL = asRise.getChildAnimations();

        // ----- Handling events for changing the width of the operation panel
        // ----- Обработка событий изменения ширины панели операций -----------
        ValueAnimator vaRise = (ValueAnimator) arrL.get(0);
        vaRise.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curWidth = (int) animation.getAnimatedValue();

                FrameLayout.LayoutParams LP = new FrameLayout.LayoutParams(
                        curWidth,
                        FrameLayout.LayoutParams.MATCH_PARENT);
                LP.gravity = Gravity.RIGHT;
                llButtonHolder.setLayoutParams(LP);
            }
        });

        // ----- Handling events changing the font size of the buttons "Delete", "Edit"
        // ----- Обработка событий изменения размеров шрифта кнопок "Delete", "Edit"
        ValueAnimator vaRiseTextSize = (ValueAnimator) arrL.get(1);
        vaRiseTextSize.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float curTextSize = (float) animation.getAnimatedValue();
                tvDelete.setTextSize(TypedValue.COMPLEX_UNIT_SP, curTextSize);
                tvEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, curTextSize);
            }
        });


        // ----- Handling Horizontal Shift Events for an Element Panel ---------
        // ----- Обработка событий изменения сдвига по горизонтали панели элемента
        ValueAnimator vaRiseShift = (ValueAnimator) arrL.get(2);
        vaRiseShift.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curX = (int) animation.getAnimatedValue();
                llItemHolder.setX(curX);
            }
        });

        // ----- Run animation "View Animation" --------------------------------
        // ----- Запуск анимации "View Animation" ------------------------------
        asRise.start();
    }

    /**
     * Run animation "View Animation" for execution.
     *
     * @param view - the widget of the list item panel
     *             above which it is necessary to apply the animation.
     *             <p>
     *             Запуск анимации "View Animation" на исполнение.
     * @param view - Ссылка на виджет панели элемента списка, над которым
     *             необходимо применять анимацию.
     */
    private void toggleViewAnimation(View view) {
        // ----- Start animation for shift of the element panel ----------------
        // ----- Запуск анимации сдвига панели элемента ------------------------
        final LinearLayout llItemHolder = (LinearLayout)
                view.findViewById(R.id.llItemHolder);

        Animation animItem = AnimationUtils.loadAnimation(this,
                (llItemHolder.getTag() == null) ?
                        R.anim.view_animation_item_panel_rise :
                        R.anim.view_animation_item_panel_fall);
        animItem.setFillAfter(true);

        llItemHolder.startAnimation(animItem);

        // ----- Start animation for operations panel --------------------------
        // ----- Запуск анимации показа панели операций ------------------------
        final LinearLayout llButtonHolder = (LinearLayout)
                view.findViewById(R.id.llButtonHolder);

        Animation animOperations = AnimationUtils.loadAnimation(this,
                (llItemHolder.getTag() == null) ?
                        R.anim.view_animation_operations_panel_rise :
                        R.anim.view_animation_operations_panel_fall);

        llButtonHolder.startAnimation(animOperations);

        // ----- Do preparatory or final actions -------------------------------
        // ----- Делаем подготовительные или завершающие действия --------------
        if (llItemHolder.getTag() == null) {
            llButtonHolder.setVisibility(View.VISIBLE);
            llItemHolder.setTag(1);
        } else {
            llItemHolder.setTag(null);
            llButtonHolder.setVisibility(View.INVISIBLE);

        }
    }

    /**
     * Run animation "Property Animation" for execution.
     *
     * @param view - the widget of the list item panel
     *             above which it is necessary to apply the animation.
     *             <p>
     *             Запуск анимации "Property Animation" на исполнение.
     * @param view - Ссылка на виджет панели элемента списка, над которым
     *             необходимо применять анимацию.
     */
    private void togglePropertyAnimation(View view) {
        // ----- Start animation for shift of the element panel ----------------
        // ----- Запуск анимации сдвига панели элемента ------------------------
        final LinearLayout llItemHolder = (LinearLayout)
                view.findViewById(R.id.llItemHolder);

        ObjectAnimator objectAnimator = (ObjectAnimator)
                AnimatorInflater.loadAnimator(
                        this,
                        (llItemHolder.getX() == 0) ?
                                R.animator.property_animation_list_item_rise :
                                R.animator.property_animation_list_item_fall);
        objectAnimator.setTarget(llItemHolder);
        objectAnimator.start();

        final TextView tvDelete = (TextView) view.findViewById(R.id.tvDelete);
        final TextView tvEdit = (TextView) view.findViewById(R.id.tvEdit);

        // ----- Run the font size change animation for the widget R.id.tvDelete
        // ----- Запуск анимации изменения размера шрифта для виджета R.id.tvDelete
        ObjectAnimator objectAnimator2 = (ObjectAnimator)
                AnimatorInflater.loadAnimator(
                        this,
                        (llItemHolder.getX() == 0) ?
                                R.animator.property_animation_text_size_rise :
                                R.animator.property_animation_text_size_fall);
        objectAnimator2.setTarget(tvDelete);
        objectAnimator2.start();

        // ----- Run the font size change animation for the widget R.id.tvEdit
        // ----- Запуск анимации изменения размера шрифта для виджета R.id.tvEdit
        ObjectAnimator objectAnimator3 = (ObjectAnimator)
                AnimatorInflater.loadAnimator(
                        this,
                        (llItemHolder.getX() == 0) ?
                                R.animator.property_animation_text_size_rise :
                                R.animator.property_animation_text_size_fall);
        objectAnimator3.setTarget(tvEdit);
        objectAnimator3.start();

        // ----- Run the animation to change the width of the operation panel --
        // ----- Запуск анимации изменения ширины панели операций --------------
        final LinearLayout llButtonHolder = (LinearLayout)
                view.findViewById(R.id.llButtonHolder);

        if (llItemHolder.getX() == 0) {
            ObjectAnimator.ofFloat(new WidthEvaluator(llButtonHolder), "Width", 0f, 300f).start();
        } else {
            ObjectAnimator.ofFloat(new WidthEvaluator(llButtonHolder), "Width", 300f, 0f).start();
        }


    }

    /**
     * Event handler for clicking on the R.id.tvDelete and R.id.tvEdit buttons.
     *
     * @param v - Reference to the button on which clicked.
     *          <p>
     *          Метод является обработчиком события нажатия на кнопки R.id.tvDelete
     *          и R.id.tvEdit панели операций.
     * @param v - ссылка на кнопку по которой осуществлено нажатие.
     */
    public void txtClick(View v) {
        // ----- Get a link to the root container of the list item -------------
        // ----- Получаем ссылку на корневой контейнер элемента списка ---------
        View view = (View) v.getParent().getParent();

        switch (MainActivity.animationType) {
            // ----- "Value Animation" ---------------------------------------------
            case MainActivity.TYPE_VALUE_ANIMATION:
            default:
                MainActivity.this.toggleValueAnimation(view);
                break;

            // ----- "View Animation" ----------------------------------------------
            case MainActivity.TYPE_VIEW_ANIMATION:
                MainActivity.this.toggleViewAnimation(view);
                break;

            // ----- "Property Animation" ------------------------------------------
            case MainActivity.TYPE_PROPERTY_ANIMATION:
                MainActivity.this.togglePropertyAnimation(view);
                break;

        }


        // ----- Here it is necessary to place the code for the operations "Delete" and "Edit"
        // ----- Здесь необходимо разместить код для операций "Delete" и "Edit"
        Log.d("#####", "txtClick");
    }


	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{

		int id = item.getItemId();
		return super.onOptionsItemSelected(item);
	}
	*/

    private String makeMovieTitle() {
        String[] first =
                {
                        "Winter", "House", "Summer", "Road", "Human", "Wind", "Soldier"
                };

        String[] second =
                {
                        "Blues", "Alarm", "Song", "Sea", "River", "Boat", "Desert"
                };

        String[] third =
                {
                        "Tree", "Cloud", "Sun", "Day", "Year", "Story", "Love"
                };

        String[] z =
                {
                        " and ", " or ", " at ", " under ", " before ", " after "
                };

        String str = first[(int) (Math.random() * first.length)] + " " +
                second[(int) (Math.random() * second.length)] +
                z[(int) (Math.random() * z.length)] +
                third[(int) (Math.random() * third.length)];

        return str;
    }

}

