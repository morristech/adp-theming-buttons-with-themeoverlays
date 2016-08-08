package com.example.alockwood.themedbuttons;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {
  private static final String STATE_ARE_ALL_BUTTONS_ENABLED = "state_are_all_buttons_enabled";
  private static final String STATE_ARE_ALL_BUTTONS_PRESSED = "state_are_all_buttons_pressed";

  private boolean areAllButtonsEnabled;
  private boolean areAllButtonsPressed;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if (savedInstanceState != null) {
      areAllButtonsEnabled = savedInstanceState.getBoolean(STATE_ARE_ALL_BUTTONS_ENABLED);
      areAllButtonsPressed = savedInstanceState.getBoolean(STATE_ARE_ALL_BUTTONS_PRESSED);
    }

    updateUi();

    final View lightButton4 = findViewById(R.id.light_button4);
    ViewCompat.setBackgroundTintList(lightButton4, ThemeUtils.createColoredButtonColorStateList(lightButton4.getContext()));

    final View darkButton4 = findViewById(R.id.dark_button4);
    ViewCompat.setBackgroundTintList(darkButton4, ThemeUtils.createColoredButtonColorStateList(darkButton4.getContext()));
  }

  private void updateUi() {
    final ViewGroup viewGroup = (ViewGroup) findViewById(R.id.button_container);
    for (int i = 0; i < viewGroup.getChildCount(); i++) {
      viewGroup.getChildAt(i).setEnabled(areAllButtonsEnabled);
      viewGroup.getChildAt(i).setPressed(areAllButtonsPressed);
    }
    updateActionBarSubtitle();
    invalidateOptionsMenu();
  }

  private void updateActionBarSubtitle() {
    final int subtitleResId;
    if (areAllButtonsEnabled && areAllButtonsPressed) {
      subtitleResId = R.string.action_bar_subtitle_enabled_pressed;
    } else if (areAllButtonsEnabled) {
      subtitleResId = R.string.action_bar_subtitle_enabled_unpressed;
    } else if (areAllButtonsPressed) {
      subtitleResId = R.string.action_bar_subtitle_disabled_pressed;
    } else {
      subtitleResId = R.string.action_bar_subtitle_disabled_unpressed;
    }
    //noinspection ConstantConditions
    getSupportActionBar().setSubtitle(subtitleResId);

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_main, menu);
    return true;
  }

  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {
    menu.findItem(R.id.enable_disable_all)
        .setTitle(areAllButtonsEnabled ? R.string.disable_all : R.string.enable_all);
    menu.findItem(R.id.press_unpress_all)
        .setTitle(areAllButtonsPressed ? R.string.unpress_all : R.string.press_all);
    return super.onPrepareOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.enable_disable_all) {
      areAllButtonsEnabled = !areAllButtonsEnabled;
      updateUi();
      return true;
    }
    if (item.getItemId() == R.id.press_unpress_all) {
      areAllButtonsPressed = !areAllButtonsPressed;
      updateUi();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putBoolean(STATE_ARE_ALL_BUTTONS_ENABLED, areAllButtonsEnabled);
    outState.putBoolean(STATE_ARE_ALL_BUTTONS_PRESSED, areAllButtonsPressed);
  }
}
