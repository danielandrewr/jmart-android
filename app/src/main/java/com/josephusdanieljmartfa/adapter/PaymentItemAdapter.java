package com.josephusdanieljmartfa.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.josephusdanieljmartfa.Interface.LoadInterface;
import com.josephusdanieljmartfa.Interface.PaymentItemInterface;
import com.josephusdanieljmartfa.R;
import com.josephusdanieljmartfa.model.Payment;
import com.josephusdanieljmartfa.request.AcceptRequest;
import com.josephusdanieljmartfa.request.CancelRequest;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Class untuk menampilkan Loading Progress Bar
 * (Experimental) - Belum fungsional
 */
class LoadingViewHolder extends RecyclerView.ViewHolder {
    public ProgressBar progressBar;

    public LoadingViewHolder(View view) {
        super(view);
        progressBar = view.findViewById(R.id.progressBar);
    }
}

/**
 * Class yang mengatur ItemHolder untuk RecycleView di InvoiceHistory
 * Agar memiliki komponen untuk setiap CardViewnya dan Button Cancel dapat diklik
 */
class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView productName, invoiceStatus;
    public ImageView cancel;
    public PaymentItemInterface onClickInterface;

    public ItemViewHolder(final View view) {
        super(view);
        productName = view.findViewById(R.id.paymentName);
        invoiceStatus = view.findViewById(R.id.invoiceStatus);
        cancel = view.findViewById(R.id.cancelButton);
    }

    /**
     * Mengirimkan Cancel POST Request ke Server agar Payment tertentu dibatalkan
     * @param view
     */
    @Override
    public void onClick(final View view) {
        if (onClickInterface != null) {
            int position = getAdapterPosition();
            List<Payment> payments = PaymentItemAdapter.getPayments();
            if (view.getId() == cancel.getId()) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(view.getContext(), "Invoice Dibatalkan!", Toast.LENGTH_LONG).show();
                    }
                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(view.getContext(), "Gagal Mengirimkan Request ke Server!", Toast.LENGTH_LONG).show();
                    }
                };
                StringRequest request = new CancelRequest(payments.get(position).id, listener, errorListener);
                RequestQueue queue = Volley.newRequestQueue(view.getContext());
                queue.add(request);
            }
            onClickInterface.imageViewOnClick(getAdapterPosition());
        }
    }
}

/**
 * Adapter Class utama untuk mengelola RecyclerView yang digunakan di InvoiceHistory
 */
public class PaymentItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0, VIEW_TYPE_LOADING = 1;
    private LoadInterface load;
    private boolean isLoading;
    private Activity activity;
    public static List<Payment> payments;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    private PaymentItemInterface onClickInterface;

    /**
     * Constructor Class
     * @param recyclerView
     * @param activity
     * @param payments
     * @param onClickInterface
     */
    public PaymentItemAdapter(RecyclerView recyclerView, Activity activity, List<Payment> payments, PaymentItemInterface onClickInterface) {
        this.activity = activity;
        PaymentItemAdapter.payments = payments;
        this.onClickInterface = onClickInterface;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (load != null) {
                        load.onLoad();
                    }
                    isLoading = true;
                }
            }
        });
    }

    /**
     * Mutator untuk state load
     * @param load
     */
    public void setLoad(LoadInterface load) {
        this.load = load;
    }

    /**
     * Memisahkan tipe View antara ITEM dan LOADING
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(activity).inflate(R.layout.item_layout, parent, false);
            return new ItemViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(activity).inflate(R.layout.loader_progressbar, parent, false);
            return new ItemViewHolder(view);
        }
        return null;
    }

    /**
     * Mengatur Isi dari ViewHolder (@CardView) yaitu: productId, status, dan Clickable
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            Payment payment = payments.get(position);
            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            viewHolder.productName.setText("Product ID: " + String.valueOf(payment.productId));
            viewHolder.invoiceStatus.setText(payment.history.get(payment.history.size()-1).status.toString());
            viewHolder.onClickInterface = this.onClickInterface;
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    /**
     * Hitung Jumlah Size Arraylist Payment
     * @return
     */
    @Override
    public int getItemCount() {
        return payments.size();
    }

    /**
     * Getter untuk payments
     * @return
     */
    public static List<Payment> getPayments() {
        return payments;
    }

    /**
     * Mutator yang akan digunakan untuk menentukan state Progress Bar Loader
     */
    public void setLoaded() {
        isLoading = false;
    }
}
