package easyway2in.hello.actcontato.app;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by LuizFilipeFerreira on 1/25/2017.
 */

public class MessageBox {
    public static void show(Context context, String titulo, String msg)
    {
        show(context, titulo, msg, android.R.drawable.ic_dialog_alert);
    }

    public static void show(Context context, String titulo, String msg, int contId)
    {
        AlertDialog.Builder dlg = new AlertDialog.Builder(context);
        dlg.setIcon(contId);
        dlg.setTitle(titulo);
        dlg.setMessage(msg);
        dlg.setNeutralButton("OK", null);
        dlg.show();
    }
}
