package ir.jmehdipour.immortalcar.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ir.jmehdipour.immortalcar.R;
import ir.jmehdipour.immortalcar.data.model.Banner;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.BannerViewHolder> {
    private List<Banner> banners = new ArrayList<>();

    public BannerAdapter() {

    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BannerViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_banner, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BannerViewHolder bannerViewHolder, int i) {
        bannerViewHolder.bindBanner(banners.get(i));
    }

    @Override
    public int getItemCount() {
        return banners.size();
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {
        private ImageView bannerImageIv;
        private TextView titleTv;
        private TextView modelTv;
        private TextView brandTv;

        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            bannerImageIv = itemView.findViewById(R.id.iv_banner_image);
            titleTv = itemView.findViewById(R.id.tv_banner_title);
            brandTv = itemView.findViewById(R.id.tv_banner_brand);
            modelTv = itemView.findViewById(R.id.tv_banner_model);
        }

        public void bindBanner(Banner banner){
            Picasso.get().load(banner.getImageUrl()).into(bannerImageIv);
            brandTv.setText(banner.getBrand());
            titleTv.setText(banner.getTitle());
            modelTv.setText(String.valueOf(banner.getModel()));
        }
    }

}
