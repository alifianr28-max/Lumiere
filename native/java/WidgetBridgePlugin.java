package com.lumiere.app;
import android.content.Context;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
@CapacitorPlugin(name = "WidgetBridge")
public class WidgetBridgePlugin extends Plugin {
  @PluginMethod public void update(PluginCall call) {
    Context c = getContext();
    c.getSharedPreferences("lumiere_widget", Context.MODE_PRIVATE).edit().putString("d", call.getString("data", "{}")).apply();
    LumiereWidget.refreshAll(c);
    call.resolve();
  }
}
