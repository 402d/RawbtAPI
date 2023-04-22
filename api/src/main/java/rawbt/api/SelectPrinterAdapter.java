package rawbt.api;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import rawbt.sdk.PrinterInfo;


public class SelectPrinterAdapter extends ArrayAdapter<PrinterInfo> {

    Context mCTx; //context needed to inflate
    private final LayoutInflater lInflater;
    int resource;
    List<PrinterInfo> printerEntities;

    public SelectPrinterAdapter(@NonNull Context context, int resource) {
        super(context, resource);

        this.mCTx = context;
        this.resource = resource;
        lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        printerEntities = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(resource, parent, false);
        }
        ((TextView)view).setText(printerEntities.get(position).description);
        return view;
    }

    @NonNull
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(android.R.layout.simple_list_item_single_choice, parent, false);
        }
        ((TextView)view).setText(printerEntities.get(position).description);
        return view;
    }

    @Override
    public void clear() {
        printerEntities.clear();
    }

    @Override
    public void add(@Nullable PrinterInfo object) {
        printerEntities.add(object);
    }

    @Override
    public int getCount() {
        return printerEntities.size();
    }

    @Nullable
    @Override
    public PrinterInfo getItem(int position) {
        return printerEntities.get(position);
    }
}

