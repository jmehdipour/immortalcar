package ir.jmehdipour.immortalcar.home;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import ir.jmehdipour.immortalcar.R;
import ir.jmehdipour.immortalcar.data.model.Car;
import ir.jmehdipour.immortalcar.details.CarDetailsActivity;
import ir.jmehdipour.immortalcar.details.CarDetailsViewModel;
import ir.jmehdipour.immortalcar.utils.PriceConverter;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {
    private List<Car> cars  = new ArrayList<>();
    //private int pendingCarPosition;

    public CarAdapter() {

    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CarViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_car, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder carViewHolder, int i) {
        carViewHolder.bindCar(cars.get(i));
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public class CarViewHolder extends RecyclerView.ViewHolder {
        private ImageView carImageIv;
        private TextView titleTv;
        private TextView brandTv;
        private TextView modelTv;
        private TextView priceTv;
        //private TextView descriptionTv;


        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            carImageIv = itemView.findViewById(R.id.iv_car_image);
            titleTv = itemView.findViewById(R.id.tv_car_title);
            brandTv = itemView.findViewById(R.id.tv_car_brand);
            modelTv = itemView.findViewById(R.id.tv_car_model);
            priceTv = itemView.findViewById(R.id.tv_car_price);
            //descriptionTv = itemView.findViewById(R.id.tv_car_description);

        }

        public void bindCar(Car car) {
            Picasso.get().load(car.getImageUrl()).into(carImageIv);
            titleTv.setText(car.getTitle());
            brandTv.setText(car.getBrand());
            modelTv.setText(String.valueOf(car.getModel()));
            priceTv.setText(PriceConverter.convert(car.getPrice()));
            //descriptionTv.setText(car.getDescription());

            itemView.setOnClickListener(v -> {
                //pendingCarPosition = getAdapterPosition();
                Intent intent = new Intent(v.getContext(), CarDetailsActivity.class);
                intent.putExtra(CarDetailsActivity.EXTRA_KEY_CAR_ID ,car.getId());
                v.getContext().startActivity(intent);
            });

        }
    }

    /*
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCarFavorite(Car car){
        cars.set(pendingCarPosition, car);
        notifyItemChanged(pendingCarPosition);
    }
    */

}
