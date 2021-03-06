package easyway2in.hello.actcontato.app;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by LuizFilipeFerreira on 1/25/2017.
 */

public class ViewHelper {
    public static ArrayAdapter<String> createArrayAdapter(Context context, Spinner spinner)
    {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter);

        return arrayAdapter;
    }
}
