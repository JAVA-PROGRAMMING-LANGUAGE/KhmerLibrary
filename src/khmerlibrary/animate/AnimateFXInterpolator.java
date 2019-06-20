/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khmerlibrary.animate;

import javafx.animation.Interpolator;

/**
 *
 * @author YCODE
 */
public final class AnimateFXInterpolator {

    private AnimateFXInterpolator() {

        throw new IllegalStateException("AnimateFX Interpolator");

    }

    public static final Interpolator EASE = Interpolator.SPLINE(0.25, 0.1, 0.25, 1);

}
