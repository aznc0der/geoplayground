package com.azncoder.geoplayground.home.adapter;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.azncoder.geoplayground.R;
import com.azncoder.geoplayground.callback.OnItemClickListener;
import com.azncoder.geoplayground.data.local.Delivery;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by aznc0der on 2/1/2018.
 */

public class HomeAdapter extends RecyclerView.Adapter {
    private final OnItemClickListener listener;
    private List<CellItem> cells = new ArrayList<>();

    public static final int TYPE_NO_DELIVERY = 0;
    public static final int TYPE_DELIVERY = 1;

    public HomeAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_NO_DELIVERY: {
                return new NoDeliveryCell(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_no_delivery, parent, false));
            }
            case TYPE_DELIVERY: {
                return new DeliveryCell(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_delivery, parent, false));
            }
        }
        return super.createViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_DELIVERY) {
            ((DeliveryCell) holder).bindData();
        }
    }

    public void setDeliveries(List<Delivery> deliveries) {
        cells.clear();
        if (deliveries == null || deliveries.size() < 1) {
            cells.add(new NoDeliveryCellItem(TYPE_NO_DELIVERY));
        } else {
            for (Delivery delivery : deliveries) {
                cells.add(new DeliveryCellItem(TYPE_DELIVERY, delivery));
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return (int) cells.get(position).VIEW_TYPE;
    }

    @Override
    public int getItemCount() {
        return cells.size();
    }

    class DeliveryCell extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_image)
        SimpleDraweeView ivImage;
        @BindView(R.id.tv_address)
        AppCompatTextView tvAddress;
        @BindView(R.id.tv_description)
        AppCompatTextView tvDescription;

        DeliveryCell(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindData() {
            ivImage.setImageURI(((DeliveryCellItem) cells.get(getAdapterPosition())).getDelivery().getImageUrl());
            tvAddress.setText(((DeliveryCellItem) cells.get(getAdapterPosition())).getDelivery().getLocation().getAddress());
            String phrase = itemView.getContext().getString(R.string.label_at,
                    ((DeliveryCellItem) cells.get(getAdapterPosition())).getDelivery().getDescription(),
                    ((DeliveryCellItem) cells.get(getAdapterPosition())).getDelivery().getLocation().getAddress());
            tvDescription.setText(phrase);
        }

        @SuppressWarnings("unchecked")
        @OnClick(R.id.container)
        void onClick() {
            if (listener != null) {
                listener.onItemClick(((DeliveryCellItem) cells.get(getAdapterPosition())).getDelivery());
            }
        }
    }

    class NoDeliveryCell extends RecyclerView.ViewHolder {
        NoDeliveryCell(View itemView) {
            super(itemView);
        }
    }
}
