p = 'android/app/src/main/AndroidManifest.xml'
s = open(p, encoding='utf-8').read()
perm = '<uses-permission android:name="android.permission.POST_NOTIFICATIONS"/><uses-permission android:name="android.permission.SCHEDULE_EXACT_ALARM"/>'
def r(n, x):
    return ('<receiver android:name=".LumiereWidget$%s" android:exported="true"><intent-filter><action android:name="android.appwidget.action.APPWIDGET_UPDATE"/></intent-filter><meta-data android:name="android.appwidget.provider" android:resource="@xml/%s"/></receiver>' % (n, x))
rc = r('Tasks', 'widget_tasks') + r('Progress', 'widget_progress') + r('Habits', 'widget_habits')
s = s.replace('</application>', rc + '</application>').replace('</manifest>', perm + '</manifest>')
open(p, 'w', encoding='utf-8').write(s)
