package com.lumiere.app;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import org.json.JSONArray;
import org.json.JSONObject;
public abstract class LumiereWidget extends AppWidgetProvider {
  abstract void fill(RemoteViews v, JSONObject d) throws Exception;
  static JSONObject data(Context c) {
    try { return new JSONObject(c.getSharedPreferences("lumiere_widget", 0).getString("d", "{}")); } catch (Exception e) { return new JSONObject(); }
  }
  @Override public void onUpdate(Context c, AppWidgetManager m, int[] ids) {
    for (int id : ids) {
      RemoteViews v = new RemoteViews(c.getPackageName(), R.layout.widget_lumiere);
      try { fill(v, data(c)); } catch (Exception e) { }
      Intent i = c.getPackageManager().getLaunchIntentForPackage(c.getPackageName());
      v.setOnClickPendingIntent(R.id.wr, PendingIntent.getActivity(c, 0, i, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT));
      m.updateAppWidget(id, v);
    }
  }
  static void refreshAll(Context c) {
    AppWidgetManager m = AppWidgetManager.getInstance(c);
    Class<?>[] ks = { Tasks.class, Progress.class, Habits.class };
    for (Class<?> k : ks) {
      int[] ids = m.getAppWidgetIds(new ComponentName(c, k));
      if (ids.length > 0) {
        Intent i = new Intent(c, k);
        i.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        i.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        c.sendBroadcast(i);
      }
    }
  }
  public static class Tasks extends LumiereWidget {
    void fill(RemoteViews v, JSONObject d) throws Exception {
      JSONArray a = d.optJSONArray("tasks"); int n = 0; StringBuilder s = new StringBuilder();
      if (a != null) for (int i = 0; i < a.length(); i++) {
        JSONObject t = a.getJSONObject(i); boolean done = t.optInt("d") == 1; if (!done) n++;
        s.append(done ? "\u2713 " : "\u2022 ").append(t.optString("m").isEmpty() ? "" : t.optString("m") + " ").append(t.optString("t")).append("\n");
      }
      v.setTextViewText(R.id.wt, "Tugas hari ini"); v.setTextViewText(R.id.wn, String.valueOf(n));
      v.setTextViewText(R.id.wb, s.length() == 0 ? "Belum ada tugas" : s.toString().trim());
    }
  }
  public static class Progress extends LumiereWidget {
    void fill(RemoteViews v, JSONObject d) throws Exception {
      v.setTextViewText(R.id.wt, "Penyelesaian hari ini"); v.setTextViewText(R.id.wn, d.optInt("pct") + "%");
      v.setTextViewText(R.id.wb, "Level " + d.optInt("lv", 1) + ": " + d.optString("ln", "Pemimpi Kecil"));
    }
  }
  public static class Habits extends LumiereWidget {
    void fill(RemoteViews v, JSONObject d) throws Exception {
      JSONArray a = d.optJSONArray("habits"); StringBuilder s = new StringBuilder();
      if (a != null) for (int i = 0; i < a.length(); i++) { JSONObject h = a.getJSONObject(i); s.append(h.optString("n")).append("  ").append(h.optInt("p")).append("%\n"); }
      v.setTextViewText(R.id.wt, "Kebiasaan"); v.setTextViewText(R.id.wn, d.optInt("streak") + " hari");
      v.setTextViewText(R.id.wb, s.length() == 0 ? "Belum ada kebiasaan hari ini" : s.toString().trim());
    }
  }
}
