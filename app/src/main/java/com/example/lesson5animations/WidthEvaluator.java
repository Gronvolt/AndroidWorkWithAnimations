package com.example.lesson5animations;

import android.view.View;
import android.view.ViewGroup;

/**
 * The WidthEvaluator class. Auxiliary class for "Property Animation".
 * It helps to apply animation to the width property of widgets.
 *
 * Класс WidthEvaluator. Вспомогательный класс для "Property Animation",
 * помогающий применить анимацию к свойству width виджетов.
 */
public class WidthEvaluator {
    /**
     * A reference to a widget whose width will be changed
     * with the help of android.animation.ObjectAnimator object.
     *
     *  Ссылка на виджет, ширина которого будет изменяться
     *  при помощи объекта android.animation.ObjectAnimator
     */
    private View v;

    // ----- Class methods -------------------------------------------------
    public WidthEvaluator(View v)
    {
        this.v = v;
    }

    /**
     * The method that will be called by the android.animation.ObjectAnimator
     * to change the width of the widget v.
     *
     * @param w	- The current value for the width of the widget
     *             that is set by the android.animation.ObjectAnimator
     *
     * Метод, который будет вызываться объектом android.animation.ObjectAnimator
     * для изменения ширины виджета v.
     *
     * @param w	- Текущее значение для ширины виджета, которое
     *          устанавливается объектом android.animation.ObjectAnimator
     */
    public void	setWidth(float w)
    {
        ViewGroup.LayoutParams params	= v.getLayoutParams();
        params.width	= (int) w;
        v.setLayoutParams(params);
    }
}
