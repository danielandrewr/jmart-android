package com.josephusdanieljmartfa.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.josephusdanieljmartfa.Interface.LoadInterface;
import com.josephusdanieljmartfa.R;
import com.josephusdanieljmartfa.model.Payment;

import java.util.List;

class LoadingViewHolder extends RecyclerView.ViewHolder {
    public ProgressBar progressBar;

    public LoadingViewHolder(View view) {
        super(view);
        progressBar = view.findViewById(R.id.progressBar);
    }
}

class ItemViewHolder extends RecyclerView.ViewHolder {
    public TextView productName, invoiceStatus;

    public ItemViewHolder(View view) {
        super(view);
        productName = view.findViewById(R.id.paymentName);
        invoiceStatus = view.findViewById(R.id.invoiceStatus);
    }
}

public class PaymentItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0, VIEW_TYPE_LOADING = 1;
    private LoadInterface load;
    private boolean isLoading;
    private Activity activity;
    private List<Payment> payments;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    public PaymentItemAdapter(RecyclerView recyclerView, Activity activity, List<Payment> payments) {
        this.activity = activity;
        this.payments = payments;

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

    public void setLoad(LoadInterface load) {
        this.load = load;
    }

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

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            Payment payment = payments.get(position);
            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            viewHolder.productName.setText(String.valueOf(payment.productId));
            viewHolder.invoiceStatus.setText(payment.history.get(payment.history.size()-1).status.toString());
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return payments.size();
    }

    public void setLoaded() {
        isLoading = false;
    }
}
