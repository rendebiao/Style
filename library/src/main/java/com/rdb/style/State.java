package com.rdb.style;

/**
 * Created by DB on 2018/5/18.
 */

public enum State {

    windowFocused(android.R.attr.state_window_focused),
    selected(android.R.attr.state_selected),
    focused(android.R.attr.state_focused),
    enable(android.R.attr.state_enabled),
    pressed(android.R.attr.state_pressed),
    activated(android.R.attr.state_activated),
    accelerated(android.R.attr.state_accelerated),
    checked(android.R.attr.state_checked),
    checkable(android.R.attr.state_checkable),
    hovered(android.R.attr.state_hovered),
    drag_can_accept(android.R.attr.state_drag_can_accept),
    drag_hovered(android.R.attr.state_drag_hovered),
    active(android.R.attr.state_active),
    single(android.R.attr.state_single),
    first(android.R.attr.state_first),
    middle(android.R.attr.state_middle),
    last(android.R.attr.state_last);

    public static final int[] STATE_NULL = new int[]{};
    public static final int[] STATE_DISABLE = new int[]{enable.getValue(false)};
    public static final int[] STATE_CHECKED = new int[]{checked.getValue(true)};
    public static final int[] STATE_SELECTED = new int[]{selected.getValue(true)};
    public static final int[] STATE_PRESSED = new int[]{pressed.getValue(true)};
    public static final int[] STATE_FOCUSED = new int[]{focused.getValue(true)};
    public static final int[] STATE_SELETED_PRESSED = new int[]{selected.getValue(true), pressed.getValue(true)};
    public static final int[] STATE_UNSELETED_UNPRESSED = new int[]{selected.getValue(false), pressed.getValue(false)};
    public static final int[] STATE_UNFOCUSED_UNPRESSED = new int[]{focused.getValue(false), pressed.getValue(false)};
    public static final int[][] STATES_NULL = new int[][]{STATE_NULL};
    public static final int[][] STATES_DISABLE_OTHER = new int[][]{STATE_DISABLE, STATE_NULL};
    public static final int[][] STATES_DISABLE_CHECKED_OTHER = new int[][]{STATE_DISABLE, STATE_CHECKED, STATE_NULL};
    public static final int[][] STATES_DISABLE_PRESSED_OTHER = new int[][]{STATE_DISABLE, STATE_PRESSED, STATE_NULL};
    public static final int[][] STATES_DISABLE_FOCUSED_OTHER = new int[][]{STATE_DISABLE, STATE_FOCUSED, STATE_NULL};
    public static final int[][] STATES_DISABLE_SELECTED_OTHER = new int[][]{STATE_DISABLE, STATE_SELECTED, STATE_NULL};
    public static final int[][] STATES_DISABLE_SELECTED_PRESSED_OTHER = new int[][]{STATE_DISABLE, STATE_SELECTED, STATE_PRESSED, STATE_NULL};
    public static final int[][] STATES_DISABLE_SELECTED_CHECKED_OTHER = new int[][]{STATE_DISABLE, STATE_SELECTED, STATE_CHECKED, STATE_NULL};
    int attr;

    State(int attr) {
        this.attr = attr;
    }

    public static boolean contains(int[] values, State state) {
        for (int value : values) {
            if (value == state.attr) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSame(int[] values1, int[] values2, State state) {
        if (values1 == null && values2 != null) {
            return false;
        }
        boolean b1 = false;
        boolean b2 = false;
        for (int value : values1) {
            if (value == state.attr) {
                b1 = true;
                break;
            }
        }
        for (int value : values2) {
            if (value == state.attr) {
                b2 = true;
                break;
            }
        }
        return b1 == b2;
    }

    public int getAttr() {
        return attr;
    }

    public int getValue(boolean value) {
        return attr * (value ? 1 : -1);
    }
}
